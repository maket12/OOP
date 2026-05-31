package ru.nsu.checker.model;

public class GradeThreshold {
    private int minScore;
    private int grade;

    public GradeThreshold() {}

    public GradeThreshold(int minScore, int grade) {
        this.minScore = minScore;
        this.grade = grade;
    }

    public int getMinScore() { return minScore; }
    public void setMinScore(int minScore) { this.minScore = minScore; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }
}
