# E-Commerce Android App

## Overview

This Android application is a feature-rich e-commerce platform built using modern Android development practices and libraries. It provides users with a seamless shopping experience, including browsing products, managing a cart and wishlist, placing orders, and more.

## Features

- User Authentication (Sign In/Sign Up)
- Product Browsing
- Product Filtering
- Shopping Cart Management
- Wishlist Functionality
- Order Placement and Tracking
- User Profile Management

## Technical Stack

- **Architecture**: Clean Architecture
- **UI**: Jetpack Compose
- **Dependency Injection**: Koin
- **Image Loading**: Coil
- **Local Database**: Room
- **Networking**: Retrofit
- **Language**: Kotlin

## Project Structure

The project follows Clean Architecture principles and is organized into the following main modules:

- `app`: Main application module
- `data`: Data sources, repositories, and models
- `domain`: Use cases and domain models
- `presentation`: UI components and ViewModels

## Setup and Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/ecommerce-android-app.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or physical device.

## Configuration

- Ensure you have the latest version of Android Studio.
- Minimum SDK version: 21
- Target SDK version: 33

## Testing

The project includes both unit tests and instrumentation tests:

- Run unit tests: `./gradlew test`
- Run instrumentation tests: `./gradlew connectedAndroidTest`

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Koin](https://insert-koin.io/)
- [Coil](https://coil-kt.github.io/coil/)
- [Room](https://developer.android.com/training/data-storage/room)
- [Retrofit](https://square.github.io/retrofit/)

## Contact

For any queries or suggestions, please contact [Your Name](mailto:your.email@example.com).
