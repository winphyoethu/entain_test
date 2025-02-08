package com.winphyoethu.entain.common.result

/**
 * Base Result Class
 */
sealed class EntainResult<out T> {

    /**
     * Generic Success Result Class
     */
    data class Success<out T>(val data: T) : EntainResult<T>()

    /**
     * Generic Error Result Class
     */
    data class Error<out T>(val e: ErrorCode): EntainResult<T>()

}