package com.spoonb.noteflow.process;

import com.intellij.openapi.project.Project;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface Processor {

    void process(Project project) throws TemplateException, IOException;
}
