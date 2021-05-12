package Editor;

import javax.swing.text.StyledDocument;
import java.io.*;
import java.net.Socket;

public class SocketReadHandler implements Runnable {
    private final Socket socket;
    private final String filePath;

    SocketReadHandler(Socket socket, String filePath) {
        this.socket = socket;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        BufferedReader reader;
        BufferedWriter writer;
        InputStream in;
        OutputStream out;
        try {

            in = socket.getInputStream();
            out = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("read " + filePath + "\n");
            writer.flush();
            if (!reader.readLine().equals("ok"))
                return;
            ObjectInputStream ois = new ObjectInputStream(in);
            StyledDocument doc = (StyledDocument) ois.readObject();
            Notepad.editWindow.setStyledDocument(doc);
            ois.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
