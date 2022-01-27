dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
}

rootProject.name = "ShafranCards"
include(
    ":app",
    ":network-data",
    ":network-core",
    ":network-ktor",
    "network-mvi",
    ":decompose",
    "app-common",
    "app-components",
    "app-ui",
    "app-startup"
)