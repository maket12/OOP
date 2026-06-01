package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import ru.nsu.checker.model.CheckerConfig;
import ru.nsu.checker.model.Group;
import ru.nsu.checker.model.Student;

public class GroupsContext {
    private final CheckerConfig config;

    public GroupsContext(CheckerConfig config) {
        this.config = config;
    }

    public void group(String name, Closure<?> closure) {
        GroupContext ctx = new GroupContext(name);
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addGroup(ctx.build());
    }

    public static class GroupContext {
        private final Group group;

        public GroupContext(String name) {
            this.group = new Group(name);
        }

        public void student(Closure<?> closure) {
            StudentContext ctx = new StudentContext();
            closure.setDelegate(ctx);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();
            group.addStudent(ctx.build());
        }

        public Group build() { return group; }
    }

    public static class StudentContext {
        private final Student student = new Student();

        public void githubNick(String nick) { student.setGithubNick(nick); }
        public void fullName(String name) { student.setFullName(name); }
        public void repository(String url) { student.setRepositoryUrl(url); }

        public Student build() { return student; }
    }
}
