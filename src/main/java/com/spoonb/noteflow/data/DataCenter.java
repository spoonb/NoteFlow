package com.spoonb.noteflow.data;

import javax.swing.table.DefaultTableModel;

public class DataCenter {

    /**
     * 选择的文本
     */
    public static String SELECTED_TEXT;

    /**
     * 当前文件名
     */
    public static String CURRENT_FILE_PATH;

    /**
     * 笔记表
     */
    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(
            null, new String[]{"标题","内容", "代码片段", "文件路径"}
    );
}
