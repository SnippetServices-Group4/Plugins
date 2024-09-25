package com.services.group4;

import org.gradle.api.Project;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckstylePluginTest {
  @Test
  void pluginRegistersCheckstyleTask() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("com.services.group4.checkstyle");

    assertTrue(project.getPlugins().hasPlugin("checkstyle"));
    assertNotNull(project.getTasks().findByName("checkstyleMain"));
    assertNotNull(project.getTasks().findByName("checkstyleTest"));
    assertFalse(project.getTasks().withType(Checkstyle.class).isEmpty());
  }
}
