package com.services.group4;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.tasks.SourceSetContainer;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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

    // Load the rules.xml from the classpath
    try (InputStream rulesConfigStream = getClass().getClassLoader().getResourceAsStream("com/services/group4/rules.xml")) {
      if (rulesConfigStream != null) {
        File tempRulesFile = File.createTempFile("rules", ".xml");
        tempRulesFile.deleteOnExit();
        Files.copy(rulesConfigStream, tempRulesFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        checkstyle.setConfigFile(tempRulesFile);
      } else {
        throw new IllegalStateException("Could not find rules.xml in the classpath");
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load rules.xml", e);
    }

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
