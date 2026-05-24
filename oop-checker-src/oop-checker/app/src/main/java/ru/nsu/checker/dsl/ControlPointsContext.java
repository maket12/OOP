package ru.nsu.checker.dsl;

import groovy.lang.Closure;
import ru.nsu.checker.model.CheckerConfig;
import ru.nsu.checker.model.ControlPoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControlPointsContext {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final CheckerConfig config;

    public ControlPointsContext(CheckerConfig config) {
        this.config = config;
    }

    public void controlPoint(Closure<?> closure) {
        ControlPointContext ctx = new ControlPointContext();
        closure.setDelegate(ctx);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addControlPoint(ctx.build());
    }

    public static class ControlPointContext {
        private final ControlPoint cp = new ControlPoint();

        public void name(String name) { cp.setName(name); }
        public void date(String date) { cp.setDate(LocalDate.parse(date, DATE_FORMAT)); }

        public ControlPoint build() { return cp; }
    }
}
