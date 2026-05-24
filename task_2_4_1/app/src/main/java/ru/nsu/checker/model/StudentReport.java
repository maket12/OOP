package ru.nsu.checker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentReport {
    private Student student;
    private String groupName;
    private List<TaskResult> taskResults = new ArrayList<>();
    private Map<String, Integer> controlPointGrades = new TreeMap<>();
    private int finalGrade;
    private int totalScore;

    public StudentReport(Student student, String groupName) {
        this.student = student;
        this.groupName = groupName;
    }

    public Student getStudent() { return student; }
    public String getGroupName() { return groupName; }

    public List<TaskResult> getTaskResults() { return taskResults; }
    public void addTaskResult(TaskResult result) { taskResults.add(result); }

    public Map<String, Integer> getControlPointGrades() { return controlPointGrades; }
    public void setControlPointGrade(String cpName, int grade) { controlPointGrades.put(cpName, grade); }

    public int getFinalGrade() { return finalGrade; }
    public void setFinalGrade(int finalGrade) { this.finalGrade = finalGrade; }

    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public TaskResult getTaskResult(String taskId) {
        return taskResults.stream()
                .filter(r -> r.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
    }
}
