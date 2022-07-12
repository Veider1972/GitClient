package ru.veider.gitclient.di

import java.util.*
import kotlin.reflect.KClass

object Di {
    private val dependenciesHolder = Hashtable<KClass<*>, DependencyFabric<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(className: KClass<T>): T {
        val dependencyFabric = dependenciesHolder[className]
        if (dependencyFabric != null) {
            return dependencyFabric.get() as T
        } else {
            throw IllegalArgumentException("No dep in map")
        }
    }

    fun <T : Any> add(className: KClass<T>, dependencyFabric: DependencyFabric<T>) {
        dependenciesHolder[className] = dependencyFabric
    }

    inline fun <reified T : Any> add(dependencyFabric: DependencyFabric<T>) {
        add(T::class, dependencyFabric)
    }
}

inline fun <reified T : Any> get(): T {
    return Di.get(T::class)
}

inline fun <reified T : Any> inject() = lazy {
    get<T>()
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