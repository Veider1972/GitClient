package ru.veider.gitclient.di

import java.util.*
import kotlin.reflect.KClass

class Di {
    private val dependenciesHolder = Hashtable<KClass<*>, Any>()

    @Suppress("UNCHECKED_CAST") fun <T : Any> get(className: KClass<T>): T {
        if (dependenciesHolder.containsKey(className)) {
            return dependenciesHolder[className] as T
        } else {
            throw IllegalArgumentException("No dep in map")
        }
    }

    fun <T : Any> add(className: KClass<T>, dependency: T) {
        dependenciesHolder[className] = dependency
    }

    fun <T : Any> add(dependency: T) {
        dependenciesHolder[dependency::class] = dependency
    }
}