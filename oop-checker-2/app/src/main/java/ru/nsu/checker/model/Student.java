package ru.nsu.checker.model;

public class Student {
    private String githubNick;
    private String fullName;
    private String repositoryUrl;

    public Student() {}

    public Student(String githubNick, String fullName, String repositoryUrl) {
        this.githubNick = githubNick;
        this.fullName = fullName;
        this.repositoryUrl = repositoryUrl;
    }

    public String getGithubNick() { return githubNick; }
    public void setGithubNick(String githubNick) { this.githubNick = githubNick; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRepositoryUrl() { return repositoryUrl; }
    public void setRepositoryUrl(String repositoryUrl) { this.repositoryUrl = repositoryUrl; }

    @Override
    public String toString() {
        return "Student{nick='" + githubNick + "', name='" + fullName + "'}";
    }
}
