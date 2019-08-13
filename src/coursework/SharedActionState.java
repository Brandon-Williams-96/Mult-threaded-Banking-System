package coursework;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class SharedActionState{
	
	private SharedActionState mySharedObj;
	private String myThreadName;
	private double account1money, account2money, account3money;
	private String account;
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers
	private static final int None =0 ;
	private static final int Add_money = 1;
    private static final int Subtract_money = 2;
    private static final int Transfer_money = 3; // to find account
    private static final int Transfer_money1 = 4; // to find money to transfer
    private int state = None;
    


// Constructor	
	
	SharedActionState(double SharedVariable) {
		account1money = SharedVariable;
		account2money = SharedVariable;
		account3money = SharedVariable;
		
	}

//Attempt to aquire a lock
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput, double x) {
			String uservalue;
    		System.out.println(myThreadName + " received "+ theInput);
    		String theOutput = null;    		
    		// Check what the client said
    		switch (state){
    		case None:
    		{
    			System.out.println(myThreadName + " received "+ theInput);
    			if (theInput.equalsIgnoreCase("Add_money")) {
    				//Correct request
    				state=Add_money;
    				theOutput = "How much Money?";
    				System.out.println(theOutput);
    				
    			}
    		
    			else if (theInput.equalsIgnoreCase("Subtract_money")) {
    				//Correct request
    				state=Subtract_money;
    				theOutput = "How much Money?";
    				System.out.println(theOutput);
       				}
    		
    			else if (theInput.equalsIgnoreCase("Transfer_money")) {
        				//Correct request
    				state=Transfer_money;
    				theOutput = "Which Account?";
    				System.out.println(theOutput);
    			}	
    		
    			else { //incorrect request
    				theOutput = myThreadName + " received incorrect request - only understand \"Add_money\" \"Subtract_money\" \"Transfer_money\"";
    			}
    		}
    		break;
    		case Add_money:
    		{
    			 boolean isDouble = false;
    		      try
    		      {
    		         Integer.parseInt(theInput);
    		 
    		         // s is a valid integer
    		 
    		         isDouble = true;
    		      }
    		      catch (NumberFormatException ex)
    		      {
    		         // s is not an integer
    		      }
    		   if (isDouble==true)
    		   {
    			if (myThreadName.equalsIgnoreCase("ActionServerThread1")) {				
					x = Double.parseDouble(theInput);
				
					account1money = account1money + x;
				
					System.out.println(myThreadName + " Account 1 Money now " + account1money);
					theOutput = "Do action completed.  Account 1 Money now = " + account1money;
					System.out.println(theOutput);
					state = None;
				}

				else if (myThreadName.equalsIgnoreCase("ActionServerThread2")) {
					
					x = Double.parseDouble(theInput);
				
					account2money = account2money + x;
				
					System.out.println(myThreadName + " Account 2 Money now " + account2money);
					theOutput = "Do action completed.  Account 2 Money now = " + account2money;
					System.out.println(theOutput);
					state = None;

					}
   			
				else if (myThreadName.equalsIgnoreCase("ActionServerThread3")) {
				
    				x = Double.parseDouble(theInput);
				
    				account3money = account3money + x;
    				
       				System.out.println(myThreadName + "Account 3 Money now " + account3money);
    				theOutput = "Do action completed.  Account 3 Money now = " + account3money;
    				System.out.println(theOutput);
    				state = None;

       			}
    		   }
    		   else
    			   theOutput = "Input a number";
    		break;
    		}
    		
    		
    		case Subtract_money:
    		{
    			boolean isDouble = false;
  		      try
  		      {
  		         Integer.parseInt(theInput);
  		 
  		         // s is a valid integer
  		 
  		         isDouble = true;
  		      }
  		      catch (NumberFormatException ex)
  		      {
  		         // s is not an integer
  		      }
  		   if (isDouble==true)
  		   {
    			if (myThreadName.equalsIgnoreCase("ActionServerThread1")) {				
					x = Double.parseDouble(theInput);
				
					account1money = account1money - x;
				
					System.out.println(myThreadName + " Account 1 Money now " + account1money);
					theOutput = "Do action completed.  Account 1 Money now = " + account1money;
					state = None;
					
				}

				else if (myThreadName.equalsIgnoreCase("ActionServerThread2")) {
					
					x = Double.parseDouble(theInput);
				
					account2money = account2money - x;
				
					System.out.println(myThreadName + " Account 2 Money now " + account2money);
					theOutput = "Do action completed.  Account 2 Money now = " + account2money;
					System.out.println(theOutput);
					state = None;
					

					}
   			
				else if (myThreadName.equalsIgnoreCase("ActionServerThread3")) {
				
    				x = Double.parseDouble(theInput);
				
    				account3money = account3money - x;
    				
       				System.out.println(myThreadName + " Account 3 Money now " + account3money);
    				theOutput = "Do action completed.  Account 3 Money now = " + account3money;
    				System.out.println(theOutput);
    				state = None;
    				
				}
       			}
  		 else
			   theOutput = "Input a number";
  		   
    		break;
    		}
    		case Transfer_money:
    			if (myThreadName.equalsIgnoreCase("ActionServerThread1")) {
    				if (theInput.equalsIgnoreCase("account1"))
    					{
    						theOutput = "Choose Account 2 or Account3";
    						System.out.println(theOutput);
    						state = Transfer_money;
    					}
    				else if (theInput.equalsIgnoreCase("Account2"))
    				{
    					account = theInput;
    					state = Transfer_money1;
    					theOutput = "How much Money?";
    					System.out.println(theOutput);
    				
    				}	
    				else if (theInput.equalsIgnoreCase("Account3"))
    				{
    					account = theInput;
    					state = Transfer_money1;
    					theOutput = "How much Money?";
    					System.out.println(theOutput);
    				
    				}
    				else
    				{
    					state = Transfer_money;
    					theOutput = "Choose Account 2 or Account3";
        				System.out.println(theOutput);
    				}
    			}
    			if (myThreadName.equalsIgnoreCase("ActionServerThread2")) {
    				if (theInput.equalsIgnoreCase("account2"))
					{
						System.out.println("Please enter account 1 or 3 can't transfer money to your own account");	
						state = Transfer_money;
					}
    				else if (theInput.equalsIgnoreCase("Account1"))
    				{
    					account = theInput;
    					state = Transfer_money1;
    					theOutput = "How much Money?";
    					System.out.println(theOutput);
    				
    				}	
    				else if (theInput.equalsIgnoreCase("Account3"))
    				{
    					account = theInput;
    					state = Transfer_money1;
    					theOutput = "How much Money?";
    					System.out.println(theOutput);
    				
    				}
    				else
    				{
    					state = Transfer_money;
    					theOutput = "Choose Account 1 or Account3";
        				System.out.println(theOutput);
    				}
    					
    			}
    			if (myThreadName.equalsIgnoreCase("ActionServerThread3")) {				
    				if (theInput.equalsIgnoreCase("account3"))
						{
							System.out.println("Please enter account 1 or 2 can't transfer money to your own account");
							state = Transfer_money;
						}
    				else if (theInput.equalsIgnoreCase("Account1"))
	    				{
	    					account = theInput;
	    					state = Transfer_money1;
	    					theOutput = "How much Money?";
	    					System.out.println(theOutput);
	    				
	    				}	
    				else if (theInput.equalsIgnoreCase("Account2"))
	    				{
	    					account = theInput;
	    					state = Transfer_money1;
	    					theOutput = "How much Money?";
	    					System.out.println(theOutput);
	    				
	    				}
    				else
	    				{
	    					state = Transfer_money;
	    					theOutput = "Choose Account 1 or Account2";
	        				System.out.println(theOutput);
	    				}
    			}
    			
    			
    			
    			break;
    		case Transfer_money1:
    			boolean isDouble = false;
  		      try
  		      {
  		         Integer.parseInt(theInput);
  		 
  		         // s is a valid integer
  		 
  		         isDouble = true;
  		      }
  		      catch (NumberFormatException ex)
  		      {
  		         // s is not an integer
  		      }
  		   if (isDouble==true)
  		   {
    			if (myThreadName.equalsIgnoreCase("ActionServerThread1")) {
    				if (account.equalsIgnoreCase("account2"))
    				{
    					x = Double.parseDouble(theInput);
    					account1money = account1money - x;
    					account2money = account2money + x;
    					System.out.println(myThreadName + " made account1 Money " + account1money);
    					System.out.println(myThreadName + " made account2 Money " + account2money);
    					theOutput = "Do action completed. made account1 Money = " + account1money + " Do action completed. made account2 Money = " + account2money;
    					state = None;
    				
    				}
    				else if (account .equalsIgnoreCase("account3"))
    				{
						x = Double.parseDouble(theInput);
						account1money = account1money - x;
						account3money = account3money + x;
						System.out.println(myThreadName + " made account1 Money " + account1money);
						System.out.println(myThreadName + " made account3 Money " + account3money);
						theOutput = "Do action completed. made account1 Money = " + account1money + " Do action completed. made account3 Money = " + account3money;
						System.out.println(theOutput);
						state = None;
    				}
    			}
				else if (myThreadName.equalsIgnoreCase("ActionServerThread2")) {
					
					if (account.equalsIgnoreCase("account1"))
    				{
    					x = Double.parseDouble(theInput);
    					account2money = account2money - x;
    					account1money = account1money + x;
    					System.out.println(myThreadName + " Made your Money " + account2money);
    					System.out.println(myThreadName + " made account1 Money " + account1money);
    					theOutput = "Do action completed. made account2 Money = " + account2money + "Do action completed. made account2 Money = " + account2money + " Do action completed. made account1 Money = " + account1money;
    					state = None;
    				
    				}
    				else if (account.equalsIgnoreCase("account3"))
    				{
						x = Double.parseDouble(theInput);
						account2money = account2money - x;
						account3money = account3money + x;
						System.out.println(myThreadName + " made account2 Money " + account2money);
						System.out.println(myThreadName + " made account3 Money " + account3money);
						theOutput = "Do action completed. made account2 Money = " + account2money + " Do action completed. made account3 Money = " + account3money;
						System.out.println(theOutput);
						state = None;
    				}
				}
   			
				else if (myThreadName.equalsIgnoreCase("ActionServerThread3")) {
						theOutput = "Choose";
						System.out.println(theOutput);
					if (account.equalsIgnoreCase("account1"))
    				{
    					x = Double.parseDouble(theInput);
    					account3money = account3money - x;
    					account1money = account1money + x;
    					System.out.println(myThreadName + " Made your Money " + account3money);
    					System.out.println(myThreadName + " made account1 Money " + account1money);
    					theOutput = "Do action completed. made account2 Money = " + account3money + " Do action completed. made account1 Money = " + account1money;
    					state = None;
    				}
					else if (account.equalsIgnoreCase("account2"))
    				{
    					x = Double.parseDouble(theInput);
    					account3money = account3money - x;
    					account2money = account2money + x;
    					System.out.println(myThreadName + " made account3 Money " + account3money);
    					System.out.println(myThreadName + " made account2 Money " + account2money);
    					theOutput = "Do action completed. made account2 Money = " + account3money + " Do action completed. made account2 Money = " + account2money;
    					state = None;
    				}
				}
  		   }
  		   else
  			   theOutput="Input a Number";
    			break;
				}
    			
 
     		//Return the output message to the ActionServer
    		return theOutput;
}
}