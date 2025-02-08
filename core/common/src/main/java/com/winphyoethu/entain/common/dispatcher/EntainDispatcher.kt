package com.winphyoethu.entain.common.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: EntainDispatcher)

enum class EntainDispatcher {
    IO,
    DEFAULT
}