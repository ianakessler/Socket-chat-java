import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientSocket(Socket socket) throws IOException{
        this.socket = socket;
        System.out.println("Cliente conectado "+ socket.getRemoteSocketAddress());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public SocketAddress getRemoteSocketAddres(){
        return socket.getRemoteSocketAddress();
    }

    public String getMessage(){
        try{
            return in.readLine();
        } catch(IOException ex){
            return null;
        }
    }

    public void close(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMsg(String msg){
        out.println(msg);
        return !out.checkError();
    }
}
