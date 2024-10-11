package com.services.group4;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension;
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification;
import org.gradle.testing.jacoco.tasks.JacocoReport;
import java.math.BigDecimal;

public class JacocoPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    // Apply 'jacoco' plugin
    project.getPluginManager().apply("jacoco");

    // Jacoco configuration
    project.getExtensions().configure(JacocoPluginExtension.class, jacoco -> {
      jacoco.setToolVersion("0.8.12");
    });

    // Configure Jacoco report after tests
    project.getTasks().matching(task -> task.getName().equals("test")).configureEach(test -> {
      test.finalizedBy(project.getTasks().withType(JacocoReport.class)); // Report after tests
    });

    // Configure Jacoco report
    project.getTasks().withType(JacocoReport.class).configureEach(report -> {
      report.dependsOn("test");
      report.getReports().getHtml().getRequired().set(true);
      report.getReports().getHtml().getOutputLocation().set(project.file(project.getRootDir() + "/reports/jacoco/" + project.getName()));
    });

    // Configure Jacoco coverage verification
    project.getTasks().withType(JacocoCoverageVerification.class).configureEach(verification -> {
      verification.violationRules(rules -> {
        rules.rule(rule -> {
          rule.limit(limit -> {
            limit.setMinimum(BigDecimal.valueOf(0.8));  // Minimum 80% coverage
          });
        });
      });
    });

    // Add Jacoco coverage verification to build task if it exists
    project.getTasks().matching(task -> task.getName().equals("build")).configureEach(task -> {
      task.dependsOn(project.getTasks().withType(JacocoCoverageVerification.class));
    });
  }
}