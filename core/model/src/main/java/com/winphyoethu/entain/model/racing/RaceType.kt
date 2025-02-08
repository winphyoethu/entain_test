package com.winphyoethu.entain.model.racing

import androidx.compose.runtime.Stable

/**
 * External data layer representation of RaceType
 */
@Stable
data class RaceType(
    val id: String = "",
    val typeName: Int = 0,
    val typeIconId: Int? = null,
    var isSelected: Boolean = true
)

/**
 * mock race type for testing
 */
val mockRaceType = RaceType(
    id = "id",
    typeIconId = 0
)