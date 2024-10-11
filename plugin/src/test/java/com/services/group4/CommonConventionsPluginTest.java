package com.services.group4;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonConventionsPluginTest {
  @Test
  void pluginRegistersConventionsTask() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("com.services.group4.common-conventions");

    assertTrue(project.getPlugins().hasPlugin("com.services.group4.java"));
    assertTrue(project.getPlugins().hasPlugin("com.services.group4.checkstyle"));
    assertTrue(project.getPlugins().hasPlugin("com.services.group4.spotless"));
    assertTrue(project.getPlugins().hasPlugin("com.services.group4.jacoco"));
  }
}
