package com.services.group4;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacocoPluginFunctionalTest {

    @TempDir
    File projectDir;

    @Test
    public void testJacocoPlugin() throws IOException {
        // Create settings.gradle
        File settingsFile = new File(projectDir, "settings.gradle");
        Files.write(settingsFile.toPath(), "".getBytes());

        // Create build.gradle
        File buildFile = new File(projectDir, "build.gradle");
        String buildFileContent = "plugins { id 'java' }\n" +
                                  "plugins { id 'com.services.group4.jacoco' }\n" +
                                  "repositories { mavenCentral() }\n" +
                                  "dependencies { testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1' }\n" +
                                  "test { useJUnitPlatform() }";
        Files.write(buildFile.toPath(), buildFileContent.getBytes());

        // Create a sample test class
        File srcDir = new File(projectDir, "src/test/java/com/example");
        srcDir.mkdirs();
        File testFile = new File(srcDir, "ExampleTest.java");
        String testFileContent = "package com.example;\n" +
                                 "import org.junit.jupiter.api.Test;\n" +
                                 "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                                 "public class ExampleTest {\n" +
                                 "  @Test\n" +
                                 "  public void test() {\n" +
                                 "  }\n" +
                                 "}";
        Files.write(testFile.toPath(), testFileContent.getBytes());

        // Run the test and generate Jacoco report
        BuildResult result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("test", "jacocoTestReport")
            .withPluginClasspath()
            .build();

        assertEquals(SUCCESS, Objects.requireNonNull(result.task(":test")).getOutcome());
        assertEquals(SUCCESS, Objects.requireNonNull(result.task(":jacocoTestReport")).getOutcome());
    }
}