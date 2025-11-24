# BPTracker - Blood Pressure Tracker

## Objectives
The primary objective of the BPTracker application is to provide users with a simple and effective way to monitor their blood pressure readings over time. By allowing users to record their systolic and diastolic pressure, pulse rate, and notes, the app aims to help users track their cardiovascular health and share this information with healthcare providers. The app also visualizes this data through charts to easily identify trends and potential health issues.

## Features
*   **Add Readings:** Easily input systolic, diastolic, and pulse readings along with the date and time.
*   **History Log:** View a comprehensive list of all past blood pressure readings in a scrollable history view.
*   **Trend Analysis (Charts):** Visualize blood pressure trends over time using an interactive line chart.
*   **Notes:** Add optional notes to each reading to record context (e.g., "after exercise", "morning reading").
*   **Health Alerts:** Receive visual indicators (color-coded entries) and alerts for high or critical blood pressure readings.
*   **Data Persistence:** All data is stored locally on the device using a Room database for privacy and offline access.

## Screenshots

| Dashboard | Add Reading | History | Chart View |
|:---:|:---:|:---:|:---:|
| ![image alt](https://github.com/Lindoln007/MobileApp_Assignment2_AsanteMbaya_BSC351/blob/ea65120e519579732988adefee3becbd0ea3a753/Screenshot_2025-11-24-20-51-27-108_com.example.bptracker.jpg) | ![Add Reading](screenshots/add_reading.png) | ![History](screenshots/history.png) | ![Chart View](screenshots/chart.png) |

*(Note: Please add screenshots to a `screenshots` folder in the root directory and name them accordingly, or update the paths above.)*

## Installation Steps

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/Lindoln007/MobileApp_Assignment2_AsanteMbaya_BSC351.git
    ```

2.  **Open in Android Studio:**
    *   Launch Android Studio.
    *   Select **File > Open**.
    *   Navigate to the directory where you cloned the repository and select the `BPTracker` folder.

3.  **Sync Project with Gradle Files:**
    *   Android Studio should automatically sync the project. If not, click the **Sync Project with Gradle Files** button (elephant icon) in the toolbar.

4.  **Build the Project:**
    *   Go to **Build > Make Project** or press `Ctrl+F9` (Cmd+F9 on Mac).

5.  **Run the Application:**
    *   Connect an Android device via USB or create/start an Android Virtual Device (AVD).
    *   Click the **Run** button (green play icon) in the toolbar or press `Shift+F10` (Ctrl+R on Mac).

## Technologies Used
*   **Language:** Java / Kotlin
*   **UI:** XML Layouts, Material Design Components
*   **Database:** Room Persistence Library
*   **Charting:** MPAndroidChart
*   **Build System:** Gradle

## Contact
For any inquiries or support, please contact [Your Name/Email].
