package Editor;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

class TDialog extends JDialog {

    @Serial
    private static final long serialVersionUID = 1L;

    String jt1="";
    String jt2="";
    TDialog jf2=this;
    public TDialog() {
        setTitle("替换");
        setModal(true);
        setSize(200, 140);//对话框的大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//关闭对话框
        setLocationRelativeTo(null);
        JLabel jl1 = new JLabel("替换:");
        final JTextField jtf1 = new JTextField(8);
        JLabel jl2 = new JLabel("替换为:");
        final JTextField jtf2 = new JTextField(8);


        final JPanel jp = new JPanel(new GridLayout(2, 2));
        jp.add(jl1);
        jp.add(jtf1);
        jp.add(jl2);
        jp.add(jtf2);

        JButton jb = new JButton("确认替换");

        jb.addActionListener(e -> {
            jt1=jtf1.getText();
            jt2=jtf2.getText();
            jp.setVisible(false);
            jf2.setVisible(false);
        });

        add(jp);
        add(jb,BorderLayout.SOUTH);

    }
}