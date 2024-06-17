import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente implements Runnable{
    
    private ClientSocket cliente;
    private final String ADDR;
    private final int PORT;
    private Scanner input;

    public Cliente(String addr, int porta){
        this.ADDR = addr;
        this.PORT = porta;
        this.input = new Scanner(System.in);
    }

    public void start() throws UnknownHostException, IOException{
        cliente = new ClientSocket(new Socket(ADDR, PORT));  
        
        new Thread(this).start();
        messageLoop();
    }

    @Override
    public void run(){
        String msg;
        while ((msg = cliente.getMessage()) != null) {
            System.out.println("Mensagem recebida do servidor:" +msg);
        }
    }

    

    private void messageLoop() throws IOException{
        String msg;
        do{
            System.out.println("Digite sua mensagem, ou '.sair' para finalizar");
            msg = input.nextLine();
            cliente.sendMsg(msg);
            
        } while(!msg.equalsIgnoreCase(".sair"));
        input.close();
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente("127.0.0.1", 9999);
        try {//todo = usar CLI ou Scanner
            cliente.start();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
