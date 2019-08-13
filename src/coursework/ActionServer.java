package coursework;

import java.net.*;
import java.io.*;

public class ActionServer {
  public static void main(String[] args) throws IOException {

	ServerSocket ActionServerSocket = null;
    boolean listening = true;
    String ActionServerName = "ActionServer";
    int ActionServerNumber = 4591;
    
    double SharedVariable = 1000; 

    //Create the shared object in the global scope...
    
    SharedActionState sharedVariable = new SharedActionState(SharedVariable);
    
        
    // Make the server socket

    try {
      ActionServerSocket = new ServerSocket(ActionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + ActionServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(ActionServerName + " started");

    //Got to do this in the correct order with only four clients!  Can automate this...
    
    while (listening){
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread1", sharedVariable).start();
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread2", sharedVariable).start();
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread3", sharedVariable).start();
      System.out.println("New " + ActionServerName + " thread started.");


    }
    ActionServerSocket.close();
  }
}