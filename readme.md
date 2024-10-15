# Video Game Backlog App

## Overview

The Video Game Backlog App is a modern Android application designed to help gamers manage their ever-growing collection of unplayed video games. Built with Jetpack Compose, this app offers a sleek, user-friendly interface to track and prioritize your gaming backlog.

## Features

- **Game Library**: Easily add games to your backlog with details like title, rating, and release date.
- **Cover Art Display**: View beautiful cover art for each game in your list.
- **Rating System**: See aggregated ratings for games to help you decide what to play next.
- **Progress Tracking**: Mark games as in progress, or completed.
- **Search Functionality**: Quickly find games to add to your backlog with a powerful search feature.

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Image Loading**: Coil
- **Database**: Room Local, AWS Remote
- **API Communication**: Retrofit

## Setup to run the project locally

1. Clone the repository:
   ```
   git clone https://gitlab.com/wgu-gitlab-environment/student-repos/Caelinh/d424-software-engineering-capstone.git
   ```
2. Open the project in Android Studio and be sure to switch to the working git branch. Version: Android Studio Hedgehog | 2023.1.1 Patch 2
3. Sync the project with Gradle files.
4. Run the app on an emulator or physical device.
5. API calls will not work locally since an private API key is required to access AWS Resources.

## Usage

1. Launch the app.
2. Add games to your backlog using the "+" button.
3. View your game library and tap on a game for more details.
4. Update game status as you start or complete games.
5. Use the search and filter options to manage your backlog effectively.


## Acknowledgments

- Thanks to [IGDB](https://www.igdb.com/) for providing game data.
- Icon assets by [Freepik](https://www.freepik.com/) from [Flaticon](https://www.flaticon.com/).

