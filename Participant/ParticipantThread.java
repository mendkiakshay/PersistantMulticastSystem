import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantThread extends Thread
{

	Socket socket,bsocket;
	ServerSocket bserSocket;
	DataInputStream input,binput;
	DataOutputStream output,boutput;
	String command = "";
	boolean shouldrun = true;
	String port; //"threadA" or "threadB"
	String messageLogFileName;
	String threadType;

	ParticipantThread(ServerSocket bserSocket ,int portB, String mycommand)
	{

		try {
			// this.command = mycommand;
			this.bserSocket = bserSocket;
			this.threadType = "B";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ParticipantThread(Socket socket)
	{
		try
		{
			this.socket = socket;
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			this.threadType="A";
		}
		catch (IOException ex)
		{
			Logger.getLogger(ParticipantThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendDataToCoordinator(String mycommand)
	{
		try
		{
			//use appropriate ports
			this.command = mycommand;
			System.out.println("Inside sendDataToServer Method; command is: "+this.command);
			output.writeUTF(command);
			output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	//Run method for multithreading
	public void run()
	{
		try
		{
			while (shouldrun) {
				if(input!=null)
				{
					while ((input.available() == 0))
					{
						try
						{
							Thread.sleep(1);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
							//Thread.currentThread().interrupt();
						}
					}
				}

				 if(this.threadType.equals("B")) 
	                { 
	                            if(this.bsocket==null) 
	                            { 
	                                this.bsocket = this.bserSocket.accept(); 
	                                binput = new DataInputStream(this.bsocket.getInputStream()); 
	                                boutput = new DataOutputStream(this.bsocket.getOutputStream()); 
	                            } 
	                            else 
	                            { 
	                                 if(binput.available()!=0) 
	                                    receivemessage(binput, boutput); 
	                            } 
	                }
	 

				if(command!="")
				{
					String inputString = "";
					if (shouldrun)
					{
						inputString = input.readUTF();
					}
					System.out.println(inputString);
					this.command="";
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			//  e.printStackTrace();
		}
	}

	public void receivemessage(DataInputStream binput, DataOutputStream boutput){
		System.out.println("Inside receivemessage");
		try {
			
			File mymessagefile = new File(messageLogFileName);
			
			if(mymessagefile.exists()){
				BufferedWriter fileoutput = new BufferedWriter(new FileWriter(mymessagefile.getAbsoluteFile(), true));
				fileoutput.write(binput.readUTF());
				fileoutput.close();
			}
			else{
				BufferedWriter fileoutput = new BufferedWriter(new FileWriter(mymessagefile));
				fileoutput.write(binput.readUTF());
				fileoutput.close();
			}
			//System.out.println("ID IS:"+input.readUTF());
			System.out.println("Multicast message is received and copied to messageLogFile");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
