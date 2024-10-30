package ru.cxhub.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import cxhub.api.NotificationApi
import cxhub.api.UserProperty
import cxhub.api.UserPropertyApi
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(private val api: NotificationApi) : ViewModel(), UserActions {
    val holder = MainHolder(api.mobileInstance, MutableStateFlow(""), MutableStateFlow(""), this)

    private val listener = NotificationApi.PushTokenListener { holder.pushTokenFlow.value = it ?: "" }

    fun init() {
        api.subscribeToPushToken(listener)
        getUserId()
    }

    fun dispose() = api.unsubscribeToPushToken(listener)

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val app = checkNotNull(extras[APPLICATION_KEY]) as App
                return MainViewModel(app.notificationApi) as T
            }
        }
    }

    // choose your user unique id property from https://[your_project_name].cxhub.ru/settings/data-manager/clients
    // and see your changes in https://[your_project].cxhub.ru/base/clients
    override fun sendUserId(userId: String) = api.setUserId("Phone", userId, true)

    // also see full list in https://[your_project_name].cxhub.ru/settings/data-manager/clients
    // and see your changes in https://[your_project].cxhub.ru/base/clients
    override fun sendUserProperty(systemName: String, value: String) = api.setUserProperty(
        UserProperty(systemName, value),
        object : UserPropertyApi.OnResultListener {
            override fun onSuccess() {
                Log.d("MainViewModel", "user property sent successfully")
            }

            override fun onFailed(t: Throwable?) {
                Log.e("MainViewModel", "user property didn't sent", t)
            }
        }
    )

    override fun getUserId() {
        api.getLastUserId { _, v ->
            Log.d("MainViewModel", "last user id: $v")
            holder.userIdFlow.value = v ?: ""
        }
    }
}

interface UserActions {
    fun sendUserId(userId: String)
    fun sendUserProperty(systemName: String, value: String)
    fun getUserId()
}

class MainHolder(
    val mobileId: String,
    val userIdFlow: MutableStateFlow<String>,
    val pushTokenFlow: MutableStateFlow<String>,
    actions: UserActions
) : UserActions by actions


