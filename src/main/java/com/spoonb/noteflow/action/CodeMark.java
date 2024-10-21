package com.spoonb.noteflow.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.spoonb.noteflow.dialog.AddNoteDialog;

import static com.spoonb.noteflow.data.DataCenter.*;

public class CodeMark extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取当前编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        // 获取选择的数据模型
        SelectionModel selectionModel = editor.getSelectionModel();
        // 获取当前选择的文本
        SELECTED_TEXT = selectionModel.getSelectedText();
        // 文件名
        CURRENT_FILE_PATH = editor.getVirtualFile().getPath();
//        CURRENT_FILE_NAME = name;
//        CURRENT_FILE_TYPE = name.substring(name.lastIndexOf('.') + 1);
        new AddNoteDialog().show();
    }
}
