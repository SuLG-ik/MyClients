plugins {
    id("com.android.library")  version Dependencies.Android.agpVersion
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
            buildConfigField(
                "String",
                "NETWORK_API_URL",
                "\"${System.getenv("SHAFRAN_API_HOST")}\""
            )
            buildConfigField(
                "String",
                "NETWORK_API_VERSION",
                "\"${System.getenv("SHAFRAN_API_VERSION")}\""
            )
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField(
                "String",
                "NETWORK_API_URL",
                "\"${System.getenv("SHAFRAN_API_HOST_DEBUG")}\""
            )
            buildConfigField(
                "String",
                "NETWORK_API_VERSION",
                "\"${System.getenv("SHAFRAN_API_VERSION_DEBUG")}\""
            )
        }
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

    kotlin{
        explicitApi()
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
    }
}

dependencies {
    implementation(Dependencies.Napier.core)
    implementation(Dependencies.Appcompat.core)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Decompose.core)
    implementation(Dependencies.MVI.core)
    implementation(Dependencies.MVI.main)
    implementation(Dependencies.MVI.logging)
    implementation(Dependencies.Ktor.core)
    implementation(Dependencies.Ktor.serialization)
    implementation(Dependencies.Ktor.cio)
    implementation(Dependencies.Activity.compose)
    implementation(Dependencies.Compose.ui)

    implementation(project(":app-common"))
    implementation(project(":app-components"))
    implementation(project(":app-ui"))
    implementation(project(":network-data"))
    implementation(project(":network-mvi"))
    implementation(project(":decompose"))
}