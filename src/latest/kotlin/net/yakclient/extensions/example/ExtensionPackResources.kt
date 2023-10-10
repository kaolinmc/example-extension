package net.yakclient.extensions.example

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.MetadataSectionSerializer
import net.minecraft.server.packs.resources.IoSupplier
import java.io.File
import java.io.InputStream
import java.util.function.Supplier

class ExtensionPackResources(
    val namespace: String,
    val resources: Map<String, IoSupplier<InputStream>>
) : PackResources {
    override fun close() {

    }

    private fun supplierOfPath(path: String): IoSupplier<InputStream>? = resources[path]

    override fun getRootResource(vararg p0: String): IoSupplier<InputStream>? {
        return supplierOfPath(p0.joinToString(separator = File.separator))
    }

    override fun getResource(p0: PackType, p1: ResourceLocation): IoSupplier<InputStream>? {
       return supplierOfPath(
            "${p0.directory}/${p1.namespace}/${p1.path}"
        )
    }

    override fun listResources(p0: PackType, p1: String, p2: String, p3: PackResources.ResourceOutput) {
        resources
            .filter { it.key.startsWith("${p0.directory}/${p1}${p2}") }
            .forEach { (key, it) ->
                val location = ResourceLocation(namespace, key.removePrefix("${p0.directory}/"))
                p3.accept(location, it)
        }
    }

    override fun getNamespaces(p0: PackType): MutableSet<String> {
        return mutableSetOf(namespace)
    }

    override fun <T : Any?> getMetadataSection(p0: MetadataSectionSerializer<T>): T? {
       return null
    }

    override fun packId(): String {
        return namespace
    }
}
