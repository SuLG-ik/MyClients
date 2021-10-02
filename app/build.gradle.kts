import com.android.tools.build.bundletool.model.utils.Versions


plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.5.30"
}

val composeVersion: String by rootProject.extra
val decomposeVersion: String by rootProject.extra
val cameraxVersion: String by rootProject.extra
val coroutinesVersion: String by rootProject.extra

android {
    compileSdk = Versions.ANDROID_R_API_VERSION
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "ru.shafran.cards"
        minSdk = Versions.ANDROID_P_API_VERSION
        targetSdk = Versions.ANDROID_R_API_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            buildConfigField(
                "String",
                "NETWORK_URL",
                "\"${System.getenv("SHAFRAN_API_HOST_RELEASE")}\""
            )
            buildConfigField(
                "String",
                "NETWORK_API_VERSION",
                "\"${System.getenv("SHAFRAN_API_VERSION_RELEASE")}\""
            )
            buildConfigField(
                "String",
                "NETWORK_API_PROTOCOL",
                "\"http\""
            )
            buildConfigField(
                "int",
                "NETWORK_API_PORT",
                System.getenv("SHAFRAN_API_PORT_RELEASE"),
            )
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            buildConfigField(
                "String",
                "NETWORK_URL",
                "\"${System.getenv("SHAFRAN_API_HOST_DEBUG")}\""
            )
            buildConfigField(
                "String",
                "NETWORK_API_VERSION",
                "\"${System.getenv("SHAFRAN_API_VERSION_DEBUG")}\""
            )
            buildConfigField(
                "String",
                "NETWORK_API_PROTOCOL",
                "\"http\""
            )
            buildConfigField(
                "int",
                "NETWORK_API_PORT",
                System.getenv("SHAFRAN_API_PORT_DEBUG"),
            )
        }
    }
    buildFeatures {
        compose = true
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=org.mylibrary.OptInAnnotation"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}

dependencies {
    implementation("com.google.android.material:material:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation("com.arkivanov.mvikotlin:mvikotlin:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:2.0.4")

    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetpack:$decomposeVersion")
    implementation("com.arkivanov.essenty:lifecycle:0.1.2")
    implementation("com.arkivanov.essenty:parcelable:0.1.2")

    implementation("io.insert-koin:koin-android:3.1.2")

    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.19.0")
    implementation("com.google.accompanist:accompanist-permissions:0.19.0")


    implementation("io.ktor:ktor-client-core:1.6.1")
    implementation("io.ktor:ktor-client-android:1.6.1")
    implementation("io.ktor:ktor-client-serialization:1.6.1")

    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:1.0.0-alpha28")
    implementation("com.google.mlkit:barcode-scanning:17.0.0")

    implementation(project(":network"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}