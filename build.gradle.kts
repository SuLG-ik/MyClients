buildscript {
    val composeVersion by extra("1.0.3")
    val decomposeVersion by extra("0.3.1")
    val cameraxVersion by extra("1.0.1")
    val coroutinesVersion by extra("1.5.1")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}