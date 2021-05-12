package Editor;

import javax.swing.text.StyledDocument;
import java.io.*;
import java.net.Socket;

public class SocketWriteHandler implements Runnable {
    private final Socket socket;
    private final String filePath;

    SocketWriteHandler(Socket socket, String filePath) {
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
            out = socket.getOutputStream();
            in = socket.getInputStream();
            writer = new BufferedWriter(new OutputStreamWriter(out));
            reader = new BufferedReader(new InputStreamReader(in));

            writer.write("write " + filePath + "\n");
            writer.flush();

            if (!reader.readLine().equals("ok"))
                return;

            ObjectOutputStream oos = new ObjectOutputStream(out);
            StyledDocument doc = (StyledDocument) Notepad.editWindow.getDocument();
            oos.writeObject(doc);
            oos.flush();
            oos.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
