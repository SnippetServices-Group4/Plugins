package com.services.group4;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.jvm.toolchain.JavaLanguageVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaPluginTest {

  @Test
  void testApplyPlugin() {
    Project project = ProjectBuilder.builder().build();
    project.getPluginManager().apply("com.services.group4.java");

    assertTrue(project.getPluginManager().hasPlugin("java"), "Java plugin should be applied");
  }

  @Test
  void testJavaToolchainVersion() {
    Project project = ProjectBuilder.builder().build();
    project.getPluginManager().apply("com.services.group4.java");

    JavaPluginExtension javaExtension = project.getExtensions().getByType(JavaPluginExtension.class);
    assertEquals(JavaLanguageVersion.of(21), javaExtension.getToolchain().getLanguageVersion().get(), "Java toolchain version should be 21");
  }
}
