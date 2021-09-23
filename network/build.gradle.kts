plugins {
    id ("kotlin")
    kotlin("plugin.serialization") version "1.5.10"
}

dependencies{
    implementation("io.ktor:ktor-client-core:1.6.1")
    implementation("io.ktor:ktor-client-serialization:1.6.1")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:2.0.4")
    implementation("com.arkivanov.mvikotlin:mvikotlin:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
}