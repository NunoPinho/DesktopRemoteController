package desktopremotecontrollerpc;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class AppConnection {
    
    public static ServerSocket serverSocket = null;
    public static Socket clientSocket = null;
    public static InputStream inputStream = null;
    public static OutputStream outputStream = null;
    public static ObjectOutputStream objectOutputStream = null;
    public static ObjectInputStream objectInputStream = null;
    
    private static Socket clientSocket2;
    private static InputStream inputStream2;
    private static OutputStream outputStream2;
    public static ObjectInputStream objectInputStream2;
    private static ObjectOutputStream objectOutputStream2;
    
    ShutdownAndOthers shutdownAndOthers = new ShutdownAndOthers();

    
    public String startServer(int port){
        
//        String connect = "";
        
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Erro a criar o socket do servidor");
        }
        
        while (true) {
            
            try {
                clientSocket = serverSocket.accept();
                InetAddress remoteInetAddress = clientSocket.getInetAddress();
                //connectToAndroid(remoteInetAddress, 3000);
                System.out.println("Connected to:" + remoteInetAddress);
//                connect = "Connected to:" + remoteInetAddress;
//                return connect;
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
    
    
    
    public void connectToAndroid(InetAddress inetAddress, int port) {
            try {
                SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
                clientSocket2 = new Socket();
                
                clientSocket2.connect(socketAddress, 3000); //timeout de 3s
//                inputStream2 = clientSocket2.getInputStream();
//                outputStream2 = clientSocket2.getOutputStream();
//                objectOutputStream2 = new ObjectOutputStream(outputStream2);
//                objectInputStream2 = new ObjectInputStream(inputStream2);
//                
            } catch(Exception e) {
                e.printStackTrace();
            }
    }
    
    
    
    public static String getIpAddress(){
        
        //ArrayList<String> ipAddresses = new ArrayList<String>();
        String ipAddresses = "";
        String temp;
        Enumeration networkInterfaces = null;
        
        //Enumera as interfaces de rede
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        
        while (networkInterfaces.hasMoreElements()) {
            
            NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
            //Em cada interface vê o IP
            Enumeration inetAdresses = networkInterface.getInetAddresses();
            
            while (inetAdresses.hasMoreElements()) {
                
                InetAddress inetAddress = (InetAddress) inetAdresses.nextElement();
                temp = inetAddress.getHostAddress();
               
                // Adiciona apenas IPV4
                if ((temp.charAt(1) == '7' || temp.charAt(1) == '9') && (temp.charAt(2) == '2')) {
                    
                    //ipAddresses.add(temp);
                    
                    if (ipAddresses.equals("")){
                        ipAddresses = temp;
                    }else{
                        ipAddresses += " / " + temp;
                    }
                    
                }
                
            }
            
        }
        
        return ipAddresses;
        
    }
}
