#!/usr/bin/env bash
set -e
JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}"
GROOVY_JAR="lib/groovy-all-2.4.21.jar"
COMMONS_JAR="lib/commons-io-2.11.0.jar"
CLASSES="build/classes"

echo "Compiling..."
mkdir -p "$CLASSES"
"$JAVA_HOME/bin/javac" --release 17 \
    -cp "$GROOVY_JAR:$COMMONS_JAR" \
    -d "$CLASSES" \
    $(find app/src/main/java -name "*.java")

echo "Packaging..."
rm -rf /tmp/_fatjar && mkdir -p /tmp/_fatjar
cd /tmp/_fatjar
"$JAVA_HOME/bin/jar" xf "$(pwd -P)/../../home/claude/oop-checker/$GROOVY_JAR" 2>/dev/null || \
    "$JAVA_HOME/bin/jar" xf "/home/claude/oop-checker/$GROOVY_JAR"
"$JAVA_HOME/bin/jar" xf "/home/claude/oop-checker/$COMMONS_JAR"
cp -r "/home/claude/oop-checker/$CLASSES/"* /tmp/_fatjar/
printf 'Manifest-Version: 1.0\nMain-Class: ru.nsu.checker.cli.Main\n\n' > META-INF/MANIFEST.MF
"$JAVA_HOME/bin/jar" cfm "/home/claude/oop-checker/build/oop-checker.jar" META-INF/MANIFEST.MF .
echo "Done: build/oop-checker.jar"
