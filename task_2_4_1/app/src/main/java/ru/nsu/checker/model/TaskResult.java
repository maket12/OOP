package ru.nsu.checker.model;

public class TaskResult {
    public enum Status { NOT_CHECKED, CLONE_FAILED, COMPILE_FAILED, SUCCESS }

    private String studentNick;
    private String taskId;
    private Status status = Status.NOT_CHECKED;
    private boolean compiled = false;
    private boolean docGenerated = false;
    private boolean styleCheckPassed = false;
    private int testsPassed = 0;
    private int testsFailed = 0;
    private int testsSkipped = 0;
    private int score = 0;
    private int bonusScore = 0;
    private String errorMessage = "";

    public TaskResult(String studentNick, String taskId) {
        this.studentNick = studentNick;
        this.taskId = taskId;
    }

    public String getStudentNick() { return studentNick; }
    public String getTaskId() { return taskId; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public boolean isCompiled() { return compiled; }
    public void setCompiled(boolean compiled) { this.compiled = compiled; }

    public boolean isDocGenerated() { return docGenerated; }
    public void setDocGenerated(boolean docGenerated) { this.docGenerated = docGenerated; }

    public boolean isStyleCheckPassed() { return styleCheckPassed; }
    public void setStyleCheckPassed(boolean styleCheckPassed) { this.styleCheckPassed = styleCheckPassed; }

    public int getTestsPassed() { return testsPassed; }
    public void setTestsPassed(int testsPassed) { this.testsPassed = testsPassed; }

    public int getTestsFailed() { return testsFailed; }
    public void setTestsFailed(int testsFailed) { this.testsFailed = testsFailed; }

    public int getTestsSkipped() { return testsSkipped; }
    public void setTestsSkipped(int testsSkipped) { this.testsSkipped = testsSkipped; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getBonusScore() { return bonusScore; }
    public void setBonusScore(int bonusScore) { this.bonusScore = bonusScore; }

    public int getTotalScore() { return score + bonusScore; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
