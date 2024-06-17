import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

public class Servidor {
    private final int PORT;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clientes = new LinkedList<>();

    public Servidor(int porta){
        this.PORT = porta;
    }

    private void start() throws IOException{
        serverSocket = new ServerSocket(PORT);
        System.out.println("Esperando conexões na porta "+ PORT );
        clientConnectionLoop();
        
    }

    private void clientConnectionLoop() throws IOException{
        while(true){
            ClientSocket cliente = new ClientSocket(serverSocket.accept());
            
            clientes.add(cliente);
           
            
            new Thread(()-> clientMessageLoop(cliente)).start();
        }
    }

    private void clientMessageLoop(ClientSocket cliente){
        String msg;
        try{

            while((msg = cliente.getMessage()) != null){
                if(".sair".equalsIgnoreCase(msg)) return;
                System.out.println("Mensagem recebida do cliente " + cliente.getRemoteSocketAddres() + ": "+ msg);
                sendMessageToAll(cliente, msg);
            }
        } finally{
            cliente.close();
        }
    }

    private void sendMessageToAll(ClientSocket sender, String msg){
        for(ClientSocket socket: clientes){
            if(!sender.equals(socket))
                socket.sendMsg(msg);
        }
    }
    public static void main(String[] args) {
        try{
            Servidor server = new Servidor(9999);//todo = usar CLI ou Scanner
            server.start();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
