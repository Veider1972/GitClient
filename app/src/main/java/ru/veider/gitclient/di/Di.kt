package ru.veider.gitclient.di

import java.util.*
import kotlin.reflect.KClass

object Di {
    private val dependenciesHolder = Hashtable<Qualifier, DependencyFabric<*>>()

    fun <T : Any> get(qualifier: Qualifier): T {
        val dependencyFabric = dependenciesHolder[qualifier]
        if (dependencyFabric != null) {
            return dependencyFabric.get() as T
        } else {
            throw IllegalArgumentException("No dep in map")
        }
    }

    fun <T : Any> add(qualifier: Qualifier, dependencyFabric: DependencyFabric<T>) {
        if (dependenciesHolder.containsKey(qualifier)) {
            throw IllegalStateException("Dep is in graph already")
        }
        dependenciesHolder[qualifier] = dependencyFabric
    }

    inline fun <reified T : Any> add(dependencyFabric: DependencyFabric<T>) {
        add(Qualifier(T::class), dependencyFabric)
    }
}

data class Qualifier(
    private val clazz: KClass<*>,
    private val name: String = "default_name"
)

inline fun <reified T : Any> get(): T {
    return Di.get(Qualifier(T::class))
}

inline fun <reified T : Any> get(name: String): T {
    return Di.get(Qualifier(T::class, name))
}

inline fun <reified T : Any> inject() = lazy {
    get<T>()
}

inline fun <reified T : Any> inject(name: String) = lazy {
    get<T>(name)
}

abstract class DependencyFabric<T : Any>(protected val creator: () -> Any) {
    abstract fun get(): Any
}

class Singleton<T : Any>(creator: () -> Any) : DependencyFabric<T>(creator) {
    private val dependency: Any by lazy { creator.invoke() }

    override fun get(): Any = dependency
}

class Fabric<T : Any>(creator: () -> Any) : DependencyFabric<T>(creator) {
    override fun get(): Any = creator()
}