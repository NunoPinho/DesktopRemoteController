package desktopremotecontrollerpc;

import java.io.IOException;

public class ShutdownAndOthers {
    
    Runtime runtime = Runtime.getRuntime();
    
//    public void hibernate(){
//        try {
//            runtime.exec("Rundll32.exe powrprof.dll,SetSuspendState");
//        } catch (IOException ex) {
//            System.out.println("Error in hibernate");
//        }
//    }     
    
    public void restart(){
        try {
            runtime.exec("shutdown -r");
        } catch (IOException ex) {
            System.out.println("Error in restart");
        }
    } 
    
    public void shutdown(){
        try {
            runtime.exec("shutdown -s");
        } catch (IOException ex) {
            System.out.println("Error in shutdown");
        }
    } 
    
    public void suspend(){
        try {
            runtime.exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
        } catch (IOException ex) {
            System.out.println("Error in suspend");
        }
    } 
}
