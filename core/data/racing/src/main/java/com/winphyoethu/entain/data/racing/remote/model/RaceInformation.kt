package com.winphyoethu.entain.data.racing.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RaceInfoList(
    @Json(name = "next_to_go_ids")
    val nextToGoIds: List<String>,
    @Json(name = "race_summaries")
    val raceSummaries: Map<String, RaceInfo>
)

@JsonClass(generateAdapter = true)
data class RaceInfo(
    @Json(name = "race_id")
    val raceId: String,
    @Json(name = "race_name")
    val raceName: String,
    @Json(name = "race_number")
    val raceNumber: Int,
    @Json(name = "meeting_id")
    val meetingId: String,
    @Json(name = "meeting_name")
    val meetingName: String,
    @Json(name = "category_id")
    val categoryId: String,
    @Json(name = "advertised_start")
    val advertisedStart: AdvertisedStart,
    @Json(name = "race_form")
    val raceForm: RaceForm?,
    @Json(name = "venue_id")
    val venueId: String?,
    @Json(name = "venue_name")
    val venueName: String?,
    @Json(name = "venue_state")
    val venueState: String?,
    @Json(name = "venue_country")
    val venueCountry: String?
)

@JsonClass(generateAdapter = true)
data class AdvertisedStart(
    @Json(name = "seconds")
    val seconds: Long
)

@JsonClass(generateAdapter = true)
data class RaceForm(
    @Json(name = "distance")
    val distance: Int,
    @Json(name = "distance_type")
    val distanceType: DistanceType,
    @Json(name = "distance_type_id")
    val distanceTypeId: String,
    @Json(name = "track_condition")
    val trackCondition: TrackCondition?,
    @Json(name = "track_condition_id")
    val trackConditionId: String?,
    @Json(name = "weather")
    val weather: Weather?,
    @Json(name = "weather_id")
    val weatherId: String?,
    @Json(name = "race_comment")
    val raceComment: String?,
    @Json(name = "additional_data")
    val additionalData: String?,
    @Json(name = "generated")
    val generated: Int,
    @Json(name = "silk_base_url")
    val silkBaseUrl: String,
    @Json(name = "race_comment_alternative")
    val raceCommentAlternative: String?
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "short_name")
    val shortName: String,
    @Json(name = "icon_uri")
    val iconUri: String
)

@JsonClass(generateAdapter = true)
data class TrackCondition(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "short_name")
    val shortName: String
)

@JsonClass(generateAdapter = true)
data class DistanceType(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "short_name")
    val shortName: String
)

internal val mockRaceInfoList: RaceInfoList
    get() = RaceInfoList(
        nextToGoIds = listOf("1"),
        raceSummaries = mapOf(
            Pair("1", mockRaceInfo),
//            Pair("2", mockRaceInfo.copy(raceId = "2", raceName = "QLD", raceNumber = 2)),
//            Pair("3", mockRaceInfo.copy(raceId = "3", raceName = "VCT", raceNumber = 2)),
        )
    )

internal val mockRaceInfo = RaceInfo(
    raceId = "1",
    raceName = "NSW",
    raceNumber = 1,
    meetingId = "meetingId",
    meetingName = "meetingName",
    categoryId = "categoryId",
    raceForm = null,
    venueId = null,
    venueName = null,
    venueState = null,
    venueCountry = null,
    advertisedStart = AdvertisedStart(1000)
)