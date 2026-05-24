package ru.nsu.checker.service;

import ru.nsu.checker.model.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CheckerService {
    private static final Logger log = Logger.getLogger(CheckerService.class.getName());

    private final GitService gitService;
    private final GradleTaskRunner gradleRunner;
    private final ScoreCalculator scoreCalculator;
    private final File workspaceDir;

    public CheckerService(File workspaceDir, SystemSettings settings) {
        this.workspaceDir = workspaceDir;
        ProcessRunner runner = new ProcessRunner();
        this.gitService = new GitService(runner, workspaceDir);
        this.gradleRunner = new GradleTaskRunner(runner);
        this.scoreCalculator = new ScoreCalculator(settings);
    }

    public List<StudentReport> runChecks(CheckerConfig config) {
        List<StudentReport> reports = new ArrayList<>();
        Map<String, List<String>> studentTaskMap = config.getStudentTaskMap();

        for (Map.Entry<String, List<String>> entry : studentTaskMap.entrySet()) {
            String nick = entry.getKey();
            List<String> taskIds = entry.getValue();

            Student student = config.findStudent(nick);
            if (student == null) {
                log.warning("Student not found in config: " + nick);
                continue;
            }

            String groupName = config.findStudentGroup(nick);
            StudentReport report = new StudentReport(student, groupName);

            log.info("Processing student: " + nick);
            File repoDir = gitService.cloneOrPull(nick, student.getRepositoryUrl());

            if (repoDir == null) {
                for (String taskId : taskIds) {
                    TaskResult result = new TaskResult(nick, taskId);
                    result.setStatus(TaskResult.Status.CLONE_FAILED);
                    result.setErrorMessage("Repository clone failed");
                    report.addTaskResult(result);
                }
                reports.add(report);
                continue;
            }

            gitService.checkoutMainBranch(repoDir);

            for (String taskId : taskIds) {
                TaskResult taskResult = checkTask(nick, taskId, repoDir, config);
                Task task = config.findTask(taskId);
                if (task != null) {
                    scoreCalculator.calculate(taskResult, task, LocalDate.now());
                }
                report.addTaskResult(taskResult);
            }

            scoreCalculator.calculateStudentReport(report, config);
            reports.add(report);
        }

        return reports;
    }

    private TaskResult checkTask(String nick, String taskId, File repoDir, CheckerConfig config) {
        TaskResult result = new TaskResult(nick, taskId);
        int timeout = config.getSettings().getTestTimeoutSeconds();

        File taskDir = findTaskDirectory(repoDir, taskId);
        if (taskDir == null) {
            log.warning("Task directory not found for " + taskId + " in " + repoDir);
            result.setStatus(TaskResult.Status.COMPILE_FAILED);
            result.setErrorMessage("Task directory not found: " + taskId);
            return result;
        }

        log.info("Compiling " + taskId + " for " + nick);
        boolean compiled = gradleRunner.compile(taskDir, timeout);
        result.setCompiled(compiled);

        if (!compiled) {
            result.setStatus(TaskResult.Status.COMPILE_FAILED);
            result.setErrorMessage("Compilation failed");
            return result;
        }

        log.info("Generating Javadoc for " + taskId + " / " + nick);
        boolean docGenerated = gradleRunner.generateJavadoc(taskDir, timeout);
        result.setDocGenerated(docGenerated);

        log.info("Running Checkstyle for " + taskId + " / " + nick);
        boolean styleOk = gradleRunner.runCheckstyle(taskDir, timeout);
        result.setStyleCheckPassed(styleOk);

        log.info("Running tests for " + taskId + " / " + nick);
        gradleRunner.runTests(taskDir, result, timeout);
        result.setStatus(TaskResult.Status.SUCCESS);

        return result;
    }

    private File findTaskDirectory(File repoDir, String taskId) {
        File direct = new File(repoDir, taskId);
        if (direct.exists() && direct.isDirectory()) return direct;

        File[] children = repoDir.listFiles();
        if (children == null) return null;

        for (File child : children) {
            if (child.isDirectory() && child.getName().toLowerCase().contains(taskId.toLowerCase())) {
                return child;
            }
        }

        File gradleFile = new File(repoDir, "build.gradle");
        File gradleKts = new File(repoDir, "build.gradle.kts");
        if (gradleFile.exists() || gradleKts.exists()) {
            return repoDir;
        }

        return null;
    }
}
