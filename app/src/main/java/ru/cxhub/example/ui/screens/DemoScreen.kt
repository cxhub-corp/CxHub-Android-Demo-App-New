package ru.cxhub.example.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import ru.cxhub.example.MainHolder
import ru.cxhub.example.R
import ru.cxhub.example.UserActions
import ru.cxhub.example.ui.elements.LoginField
import ru.cxhub.example.ui.elements.SimpleField
import ru.cxhub.example.ui.elements.PropertyField
import ru.cxhub.example.ui.theme.CxHubSdkDemoTheme

@Composable
fun DemoScreen(holder: MainHolder) = CxHubSdkDemoTheme {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val clipboardManager = LocalClipboardManager.current

            SimpleField(
                name = stringResource(R.string.mobile_id),
                value = holder.mobileId,
                actionName = "Copy",
                action = { clipboardManager.setText(AnnotatedString(holder.mobileId)) }
            )

            val pushToken by holder.pushTokenFlow.collectAsState("")
            SimpleField(
                name = stringResource(R.string.push_token),
                value = pushToken,
                actionName = "Copy",
                action = { clipboardManager.setText(AnnotatedString(pushToken)) }
            )

            val userId by holder.userIdFlow.collectAsState("")
            LoginField(
                name = stringResource(R.string.user_id_phone),
                value = userId,
                actionName = "Send",
                action = { holder.sendUserId(it) }
            )


            PropertyField(
                types = stringArrayResource(id = R.array.property_types).asList(),
                actionName = stringResource(R.string.send),
                action = { key, value ->
                    holder.sendUserProperty(key, value)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DemoScreenPreview() = DemoScreen(
    MainHolder(
        mobileId = "mobileId",
        userIdFlow = MutableStateFlow("userId"),
        pushTokenFlow = MutableStateFlow("pushToken"),
        actions = object : UserActions {
            override fun sendUserId(userId: String) {}
            override fun sendUserProperty(systemName: String, value: String) {}
            override fun getUserId() {}
        }
    )
)