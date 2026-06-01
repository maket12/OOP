package ru.nsu.checker.model;

import java.util.ArrayList;
import java.util.List;

public class SystemSettings {
    private int testTimeoutSeconds = 60;
    private List<GradeThreshold> gradeThresholds = new ArrayList<>();
    private List<BonusScore> bonusScores = new ArrayList<>();

    public SystemSettings() {
        gradeThresholds.add(new GradeThreshold(85, 5));
        gradeThresholds.add(new GradeThreshold(70, 4));
        gradeThresholds.add(new GradeThreshold(50, 3));
        gradeThresholds.add(new GradeThreshold(0, 2));
    }

    public int getTestTimeoutSeconds() { return testTimeoutSeconds; }
    public void setTestTimeoutSeconds(int testTimeoutSeconds) { this.testTimeoutSeconds = testTimeoutSeconds; }

    public List<GradeThreshold> getGradeThresholds() { return gradeThresholds; }
    public void setGradeThresholds(List<GradeThreshold> gradeThresholds) { this.gradeThresholds = gradeThresholds; }

    public List<BonusScore> getBonusScores() { return bonusScores; }
    public void setBonusScores(List<BonusScore> bonusScores) { this.bonusScores = bonusScores; }

    public void addBonusScore(BonusScore bonusScore) {
        this.bonusScores.add(bonusScore);
    }

    public void addGradeThreshold(GradeThreshold threshold) {
        this.gradeThresholds.add(threshold);
    }

    public int calculateGrade(int totalScore, int maxPossibleScore) {
        if (maxPossibleScore == 0) return 2;
        int percentage = (int) ((totalScore * 100.0) / maxPossibleScore);
        return gradeThresholds.stream()
                .sorted((a, b) -> b.getMinScore() - a.getMinScore())
                .filter(t -> percentage >= t.getMinScore())
                .findFirst()
                .map(GradeThreshold::getGrade)
                .orElse(2);
    }
}
