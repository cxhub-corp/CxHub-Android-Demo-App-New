package ru.cxhub.example

import android.app.Application
import android.util.Log
import core.api.BackgroundAwakeMode
import core.api.NetworkSyncMode
import cxhub.api.FirebasePlatformManager
import cxhub.api.NotificationApi
import cxhub.api.NotificationFactory

class App : Application() {
    val notificationApi: NotificationApi
        get() = NotificationFactory.get(this)

    override fun onCreate() {
        super.onCreate()

        // minimum init
        NotificationFactory.setPlatformManagers(FirebasePlatformManager.getInstance())
        NotificationFactory.initialize(this)

        //advanced
        NotificationFactory.setPushListener { eventType, pushId, message ->
            Log.i("App", "CxHubPushListener $eventType $pushId" + "\nobj ${message?.obj}")
        }

        NotificationFactory.setBackgroundAwakeMode(BackgroundAwakeMode.DEFAULT)
        NotificationFactory.setNetworkSyncMode(NetworkSyncMode.DEFAULT)

        // start
        NotificationFactory.bootstrap(this)

        //custom event
        NotificationFactory.get(this).collectEvent("AppCreate")
    }
}