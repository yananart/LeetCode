import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}


group = "cn.yananart"
version = "1.0"


allprojects {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        mavenCentral()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

val slf4jVersion = "1.7.36"
val logbackVersion = "1.2.10"
val hutoolVersion = "5.7.22"
val freemarkerVersion = "2.3.31"

dependencies {
    implementation("org.slf4j", "slf4j-api", slf4jVersion)
    implementation("cn.hutool", "hutool-extra", hutoolVersion)
    implementation("org.freemarker", "freemarker", freemarkerVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
}