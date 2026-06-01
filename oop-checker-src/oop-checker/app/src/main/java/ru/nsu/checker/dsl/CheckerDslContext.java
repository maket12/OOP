package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import ru.nsu.checker.model.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class CheckerDslContext {
    private static final Logger log = Logger.getLogger(CheckerDslContext.class.getName());

    private final CheckerConfig config;
    private final File configDir;

    public CheckerDslContext(CheckerConfig config, File configDir) {
        this.config = config;
        this.configDir = configDir;
    }

    public void importConfig(String filename) throws IOException {
        log.info("Importing configuration from: " + filename);
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
