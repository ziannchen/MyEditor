package Editor;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.net.Socket;

public class Notepad extends JFrame {

    //初始化
    @Serial
    private static final long serialVersionUID = 1L;
    ListenerSet listenerSet = new ListenerSet();//监听声明
    JMenuBar menuBar = new JMenuBar();

    JMenu file = new JMenu("文件(F)");
    JMenuItem newFile = new JMenuItem("  新建(N)");
    JMenuItem openLocal = new JMenuItem("  打开");
    JMenuItem saveLocal = new JMenuItem("  另存为");
    JMenuItem insertPic = new JMenuItem("  插入图片(Y)");
    JMenuItem openRemote = new JMenuItem("  远程打开");
    JMenuItem saveRemote = new JMenuItem("  远程保存");
    JMenuItem save = new JMenuItem("  保存(S)");
    JMenuItem exit = new JMenuItem("  退出(X)");

    JMenu help = new JMenu("帮助(H)");
    final JMenuItem about = new JMenuItem("  关于");
    final JMenuItem viewHelp = new JMenuItem("  查看帮助");

    JMenu Edit = new JMenu("编辑(E)");
    JMenuItem cut = new JMenuItem("  剪切(X)");
    JMenuItem copy = new JMenuItem("  复制(C)");
    JMenuItem paste = new JMenuItem("  黏贴(P)");
    JMenuItem delete = new JMenuItem("  删除(L)");
    JMenuItem find = new JMenuItem("  查找(F)");
    JMenuItem replace = new JMenuItem("  替换(R)");
    JMenuItem selectAll = new JMenuItem("  全选(A)");
    JMenuItem time = new JMenuItem("  时间日期(D)");

    JMenu format = new JMenu("格式(U)");
    JMenuItem fonts = new JMenuItem("  字体(P)");

    static JTextPane editWindow = new JTextPane();
    
    JFrame frame;

    LinkedList<ImageIcon> picList = new LinkedList<>();


    static class FontEditBox extends JFrame {
        @Serial
        private static final long serialVersionUID = 1L;
        private final JComboBox<String> sizeBox;
        private final JComboBox<String> styleBox;
        private final JComboBox<String> fontBox;
        private final JComboBox<String> colorBox;
        String[] sizeList = {"初号", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六"};
        String[] styleList = {"常规", "倾斜", "粗体", "倾斜  粗体"};
        String[] fontList = {"微软雅黑", "宋体", "楷体", "华文行楷", "黑体"};
        String[] colorList = {"黑色", "红色", "黄色", "蓝色", "绿色"};

        public FontEditBox() {
            setFont(new Font("微软雅黑", Font.PLAIN, 18));
            setTitle("字  体");

            setBounds(400, 300, 333, 218);
            JPanel contentPane = new JPanel();
            contentPane.setToolTipText("下拉框");
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);

            JLabel labelFont = new JLabel("字体：");
            labelFont.setBounds(20, 10, 54, 15);
            contentPane.add(labelFont);

            JLabel labelStyle = new JLabel("字形：");
            labelStyle.setBounds(131, 10, 54, 15);
            contentPane.add(labelStyle);

            JLabel labelSize = new JLabel("大小");
            labelSize.setBounds(230, 10, 54, 15);
            contentPane.add(labelSize);

            JLabel labelColor = new JLabel("颜色");
            labelColor.setBounds(20, 80, 54, 15);
            contentPane.add(labelColor);


            sizeBox = new JComboBox<>();
            for (String s : sizeList) {
                sizeBox.addItem(s);
            }
            sizeBox.setToolTipText("下拉框");
            sizeBox.addActionListener(e -> {
                int selectedIndex = sizeBox.getSelectedIndex();

                switch (selectedIndex) {
                    case 0 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 50));
                    case 1 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 48));
                    case 2 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 46));
                    case 3 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 44));
                    case 4 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 42));
                    case 5 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 40));
                    case 6 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 38));
                    case 7 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 36));
                    case 8 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 34));
                    case 9 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 32));
                    case 10 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 28));
                    case 11 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 24));
                    case 12 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 18));
                }

            });
            sizeBox.setEditable(true);
            sizeBox.setBounds(230, 35, 85, 21);
            contentPane.add(sizeBox);

            styleBox = new JComboBox<>();
            for (String s : styleList) {
                styleBox.addItem(s);
            }
            styleBox.setEditable(true);
            styleBox.setBounds(131, 35, 79, 21);
            styleBox.addActionListener(e -> {
                int selectedIndex= styleBox.getSelectedIndex();

                switch (selectedIndex) {
                    case 0 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    case 2 -> editWindow.setFont(new Font("微软雅黑", Font.BOLD, 12));
                    case 1 -> editWindow.setFont(new Font("微软雅黑", Font.ITALIC, 12));
                    case 3 -> editWindow.setFont(new Font("微软雅黑", Font.ITALIC + Font.BOLD, 12));
                }
            });
            contentPane.add(styleBox);

            fontBox = new JComboBox<>();
            for (String s : fontList) {
                fontBox.addItem(s);
            }
            fontBox.setEditable(true);
            fontBox.setBounds(20, 35, 85, 21);
            fontBox.addActionListener(e -> {
                int x= fontBox.getSelectedIndex();

                switch (x) {//字体名字需要修改
                    case 0 -> editWindow.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    case 1 -> editWindow.setFont(new Font(fontList[1], Font.PLAIN, 12));
                    case 2 -> editWindow.setFont(new Font(fontList[2], Font.PLAIN, 12));
                    case 3 -> editWindow.setFont(new Font(fontList[3], Font.PLAIN, 18));
                    case 4 -> editWindow.setFont(new Font(fontList[4], Font.PLAIN, 12));

                }
            });
            contentPane.add(fontBox);

            colorBox = new JComboBox<>();
            for (String s : colorList) {
                colorBox.addItem(s);
            }
            colorBox.setEditable(true);
            colorBox.setBounds(20, 100, 85, 21);
            colorBox.addActionListener(e -> {
                int selectedIndex = colorBox.getSelectedIndex();

                switch (selectedIndex) {//字体名字需要修改
                    case 0 -> editWindow.setSelectedTextColor(Color.BLACK);
                    case 1 -> editWindow.setSelectedTextColor(Color.RED);
                    case 2 -> editWindow.setSelectedTextColor(Color.YELLOW);
                    case 3 -> editWindow.setSelectedTextColor(Color.BLUE);
                    case 4 -> editWindow.setSelectedTextColor(Color.GREEN);

                }
            });
            contentPane.add(colorBox);

        }
    }

    //运行窗体，主方法
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Notepad frame = new Notepad();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public Notepad() {
        setTitle(" 记事本 - 版本 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300,200,600,400);

        setJMenuBar(menuBar);


        file.setFont(new Font("黑体", Font.PLAIN, 13));
        file.setForeground(SystemColor.infoText);
        file.setMnemonic('F');
        file.setBackground(SystemColor.desktop);
        menuBar.add(file);

        Edit.setFont(new Font("黑体", Font.PLAIN, 13));
        Edit.setForeground(SystemColor.infoText);
        Edit.setMnemonic('X');
        Edit.setBackground(SystemColor.desktop);
        menuBar.add(Edit);

        format.setFont(new Font("黑体", Font.PLAIN, 13));
        format.setForeground(SystemColor.infoText);
        format.setMnemonic('U');
        format.setBackground(SystemColor.desktop);
        menuBar.add(format);

        fonts.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        fonts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
        format.add(fonts);
        fonts.addActionListener(listenerSet);

        cut.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        Edit.add(cut);
        cut.addActionListener(listenerSet);

        copy.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        Edit.add(copy);
        copy.addActionListener(listenerSet);

        paste.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        Edit.add(paste);
        paste.addActionListener(listenerSet);

        delete.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        delete.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE));
        Edit.add(delete);
        delete.addActionListener(listenerSet);

        find.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        Edit.add(find);
        find.addActionListener(listenerSet);

        replace.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        Edit.add(replace);
        replace.addActionListener(listenerSet);

        selectAll.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        Edit.add(selectAll);
        selectAll.addActionListener(listenerSet);

        time.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        time.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        Edit.add(time);
        time.addActionListener(listenerSet);

        newFile.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        file.add(newFile);
        newFile.addActionListener(listenerSet);

        insertPic.setFont(new Font("微软雅黑", Font.PLAIN, 13));//插入图片
        insertPic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        file.add(insertPic);
        insertPic.addActionListener(listenerSet);

        openLocal.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        openLocal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        file.add(openLocal);
        openLocal.addActionListener(listenerSet);

        openRemote.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        file.add(openRemote);
        openRemote.addActionListener(listenerSet);

        saveLocal.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        saveLocal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        file.add(saveLocal);
        saveLocal.addActionListener(listenerSet);

        saveRemote.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        file.add(saveRemote);
        saveRemote.addActionListener(listenerSet);

        save.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        file.add(save);
        save.addActionListener(listenerSet);

        exit.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        file.add(exit);
        exit.addActionListener(listenerSet);

        help.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        help.setForeground(SystemColor.infoText);
        menuBar.add(help);

        viewHelp.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        help.add(viewHelp);
        viewHelp.addActionListener(e -> JOptionPane.showMessageDialog(viewHelp, "没有帮助", "帮助", JOptionPane.INFORMATION_MESSAGE));

        about.addActionListener(e -> JOptionPane.showMessageDialog(about, "版本 1.0\n作者： cza and wzy", "关于", JOptionPane.INFORMATION_MESSAGE));
        about.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        help.add(about);

        //容器声明
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        contentPane.add(editWindow, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(editWindow);
        contentPane.add(scrollPane);

    }//初始化结束
    
    
    class ListenerSet implements ActionListener {
        FileDialog openFile = new FileDialog(frame, "打开文件", FileDialog.LOAD);
        String picPath;
        FileInputStream fis;
        String filePath;

        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == newFile) {
                FileDialog saveFile = new FileDialog(frame, "新建文件", FileDialog.SAVE);
                saveFile.setVisible(true);
                filePath = saveFile.getDirectory() + saveFile.getFile();
                setTitle(saveFile.getFile() + " - 记事本");
                editWindow.setText("");
            }//新建
            else if (e.getSource() == save) {
                clearInfoForFile(filePath);
                writeToObject();
            }//保存
            else if (e.getSource() == exit) {
                System.exit(0);
            }
            else if (e.getSource() == selectAll) {
                editWindow.selectAll();
            }
            else if (e.getSource() == copy) {
                editWindow.copy();
            }
            else if (e.getSource() == paste) {
                editWindow.paste();
            }
            else if (e.getSource() == cut) {
                editWindow.cut();
            }
            else if (e.getSource() == delete && !editWindow.getSelectedText().isEmpty()) {
                editWindow.cut();
            }
            else if (e.getSource() == find) {
                //查找方法，只能查到第一个
                int weizhi;
                int wzend;
                String lookinfo = JOptionPane.showInputDialog(find,"请输入：","查找",JOptionPane.INFORMATION_MESSAGE);
                String txtinfo = editWindow.getText();
                weizhi = txtinfo.indexOf(lookinfo);
                if (weizhi == -1) {
                    JOptionPane.showMessageDialog(find, "没有查找到此结果", "查找结果", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    wzend = weizhi+lookinfo.length();
                    editWindow.setSelectionStart(weizhi);
                    editWindow.setSelectionEnd(wzend);
                    editWindow.setRequestFocusEnabled(true);

                }}
            else if (e.getSource() == replace) {
                //替换方法
                if (editWindow.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(replace, "文本为空，无法替换！", "结果", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    TDialog td = new TDialog();
                    td.show();

                    String tempend = editWindow.getText().replaceAll(td.jt1,td.jt2);
                    editWindow.setText(tempend);
                    int picIndex = 0;


                    for (int i = 0; i < tempend.length(); i++) {
                        if (tempend.charAt(i) != ' ')
                            continue;

                        editWindow.setSelectionStart(i);
                        editWindow.setSelectionEnd(i + 1);
                        editWindow.replaceSelection("");

                        editWindow.setCaretPosition(i);
                        editWindow.insertIcon(picList.get(picIndex++));
                    }
                }
            }
            else if (e.getSource() == insertPic) {
                //按下插图实现
                openFile.setVisible(true);
                // 获取文件路径
                picPath = openFile.getDirectory() + openFile.getFile();

                ImageIcon pic = new ImageIcon(picPath);

                editWindow.insertIcon(pic);
                picList.add(pic);

            }
            else if (e.getSource() == fonts) {
                FontEditBox fontjf = new FontEditBox();
                fontjf.setVisible(true);
            }
            else if (e.getSource() == openLocal)
            {

                openFile.setVisible(true);
                // 获取文件路径
                filePath = openFile.getDirectory() + openFile.getFile();
                try {
                    fis = new FileInputStream(filePath);
                    File fname = new File(filePath);

                    ObjectInputStream ois = new ObjectInputStream(fis);
                    StyledDocument doc = (StyledDocument) ois.readObject();

                    ois.close();

                    editWindow.setStyledDocument(doc);

                    fis.close();
                    setTitle(fname.getName() + " - 记事本");
                }
                catch (IOException e3)
                {
                    System.out.println("输入输出异常");
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }//打开
            else if (e.getSource() == saveLocal) {
                //保存文件
                FileDialog saveFile = new FileDialog(frame, "保存文件", FileDialog.SAVE);
                saveFile.setVisible(true);
                filePath = saveFile.getDirectory() + saveFile.getFile();

                writeToObject();
            }//另存为
            else if (e.getSource() == openRemote) {
                FilePathDialog filePathDialog = new FilePathDialog();
                filePathDialog.setVisible(true);
                filePath = filePathDialog.getRemoteFilePath();
                String serverName = "localhost";
                int port = 8000;
                try {
                    Socket socket = new Socket(serverName, port);
                    new Thread(new SocketReadHandler(socket, filePath)).start();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                setTitle(filePath + " - 记事本");
            }
            else if (e.getSource() == saveRemote) {
                FilePathDialog filePathDialog = new FilePathDialog();
                filePathDialog.setVisible(true);
                String filePath = filePathDialog.getRemoteFilePath();
                System.out.println(filePath);
                String serverName = "localhost";
                int port = 8000;
                try {
                    Socket socket = new Socket(serverName, port);
                    new Thread(new SocketWriteHandler(socket, filePath)).start();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }

        private void writeToObject() {
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                StyledDocument doc = (StyledDocument) editWindow.getDocument();
                oos.writeObject(doc);

                oos.flush();
                oos.close();

                fos.close();

            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void clearInfoForFile(String fileName) {
            File file = new File(fileName);
            try {
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}





