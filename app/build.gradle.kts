import com.android.tools.build.bundletool.model.utils.Versions



plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.5.10"
}

val compose_version: String by rootProject.extra

val decompose_version: String by rootProject.extra
val camerax_version: String by rootProject.extra
val coroutines_version: String by rootProject.extra

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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "NETWORK_URL", "\"${System.getenv("SHAFRAN_API_HOST")}\"")
            buildConfigField("String", "NETWORK_API_VERSION", "\"0.1\"")
            buildConfigField("String", "NETWORK_API_PROTOCOL", "\"http\"")
            buildConfigField("int", "NETWORK_API_PORT", "80")
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
        kotlinCompilerExtensionVersion = compose_version
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:2.0.4")
    implementation("com.arkivanov.decompose:decompose:$decompose_version")
    implementation("com.arkivanov.decompose:extensions-compose-jetpack:$decompose_version")
    implementation("com.arkivanov.essenty:lifecycle:0.1.2")
    implementation("com.arkivanov.essenty:parcelable:0.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.17.0")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.18.0")

    implementation("io.ktor:ktor-client-core:1.6.1")
    implementation("io.ktor:ktor-client-android:1.6.1")
    implementation("io.ktor:ktor-client-serialization:1.6.1")

    implementation("com.google.zxing:core:3.4.1")

    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:1.0.0-alpha27")
    implementation("com.google.mlkit:barcode-scanning:17.0.0")

    implementation(project(":logger"))
    implementation(project(":network"))


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
}