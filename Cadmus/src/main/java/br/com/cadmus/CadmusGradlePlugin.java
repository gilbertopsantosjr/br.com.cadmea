package br.com.cadmus;

import org.gradle.api.Plugin;
import org.gradle.api.Project;


public class CadmusGradlePlugin implements Plugin<Project> {
    @Override
    public void apply(final Project target) {
        target.getExtensions().create("cadmusPlugin",
                TemplateDir.class);
    }
}
