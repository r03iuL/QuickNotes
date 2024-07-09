# QuickNotes

QuickNotes is a basic note-taking application developed as part of an Object-Oriented Programming (OOP) course. The app is built using Java and Firebase, providing users with secure authentication, real-time data synchronization, and CRUD operations for managing their notes.

## Features

1. **User Authentication using Firebase:**
   - Secure registration and login using email and password.
   - Managed by Firebase Authentication for reliable access and session management.

2. **Storing Notes in Firebase Firestore:**
   - Real-time data synchronization and offline support.
   - Notes include fields such as title, content, and timestamp for detailed management.

3. **CRUD Operations on Notes:**
   - Create, Read, Update, and Delete notes effortlessly.
   - Efficient data retrieval and manipulation with Firestore’s robust querying capabilities.

4. **Synchronization of Notes Across Devices:**
   - Changes made on one device are instantly reflected on other devices.
   - Real-time data synchronization ensures the latest version of notes is always available.

## Getting Started

### Prerequisites

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Android Studio](https://developer.android.com/studio)
- [Firebase Account](https://firebase.google.com/)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/quicknotes.git
    cd quicknotes
    ```

2. Open the project in Android Studio.

3. Set up Firebase in your project:
    - Go to the [Firebase Console](https://console.firebase.google.com/).
    - Create a new project.
    - Add an Android app to your Firebase project.
    - Follow the instructions to download the `google-services.json` file.
    - Place the `google-services.json` file in the `app` directory of your project.
    - Add Firebase SDK dependencies in your `build.gradle` files.

4. Sync your project with Gradle files.

### Usage

- Run the app on an emulator or a physical device.
- Register a new user or log in with an existing account.
- Verify your email.
- Create, read, update, and delete notes.
- Notes will be synchronized across all devices where the user is logged in.

## Built With

- [Java](https://www.oracle.com/java/)
- [Firebase Authentication](https://firebase.google.com/products/auth)
- [Firebase Firestore](https://firebase.google.com/products/firestore)
- [Android Studio](https://developer.android.com/studio)


## Acknowledgments

- Thanks to the OOP course for providing the foundation and inspiration for this project.
- [Firebase](https://firebase.google.com/) for providing powerful backend services.
