package net.yakclient.extensions.example

val bootstrapCallbacks = ArrayList<() -> Unit>()
fun onBootStrap(callback: () -> Unit) {
    bootstrapCallbacks.add(callback)
}