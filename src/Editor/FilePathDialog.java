package Editor;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

class FilePathDialog extends JDialog {

    @Serial
    private static final long serialVersionUID = 1L;

    private String remoteFilePath = "";

    public FilePathDialog() {
        setTitle("地址");
        setModal(true);
        setSize(200, 140);//对话框的大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//关闭对话框
        setLocationRelativeTo(null);
        JLabel filePathLabel = new JLabel("请输入文件名:");
        final JTextField textField = new JTextField(8);

        final JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(filePathLabel);
        panel.add(textField);


        JButton jb = new JButton("确认");

        jb.addActionListener(e -> {
            remoteFilePath = textField.getText();

            panel.setVisible(false);
            this.setVisible(false);
        });

        add(panel);
        add(jb, BorderLayout.SOUTH);

    }

    public String getRemoteFilePath() {
        return this.remoteFilePath;
    }
}