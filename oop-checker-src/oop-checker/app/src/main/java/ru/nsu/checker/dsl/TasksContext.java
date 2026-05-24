package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import ru.nsu.checker.model.CheckerConfig;
import ru.nsu.checker.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksContext {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final CheckerConfig config;

    public TasksContext(CheckerConfig config) {
        this.config = config;
    }

    public void task(Closure<?> closure) {
        TaskContext ctx = new TaskContext();
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addTask(ctx.build());
    }

    public static class TaskContext {
        private final Task task = new Task();

        public void id(String id) { task.setId(id); }
        public void name(String name) { task.setName(name); }
        public void maxScore(int score) { task.setMaxScore(score); }
        public void softDeadline(String date) { task.setSoftDeadline(LocalDate.parse(date, DATE_FORMAT)); }
        public void hardDeadline(String date) { task.setHardDeadline(LocalDate.parse(date, DATE_FORMAT)); }

        public Task build() { return task; }
    }
}
