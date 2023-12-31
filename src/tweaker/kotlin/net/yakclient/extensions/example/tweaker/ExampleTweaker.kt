package net.yakclient.extensions.example.tweaker

import net.yakclient.archives.ArchiveReference
import net.yakclient.components.extloader.api.environment.ExtLoaderEnvironment
import net.yakclient.components.extloader.api.extension.observeNodes
import net.yakclient.components.extloader.api.tweaker.EnvironmentTweaker

class ExampleTweaker : EnvironmentTweaker {
    companion object {
        val extensionsToRegister : MutableList<ArchiveReference> = ArrayList()
    }

    override fun tweak(environment: ExtLoaderEnvironment) {
        environment.observeNodes {
            extensionsToRegister.add(it.archiveReference!!)
        }
    }
}

