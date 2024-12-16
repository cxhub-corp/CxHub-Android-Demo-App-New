package ru.cxhub.example.ui.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import kotlinx.coroutines.flow.MutableStateFlow
import ru.cxhub.example.MainHolder
import ru.cxhub.example.R
import ru.cxhub.example.UserActions
import ru.cxhub.example.ui.elements.LoginField
import ru.cxhub.example.ui.elements.NotificationDialog
import ru.cxhub.example.ui.elements.PropertyField
import ru.cxhub.example.ui.elements.SimpleBanner
import ru.cxhub.example.ui.elements.SimpleField
import ru.cxhub.example.ui.theme.CxHubSdkDemoTheme

fun checkNotificationPermission(context: Context): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }
    return true
}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

@Composable
fun DemoScreen(holder: MainHolder) = CxHubSdkDemoTheme {

    val context = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }

    var showBanner by remember {
        mutableStateOf(!checkNotificationPermission(context))
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        showBanner = !checkNotificationPermission(context)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        showBanner = !checkNotificationPermission(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            val clipboardManager = LocalClipboardManager.current

            SimpleBanner(showBanner, "Notifications denied!", "REQUEST") {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                    return@SimpleBanner

                val rat = context.getActivity()?.let {
                    return@let ActivityCompat.shouldShowRequestPermissionRationale(
                        it, android.Manifest.permission.POST_NOTIFICATIONS
                    )
                } ?: false

                if (!rat)
                    openAlertDialog = true
                else
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

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

    if (openAlertDialog)
        NotificationDialog({ openAlertDialog = false }) {
            openAlertDialog = false
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.setData(uri)
            startActivity(context, intent, null)
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