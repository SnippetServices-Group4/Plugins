package com.services.group4;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.UnexpectedBuildFailure;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpotlessPluginFunctionalTest {

  @Test
  public void testSpotlessPlugin() throws IOException {
    File projectDir = Files.createTempDirectory("gradle-test").toFile();
    File buildFile = new File(projectDir, "build.gradle");
    Files.write(buildFile.toPath(), (
        """
            plugins {\
              id 'com.services.group4.spotless'\
            }
            repositories { mavenCentral() }
            """
    ).getBytes());

    File srcDir = new File(projectDir, "src/main/java/com/example");
    srcDir.mkdirs();
    File javaFile = new File(srcDir, "Example.java");
    Files.write(javaFile.toPath(), (
        """
            package com.example;
            public class Example { }
            """
    ).getBytes());

    assertThrows(UnexpectedBuildFailure.class, () -> GradleRunner.create()
        .withProjectDir(projectDir)
        .withArguments("spotlessCheck")
        .withPluginClasspath()
        .build());

    BuildResult runner = GradleRunner.create()
        .withProjectDir(projectDir)
        .withArguments("spotlessApply")
        .withPluginClasspath()
        .build();

    assertEquals(SUCCESS, Objects.requireNonNull(runner.task(":spotlessApply")).getOutcome());

    BuildResult runner2 = GradleRunner.create()
        .withProjectDir(projectDir)
        .withArguments("spotlessCheck")
        .withPluginClasspath()
        .build();

    assertEquals(SUCCESS, Objects.requireNonNull(runner2.task(":spotlessCheck")).getOutcome());
  }
}