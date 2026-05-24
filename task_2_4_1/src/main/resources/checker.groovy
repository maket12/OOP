// Import long-lived configurations
importConfig "tasks.groovy"
importConfig "groups.groovy"

// Current check assignments
check {
    assign {
        student "ivanov_ivan"
        tasks "Task_1", "Task_2", "Task_3"
    }
    assign {
        student "petrov_petr"
        tasks "Task_1", "Task_2"
    }
    assign {
        student "sidorov_alex"
        tasks "Task_1", "Task_2", "Task_3", "Task_4"
    }
}

// System settings
settings {
    testTimeoutSeconds 120

    gradeThresholds {
        threshold 85, 5
        threshold 70, 4
        threshold 50, 3
        threshold 0,  2
    }

    bonus {
        student "sidorov_alex"
        task "Task_3"
        score 10
        reason "Excellent algorithm optimization"
    }
}
