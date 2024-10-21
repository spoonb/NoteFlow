package com.spoonb.noteflow.process;

import com.spoonb.noteflow.data.NoteData;

import java.util.List;

public interface SourceNote {
    String getTopic();

    List<NoteData> getNotes();
}
