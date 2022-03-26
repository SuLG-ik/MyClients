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
    implementation(Dependencies.Napier.core)
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.guava)
    implementation(Dependencies.Coroutines.tasks)

    implementation(Dependencies.Decompose.core)
    implementation(Dependencies.Decompose.compose)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.foundation)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.material)
    compileOnly(Dependencies.Compose.tooling)

    implementation(Dependencies.Accompanist.swiperefresh)
    implementation(Dependencies.Accompanist.permissions)
    implementation(Dependencies.Accompanist.placeholder)

    implementation(Dependencies.Coil.core)
    implementation(Dependencies.Coil.compose)

    implementation(Dependencies.Camera.core)
    implementation(Dependencies.Camera.camera)
    implementation(Dependencies.Camera.lifecycle)
    implementation(Dependencies.Camera.view)
    implementation(Dependencies.Camera.extensions)

    implementation(Dependencies.MLKit.scanner)
    implementation(Dependencies.ZXing.core)
    implementation(projects.appCommon)
    implementation(projects.networkData)
}