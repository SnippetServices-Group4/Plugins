package com.services.group4;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpotlessPluginTest {

  @Test
  public void pluginRegistersExtension() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("com.services.group4.spotless");

    assertNotNull(project.getExtensions().findByName("spotless"));
  }
}
