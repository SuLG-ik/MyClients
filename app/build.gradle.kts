plugins {
    id("com.android.application") version Dependencies.Android.agpVersion
    kotlin("android") version Dependencies.Kotlin.version
    kotlin("plugin.parcelize") version Dependencies.Kotlin.version
    kotlin("plugin.serialization") version Dependencies.Kotlin.version
    id("com.google.firebase.crashlytics") version Dependencies.Firebase.crashlyticsVersion
    id("com.google.gms.google-services") version Dependencies.Firebase.playServicesVersion
}


android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("D:\\shafran\\apk-key\\shafran.jks")
            storePassword = "snpn!8@%Jx&TY))g"
            keyAlias = "Shafran"
            keyPassword = "snpn!8@%Jx&TY))g"
        }
    }
    compileSdk = Config.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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