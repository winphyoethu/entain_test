package com.winphyoethu.entain.designsystem.entaincomponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winphyoethu.entain.designsystem.R
import com.winphyoethu.entain.designsystem.basiccomponent.EntainSubTitle
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import com.winphyoethu.entain.designsystem.ui.theme.largeDp
import com.winphyoethu.entain.designsystem.ui.theme.mediumDp
import com.winphyoethu.entain.model.racing.RaceInfo
import com.winphyoethu.entain.model.racing.RaceType
import com.winphyoethu.entain.model.racing.mockRaceType

/**
 * [RaceInfo] card used on the following screen: Racing
 */
@Composable
fun RaceTypeSelect(raceType: RaceType, onClick: (raceType: RaceType) -> Unit) {
    val animate by
    animateColorAsState(
        if (raceType.isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        label = "Race Select Animation",
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    Card(
        shape = RoundedCornerShape(largeDp),
        border = BorderStroke(2.dp, animate),
        onClick = { onClick(raceType) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = mediumDp, end = mediumDp)
        ) {
            raceType.typeIconId?.let {
                Image(
                    painterResource(it),
                    contentDescription = stringResource(raceType.typeName),
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
                )
            }
            EntainSubTitle(
                subtitle = stringResource(raceType.typeName),
                modifier = Modifier.padding(mediumDp)
            )
        }
    }
}

@Preview
@Composable
internal fun RaceTypePreview() {
    EntainTheme(dynamicColor = false) {
        RaceTypeSelect(raceType = mockRaceType.copy(typeIconId = R.drawable.greyhound)) {

        }
    }
}