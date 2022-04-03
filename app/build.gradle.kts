plugins {
    id("com.android.application") version Dependencies.Android.agpVersion
    kotlin("android") version Dependencies.Kotlin.version
    kotlin("plugin.parcelize") version Dependencies.Kotlin.version
    kotlin("plugin.serialization") version Dependencies.Kotlin.version
    id("com.google.firebase.crashlytics") version Dependencies.Firebase.crashlyticsVersion
    id("com.google.gms.google-services") version Dependencies.Firebase.playServicesVersion
}


android {
    compileSdk = Config.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 8
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            extra["enableCrashlytics"] = false
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
    }
}

dependencies {
    implementation(platform(Dependencies.Firebase.BoM))
    implementation(Dependencies.Firebase.crashlytics)
    implementation(Dependencies.Firebase.analytics)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Material.design)
    implementation(Dependencies.Android.splash)
    implementation(projects.appStartup)
}
