package com.services.group4;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.tasks.SourceSetContainer;

public class CheckstylePlugin implements Plugin<Project> {
  @Override
  public void apply(Project project) {
    // Apply the Java plugin
    project.getPlugins().apply(JavaPlugin.class);

    // Apply the Checkstyle plugin
    project.getPlugins().apply("checkstyle");

    // Configure the Checkstyle extension
    CheckstyleExtension checkstyle = project.getExtensions().getByType(CheckstyleExtension.class);
    checkstyle.setToolVersion("10.17.0");
    checkstyle.setConfigFile(project.file(project.getRootDir() + "/src/main/java/com/services/group4/rules.xml"));

    // Configure Checkstyle tasks for each source set
    SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
    sourceSets.all(sourceSet -> {
      String taskName = "checkstyle" + capitalize(sourceSet.getName());
      project.getTasks().named(taskName, Checkstyle.class, task -> {
        task.setSource(sourceSet.getAllSource());
        task.getReports().getXml().getRequired().set(false);
        task.getReports().getHtml().getRequired().set(true);
        task.getReports().getHtml().getOutputLocation().set(project.file(project.getRootDir() + "/reports/checkstyle/" + project.getName() + "-" + sourceSet.getName() + ".html"));
      });
    });
  }

  private String capitalize(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
}
