# UG Navigate 🚶‍♂️🗺️
**Optimal Campus Route Finder** for the University of Ghana  
A sleek and user-friendly desktop application built to help students, staff, and visitors navigate the vast campus of the University of Ghana, Legon. Find buildings, get directions, and save your favorite locations with ease.
A Java-based application using Data Structures & Algorithms (DCIT 204 Group Project)


# UG Nav - University of Ghana Navigator
https://img.shields.io/badge/Java-17-%2523ED8B00?logo=openjdk 

https://img.shields.io/badge/JavaFX-19-%2523ED8B00?logo=javafx

https://img.shields.io/badge/License-MIT-green.svg

https://img.shields.io/badge/Status-Completed-brightgreen


# Team Members 
https://github.com/FATTAHMALIK

https://github.com/clementocran

https://github.com/asiateymike03

https://github.com/Appeatsenanaama 

---


## 📌 Overview

**UG Navigate** is a campus navigation system designed as a Data Structures and Algorithms group project (DCIT 204, University of Ghana). It helps users find:
- The shortest path between two locations
- Alternate route options
- Routes through selected landmarks
- Estimated travel times (considering traffic)
- A visual map with the selected route drawn

---

## 🔧 Features

| Feature                         | Description                                                   |
|-------------------------------|---------------------------------------------------------------|
| 🔢 Shortest path algorithm     | Uses Dijkstra's algorithm to calculate shortest distance      |
| 🧭 Landmark routing            | Specify a landmark to pass through (e.g., "Bank")            |
| 🔄 Multiple route generation   | Shows top 3 shortest paths using DFS traversal                |
| 🚦 Traffic estimation          | Simulates traffic delays using random traffic multipliers     |
| 🗺️ Map display                | Shows selected route on real UG campus map                    |
| 🧪 Sorted route presentation   | Routes are sorted using Merge Sort                            |

---

## 🚀 Setup & Run Instructions

### ✅ Requirements
- Java 11+
- JavaFX SDK (download from [https://openjfx.io/](https://openjfx.io/))
- VS Code / IntelliJ / Terminal

---


```### 🔧 Folder Structure
 UG_Navigate/
            ├── Main.java
            ├──  Graph.java
            ├── Route.java
            ├── Sorter.java
            ├── MapCanvas.java
            ├── campus_map.png
```
--- 

## 🛠 Requirements

- Java 11 or newer
- JavaFX SDK (download from [https://openjfx.io/](https://openjfx.io/))
- Any IDE (VS Code, IntelliJ) or terminal

---

## ▶️ Running the Project

### 🔧 Compile
Clone the repository:

```bash
git clone https://github.com/PBoahene/UG_Nav.git
cd UG_Nav
```

```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls *.java
```

# Open the project in your IDE.

Add VM Options: Configure your IDE to run the project with the following VM arguments. You must replace /path/to/javafx-sdk-19/lib with the actual path to the lib folder inside your extracted JavaFX SDK.

```bash
text
--module-path /path/to/javafx-sdk-19/lib --add-modules javafx.controls,javafx.fxml
```
In IntelliJ: Run -> Edit Configurations -> Modify options -> Add VM options.

### 🗺️ How to Use
Launch the application.

The main map view will load. You can pan around by dragging the mouse.

Use the search bar to type the name of a location (e.g., "JQB", "Balme Library").

Select your desired destination from the dropdown results.

Click "Get Directions". The path will be drawn on the map, and a list of instructions will appear.

(Optional) Click on the star icon next to a location to add it to your Favorites.

 ## 🙏 Acknowledgments
University of Ghana for providing the campus layout.

Inspiration from other mapping services like Google Maps.

The JavaFX community for excellent documentation and resources.

# 🤝 Contributing
This was a university project completed as part of a course. While it is feature-complete, contributions and suggestions are always welcome.

Fork the Project.

Create your Feature Branch (git checkout -b feature/AmazingFeature).

Commit your Changes (git commit -m 'Add some AmazingFeature').

Push to the Branch (git push origin feature/AmazingFeature).

Open a Pull Request.
