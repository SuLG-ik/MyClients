plugins {
    id("com.android.library") version Dependencies.Android.agpVersion
    kotlin("plugin.parcelize") version Dependencies.Kotlin.version
    kotlin("android") version Dependencies.Kotlin.version
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        explicitApi()
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = false
    }

}

dependencies {
    implementation(Dependencies.Material.design)
    implementation(Dependencies.Android.core)
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Decompose.core)
    implementation(projects.networkData)
}