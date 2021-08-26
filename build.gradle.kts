buildscript {
    val compose_version by extra("1.0.0")
    val decompose_version by extra("0.3.1")
    val camerax_version by extra("1.0.1")
    val coroutines_version by extra("1.5.1")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}