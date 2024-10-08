pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieApp"
include(":app")
include(":core-data")
include(":core-network")
include(":core-database")
include(":core-domain")
include(":core-model")
include(":feature-home")
include(":feature-favorite")
include(":feature-chat")
include(":feature-detail")
include(":feature-search")
include(":core-ui")
include(":navigation")
