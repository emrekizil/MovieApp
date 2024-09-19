<h1 align="center">Movie App</h1>
</br>
<p align="center">
  A Movie App showcasing favorite and search functionality, implemented with multi-modularization using the feature-based pattern and designed following the MVVM architecture.
</p>
</br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/emrekizil"><img alt="Profile" src="https://img.shields.io/badge/github-emrekizil-red"/></a> 
</p>

## Screenshots
<p align="center">
<img src="/previews/home.png" width="19%"/>
<img src="/previews/search.png" width="19%"/>
<img src="/previews/favorite.png" width="19%"/>
<img src="/previews/detail.png" width="19%"/>
<img src="/previews/chat.png" width="19%"/>
</p>

## Tech Stack & Open Source Libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/)  based + [Flow](https://developer.android.com/kotlin/flow) and [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Architecture Components](https://developer.android.com/topic/libraries/architecture)
  - [Use Cases](https://developer.android.com/topic/architecture/domain-layer) purpose is to request data from repositories and turn them ready to use for the UI layer
  - [Repository](https://developer.android.com/topic/architecture/data-layer) pattern is a design pattern that isolates the data layer from the rest of the app
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) class is a business logic or screen level state holder. It exposes the state to the UI and encapsulates related business logic
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) is a class that holds the information about the lifecycle state of a component (like an activity or a fragment) and allows other objects to observe this state
- [Navigation Component](https://developer.android.com/guide/navigation) refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within the app
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Gson](https://github.com/google/gson) is a Java library for converting Java Objects into their JSON representation
- [Dagger Hilt](https://dagger.dev/hilt/) Hilt provides a standard way to incorporate Dagger dependency injection into an Android application
- [Coil](https://coil-kt.github.io/coil/) An image loading library for Android backed by Kotlin Coroutines
- [Room](https://developer.android.com/training/data-storage/room) persistence library provides an abstraction layer over SQLite, allowing for more robust database access while harnessing the full power of SQLite
- [Jetpack Datastore](https://developer.android.com/topic/libraries/architecture/datastore) is a data storage solution that stores key-value pairs or typed objects with protocol buffers
- [Jetpack Compose](https://developer.android.com/compose) is Android’s recommended modern toolkit for building native UI
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) helps you load and display pages of data from a larger dataset from local storage or over a network

## Architecture
MVVM [***Model View ViewModel***](https://developer.android.com/topic/architecture#recommended-app-arch)

![Architecture](https://user-images.githubusercontent.com/21035435/69536839-9f4c8e80-0fa0-11ea-85ee-d7823e5a46b0.png)
