package ru.nsu.checker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CheckerConfig {
    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<CheckAssignment> assignments = new ArrayList<>();
    private List<ControlPoint> controlPoints = new ArrayList<>();
    private SystemSettings settings = new SystemSettings();

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
    public void addTask(Task task) { tasks.add(task); }

    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { this.groups = groups; }
    public void addGroup(Group group) { groups.add(group); }

    public List<CheckAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<CheckAssignment> assignments) { this.assignments = assignments; }
    public void addAssignment(CheckAssignment assignment) { assignments.add(assignment); }

    public List<ControlPoint> getControlPoints() { return controlPoints; }
    public void setControlPoints(List<ControlPoint> controlPoints) { this.controlPoints = controlPoints; }
    public void addControlPoint(ControlPoint controlPoint) { controlPoints.add(controlPoint); }

    public SystemSettings getSettings() { return settings; }
    public void setSettings(SystemSettings settings) { this.settings = settings; }

    public Task findTask(String taskId) {
        return tasks.stream().filter(t -> t.getId().equals(taskId)).findFirst().orElse(null);
    }

    public Student findStudent(String nick) {
        return groups.stream()
                .flatMap(g -> g.getStudents().stream())
                .filter(s -> s.getGithubNick().equals(nick))
                .findFirst()
                .orElse(null);
    }

    public String findStudentGroup(String nick) {
        return groups.stream()
                .filter(g -> g.getStudents().stream().anyMatch(s -> s.getGithubNick().equals(nick)))
                .map(Group::getName)
                .findFirst()
                .orElse("Unknown");
    }

    public Map<String, List<String>> getStudentTaskMap() {
        Map<String, List<String>> map = new HashMap<>();
        for (CheckAssignment assignment : assignments) {
            map.put(assignment.getStudentNick(), assignment.getTaskIds());
        }
        return map;
    }
}
