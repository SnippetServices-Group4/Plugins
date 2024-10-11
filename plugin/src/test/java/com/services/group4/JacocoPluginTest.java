package com.services.group4;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JacocoPluginTest {

  @Test
  public void pluginRegistersExtension() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("com.services.group4.jacoco");

    assertNotNull(project.getExtensions().findByType(JacocoPluginExtension.class));
  }
}
