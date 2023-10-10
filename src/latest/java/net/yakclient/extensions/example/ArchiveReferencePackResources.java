package net.yakclient.extensions.example;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.yakclient.archives.ArchiveReference;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ArchiveReferencePackResources extends AbstractPackResources {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Splitter RESOURCE_PATH_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
    private final ArchiveReference archiveReference;

    public ArchiveReferencePackResources(String packName, ArchiveReference archiveReference, boolean isOptional) {
        super(packName, isOptional);
        this.archiveReference = archiveReference;
    }

    private ArchiveReference.Reader getOrCreateArchiveReader() {
        return this.archiveReference.getReader();
    }

    private IoSupplier<InputStream> fromEntry(ArchiveReference.Entry entry) {
        return () -> entry.getResource().open();
    }

    private static String getResourcePathFromLocation(PackType packType, ResourceLocation resourceLocation) {
        return String.format(Locale.ROOT, "%s/%s/%s", packType.getDirectory(), resourceLocation.getNamespace(), resourceLocation.getPath());
    }

    @Nullable
    public IoSupplier<InputStream> getRootResource(String... resourcePathParts) {
        return this.getResource(String.join("/", resourcePathParts));
    }

    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation resourceLocation) {
        return this.getResource(getResourcePathFromLocation(packType, resourceLocation));
    }

    @Nullable
    private IoSupplier<InputStream> getResource(String resourcePath) {
        ArchiveReference.Reader archiveReader = this.getOrCreateArchiveReader();
        if (archiveReader == null) {
            return null;
        } else {
            ArchiveReference.Entry archiveEntry = archiveReader.of(resourcePath);
            if (archiveEntry == null) {
                return null;
            }
            return fromEntry(archiveEntry);
        }
    }

    public Set<String> getNamespaces(PackType packType) {
        ArchiveReference.Reader archiveReader = this.getOrCreateArchiveReader();
        if (archiveReader == null) {
            return Set.of();
        } else {
            Set<String> namespaces = new HashSet<>();

            for (Iterator<ArchiveReference.Entry> it = archiveReader.entries().iterator(); it.hasNext(); ) {
                ArchiveReference.Entry entry = it.next();
                String entryName = entry.getName();
                if (entryName.startsWith(packType.getDirectory() + "/")) {
                    List<String> parts = Lists.newArrayList(RESOURCE_PATH_SPLITTER.split(entryName));
                    if (parts.size() > 1) {
                        String namespace = parts.get(1);
                        if (namespace.equals(namespace.toLowerCase(Locale.ROOT))) {
                            namespaces.add(namespace);
                        } else {
                            LOGGER.warn("Ignored non-lowercase namespace: {} in {}", namespace, this.archiveReference.getLocation());
                        }
                    }
                }
            }

            return namespaces;
        }
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void close() {
    }

    public void listResources(PackType packType, String namespace, String subdirectory, PackResources.ResourceOutput resourceOutput) {
        ArchiveReference.Reader archiveReader = this.getOrCreateArchiveReader();
        if (archiveReader != null) {
            String packDirectory = packType.getDirectory();
            String namespaceDirectory = packDirectory + "/" + namespace + "/";
            String subdirectoryPath = namespaceDirectory + subdirectory + "/";

            for (Iterator<ArchiveReference.Entry> it = archiveReader.entries().iterator(); it.hasNext(); ) {
                ArchiveReference.Entry entry = it.next();
                if (!entry.isDirectory()) {
                    String entryName = entry.getName();
                    if (entryName.startsWith(subdirectoryPath)) {
                        String resourcePath = entryName.substring(namespaceDirectory.length());
                        ResourceLocation resourceLocation = ResourceLocation.tryBuild(namespace, resourcePath);
                        if (resourceLocation != null) {
                            resourceOutput.accept(resourceLocation, fromEntry(entry));
                        } else {
                            LOGGER.warn("Invalid path in datapack: {}:{}, ignoring", namespace, resourcePath);
                        }
                    }
                }
            }
        }
    }
}
