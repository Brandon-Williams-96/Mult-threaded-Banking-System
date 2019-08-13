package coursework;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ActionClient3 {
	private static final int None =0 ;
	private static final int Money = 2;
    private static final int Transfer_money = 1;
    private static int state = None;
    
    public static void main(String[] args) throws IOException, InterruptedException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4591;
        String ActionServerName = "localhost";
        String ActionClientID = "ActionClient3";

        try {
            ActionClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(ActionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ActionClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + ActionClientID + " client and IO connections");
        
        Random random = new Random(); 
        String UserLoopOption=null;
        int money;
        int account;
        int x;
        // This is modified as it's the client that speaks first

        /*while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
        }*/
        
        int i=0;
        Thread.sleep(1000);
		do
    	{
    		x = random.nextInt(3) + 1;
    		money = random.nextInt(200) +1 ;
    		account= random.nextInt(2) +1;
    		
    		if (x==1)
    		{
    			UserLoopOption="Add_money";
    		}
    		
    		if (x==2)
    		{
    			UserLoopOption="Subtract_money";
    		}
    		
    		if (x==3)
    		{
    			UserLoopOption="Transfer_money";
    		}
    		
    		switch (state){
    		case None:
    		{
    			if ((UserLoopOption.equals("Add_money") || (UserLoopOption.equals("Subtract_money"))))
				{
    	        	state= Money;				
				}
    	        
    	        
    	        else if (UserLoopOption=="Transfer_money")
    	        {
    	        	state=Transfer_money;
    	        }
    			fromUser = UserLoopOption;
    			if (fromUser != null) {
    				System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
    				out.println(fromUser);
    			}
    	        fromServer = in.readLine();
    	        System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
    	        break;
    		}
    		
    		
    		case Transfer_money:
    		{
    			if (account==1)
    			{
    				UserLoopOption="Account1";
    			}
    			else if (account==2)
    			{
    				UserLoopOption="Account2";
    			}
    			fromUser = UserLoopOption;
    			if (fromUser != null) {
    				System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
    				out.println(fromUser);
    			}
    	        fromServer = in.readLine();
    	        System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
    	        state= Money;
    	        break;
    		}
    		
    		
    		
    		case Money:
    			UserLoopOption=Integer.toString(money);
    			fromUser = UserLoopOption;
    			if (fromUser != null) {
                    System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
                    out.println(fromUser);
                }
                fromServer = in.readLine();
                System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
    			state=None;
    			i=i+1;
    			break;
        	}
    	}while (i<100);
            
        
       // Tidy up - not really needed due to true condition in while loop
        out.close();
        in.close();
        stdIn.close();
        ActionClientSocket.close();
    }
}
