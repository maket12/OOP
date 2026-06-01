package ru.nsu.checker.report;

import ru.nsu.checker.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HtmlReportGenerator {

    public String generate(List<StudentReport> reports, CheckerConfig config) {
        StringBuilder html = new StringBuilder();
        html.append(buildHeader());
        html.append(buildSummaryTable(reports, config));
        for (StudentReport report : reports) {
            html.append(buildStudentSection(report, config));
        }
        html.append(buildFooter());
        return html.toString();
    }

    private String buildHeader() {
        String date = LocalDate.now().toString();
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "  <meta charset=\"UTF-8\">\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "  <title>OOP Course Report</title>\n"
                + "  <style>\n"
                + "    body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }\n"
                + "    h1, h2, h3 { color: #333; }\n"
                + "    table { border-collapse: collapse; width: 100%; margin-bottom: 20px; background: white; }\n"
                + "    th, td { border: 1px solid #ddd; padding: 8px 12px; text-align: left; }\n"
                + "    th { background-color: #4a90d9; color: white; }\n"
                + "    tr:nth-child(even) { background-color: #f9f9f9; }\n"
                + "    .card { background: white; border-radius: 8px; padding: 20px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n"
                + "    .badge { display: inline-block; padding: 2px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; }\n"
                + "    .badge-success { background: #28a745; color: white; }\n"
                + "    .badge-fail { background: #dc3545; color: white; }\n"
                + "    .badge-warn { background: #ffc107; color: black; }\n"
                + "    .grade-5 { color: #28a745; font-weight: bold; }\n"
                + "    .grade-4 { color: #17a2b8; font-weight: bold; }\n"
                + "    .grade-3 { color: #ffc107; font-weight: bold; }\n"
                + "    .grade-2 { color: #dc3545; font-weight: bold; }\n"
                + "    .status-ok { color: green; }\n"
                + "    .status-fail { color: red; }\n"
                + "  </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <h1>OOP Course Automated Checker Report</h1>\n"
                + "  <p><em>Generated: " + date + "</em></p>\n";
    }

    private String buildSummaryTable(List<StudentReport> reports, CheckerConfig config) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"card\"><h2>Summary</h2>\n");
        sb.append("<table>\n<thead><tr>");
        sb.append("<th>Student</th><th>Group</th><th>Total Score</th>");

        for (ControlPoint cp : config.getControlPoints()) {
            sb.append("<th>").append(escape(cp.getName())).append("</th>");
        }
        sb.append("<th>Final Grade</th></tr></thead>\n<tbody>\n");

        for (StudentReport report : reports) {
            sb.append("<tr>");
            sb.append("<td>").append(escape(report.getStudent().getFullName())).append("</td>");
            sb.append("<td>").append(escape(report.getGroupName())).append("</td>");
            sb.append("<td>").append(report.getTotalScore()).append("</td>");

            for (ControlPoint cp : config.getControlPoints()) {
                Integer grade = report.getControlPointGrades().get(cp.getName());
                String gradeStr = grade != null ? String.valueOf(grade) : "-";
                String cls = grade != null ? "grade-" + grade : "";
                sb.append("<td><span class=\"").append(cls).append("\">").append(gradeStr).append("</span></td>");
            }

            String finalCls = "grade-" + report.getFinalGrade();
            sb.append("<td><span class=\"").append(finalCls).append("\">")
                    .append(report.getFinalGrade()).append("</span></td>");
            sb.append("</tr>\n");
        }

        sb.append("</tbody></table></div>\n");
        return sb.toString();
    }

    private String buildStudentSection(StudentReport report, CheckerConfig config) {
        StringBuilder sb = new StringBuilder();
        Student student = report.getStudent();

        sb.append("<div class=\"card\">\n");
        sb.append("<h2>").append(escape(student.getFullName()))
                .append(" (<code>").append(escape(student.getGithubNick())).append("</code>)</h2>\n");
        sb.append("<p>Group: <strong>").append(escape(report.getGroupName())).append("</strong> | ");
        sb.append("Repository: <a href=\"").append(escape(student.getRepositoryUrl())).append("\">")
                .append(escape(student.getRepositoryUrl())).append("</a></p>\n");
        sb.append("<p>Total Score: <strong>").append(report.getTotalScore()).append("</strong> | ");
        sb.append("Final Grade: <strong class=\"grade-").append(report.getFinalGrade()).append("\">")
                .append(report.getFinalGrade()).append("</strong></p>\n");

        sb.append(buildTaskResultsTable(report, config));
        sb.append("</div>\n");
        return sb.toString();
    }

    private String buildTaskResultsTable(StudentReport report, CheckerConfig config) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Task Results</h3>\n");
        sb.append("<table>\n<thead><tr>");
        sb.append("<th>Task</th><th>Status</th><th>Compiled</th><th>Javadoc</th>");
        sb.append("<th>Style</th><th>Tests Passed</th><th>Tests Failed</th>");
        sb.append("<th>Tests Skipped</th><th>Score</th><th>Bonus</th><th>Total</th>");
        sb.append("</tr></thead>\n<tbody>\n");

        for (TaskResult result : report.getTaskResults()) {
            Task task = config.findTask(result.getTaskId());
            String taskName = task != null ? task.getName() : result.getTaskId();
            int maxScore = task != null ? task.getMaxScore() : 0;

            sb.append("<tr>");
            sb.append("<td>").append(escape(taskName)).append(" <small>(").append(result.getTaskId()).append(")</small></td>");
            sb.append("<td>").append(statusBadge(result.getStatus())).append("</td>");
            sb.append("<td>").append(boolIcon(result.isCompiled())).append("</td>");
            sb.append("<td>").append(boolIcon(result.isDocGenerated())).append("</td>");
            sb.append("<td>").append(boolIcon(result.isStyleCheckPassed())).append("</td>");
            sb.append("<td class=\"status-ok\">").append(result.getTestsPassed()).append("</td>");
            sb.append("<td class=\"status-fail\">").append(result.getTestsFailed()).append("</td>");
            sb.append("<td>").append(result.getTestsSkipped()).append("</td>");
            sb.append("<td>").append(result.getScore()).append(" / ").append(maxScore).append("</td>");
            sb.append("<td>").append(result.getBonusScore() > 0 ? "+" + result.getBonusScore() : "0").append("</td>");
            sb.append("<td><strong>").append(result.getTotalScore()).append("</strong></td>");
            sb.append("</tr>\n");

            if (!result.getErrorMessage().isEmpty()) {
                sb.append("<tr><td colspan=\"11\" style=\"color:red;font-style:italic;\">Error: ")
                        .append(escape(result.getErrorMessage())).append("</td></tr>\n");
            }
        }

        sb.append("</tbody></table>\n");
        return sb.toString();
    }

    private String buildFooter() {
        return "</body>\n</html>\n";
    }

    private String statusBadge(TaskResult.Status status) {
        return switch (status) {
            case SUCCESS -> "<span class=\"badge badge-success\">SUCCESS</span>";
            case COMPILE_FAILED -> "<span class=\"badge badge-fail\">COMPILE FAILED</span>";
            case CLONE_FAILED -> "<span class=\"badge badge-fail\">CLONE FAILED</span>";
            case NOT_CHECKED -> "<span class=\"badge badge-warn\">NOT CHECKED</span>";
        };
    }

    private String boolIcon(boolean value) {
        return value ? "<span class=\"status-ok\">&#10003;</span>" : "<span class=\"status-fail\">&#10007;</span>";
    }

    private String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
