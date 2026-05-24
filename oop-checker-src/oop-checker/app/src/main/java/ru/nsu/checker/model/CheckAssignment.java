package ru.nsu.checker.model;

import java.util.ArrayList;
import java.util.List;

public class CheckAssignment {
    private String studentNick;
    private List<String> taskIds = new ArrayList<>();

    public CheckAssignment() {}

    public CheckAssignment(String studentNick, List<String> taskIds) {
        this.studentNick = studentNick;
        this.taskIds = new ArrayList<>(taskIds);
    }

    public String getStudentNick() { return studentNick; }
    public void setStudentNick(String studentNick) { this.studentNick = studentNick; }

    public List<String> getTaskIds() { return taskIds; }
    public void setTaskIds(List<String> taskIds) { this.taskIds = taskIds; }

    public void addTaskId(String taskId) {
        this.taskIds.add(taskId);
    }
}
