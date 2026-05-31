package ru.nsu.checker.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ProcessRunner {
    private static final Logger log = Logger.getLogger(ProcessRunner.class.getName());

    public record ProcessResult(int exitCode, String stdout, String stderr) {
        public boolean isSuccess() { return exitCode == 0; }
    }

    public ProcessResult run(List<String> command, File workingDir, int timeoutSeconds) {
        log.info("Running command: " + String.join(" ", command) + " in " + workingDir);
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(workingDir);
            pb.redirectErrorStream(false);

            // === ВОТ ЭТОТ БЛОК РЕШАЕТ ТВОЮ ПРОБЛЕМУ ===
            // Берем путь к JVM (Java 17), на которой крутится сам чекер,
            // и прокидываем его как JAVA_HOME для вызываемого процесса gradlew.bat
            String currentJavaHome = System.getProperty("java.home");
            if (currentJavaHome != null) {
                pb.environment().put("JAVA_HOME", currentJavaHome);
            }
            // ==========================================

            Process process = pb.start();

            // === ВНИМАНИЕ: КРИТИЧЕСКИЙ БАГФИКС ТАЙМАУТА ===
            // У тебя readAllBytes() вызывался ДО process.waitFor().
            // Метод readAllBytes() — блокирующий. Если у студента в коде будет
            // бесконечный цикл, чекер зависнет на строке чтения НАВСЕГДА,
            // а до проверки таймаута (waitFor) код даже не дойдет.
            // Поэтому сначала ждем завершения (или таймаута), а потом читаем логи.

            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                log.warning("Process timed out after " + timeoutSeconds + "s: " + String.join(" ", command));
                return new ProcessResult(1, "", "Process timed out after " + timeoutSeconds + "s");
            }

            // Читаем потоки только после того, как процесс успешно завершился
            byte[] stdout = process.getInputStream().readAllBytes();
            byte[] stderr = process.getErrorStream().readAllBytes();

            int exitCode = process.exitValue();
            return new ProcessResult(exitCode, new String(stdout), new String(stderr));
        } catch (IOException | InterruptedException e) {
            log.severe("Failed to run process: " + e.getMessage());
            Thread.currentThread().interrupt();
            return new ProcessResult(1, "", e.getMessage());
        }
    }

    public ProcessResult run(List<String> command, File workingDir) {
        return run(command, workingDir, 300);
    }
}
