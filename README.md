# UG Navigate 🚶‍♂️🗺️
**Optimal Campus Route Finder** for the University of Ghana  
A Java-based application using Data Structures & Algorithms (DCIT 204 Group Project)


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

### 🔧 Folder Structure
UG_Navigate/
├── Main.java
├── Graph.java
├── Route.java
├── Sorter.java
├── MapCanvas.java
├── campus_map.png 


--- 

## 🛠 Requirements

- Java 11 or newer
- JavaFX SDK (download from [https://openjfx.io/](https://openjfx.io/))
- Any IDE (VS Code, IntelliJ) or terminal

---

## ▶️ Running the Project

### 🔧 Compile

```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls *.java


