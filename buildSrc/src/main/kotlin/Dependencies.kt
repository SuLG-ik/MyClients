object Config {

    const val compileSdk = 31
    const val minSdk = 28
    const val targetSdk = 31
    const val packageName = "ru.shafran.cards"

}

interface Dependencies {


    object Validators : Dependencies {
        const val phoneValidator = "com.googlecode.libphonenumber:libphonenumber:8.12.45"
    }

    object Material : Dependencies {
        const val version = "1.4.0"
        const val design = "com.google.android.material:material:$version"
    }

    object Kotlin : Dependencies {
        const val version = "1.6.10"
    }

    object Napier : Dependencies {
        const val version = "2.3.0"
        const val core = "io.github.aakira:napier:$version"
    }

    object Coroutines : Dependencies {
        const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val guava = "org.jetbrains.kotlinx:kotlinx-coroutines-guava:$version"
        const val tasks = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
    }

    object Serialization : Dependencies {
        const val version = "1.3.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:$version"
        const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
    }

    object MVI : Dependencies {
        const val version = "3.0.0-beta01"
        const val core = "com.arkivanov.mvikotlin:mvikotlin:$version"
        const val main = "com.arkivanov.mvikotlin:mvikotlin-main:$version"
        const val logging = "com.arkivanov.mvikotlin:mvikotlin-logging:$version"
        const val coroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$version"
    }

    object Essenty : Dependencies {
        const val version = "0.2.2"
        const val lifecycle = "com.arkivanov.essenty:lifecycle:$version"
        const val parcelable = "com.arkivanov.essenty:parcelable:$version"
    }

    object Decompose : Dependencies {
        const val version = "0.5.2"
        const val core = "com.arkivanov.decompose:decompose:$version"
        const val compose = "com.arkivanov.decompose:extensions-compose-jetpack:$version"
    }

    object Activity : Dependencies {
        const val version = "1.5.0-alpha03"
        const val core = "androidx.activity:activity-ktx:$version"
        const val compose = "androidx.activity:activity-compose:$version"
    }

    object Appcompat : Dependencies {
        const val version = "1.4.1"
        const val core = "androidx.appcompat:appcompat:$version"
    }

    object Android : Dependencies {
        const val agpVersion = "7.1.0-rc01"
        const val version = "1.8.0-alpha05"
        const val core = "androidx.core:core-ktx:$version"
        const val splash = "androidx.core:core-splashscreen:1.0.0-beta01"
    }

    object Lifecycle : Dependencies {
        const val version = "2.5.0-alpha03"
        const val core = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel:$version"
    }

    object Compose : Dependencies {
        const val compilerVersion = "1.1.1"
        const val version = "1.1.1"
        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val material3 = "androidx.compose.material3:material3:1.0.0-alpha06"
        const val animation = "androidx.compose.animation:animation:$version"
        const val foundation = "androidx.compose.foundation:foundation:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
    }

    object Koin : Dependencies {
        const val version = "3.2.0-beta-1"
        const val android = "io.insert-koin:koin-android:$version"
        const val core = "io.insert-koin:koin-core:$version"
    }

    object Accompanist : Dependencies {
        const val version = "0.23.1"
        const val swiperefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val permissions = "com.google.accompanist:accompanist-permissions:$version"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
    }

    object Ktor : Dependencies {
        const val version = "2.0.0-beta-1"
        const val core = "io.ktor:ktor-client-core:$version"
        const val android = "io.ktor:ktor-client-android:$version"
        const val cio = "io.ktor:ktor-client-cio:$version"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val logging = "io.ktor:ktor-client-logging:$version"
    }


    object Camera : Dependencies {
        const val version = "1.0.2"
        const val core = "androidx.camera:camera-core:$version"
        const val camera = "androidx.camera:camera-camera2:$version"
        const val lifecycle = "androidx.camera:camera-lifecycle:$version"
        const val view = "androidx.camera:camera-view:1.0.0-alpha28"
        const val extensions = "androidx.camera:camera-extensions:1.0.0-alpha28"
    }

    object Firebase : Dependencies {
        const val BoM = "com.google.firebase:firebase-bom:29.2.1"

        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"

        const val playServicesVersion = "4.3.10"
        const val crashlyticsVersion = "2.8.1"

    }

    object MLKit : Dependencies {
        const val version = "17.0.2"
        const val scanner = "com.google.mlkit:barcode-scanning:$version"
    }

    object ZXing {
        val version = "3.4.1"
        val core = "com.google.zxing:core:$version"
    }

    object Coil : Dependencies {
        const val version = "2.0.0-rc01"
        const val core = "io.coil-kt:coil:$version"
        const val compose = "io.coil-kt:coil-compose:$version"
    }

    object Test {
        const val juint = "junit:junit:4.13.2"
        const val android = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val compose = "androidx.compose.ui:ui-test-junit4:${Compose.version}"
    }
}