package ru.nsu.checker.service;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class GitService {
    private static final Logger log = Logger.getLogger(GitService.class.getName());

    private final ProcessRunner runner;
    private final File workspaceDir;

    public GitService(ProcessRunner runner, File workspaceDir) {
        this.runner = runner;
        this.workspaceDir = workspaceDir;
        workspaceDir.mkdirs();
    }

    public File cloneOrPull(String studentNick, String repositoryUrl) {
        File repoDir = new File(workspaceDir, studentNick);
        if (repoDir.exists()) {
            log.info("Pulling existing repository for " + studentNick);
            ProcessRunner.ProcessResult result = runner.run(
                    List.of("git", "pull", "--ff-only"),
                    repoDir
            );
            if (!result.isSuccess()) {
                log.warning("Pull failed for " + studentNick + ", trying reset: " + result.stderr());
                runner.run(List.of("git", "fetch", "origin"), repoDir);
                runner.run(List.of("git", "reset", "--hard", "origin/HEAD"), repoDir);
            }
        } else {
            log.info("Cloning repository for " + studentNick + " from " + repositoryUrl);
            ProcessRunner.ProcessResult result = runner.run(
                    List.of("git", "clone", repositoryUrl, repoDir.getAbsolutePath()),
                    workspaceDir
            );
            if (!result.isSuccess()) {
                log.severe("Clone failed for " + studentNick + ": " + result.stderr());
                return null;
            }
        }
        return repoDir;
    }

    public boolean checkoutMainBranch(File repoDir) {
        ProcessRunner.ProcessResult mainResult = runner.run(
                List.of("git", "checkout", "main"),
                repoDir
        );
        if (mainResult.isSuccess()) {
            return true;
        }
        ProcessRunner.ProcessResult masterResult = runner.run(
                List.of("git", "checkout", "master"),
                repoDir
        );
        return masterResult.isSuccess();
    }

    public boolean isGitConfigured() {
        ProcessRunner.ProcessResult result = runner.run(
                List.of("git", "config", "--global", "user.name"),
                new File(System.getProperty("user.home"))
        );
        return result.isSuccess() && !result.stdout().isBlank();
    }
}
