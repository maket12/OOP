package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import ru.nsu.checker.model.*;

public class SettingsContext {
    private final CheckerConfig config;

    public SettingsContext(CheckerConfig config) {
        this.config = config;
    }

    public void testTimeoutSeconds(int timeout) {
        config.getSettings().setTestTimeoutSeconds(timeout);
    }

    public void gradeThresholds(Closure<?> closure) {
        GradeThresholdsContext ctx = new GradeThresholdsContext(config.getSettings());
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        config.getSettings().getGradeThresholds().clear();
        closure.call();
    }

    public void bonus(Closure<?> closure) {
        BonusContext ctx = new BonusContext();
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.getSettings().addBonusScore(ctx.build());
    }

    public static class GradeThresholdsContext {
        private final SystemSettings settings;

        public GradeThresholdsContext(SystemSettings settings) {
            this.settings = settings;
        }

        public void threshold(int minScore, int grade) {
            settings.addGradeThreshold(new GradeThreshold(minScore, grade));
        }
    }

    public static class BonusContext {
        private final BonusScore bonus = new BonusScore();

        public void student(String nick) { bonus.setStudentNick(nick); }
        public void task(String taskId) { bonus.setTaskId(taskId); }
        public void score(int score) { bonus.setScore(score); }
        public void reason(String reason) { bonus.setReason(reason); }

        public BonusScore build() { return bonus; }
    }
}
