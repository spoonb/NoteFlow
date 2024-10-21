package com.spoonb.noteflow.process.impl.process;

import com.intellij.openapi.project.Project;
import com.spoonb.noteflow.process.Processor;
import com.spoonb.noteflow.process.SourceNote;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public abstract class TemplateProcessor implements Processor {

    protected SourceNote sourceNote;

    public TemplateProcessor(SourceNote sourceNote) {
        this.sourceNote = sourceNote;
    }

    protected abstract Template getTemplate() throws IOException;

    protected abstract Object getModel();

    protected abstract Writer getWriter(Project project) throws IOException;

    @Override
    public final void process(Project project) throws TemplateException, IOException {
        Template template = getTemplate();
        Object model = getModel();
        Writer writer = getWriter(project);
        template.process(model, writer);
    }
}
