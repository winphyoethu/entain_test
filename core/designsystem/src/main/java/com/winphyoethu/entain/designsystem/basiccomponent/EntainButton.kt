package com.winphyoethu.entain.designsystem.basiccomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import com.winphyoethu.entain.designsystem.ui.theme.mediumDp
import com.winphyoethu.entain.designsystem.ui.theme.smallDp

enum class IconPosition {
    LEADING,
    TRAILING
}

/**
 * Button designed for Entain app
 */
@Composable
fun EntainButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPosition: IconPosition = IconPosition.LEADING,
    icon: ImageVector? = null,
    iconDescription: String? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(onClick = onClick, enabled = isEnabled, modifier = modifier) {
        EntainButtonContent(text, iconPosition, icon, iconDescription)
    }
}

/**
 * Outlined Button designed for Entain app
 */
@Composable
fun EntainOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPosition: IconPosition = IconPosition.LEADING,
    icon: ImageVector? = null,
    iconDescription: String? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(onClick = onClick, enabled = isEnabled, modifier = modifier) {
        EntainButtonContent(text, iconPosition, icon, iconDescription)
    }
}

/**
 * Button content for primary button and outlined button
 */
@Composable
private fun EntainButtonContent(
    text: String,
    iconPosition: IconPosition = IconPosition.LEADING,
    icon: ImageVector? = null,
    iconDescription: String? = null,
) {
    if (icon == null) {
        EntainBody(
            body = text,
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(smallDp)
        ) {
            if (iconPosition == IconPosition.LEADING) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconDescription,
                    modifier = Modifier.size(20.dp)
                )
                EntainBody(
                    body = text,
//                    color = if (isOutlined) Color.Unspecified else MaterialTheme.colorScheme.onPrimary
                )
            } else {
                EntainBody(
                    body = text,
//                    color = if (isOutlined) Color.Unspecified else MaterialTheme.colorScheme.onPrimary
                )
                Icon(imageVector = icon, contentDescription = iconDescription)
            }
        }
    }
}

@Preview
@Composable
fun EntainButtonPreview() {
    EntainTheme(dynamicColor = false) {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(mediumDp)) {
                Text(text = "Enabled Buttons")

                EntainButton(text = "Primary Button") { }
                EntainButton(text = "Primary Button", icon = Icons.Default.Add) { }
                EntainOutlinedButton(text = "Outlined Button") { }
                EntainOutlinedButton(
                    text = "Outlined Button",
                    icon = Icons.Default.Add,
                    iconPosition = IconPosition.TRAILING
                ) { }

                Text(text = "Disabled Buttons")

                EntainButton(text = "Primary Button", isEnabled = false) { }
                EntainButton(
                    text = "Primary Button",
                    icon = Icons.Default.Add,
                    isEnabled = false
                ) { }
                EntainOutlinedButton(text = "Outlined Button", isEnabled = false) { }
                EntainOutlinedButton(
                    text = "Outlined Button",
                    icon = Icons.Default.Add,
                    iconPosition = IconPosition.TRAILING
                    , isEnabled = false
                ) { }
            }
        }
    }
}