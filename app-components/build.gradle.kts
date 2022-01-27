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

}

dependencies {
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Decompose.core)
    implementation(Dependencies.MVI.coroutines)
    implementation(Dependencies.MVI.core)
    implementation(Dependencies.Koin.core)
    implementation(project(":app-common"))
    implementation(project(":network-data"))
    implementation(project(":network-core"))
    implementation(project(":network-mvi"))
}