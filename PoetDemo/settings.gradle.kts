pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        id("com.google.devtools.ksp") version kspVersion
        kotlin("jvm") version kotlinVersion
    }
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }
}

rootProject.name = "PoetDemo"

include(":kspconsumer")
include (":annotation")
include (":kspcomplier")
include (":kotlinpoet")
include (":javapoet")
include (":app")

