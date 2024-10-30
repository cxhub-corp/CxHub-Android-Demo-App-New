pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
        maven("https://developer.huawei.com/repo/")
    }
/*
    resolutionStrategy.eachPlugin {
        when (requested.id.id) {
            "com.huawei.agconnect" -> useModule("com.huawei.agconnect:agcp:1.9.1.303")
        }
    }*/
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://developer.huawei.com/repo/")
        maven ("https://artifactory-external.vkpartner.ru/artifactory/maven")
    }
}

rootProject.name = "CxHubSdkAndroidDemo"
include(":app")
 