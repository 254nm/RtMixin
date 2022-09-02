import org.gradle.internal.jvm.Jvm

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.txmc.me/releases") }
}
dependencies {
    implementation("org.javassist:javassist:3.28.0-GA")
    if (JavaVersion.current() == JavaVersion.VERSION_1_8) { //Make sure that gradle doesn't throw 10000 errors and die if we try to compile on java 8
        compileOnly(files(Jvm.current().toolsJar))
        println("Including the tools jar")
    }
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks.shadowJar {
//    from("src/main/resources").include("*")
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Premain-Class" to "me.txmc.rtmixin.jagent.AgentMain",
            "Agent-Class" to "me.txmc.rtmixin.jagent.AgentMain",
            "Can-Redefine-Classes" to "true",
            "Can-Retransform-Classes" to "true",
            "Can-Set-Native-Method-Prefix" to "true"
        )
    }
//    includeEmptyDirs = false
}



group = "me.txmc"
version = "1.4-BETA"
description = "Add mixins at runtime"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}