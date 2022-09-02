rootProject.name = "RtMixin"

include(":RtMixin", ":JavaMVLoader", "Tests")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
