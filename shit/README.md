# OOP Checker

A console application for teachers to automatically check student OOP course assignments hosted on GitHub.

## Architecture

```
oop-checker/
├── app/src/main/java/ru/nsu/checker/
│   ├── cli/          — Entry point and CLI argument parsing
│   ├── dsl/          — DSL engine: script base class, context classes per block
│   ├── model/        — Domain model (Task, Student, Group, …)
│   ├── report/       — HTML report generator
│   └── service/      — Git, Gradle, score calculation services
├── app/src/main/resources/
│   ├── checker.groovy  — Main (frequently changing) config
│   ├── tasks.groovy    — Long-lived task definitions
│   └── groups.groovy   — Semester-level group definitions
├── lib/               — Local Groovy and commons-io jars for build
├── build/oop-checker.jar
├── build.sh           — Manual build script
├── pom.xml            — Maven build
└── app/build.gradle   — Gradle build (requires Gradle 8+)
```

## DSL Format

The configuration is a Groovy-based DSL where the script base class provides top-level methods.

### Main config file (`checker.groovy`)

```groovy
// Import shared configs
importConfig "tasks.groovy"
importConfig "groups.groovy"

// Who checks what
check {
    assign {
        student "github_nick"
        tasks "Task_1", "Task_2"
    }
}

// System settings (override defaults here)
settings {
    testTimeoutSeconds 120

    gradeThresholds {
        threshold 85, 5
        threshold 70, 4
        threshold 50, 3
        threshold 0,  2
    }

    bonus {
        student "github_nick"
        task "Task_1"
        score 10
        reason "Early submission bonus"
    }
}
```

### Tasks config (`tasks.groovy`)

```groovy
tasks {
    task {
        id "Task_1"
        name "Stack Implementation"
        maxScore 100
        softDeadline "2024-10-01"
        hardDeadline "2024-10-15"
    }
}

controlPoints {
    controlPoint {
        name "Midterm"
        date "2024-11-10"
    }
}
```

### Groups config (`groups.groovy`)

```groovy
groups {
    group("Group 21209") {
        student {
            githubNick "ivanov_ivan"
            fullName "Ivanov Ivan Ivanovich"
            repository "https://github.com/ivanov_ivan/OOP"
        }
    }
}
```

## Building

### With Maven (requires internet for dependencies)
```bash
mvn package
java -jar target/oop-checker-1.0.0-jar-with-dependencies.jar run
```

### With Gradle (requires Gradle 8+ and internet)
```bash
./gradlew :app:jar
java -jar app/build/libs/oop-checker.jar run
```

### Manual (offline, uses local jars in `lib/`)
```bash
./build.sh
```

## Running

Place your `checker.groovy` (and any imported configs) in a directory, then:

```bash
# Check all assigned students and output HTML report to stdout
java -jar oop-checker.jar run

# With explicit directory
java -jar oop-checker.jar --dir /path/to/config/dir run

# With custom config file name
java -jar oop-checker.jar --config my-config.groovy run

# With custom workspace directory for cloned repos
java -jar oop-checker.jar --workspace /tmp/repos run
```

## How It Works

1. Reads `checker.groovy` from the working directory (Gradle-style)
2. For each assigned student:
   - Clones or pulls their repository via `git`
   - Checks out `main` or `master` branch
   - For each assigned task, finds its directory in the repo
   - Runs `./gradlew compileJava` — stops here on failure
   - Runs `./gradlew javadoc` and `./gradlew checkstyleMain`
   - Runs `./gradlew test`, parses passed/failed/skipped counts
   - Calculates score based on test ratio, style, docs, and deadlines
3. Applies bonus scores from settings
4. Calculates control point and final grades
5. Generates HTML report to stdout

## Score Calculation

- Base score = `maxScore × (testsPassed / totalTests + 0.1×styleOk + 0.1×docOk)`
- After soft deadline: score × 0.5
- After hard deadline: score = 0
- Bonus scores are added on top

## Git Requirements

The application uses the system `git` client. It runs as whoever is configured in `git config --global`. Repositories must be accessible without interactive prompts (SSH keys, credential helpers, or public repos).
