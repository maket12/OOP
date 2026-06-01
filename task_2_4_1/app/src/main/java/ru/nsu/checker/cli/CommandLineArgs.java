package ru.nsu.checker.cli;

import java.util.logging.Logger;

public record CommandLineArgs(
        String command,
        String configFile,
        String workingDir,
        String workspace
) {
    private static final Logger log = Logger.getLogger(CommandLineArgs.class.getName());

    public static CommandLineArgs parse(String[] args) {
        if (args.length == 0) {
            return null;
        }

        String command = null;
        String configFile = null;
        String workingDir = null;
        String workspace = null;

        int i = 0;
        while (i < args.length) {
            switch (args[i]) {
                case "--config" -> {
                    if (i + 1 >= args.length) {
                        System.err.println("--config requires a value");
                        return null;
                    }
                    configFile = args[++i];
                }
                case "--dir" -> {
                    if (i + 1 >= args.length) {
                        System.err.println("--dir requires a value");
                        return null;
                    }
                    workingDir = args[++i];
                }
                case "--workspace" -> {
                    if (i + 1 >= args.length) {
                        System.err.println("--workspace requires a value");
                        return null;
                    }
                    workspace = args[++i];
                }
                default -> {
                    if (!args[i].startsWith("--")) {
                        command = args[i];
                    } else {
                        System.err.println("Unknown option: " + args[i]);
                        return null;
                    }
                }
            }
            i++;
        }

        if (command == null) {
            System.err.println("No command specified");
            return null;
        }

        log.info("Parsed command: " + command + ", config: " + configFile + ", dir: " + workingDir);
        return new CommandLineArgs(command, configFile, workingDir, workspace);
    }
}
