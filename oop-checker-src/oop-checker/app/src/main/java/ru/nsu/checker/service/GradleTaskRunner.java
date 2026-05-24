package ru.nsu.checker.service;

import ru.nsu.checker.model.TaskResult;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleTaskRunner {
    private static final Logger log = Logger.getLogger(GradleTaskRunner.class.getName());

    private static final Pattern TESTS_PASSED = Pattern.compile("(\\d+) tests? completed");
    private static final Pattern TESTS_FAILED = Pattern.compile("(\\d+) (?:tests? )?failed");
    private static final Pattern TESTS_SKIPPED = Pattern.compile("(\\d+) (?:tests? )?skipped");

    private final ProcessRunner runner;

    public GradleTaskRunner(ProcessRunner runner) {
        this.runner = runner;
    }

    public boolean compile(File taskDir, int timeoutSeconds) {
        log.info("Compiling in " + taskDir);
        String gradlew = resolveGradlew(taskDir);
        ProcessRunner.ProcessResult result = runner.run(
                List.of(gradlew, "compileJava", "--no-daemon"),
                taskDir,
                timeoutSeconds
        );
        if (!result.isSuccess()) {
            log.warning("Compilation failed in " + taskDir + ": " + result.stderr());
        }
        return result.isSuccess();
    }

    public boolean generateJavadoc(File taskDir, int timeoutSeconds) {
        log.info("Generating Javadoc in " + taskDir);
        String gradlew = resolveGradlew(taskDir);
        ProcessRunner.ProcessResult result = runner.run(
                List.of(gradlew, "javadoc", "--no-daemon"),
                taskDir,
                timeoutSeconds
        );
        return result.isSuccess();
    }

    public boolean runCheckstyle(File taskDir, int timeoutSeconds) {
        log.info("Running Checkstyle in " + taskDir);
        String gradlew = resolveGradlew(taskDir);
        ProcessRunner.ProcessResult result = runner.run(
                List.of(gradlew, "checkstyleMain", "--no-daemon"),
                taskDir,
                timeoutSeconds
        );
        return result.isSuccess();
    }

    public void runTests(File taskDir, TaskResult taskResult, int timeoutSeconds) {
        log.info("Running tests in " + taskDir);
        String gradlew = resolveGradlew(taskDir);
        ProcessRunner.ProcessResult result = runner.run(
                List.of(gradlew, "test", "--no-daemon"),
                taskDir,
                timeoutSeconds
        );

        parseTestResults(result.stdout() + "\n" + result.stderr(), taskResult);
    }

    private void parseTestResults(String output, TaskResult taskResult) {
        Matcher passedMatcher = TESTS_PASSED.matcher(output);
        if (passedMatcher.find()) {
            taskResult.setTestsPassed(Integer.parseInt(passedMatcher.group(1)));
        }

        Matcher failedMatcher = TESTS_FAILED.matcher(output);
        if (failedMatcher.find()) {
            taskResult.setTestsFailed(Integer.parseInt(failedMatcher.group(1)));
        }

        Matcher skippedMatcher = TESTS_SKIPPED.matcher(output);
        if (skippedMatcher.find()) {
            taskResult.setTestsSkipped(Integer.parseInt(skippedMatcher.group(1)));
        }
    }

    private String resolveGradlew(File taskDir) {
        File gradlewUnix = new File(taskDir, "gradlew");
        File gradlewWin = new File(taskDir, "gradlew.bat");
        if (gradlewUnix.exists()) return "./gradlew";
        if (gradlewWin.exists()) return "gradlew.bat";
        return "gradle";
    }
}
