package com.winphyoethu.entain.network.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Generic Base Model for Api Responses
 */
@JsonClass(generateAdapter = true)
data class BaseModel<T>(
    @field:Json(name = "status")
    val status: Int,
    @field:Json(name = "data")
    val data: T?
)