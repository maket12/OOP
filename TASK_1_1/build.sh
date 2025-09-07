#!/bin/bash

SRC_DIR="src/main/java"
OUT_DIR="out"
DOCS_DIR="docs"
MAIN_CLASS="HeapSort"
JAR_NAME="heapsort.jar"

rm -rf $OUT_DIR $DOCS_DIR $JAR_NAME
mkdir -p $OUT_DIR

echo "=== Compiling ==="
javac -d $OUT_DIR $SRC_DIR/*.java

echo "=== Documentation generation ==="
mkdir -p $DOCS_DIR
javadoc -d $DOCS_DIR $SRC_DIR/*.java

echo "=== Creating jar file ==="
jar cfe $JAR_NAME $MAIN_CLASS -C $OUT_DIR .

echo "=== Launching program ==="
java -cp $JAR_NAME $MAIN_CLASS
