package com.services.group4;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CommonConventionsPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPlugins().apply("com.services.group4.java");
    project.getPlugins().apply("com.services.group4.checkstyle");
    project.getPlugins().apply("com.services.group4.spotless");
    project.getPlugins().apply("com.services.group4.jacoco");
  }
}
