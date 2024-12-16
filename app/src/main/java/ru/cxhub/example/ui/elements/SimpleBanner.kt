package ru.cxhub.example.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.cxhub.example.ui.theme.CxHubSdkDemoTheme

@Composable
fun SimpleBanner(showBanner: Boolean, text: String, actionName: String, action: (() -> Unit)) {
    if (showBanner)
        Row(modifier = Modifier
            .background(color = Color(0xFF, 0xDD, 0xDD))
        ) {
            Text(
                text,
                modifier = Modifier
                    .padding(start = 24.dp, top = 12.dp)
                    .weight(1.0f, true)
            )

            TextButton(
                modifier = Modifier.padding(end = 24.dp),
                onClick = action,
            ) {
                Text(text = actionName)
            }
        }
}

@Preview(showBackground = true)
@Composable
fun SimpleBannerPreview() = CxHubSdkDemoTheme {
    SimpleBanner(true, "Notifications denied!", actionName = "REQUEST", action = {})
}