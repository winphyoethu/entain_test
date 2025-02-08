package com.winphyoethu.entain.designsystem.basiccomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.winphyoethu.entain.designsystem.ui.theme.AppTypography
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import com.winphyoethu.entain.designsystem.ui.theme.smallDp

/**
 * Text component for Extra large title with text size 36 and bold
 */
@Composable
fun EntainXLTitle(
    modifier: Modifier = Modifier,
    title: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = title,
        style = AppTypography.displaySmall.plus(TextStyle(fontWeight = FontWeight.Bold)),
        maxLines = 1,
        modifier = modifier,
        textAlign = textAlign
    )
}

/**
 * Text component for title with text size 24 and bold
 */
@Composable
fun EntainTitle(
    modifier: Modifier = Modifier,
    title: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = title,
        style = AppTypography.headlineSmall.plus(TextStyle(fontWeight = FontWeight.Bold)),
        modifier = modifier,
        textAlign = textAlign
    )
}

/**
 * Text component for Subtitle with text size 16 and bold
 */
@Composable
fun EntainSubTitle(
    modifier: Modifier = Modifier,
    subtitle: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = subtitle,
        style = AppTypography.titleMedium.plus(TextStyle(fontWeight = FontWeight.Bold)),
        modifier = modifier,
        textAlign = textAlign
    )
}

/**
 * Text component for Body with text size 14
 */
@Composable
fun EntainBody(
    modifier: Modifier = Modifier,
    body: String,
    textAlign: TextAlign? = null

) {
    Text(
        text = body,
        style = AppTypography.labelLarge,
        modifier = modifier,
        textAlign = textAlign
    )
}

/**
 * Text component for Label with text size 11
 */
@Composable
fun EntainLabel(
    modifier: Modifier = Modifier,
    label: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = label,
        style = AppTypography.labelSmall,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Preview
@Composable
internal fun EntainTextPreview() {
    EntainTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.spacedBy(smallDp)) {
                Text("Entain Text", style = AppTypography.displaySmall)
                EntainXLTitle(title = "XL Title - 36.sp")
                EntainTitle(title = "Title - 24.sp")
                EntainSubTitle(subtitle = "Sub Title - 16.sp")
                EntainBody(body = "Body - 14.sp")
                EntainLabel(label = "Label - 11.sp")
            }
        }
    }
}