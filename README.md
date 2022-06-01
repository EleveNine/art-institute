# Art Institute of Chicago
Simple test project with Clean Architecture, MVVM + UiState, Kotlin Coroutines and Flow. 

Based on the public API provided Art Institute of Chicago: [API Docs Link](https://api.artic.edu/docs/#introduction)

Consider this project as demonstration of known technologies and methodologies. 


## Main highlights:

- Written natively in Kotlin, using Android SDK;
- Minimal supported Android SDK version - 23 (Android OS 6.0);
- Designed based on the Clean Architecture principles - the app is divided into UI (Presentation), Domain and Data layers;
- Implemented MVVM pattern for UI <—> Business Logic communication using Unidirectional Data Flow (UDF) principle;
- Single activity, separate screens are represented in the form of Fragments;
- Jetpack Navigation Component is used for the navigation between fragments;
- UI is built using XML View-based system;
- Dagger2 for Dependency Injection;
- Retrofit2, OkHttp3, kotlinx.serialization for accessing remote REST API;
- Android Room for storing data locally;
- Kotlin Coroutines, Kotlin Flow for handling asynchronous and reactive code;
- Glide for image loading;
