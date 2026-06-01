package ru.nsu.checker.dsl;

import ru.nsu.checker.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DslBuilders {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static class TaskBuilder {
        private final Task task = new Task();

        public void id(String id) { task.setId(id); }
        public void name(String name) { task.setName(name); }
        public void maxScore(int score) { task.setMaxScore(score); }
        public void softDeadline(String date) { task.setSoftDeadline(LocalDate.parse(date, DATE_FORMAT)); }
        public void hardDeadline(String date) { task.setHardDeadline(LocalDate.parse(date, DATE_FORMAT)); }

        public Task build() { return task; }
    }

    public static class StudentBuilder {
        private final Student student = new Student();

        public void githubNick(String nick) { student.setGithubNick(nick); }
        public void fullName(String name) { student.setFullName(name); }
        public void repository(String url) { student.setRepositoryUrl(url); }

        public Student build() { return student; }
    }

    public static class GroupBuilder {
        private final Group group = new Group();

        public void name(String name) { group.setName(name); }
        public void student(StudentBuilder builder) { group.addStudent(builder.build()); }

        public Group build() { return group; }
    }

    public static class ControlPointBuilder {
        private final ControlPoint cp = new ControlPoint();

        public void name(String name) { cp.setName(name); }
        public void date(String date) { cp.setDate(LocalDate.parse(date, DATE_FORMAT)); }

        public ControlPoint build() { return cp; }
    }

    public static class AssignmentBuilder {
        private final CheckAssignment assignment = new CheckAssignment();

        public void student(String nick) { assignment.setStudentNick(nick); }
        public void tasks(String... taskIds) {
            for (String id : taskIds) assignment.addTaskId(id);
        }

        public CheckAssignment build() { return assignment; }
    }

    public static class GradeThresholdBuilder {
        private final GradeThreshold threshold = new GradeThreshold();

        public void minScore(int score) { threshold.setMinScore(score); }
        public void grade(int grade) { threshold.setGrade(grade); }

        public GradeThreshold build() { return threshold; }
    }

    public static class BonusScoreBuilder {
        private final BonusScore bonus = new BonusScore();

        public void student(String nick) { bonus.setStudentNick(nick); }
        public void task(String taskId) { bonus.setTaskId(taskId); }
        public void score(int score) { bonus.setScore(score); }
        public void reason(String reason) { bonus.setReason(reason); }

        public BonusScore build() { return bonus; }
    }

    public static class SettingsBuilder {
        private final SystemSettings settings = new SystemSettings();

        public void testTimeoutSeconds(int timeout) { settings.setTestTimeoutSeconds(timeout); }
        public void gradeThreshold(GradeThresholdBuilder builder) {
            settings.addGradeThreshold(builder.build());
        }
        public void bonus(BonusScoreBuilder builder) {
            settings.addBonusScore(builder.build());
        }

        public SystemSettings build() { return settings; }
    }
}
