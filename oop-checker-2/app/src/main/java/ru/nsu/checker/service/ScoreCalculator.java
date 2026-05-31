package ru.nsu.checker.service;

import ru.nsu.checker.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class ScoreCalculator {
    private static final Logger log = Logger.getLogger(ScoreCalculator.class.getName());

    private final SystemSettings settings;

    public ScoreCalculator(SystemSettings settings) {
        this.settings = settings;
    }

    public void calculate(TaskResult result, Task task, LocalDate submissionDate) {
        log.info("=== Calculating score for " + result.getStudentNick() + "/" + task.getId() + " ===");
        log.info("Status: " + result.getStatus());
        log.info("Submission date: " + submissionDate);
        log.info("Soft deadline: " + task.getSoftDeadline());
        log.info("Hard deadline: " + task.getHardDeadline());

        if (result.getStatus() != TaskResult.Status.SUCCESS) {
            log.info("Not SUCCESS, score = 0");
            result.setScore(0);
            applyBonus(result);
            return;
        }

        int maxScore = task.getMaxScore();
        double multiplier = 1.0;

        if (submissionDate != null && task.getHardDeadline() != null) {
            if (submissionDate.isAfter(task.getHardDeadline())) {
                multiplier = 0.0;
                log.info("Task " + task.getId() + " submitted after hard deadline, score = 0");
            } else if (task.getSoftDeadline() != null && submissionDate.isAfter(task.getSoftDeadline())) {
                multiplier = 0.5;
                log.info("Task " + task.getId() + " submitted after soft deadline, score halved");
            }
        }

        int totalTests = result.getTestsPassed() + result.getTestsFailed() + result.getTestsSkipped();
        log.info("Tests: passed=" + result.getTestsPassed() + ", failed=" + result.getTestsFailed()
                + ", skipped=" + result.getTestsSkipped() + ", total=" + totalTests);

        double testRatio = totalTests > 0 ? (double) result.getTestsPassed() / totalTests : 0.5;

        double styleBonus = result.isStyleCheckPassed() ? 0.1 : 0.0;
        double docBonus = result.isDocGenerated() ? 0.1 : 0.0;
        double baseRatio = Math.min(1.0, testRatio + styleBonus + docBonus);

        log.info("Calculation: maxScore=" + maxScore + ", testRatio=" + testRatio
                + ", styleBonus=" + styleBonus + ", docBonus=" + docBonus
                + ", baseRatio=" + baseRatio + ", multiplier=" + multiplier);

        int score = (int) (maxScore * baseRatio * multiplier);
        result.setScore(score);
        applyBonus(result);

        log.info("Final score for " + result.getStudentNick() + "/" + task.getId()
                + ": " + result.getTotalScore() + " (base=" + score + ", bonus=" + result.getBonusScore() + ")");
    }

    private void applyBonus(TaskResult result) {
        int bonus = settings.getBonusScores().stream()
                .filter(b -> b.getStudentNick().equals(result.getStudentNick())
                        && b.getTaskId().equals(result.getTaskId()))
                .mapToInt(BonusScore::getScore)
                .sum();
        result.setBonusScore(bonus);
    }

    public void calculateStudentReport(StudentReport report, CheckerConfig config) {
        int totalScore = report.getTaskResults().stream()
                .mapToInt(TaskResult::getTotalScore)
                .sum();
        report.setTotalScore(totalScore);

        int maxPossibleScore = report.getTaskResults().stream()
                .map(r -> config.findTask(r.getTaskId()))
                .filter(t -> t != null)
                .mapToInt(Task::getMaxScore)
                .sum();

        for (ControlPoint cp : config.getControlPoints()) {
            int cpScore = report.getTaskResults().stream()
                    .filter(r -> {
                        Task t = config.findTask(r.getTaskId());
                        return t == null || t.getSoftDeadline() == null
                                || !t.getSoftDeadline().isAfter(cp.getDate());
                    })
                    .mapToInt(TaskResult::getTotalScore)
                    .sum();
            int cpMax = report.getTaskResults().stream()
                    .filter(r -> {
                        Task t = config.findTask(r.getTaskId());
                        return t == null || t.getSoftDeadline() == null
                                || !t.getSoftDeadline().isAfter(cp.getDate());
                    })
                    .map(r -> config.findTask(r.getTaskId()))
                    .filter(t -> t != null)
                    .mapToInt(Task::getMaxScore)
                    .sum();
            if (cpMax > 0) {
                report.setControlPointGrade(cp.getName(), settings.calculateGrade(cpScore, cpMax));
            }
        }

        int finalGrade = settings.calculateGrade(totalScore, maxPossibleScore);
        report.setFinalGrade(finalGrade);
    }
}