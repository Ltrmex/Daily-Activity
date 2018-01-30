package gmit;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Requester {
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message="";
 	String ipaddress;
 	Scanner stdin;
	Requester(){}
	
	void run()
	{
		// Scanner
		stdin = new Scanner(System.in);
		
		try{
			// Creating a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			requestSocket = new Socket(ipaddress, 2004);
			
			System.out.println("Connected to "+ipaddress+" in port 2004");
			
			// Get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());

			
			// Communicating with the server
			do{
				try
				{
					// Menu Options
					System.out.print("\n"+(String)in.readObject());
					
					// Chose one of the options
					message = stdin.next();
					sendMessage(message);
					
					// Different menu options
					if(message.compareToIgnoreCase("1")==0)
					{
						// Name
						System.out.print("\n"+(String)in.readObject());
						sendMessage(stdin.next());

						// Address
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// PPS
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Age
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Height
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Weight
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Print
						System.out.print("\n"+(String)in.readObject());
						
					}
					else if(message.compareToIgnoreCase("2")==0)
					{	
						// Message
						System.out.print("\n"+(String)in.readObject());
						
						// Input of name for login
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Message saying whenever logged in or not
						System.out.print((String)in.readObject()+"\n");
					}
					else if(message.compareToIgnoreCase("3")==0 || message.compareToIgnoreCase("4")==0)
					{
						// Message
						System.out.print("\n"+(String)in.readObject());
						
						// Mode or Type of Meal depending on the option input
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Duration or Meal Description depending on the option input
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Confirmation on what record was added
						System.out.print((String)in.readObject()+"\n");
					}
					else if(message.compareToIgnoreCase("5")==0 || message.compareToIgnoreCase("6")==0)
					{
						// Outputting last ten records
						for(int i = 0; i < 10; i++)
							System.out.print((String)in.readObject());
					}
					//NOTE: Delete option doesn't work, however decided to leave the code
					//		as it doesn't affect the program
					else if(message.compareToIgnoreCase("7")==0){
						// Record Number to delete
						System.out.print((String)in.readObject());
						sendMessage(stdin.next());
						
						// Meal or Fitness record
						System.out.print("\n"+(String)in.readObject()+"\n");
						sendMessage(stdin.next());
					}

				}
				catch(ClassNotFoundException classNot)
				{
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("-1"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			// Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			//System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		Requester client = new Requester();
		client.run();
	}
}