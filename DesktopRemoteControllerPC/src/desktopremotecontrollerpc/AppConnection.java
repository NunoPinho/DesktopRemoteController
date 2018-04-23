package desktopremotecontrollerpc;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConnection {
    
    public static ServerSocket serverSocket = null;
    public static Socket clientSocket = null;
    public static InputStream inputStream = null;
    public static OutputStream outputStream = null;
    public static ObjectOutputStream objectOutputStream = null;
    public static ObjectInputStream objectInputStream = null;
    
    ShutdownAndOthers shutdownAndOthers = new ShutdownAndOthers();
    
    public void startServer(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Erro a criar o socket do servidor");
        }
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                InetAddress remoteInetAddress = clientSocket.getInetAddress();
                System.out.println("Connected to:" + remoteInetAddress); 
            } catch (IOException ex) {
                System.out.println("Erro a criar o socket do cliente");
            }
            
            try {
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();           
                objectInputStream = new ObjectInputStream(inputStream);
                objectOutputStream = new ObjectOutputStream(outputStream);
            } catch (IOException ex) {
                System.out.println("Erro ao criar os sockets de input e output");
            }
            
            String action = null;
            try {
                action = (String) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Erro ao ler a mensagem vinda do dispositivo mobile");
            }
            
            if (action != null){
                if (action.equals("SLEEP_PC")){
                    shutdownAndOthers.suspend();
                }
            }
        }
    }
}
