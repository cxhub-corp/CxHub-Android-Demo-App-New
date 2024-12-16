package ru.cxhub.example.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.cxhub.example.ui.theme.CxHubSdkDemoTheme

@Composable
fun SimpleField(name: String, value: String, modifier: Modifier = Modifier, actionName: String, action: (() -> Unit)) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column(
                modifier = Modifier
                    .weight(weight = 1.0f)
                    .alignByBaseline()
            ) {
                Text(text = name, style = MaterialTheme.typography.labelSmall)
                Text(
                    text = value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = modifier.width(16.dp))
            OutlinedButton(onClick = action) {
                Text(text = actionName)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleFieldPreview() = CxHubSdkDemoTheme {
    SimpleField("MobileId", "Empty", actionName = "Copy", action = {})
}