package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import ru.nsu.checker.model.CheckAssignment;
import ru.nsu.checker.model.CheckerConfig;

import java.util.Arrays;

public class CheckContext {
    private final CheckerConfig config;

    public CheckContext(CheckerConfig config) {
        this.config = config;
    }

    public void assign(Closure<?> closure) {
        AssignmentContext ctx = new AssignmentContext();
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addAssignment(ctx.build());
    }

    public static class AssignmentContext {
        private final CheckAssignment assignment = new CheckAssignment();

        public void student(String nick) { assignment.setStudentNick(nick); }
        public void tasks(String... taskIds) {
            Arrays.stream(taskIds).forEach(assignment::addTaskId);
        }

        public CheckAssignment build() { return assignment; }
    }
}
