package ru.veider.gitclient.di

class Module(private val block: Module.() -> Unit) {
    fun install() {
        block()
    }

    inline fun <reified T : Any> singleton(noinline creator: () -> T) {
        Di.add(Singleton<T>(creator))
    }

    inline fun <reified T : Any> singleton(name: String, noinline creator: () -> T) {
        Di.add(Qualifier(T::class, name), Singleton<T>(creator))
    }

    inline fun <reified T : Any> fabric(noinline creator: () -> T) {
        Di.add(Fabric<T>(creator))
    }

    inline fun <reified T : Any> fabric(name: String, noinline creator: () -> T) {
        Di.add(Qualifier(T::class, name), Fabric<T>(creator))
    }
}