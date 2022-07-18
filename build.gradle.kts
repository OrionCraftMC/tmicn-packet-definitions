import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("tmicn-definitions-helper")
    kotlin("jvm") version "1.7.0"
}

repositories {
    mavenCentral()
}

tmicn {
    protocols = arrayOf("craftlandia")
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}