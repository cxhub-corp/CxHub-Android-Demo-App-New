package ru.cxhub.example.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.cxhub.example.R
import ru.cxhub.example.ui.theme.CxHubSdkDemoTheme

@Composable
fun LoginField(name: String, value: String, modifier: Modifier = Modifier, actionName: String, action: ((String) -> Unit)) {
    var result by remember(value) { mutableStateOf(value) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = modifier.padding(start = 16.dp, end = 12.dp, top = 8.dp, bottom = 16.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = result,
                onValueChange = { result = it },
                label = { Text(name, style = MaterialTheme.typography.labelSmall) },
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent)
            )

            Spacer(modifier = modifier.width(16.dp))

            OutlinedButton(
                onClick = { action(result) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = actionName)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun EditFieldPreview() = CxHubSdkDemoTheme {
    LoginField(
        stringResource(R.string.user_id_phone),
        stringResource(R.string.empty),
        actionName = stringResource(R.string.copy),
        action = {}
    )
}