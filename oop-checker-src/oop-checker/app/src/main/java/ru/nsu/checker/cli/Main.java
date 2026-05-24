package ru.nsu.checker.cli;

import ru.nsu.checker.dsl.ConfigLoader;
import ru.nsu.checker.model.CheckerConfig;
import ru.nsu.checker.model.StudentReport;
import ru.nsu.checker.report.HtmlReportGenerator;
import ru.nsu.checker.service.CheckerService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        CommandLineArgs cli = CommandLineArgs.parse(args);
        if (cli == null) {
            printUsage();
            System.exit(1);
        }

        File workingDir = cli.workingDir() != null ? new File(cli.workingDir()) : new File(".");
        String configFile = cli.configFile() != null ? cli.configFile() : ConfigLoader.DEFAULT_CONFIG_FILENAME;

        try {
            ConfigLoader loader = new ConfigLoader();
            CheckerConfig config = loader.load(workingDir, configFile);

            switch (cli.command()) {
                case "run" -> runChecks(config, workingDir, cli);
                case "report" -> generateReportOnly(config);
                default -> {
                    System.err.println("Unknown command: " + cli.command());
                    printUsage();
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            log.severe("Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runChecks(CheckerConfig config, File workingDir, CommandLineArgs cli) {
        File workspaceDir = new File(workingDir, cli.workspace() != null ? cli.workspace() : ".oop-checker-workspace");
        CheckerService service = new CheckerService(workspaceDir, config.getSettings());

        log.info("Starting OOP checker...");
        List<StudentReport> reports = service.runChecks(config);

        HtmlReportGenerator generator = new HtmlReportGenerator();
        String html = generator.generate(reports, config);
        System.out.println(html);

        log.info("Check complete. Reports generated for " + reports.size() + " students.");
    }

    private static void generateReportOnly(CheckerConfig config) {
        HtmlReportGenerator generator = new HtmlReportGenerator();
        String html = generator.generate(List.of(), config);
        System.out.println(html);
    }

    private static void printUsage() {
        System.err.println("Usage: oop-checker [options] <command>");
        System.err.println("Commands:");
        System.err.println("  run     - Clone/pull repositories, run checks and generate HTML report");
        System.err.println("  report  - Generate empty report structure from config");
        System.err.println("Options:");
        System.err.println("  --config <file>      Config file name (default: checker.groovy)");
        System.err.println("  --dir <directory>    Working directory (default: current directory)");
        System.err.println("  --workspace <dir>    Directory for cloned repos (default: .oop-checker-workspace)");
    }
}
