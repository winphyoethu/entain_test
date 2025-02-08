package com.winphyoethu.entain.designsystem.entaincomponent

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winphyoethu.entain.designsystem.basiccomponent.EntainBody
import com.winphyoethu.entain.designsystem.basiccomponent.EntainLabel
import com.winphyoethu.entain.designsystem.basiccomponent.EntainSubTitle
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import com.winphyoethu.entain.designsystem.ui.theme.largeDp
import com.winphyoethu.entain.designsystem.ui.theme.mediumDp
import com.winphyoethu.entain.designsystem.ui.theme.smallDp
import com.winphyoethu.entain.model.racing.RaceInfo
import com.winphyoethu.entain.model.racing.mockRaceInfo

/**
 * [RaceInfo] card used on the following screen: Racing
 */
@Composable
fun RaceCard(raceInfo: RaceInfo) {
    Card(
        shape = RoundedCornerShape(largeDp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = smallDp, end = smallDp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(largeDp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EntainSubTitle(subtitle = raceInfo.meetingName)
                EntainLabel(
                    label = "R${raceInfo.raceNumber}",
                    modifier = Modifier
                        .padding(start = mediumDp)
                        .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                        .padding(smallDp)
                )
            }
            EntainBody(body = raceInfo.timeToShow, modifier = Modifier.align(Alignment.CenterEnd))
        }
    }
}

@Composable
@Preview
internal fun RaceCardPreview() {
    EntainTheme(dynamicColor = false) {
        RaceCard(mockRaceInfo)
    }
}