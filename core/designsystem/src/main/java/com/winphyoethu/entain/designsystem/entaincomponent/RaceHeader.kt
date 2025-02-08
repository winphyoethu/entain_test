package com.winphyoethu.entain.designsystem.entaincomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.winphyoethu.entain.designsystem.R
import com.winphyoethu.entain.designsystem.basiccomponent.EntainSubTitle
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import com.winphyoethu.entain.designsystem.ui.theme.largeDp
import com.winphyoethu.entain.designsystem.ui.theme.mediumDp
import com.winphyoethu.entain.designsystem.ui.theme.xLargeDp
import com.winphyoethu.entain.model.racing.RaceType
import com.winphyoethu.entain.model.racing.mockRaceType

/**
 * [RaceType] header used on the following screen: Racing
 */
@Composable
fun RaceHeader(raceType: RaceType) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = largeDp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        raceType.typeIconId?.let {
            Image(
                painterResource(it),
                contentDescription = stringResource(raceType.typeName),
                modifier = Modifier
                    .size(xLargeDp),
                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
            )
        }
        EntainSubTitle(
            subtitle = stringResource(raceType.typeName),
            modifier = Modifier.padding(mediumDp)
        )
    }
}

@Preview
@Composable
fun RaceHeaderPreview() {
    EntainTheme {
        RaceHeader(mockRaceType.copy(typeIconId = R.drawable.greyhound))
    }
}