package com.winphyoethu.entain.model.racing

import androidx.compose.runtime.Stable

/**
 * External data layer representation of RaceInfo
 */
@Stable
data class RaceInfo (
    val raceId: String,
    val raceNumber: Int,
    val meetingName: String,
    val startTime: Long,
    val timeToShow: String
)

@Stable
data class RaceInfoSection(
    val raceType: RaceType,
    val raceInfoList: List<RaceInfo>
)

/**
 * mock race information for testing
 */
val mockRaceInfo = RaceInfo(
    raceId = "race-id",
    raceNumber = 1,
    meetingName = "NSW",
    startTime = 10000L,
    timeToShow = "10s"
)

/**
 * mock race information section for testing
 */
val mockRaceInfoSection = RaceInfoSection(
    raceType = mockRaceType,
    raceInfoList = listOf(mockRaceInfo)
)