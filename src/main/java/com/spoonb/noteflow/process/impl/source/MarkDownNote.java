package com.spoonb.noteflow.process.impl.source;

import com.spoonb.noteflow.data.NoteData;
import com.spoonb.noteflow.process.SourceNote;

import java.util.List;
import java.util.stream.Collectors;

import static com.spoonb.noteflow.data.DataCenter.TABLE_MODEL;

public class MarkDownNote implements SourceNote {

    private final String topic;

    public MarkDownNote(String topic) {
        this.topic = topic;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public List<NoteData> getNotes() {
        return TABLE_MODEL.getDataVector().stream().map(e -> {
            String title = (String) e.get(0);
            String content = (String) e.get(1);
            String target = (String) e.get(2);
            String filePath = (String) e.get(3);
            return new NoteData(title, content, target, filePath);
        }).collect(Collectors.toList());
    }
}
