@file:DefineFeatures

package dev.extframework.extensions.example

import dev.extframework.core.api.feature.DefineFeatures
import dev.extframework.core.api.feature.Feature
import dev.extframework.core.api.feature.FeatureImplementationException

@Feature
fun asdf(int: Int?) : Unit = throw FeatureImplementationException()

@Feature
fun registerBlock(): Unit = throw FeatureImplementationException()


