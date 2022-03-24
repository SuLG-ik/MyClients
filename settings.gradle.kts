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
            if (requested.id.id.startsWith("com.google.gms")) {
                useModule("com.google.gms:google-services:${requested.version}")
            }
            if (requested.id.id.startsWith("com.google.firebase.crashlytics")) {
                useModule("com.google.firebase:firebase-crashlytics-gradle:${requested.version}")
            }
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "ShafranCards"
include(
    ":app",
    ":network-data",
    ":network-core",
    ":network-ktor",
    "network-mvi",
    "app-common",
    "app-components",
    "app-ui",
    "app-startup"
)