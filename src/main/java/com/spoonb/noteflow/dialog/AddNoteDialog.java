package com.spoonb.noteflow.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

import static com.spoonb.noteflow.data.DataCenter.*;

public class AddNoteDialog extends DialogWrapper {

    /**
     * 标题输入框
     */
    private EditorTextField title;

    /**
     * 内容输入框
     */
    private EditorTextField content;

    public AddNoteDialog() {
        super(true);
        init();
        setTitle("添加笔记");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        title = new EditorTextField("笔记标题");
        content = new EditorTextField("笔记内容");
        content.setPreferredSize(new Dimension(400, 300));
        panel.add(title, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton("添加笔记");
        button.addActionListener(e -> {
            String titleText = title.getText();
            String contentText = content.getText();
            TABLE_MODEL.addRow(new String[]{titleText, contentText, SELECTED_TEXT, CURRENT_FILE_PATH});
            // 触发事件后关闭Dialog
            close(OK_EXIT_CODE);
        });
        panel.add(button);
        return panel;
    }
}
