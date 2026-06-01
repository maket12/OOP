tasks {
    task {
        id "Task_2_1_1"
        name "Simple numbers"
        maxScore 100
        softDeadline "2026-02-20"
        hardDeadline "2026-02-28"
    }
    task {
        id "Task_2_2_1"
        name "Pizzeria"
        maxScore 150
        softDeadline "2026-03-20"
        hardDeadline "2026-04-01"
    }
    task {
        id "Task_2_3_1"
        name "Snake Game"
        maxScore 200
        softDeadline "2026-05-01"
        hardDeadline "2026-05-29"
    }
}

groups {
    group("Group 24216") {
        student {
            githubNick "maket12"
            fullName "Vladimir Ziabkin"
            repository "https://github.com/maket12/OOP"
        }
        student {
            githubNick "24216-Maslova-Alina"
            fullName "Maslova Alina"
            repository "https://github.com/24216-Maslova-Alina/OOP"
        }
    }
}

check {
    assign {
        student "maket12"
        tasks "Task_2_3_1"
//        tasks "Task_2_1_1", "Task_2_2_1", "Task_2_3_1"
    }
//    assign {
//        student "24216-Maslova-Alina"
//        tasks "Task_2_3_1"
//    }
}

controlPoints {
    controlPoint {
        name "EndTerm"
        date "2026-06-15"
    }
}

settings {
    testTimeoutSeconds 600
    gradeThresholds {
        threshold 85, 5
        threshold 70, 4
        threshold 50, 3
        threshold 0, 2
    }
}