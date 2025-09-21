plugins {
    kotlin("jvm") version "2.2.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    // Ici tu mets le nom du fichier contenant "fun main"
    // Si ton fichier s’appelle Main.kt (sans package en haut), c’est "MainKt"
    mainClass.set("MainKt")
}
