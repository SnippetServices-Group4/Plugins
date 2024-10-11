package com.services.group4;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.jvm.toolchain.JavaLanguageVersion;

public class JavaPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    // Aplicar el plugin 'java'
    project.getPluginManager().apply("java");

    // Configurar el toolchain para Java con la versión 21
    project.getExtensions().configure(JavaPluginExtension.class, javaExtension -> {
      javaExtension.getToolchain().getLanguageVersion().set(JavaLanguageVersion.of(21));
    });
  }
}
