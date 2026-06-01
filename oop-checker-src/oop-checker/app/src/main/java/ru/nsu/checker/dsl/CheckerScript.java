package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;
import ru.nsu.checker.model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class CheckerScript extends Script {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private CheckerConfig config;
    private File configDir;

    public void setCheckerConfig(CheckerConfig config) {
        this.config = config;
    }

    public void setConfigDir(File configDir) {
        this.configDir = configDir;
    }

    public void importConfig(String filename) throws IOException {
        File importFile = new File(configDir, filename);
        if (!importFile.exists()) {
            throw new IOException("Imported config file not found: " + importFile.getAbsolutePath());
        }
        ConfigLoader loader = new ConfigLoader();
        CheckerConfig imported = loader.load(importFile);
        for (Task t : imported.getTasks()) config.addTask(t);
        for (Group g : imported.getGroups()) config.addGroup(g);
        for (CheckAssignment a : imported.getAssignments()) config.addAssignment(a);
        for (ControlPoint cp : imported.getControlPoints()) config.addControlPoint(cp);
    }

    public void tasks(Closure<?> closure) {
        TasksContext ctx = new TasksContext(config);
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void groups(Closure<?> closure) {
        GroupsContext ctx = new GroupsContext(config);
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void check(Closure<?> closure) {
        CheckContext ctx = new CheckContext(config);
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void controlPoints(Closure<?> closure) {
        ControlPointsContext ctx = new ControlPointsContext(config);
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void settings(Closure<?> closure) {
        SettingsContext ctx = new SettingsContext(config);
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
}
