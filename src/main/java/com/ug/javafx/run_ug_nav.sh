#!/bin/bash

# Path to JavaFX SDK
JAVA_FX_PATH="/Users/shyne883/javafx-sdk-24"

cd /Users/shyne883/Desktop/UG_Nav_-main/src/main/java || exit

echo "Compiling Java files..."
javac --module-path /Users/shyne883/downloads/javafx-sdk-24.0.2/lib \
      --add-modules javafx.controls,javafx.fxml \
      com/ug/javafx/*.java

echo "Running UG Navigate..."
java --module-path /Users/shyne883/downloads/javafx-sdk-24.0.2/lib \
     --add-modules javafx.controls,javafx.fxml \
     com.ug.javafx.Main
