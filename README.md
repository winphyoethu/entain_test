# Entain_Test - A Sport App 📊

A sport app that covers horse races, greyhound races, and harness races

## Overview
The project is designed by following the MVVM architecture. This project is structured to ensure scalability, maintainability, and separation of concerns.

📂 project-root<br>
├── 📂 build-logic        # Build script module to be shared across project<br>
├── 📂 app                # Main application module<br>
├── 📂 core               # Core module<br>
├── ├── 📂 common         # Common utilities and helpers<br>
├── ├── 📂 design-system  # UI components and theme<br>
├── ├── 📂 data           # Repository and data sources<br>
├── ├── 📂 model          # Models to be consumed  in Ui<br>
├── ├── 📂 network        # API and network layer<br>
├── 📂 features           # Feature module<br>
├── ├── 📂 racing         # Racing Feature<br>
├── 📄 settings.gradle.kts<br>
└── 📄 build.gradle.kts<br>

## Modules Description
- Build-Logic Module: Gradle Build script module that contains build scripts to be shared among modules.
- App Module: The main application that brings all modules together.
- Core Module: Contains shared components and logic used across feature modules.
- common: Shared utilities, extensions, and constants.
- design-system: Shared UI components and theming.
- data: Repository layer handling business logic and data sources.
- network: API services and networking configurations.
- Feature Modules: Independent features that interact with core modules.

BuildSrc: Centralized dependency and build management.

## 🛠️ Tech Stack
- Programming Language: Kotlin
- UI Framework - Jetpack Compose
- Architecture: MVVM
- Dependency Injection: Hilt
- Asynchronous Programming: Coroutines
- Networking: Retrofit, OkHttp
- State Management: StateFlow
- Testing: JUnit, Mockito
- CI: GitHub Actions

## Setup & Installation

1. Clone the repository:
```
git clone https://github.com/your-repo.git 
cd your-repo
```
2. Open the project in Android Studio.
3. Sync Gradle files and build the project.
4. Run the application on an emulator or device.

## Screenshots
<img src="https://github.com/winphyoethu/entain_test/blob/main/screenshots/lightmode.jpg?raw=true" width="200" alt="lightmode"/><img src="https://github.com/winphyoethu/entain_test/blob/main/screenshots/darkmode.jpg?raw=true" width="200" alt="darkmode"/>
