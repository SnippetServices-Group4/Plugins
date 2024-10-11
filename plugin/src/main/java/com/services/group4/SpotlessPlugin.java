package com.services.group4;

import com.diffplug.gradle.spotless.SpotlessExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class SpotlessPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    // Apply the Spotless plugin
    project.getPluginManager().apply("com.diffplug.spotless");

    // Configure Spotless for Java formatting
    project.getExtensions().configure(SpotlessExtension.class, spotless -> spotless.java(java -> {
      java.googleJavaFormat("1.17.0");
      java.target("src/**/*.java");
    }));
  }
}