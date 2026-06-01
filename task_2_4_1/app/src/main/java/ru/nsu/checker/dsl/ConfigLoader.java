package ru.nsu.checker.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.checker.model.CheckerConfig;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConfigLoader {
    private static final Logger log = Logger.getLogger(ConfigLoader.class.getName());

    public static final String DEFAULT_CONFIG_FILENAME = "checker.groovy";

    public CheckerConfig load(File configFile) throws IOException {
        log.info("Loading configuration from: " + configFile.getAbsolutePath());

        CheckerConfig config = new CheckerConfig();

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(CheckerScript.class.getName());

        GroovyShell shell = new GroovyShell(compilerConfig);
        try {
            CheckerScript script = (CheckerScript) shell.parse(configFile);
            script.setCheckerConfig(config);
            script.setConfigDir(configFile.getParentFile());
            script.run();
        } catch (Exception e) {
            throw new IOException("Failed to evaluate config file: " + configFile, e);
        }

        log.info("Configuration loaded: " + config.getTasks().size() + " tasks, "
                + config.getGroups().size() + " groups, "
                + config.getAssignments().size() + " assignments");

        return config;
    }

    public CheckerConfig load(File workingDir, String filename) throws IOException {
        File configFile = new File(workingDir, filename);
        if (!configFile.exists()) {
            throw new IOException("Configuration file not found: " + configFile.getAbsolutePath());
        }
        return load(configFile);
    }

    public CheckerConfig loadDefault(File workingDir) throws IOException {
        return load(workingDir, DEFAULT_CONFIG_FILENAME);
    }
}
