tasks {
    task {
        id "Task_1"
        name "Stack"
        maxScore 100
        softDeadline "2024-10-01"
        hardDeadline "2024-10-15"
    }
}

groups {
    group("Group 1") {
        student {
            githubNick "maket12"
            fullName "Зябкин Владимир"
            repository "https://github.com/maket12/OOP"
        }
    }
}

check {
    assign {
        student "maket12"
        tasks "Task_1"
    }
}

controlPoints {
    controlPoint {
        name "Midterm"
        date "2024-11-10"
    }
}

settings {
    testTimeoutSeconds 120
    gradeThresholds {
        threshold 85, 5
        threshold 70, 4
        threshold 50, 3
        threshold 0, 2
    }
}