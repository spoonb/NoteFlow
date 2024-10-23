package com.spoonb.noteflow.process.impl.process;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.spoonb.noteflow.process.SourceNote;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MarkDownProcessor extends TemplateProcessor {

    public MarkDownProcessor(SourceNote sourceNote) {
        super(sourceNote);
    }

    @Override
    protected Template getTemplate() throws IOException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_31);
        String temp = UrlUtil.loadText(Objects.requireNonNull(MarkDownProcessor.class.getResource("/template/markdown.ftl")));
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("markdown", temp);
        config.setTemplateLoader(templateLoader);
        return config.getTemplate("markdown");
    }

    @Override
    protected Object getModel() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("topic", sourceNote.getTopic());
        model.put("notes", sourceNote.getNotes());
        return model;
    }

    @Override
    protected Writer getWriter(Project project) throws IOException {
        String topic = sourceNote.getTopic();
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getBaseDir());
        if (virtualFile != null) {
            String path = virtualFile.getPath();
            String fullPath = path + "/" + topic + ".md";
//            return new BufferedWriter(new FileWriter(fullPath));
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fullPath), StandardCharsets.UTF_8));
        }
        return null;
    }
}
