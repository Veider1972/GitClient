package ru.veider.gitclient.di

class Module(private val block: Module.() -> Unit) {

    fun install() {
        block()
    }

    inline fun <reified T : Any> singleton(noinline creator: () -> T) {
        Di.add(Singleton<T>(creator))
    }

    inline fun <reified T : Any> fabric(noinline creator: () -> T) {
        Di.add(Fabric<T>(creator))
    }
}