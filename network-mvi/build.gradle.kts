plugins {
    id("com.android.library") version Dependencies.Android.agpVersion
    kotlin("android") version Dependencies.Kotlin.version
    kotlin("plugin.serialization") version Dependencies.Kotlin.version
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin{
        explicitApi()
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = false
    }
}


dependencies{
    implementation(Dependencies.MVI.core)
    implementation(Dependencies.MVI.coroutines)
    implementation(Dependencies.Essenty.parcelable)
    implementation(Dependencies.Essenty.lifecycle)
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Napier.core)
    implementation(projects.networkData)
    implementation(projects.networkCore)
    implementation(projects.networkKtor)
}