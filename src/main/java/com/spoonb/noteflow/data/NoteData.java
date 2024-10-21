package com.spoonb.noteflow.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteData {

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记内容
     */
    private String content;

    /**
     * 目标代码
     */
    private String target;

    /**
     * 文件路径
     */
    private String filePath;

    public String getFileName() {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }

    public String getFileType() {
        return filePath.substring(filePath.lastIndexOf('.') + 1);
    }
}
