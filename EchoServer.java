package gmit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
  public static void main(String[] args) throws Exception {
    @SuppressWarnings("resource")
	ServerSocket m_ServerSocket = new ServerSocket(2004,10);
    int id = 0;
    while (true) {
      Socket clientSocket = m_ServerSocket.accept();
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
    }
  }
}

class ClientServiceThread extends Thread {
  Socket clientSocket;
  String message;
  int clientID = -1;
  boolean running = true;
  ObjectOutputStream out;
  ObjectInputStream in;

ClientServiceThread(Socket s, int i) {
	clientSocket = s;
	clientID = i;
}

void sendMessage(String msg){
	try{
		out.writeObject(msg);
		out.flush();
		
	} catch(IOException ioException){
		ioException.printStackTrace();
	}
}

// Delete Record
void deleteRecord(String dRecordNumber, String user, String choice) throws IOException {
	File inputFile = new File(user+choice+".txt");
	
    //Construct the new file that will later be renamed to the original filename.
    File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
    BufferedReader br = new BufferedReader(new FileReader(user+choice+".txt"));
    PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
    String line = null;

    //Read from the original file and write to the new
    //unless content matches data to be removed.
    while ((line = br.readLine()) != null) {
        if (!line.trim().equals(dRecordNumber)) {
            pw.println(line);
            pw.flush();
        }
    }
    
    pw.close();
    br.close();
    
    //Delete the original file
    if (!inputFile.delete()) {
    	System.out.println("Could not delete file");
        return;
    }

    //Rename the new file to the filename the original file had.
    if (!tempFile.renameTo(inputFile))
    	System.out.println("Could not rename file");
}

// Display last ten records
void lastTen(String choice, String user) throws IOException {
	String[] lines = new String[10];	// max 10 entries... allows last 10 to be inserted
	int count = 0;	// counter for the position in the array
	String line = null;	// current line
	File file = new File(user+choice+".txt");	//  file
	BufferedReader reader = new BufferedReader(new FileReader(file));	// reader
    
	while ((line = reader.readLine()) != null) {	// read line by line
	    lines[count % lines.length] = line;
	    count++;
	}
	
	reader.close();	// close reader
	
	// Works more or less like a sort
	int start = count - 10;
	if (start < 0)
	    start = 0;

	for (int i = start; i < count; i++)
		sendMessage(lines[i % lines.length] + "\n");	
	
}

// Add Meal Record
void mealRecord(String user) throws IOException, ClassNotFoundException {
	sendMessage("Adding Fitness Record");
	
	// Input
	sendMessage("\nEnter Type of Meal: ");
	String type = (String)in.readObject();
	
	// Input
	sendMessage("Enter Meal Description: ");
	String description = (String)in.readObject();
	
	// File
	File yourFile = new File(user+"Meal.txt");
	yourFile.createNewFile();
	
	// File scanner
	Scanner inFile = new Scanner(new File(user+"Meal.txt"));
	int fRecordNumber = 0;	// to find last number record
	
	while (inFile.hasNext()) {
		fRecordNumber = inFile.nextInt();
		@SuppressWarnings("unused")
		String fType = inFile.next();
		@SuppressWarnings("unused")
		String fDescription = inFile.next();
		
	}
	
	inFile.close();
	
	int recordNumber = fRecordNumber + 1;	// increment last found record number and increment by 1 to set to new recordNumber
	
	// File Variables
	FileWriter fw = null;
	BufferedWriter bw = null;
	PrintWriter out = null;
	
	try {
	    fw = new FileWriter(user+"Meal.txt", true);
	    bw = new BufferedWriter(fw);
	    out = new PrintWriter(bw);
	    
	    // Print to file
	    out.println(recordNumber + " " + type + " " + description);
	    
	    // Close connections
	    out.close();
	    bw.close();
	    fw.close();
	} catch (IOException e) {
	    System.out.println(e);
	}
	
	// Sent confirmation
	sendMessage("Added Fitness Record: " + recordNumber + " " + type + " " + description);	
}

// Add Fitness Record
void fitnessRecord(String user) throws ClassNotFoundException, IOException {
	sendMessage("Adding Fitness Record");
	
	sendMessage("\nEnter Mode: ");
	String mode = (String)in.readObject();
	
	sendMessage("Enter Duration: ");
	String duration = (String)in.readObject();
	
	File yourFile = new File(user+"Fitness.txt");
	yourFile.createNewFile();
	
	Scanner inFile = new Scanner(new File(user+"Fitness.txt"));
	int fRecordNumber = 0;
	
	while (inFile.hasNext()) {
		fRecordNumber = inFile.nextInt();
		@SuppressWarnings("unused")
		String fMode = inFile.next();
		@SuppressWarnings("unused")
		String fDuration = inFile.next();
		
	}
	
	inFile.close();
	
	int recordNumber = fRecordNumber + 1;
	
	FileWriter fw = null;
	BufferedWriter bw = null;
	PrintWriter out = null;
	
	try {
	    fw = new FileWriter(user+"Fitness.txt", true);
	    bw = new BufferedWriter(fw);
	    out = new PrintWriter(bw);
	    
	    out.println(recordNumber + " " + mode + " " + duration);
	    
	    out.close();
	    bw.close();
	    fw.close();
	} catch (IOException e) {
	    System.out.println(e);
	}
	
	sendMessage("Added Fitness Record: " + recordNumber + " " + mode + " " + duration);	
}

// Login to user records
String login() throws ClassNotFoundException, IOException {
	sendMessage("Attempting to log in");
	
	// Input
	sendMessage("\nName: ");
	String name = (String)in.readObject();
	
	// File
	Scanner inFile = new Scanner(new File("UserDatabase.txt"));
	
	// Message, success or failure
	String output = "Failed to log in!";
	
	// User that has been found
	String found = "";
	
	// For name search purposes
	while (inFile.hasNext()) {
		
		// New user object
		User user = new User();
		
		// Set values
		user.setName(inFile.next());
		user.setAddress(inFile.next());
		user.setPPS(inFile.next());
		user.setAge(inFile.next());
		user.setWeight(inFile.next());
		user.setHeight(inFile.next());
		
		// To lower case to compare
		user.getName().toLowerCase();
		name.toLowerCase();
		
		if(user.getName().equals(name)) {	// compare
			output = "Successfully Logged in!";	// on success change output message
			found = user.getName();	// set user to found
			break;	// break out
		}
	}
	
	inFile.close();	// close 
	
	sendMessage(output);	// send message
	
	return found;
}

// Register a new user
void register() throws ClassNotFoundException, IOException {
	// User object
	User u1 = new User();
	
	System.out.println("Register");
	
	// Input
	sendMessage("Enter Name: ");
	u1.setName((String)in.readObject());
	
	sendMessage("Enter Address: ");
	u1.setAddress((String)in.readObject());
	
	sendMessage("Enter PPS: ");
	u1.setPPS((String)in.readObject());
	
	sendMessage("Enter Age: ");
	u1.setAge((String)in.readObject());
	
	sendMessage("Enter Weight: ");
	u1.setWeight((String)in.readObject());
	
	sendMessage("Enter Height: ");
	u1.setHeight((String)in.readObject());
	
	// Confirmation message
	sendMessage(u1.toString());
	
	// File Variables
	FileWriter fw = null;
	BufferedWriter bw = null;
	PrintWriter out = null;
	
	try {
	    fw = new FileWriter("UserDatabase.txt", true);	// file
	    bw = new BufferedWriter(fw);
	    out = new PrintWriter(bw);
	    
	    // Output to file
	    out.println(u1.getName() + " " + u1.getAddress() + " " + u1.getPPS() + " " + u1.getAge() + " " 
	    + u1.getWeight() + " " + u1.getHeight());
	    
	    // Close the connection
	    out.close();
	    bw.close();
	    fw.close();
	} catch (IOException e) {
	    System.out.println(e);
	}
}

public void run() {
	System.out.println("Accepted Client : ID - " + clientID + " : Address - "
        + clientSocket.getInetAddress().getHostName());
    try 
    {
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("Accepted Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
		
		String user = "Maciej";
		
		do{
			try
			{
				// Menu options
				sendMessage("1 - Register\n2 - Log in\n3 - Add fitness record\n4 - Add meal record\n5 - View last ten meal records"
						+ "\n6 - View last ten fitness records\n7 - Delete a record\n-1 - Exit\nYour Option: ");
				message = (String)in.readObject();
				
				// Different options being implemented
				if(message.compareToIgnoreCase("1")==0) {
					register();	// register new user
				} // if
				
				else if(message.compareToIgnoreCase("2")==0)
				{
					user = login();	// verify login and set user to be used below
				}
			
				else if(message.compareToIgnoreCase("3")==0) 
				{
					fitnessRecord(user);	// add fitness record
				}
				
				else if(message.compareToIgnoreCase("4")==0) 
				{
					mealRecord(user);	// add meal record
				}
				
				else if(message.compareToIgnoreCase("5")==0) 
				{
					lastTen("Meal", user);	// display last ten records for meals
				}
				
				else if(message.compareToIgnoreCase("6")==0) 
				{
					lastTen("Fitness", user);	// display last ten records for fitness
				}
				
				//NOTE: Delete option doesn't work, however decided to leave the code
				//		as it doesn't affect the program
				else if(message.compareToIgnoreCase("7")==0) 
				{
					// Record number to delete
					sendMessage("Enter Record Number to delete: ");
					String dRecordNumber = (String)in.readObject();
					
					// Fitness or Meal record to delete
					sendMessage("Type 'Fitness' or 'Meal' depending on what record you want to delete");
					String choice = (String)in.readObject();

					// Attempt to delete
					deleteRecord(dRecordNumber, user, choice);

				}
					
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			}
			
    	}while(!message.equals("-1"));
      
		System.out.println("Ending Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
