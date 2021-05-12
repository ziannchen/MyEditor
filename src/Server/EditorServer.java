import javax.swing.text.StyledDocument;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EditorServer {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        Socket socket;
        ServerSocket serverSocket = new ServerSocket(port);
        while ((socket = serverSocket.accept()) != null)
        {
            new Thread(new SocketHandler(socket)).start();
        }
    }
}

class SocketHandler implements Runnable {
    private final Socket socket;

    SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("connected");
        BufferedReader reader;
        BufferedWriter writer;
        InputStream in;
        OutputStream out;
        String re;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            writer = new BufferedWriter(new OutputStreamWriter(out));
            re = reader.readLine();

            writer.write("ok\n");
            writer.flush();

            String[] request = re.split(" ");
            String requestType = request[0];
            String filePath = request[1];

            if (requestType.equals("read")) {

                FileInputStream fin = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fin);
                ObjectOutputStream oos = new ObjectOutputStream(out);

                oos.writeObject(ois.readObject());
                oos.flush();
                oos.close();


            } else {//write

                ObjectInputStream ois = new ObjectInputStream(in);
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                StyledDocument doc = (StyledDocument) ois.readObject();
                oos.writeObject(doc);
                oos.flush();
                oos.close();
                fos.close();

            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}