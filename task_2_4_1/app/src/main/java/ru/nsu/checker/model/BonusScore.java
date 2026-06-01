package ru.nsu.checker.model;

public class BonusScore {
    private String studentNick;
    private String taskId;
    private int score;
    private String reason;

    public BonusScore() {}

    public BonusScore(String studentNick, String taskId, int score, String reason) {
        this.studentNick = studentNick;
        this.taskId = taskId;
        this.score = score;
        this.reason = reason;
    }

    public String getStudentNick() { return studentNick; }
    public void setStudentNick(String studentNick) { this.studentNick = studentNick; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
