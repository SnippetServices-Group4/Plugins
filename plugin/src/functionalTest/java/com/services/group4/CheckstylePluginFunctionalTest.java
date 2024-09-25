package com.services.group4;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class CheckstylePluginFunctionalTest {
  @TempDir
  File projectDir;

  @Test
  void canRunCheckstyleTask() throws IOException {
    // Create settings.gradle
    File settingsFile = new File(projectDir, "settings.gradle");
    Files.write(settingsFile.toPath(), "".getBytes());

    // Create build.gradle
    File buildFile = new File(projectDir, "build.gradle");
    String buildFileContent = "plugins { id 'com.services.group4.checkstyle' }";
    Files.write(buildFile.toPath(), buildFileContent.getBytes());

    // Run the Checkstyle task
    BuildResult result = GradleRunner.create()
        .withProjectDir(projectDir)
        .withArguments("checkstyleMain")
        .withPluginClasspath()
        .build();

    assertTrue(result.getOutput().contains("BUILD SUCCESSFUL"));

    BuildResult resultTest = GradleRunner.create()
        .withProjectDir(projectDir)
        .withArguments("checkstyleMain")
        .withPluginClasspath()
        .build();

    assertTrue(resultTest.getOutput().contains("BUILD SUCCESSFUL"));
  }
}
