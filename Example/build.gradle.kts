plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.txmc.me/releases") }
}

dependencies {
    implementation("org.javassist:javassist:3.28.0-GA")
    implementation(project(":RtMixin"))
    if (JavaVersion.current() == JavaVersion.VERSION_1_8) {
        compileOnly(files(org.gradle.internal.jvm.Jvm.current().toolsJar))
    }
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks.shadowJar {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Main-Class" to "me.txmc.Main",
            "Premain-Class" to "me.txmc.rtmixin.jagent.AgentMain",
            "Agent-Class" to "me.txmc.rtmixin.jagent.AgentMain",
            "Can-Redefine-Classes" to "true",
            "Can-Retransform-Classes" to "true",
            "Can-Set-Native-Method-Prefix" to "true"
        )
    }
    includeEmptyDirs = false
    minimize()
}

group = "me.txmc"
version = "1.0-SNAPSHOT"
description = "RtMixinTest"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}