#!/bin/bash
set -e

rm -rf out
mkdir -p out/classes
mkdir -p out/doc

echo "Compiling..."
javac -d out/classes src/main/java/oop/task1_1/HeapSort.java

echo "Generation of documentation..."
javadoc -d out/doc src/main/java/oop/task1_1/HeapSort.java

echo "Packing into JAR..."
jar cfe out/HeapSort.jar oop.task1_1.HeapSort -C out/classes .

echo "Launching program..."
java -jar out/HeapSort.jar
