plugins {
    id("com.android.library") version Dependencies.Android.agpVersion
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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
    }
}

dependencies {
    implementation(Dependencies.Lifecycle.core)
    implementation(Dependencies.Activity.core)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.animation)
    implementation(Dependencies.Decompose.core)
    implementation(Dependencies.Essenty.lifecycle)
    implementation(Dependencies.Essenty.parcelable)
}