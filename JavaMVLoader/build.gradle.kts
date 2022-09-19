plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenLocal()
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.24")
    if (JavaVersion.current() == JavaVersion.VERSION_1_8) { //Make sure that gradle doesn't throw 10000 errors and die if we try to compile on java 8
        compileOnly(files(org.gradle.internal.jvm.Jvm.current().toolsJar))
        println("Including the tools jar")
    }
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks.shadowJar {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Main-Class" to "me.txmc.rtmixin.loader.Main",
        )
    }
    includeEmptyDirs = false
    minimize()
}

group = "me.txmc"
version = "1.0-SNAPSHOT"
description = "Runtime mixin multi version agent loader"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}