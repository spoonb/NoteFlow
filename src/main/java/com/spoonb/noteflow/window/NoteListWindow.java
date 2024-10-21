package com.spoonb.noteflow.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.wm.ToolWindow;
import com.spoonb.noteflow.process.impl.process.MarkDownProcessor;
import com.spoonb.noteflow.process.impl.source.MarkDownNote;
import io.netty.util.internal.StringUtil;
import lombok.Getter;

import javax.swing.*;

import static com.spoonb.noteflow.data.DataCenter.TABLE_MODEL;

public class NoteListWindow {

    @Getter
    private JPanel panel;

    // north
    private JTextField topic;

    // center
    private JTable parts;

    // south
    private JButton createBtn;

    private JButton clearBtn;

    private JButton closeBtn;

    public NoteListWindow(Project project, ToolWindow toolWindow) {
        parts.setModel(TABLE_MODEL);
        parts.setEnabled(true);
        createBtn.addActionListener(e -> {
            String topic = this.topic.getText();
            if (StringUtil.isNullOrEmpty(topic)) {
                MessageDialogBuilder.okCancel("异常信息", "没有输入标题").guessWindowAndAsk();
                return;
            }
            MarkDownNote markDownNote = new MarkDownNote(topic);
            MarkDownProcessor markDownProcessor = new MarkDownProcessor(markDownNote);
            try {
                markDownProcessor.process(project);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clearBtn.addActionListener(e -> {
            for (int i = TABLE_MODEL.getRowCount() - 1; i >= 0; i --) {
                TABLE_MODEL.removeRow(i);
            }
            // 也可以删除,但存在刷新延迟的问题
//            TABLE_MODEL.getDataVector().clear();
        });
        closeBtn.addActionListener(e -> {
            toolWindow.hide(null);
        });
    }
}
