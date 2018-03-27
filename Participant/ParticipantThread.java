import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

	ParticipantThread(ServerSocket bserSocket ,int portB, String mycommand)
	{

		try {
			this.command = mycommand;
			this.bserSocket = bserSocket;
			//this.bsocket = this.bserSocket.accept();
			/*binput = new DataInputStream(this.bsocket.getInputStream());
			boutput = new DataOutputStream(this.bsocket.getOutputStream());*/
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
		}
		catch (IOException ex)
		{
			Logger.getLogger(ParticipantThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendDataToServer(String mycommand)
	{
		/*if(mycommand.contains("register")){

			try {
				System.out.println("Inside sendDataToServer Method; command is: "+this.command);
				boutput.writeUTF(command);
				boutput.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else{
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
		}*/
		
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

				if(command.contains("register")){
					this.bsocket = this.bserSocket.accept();
					binput = new DataInputStream(this.bsocket.getInputStream());
					boutput = new DataOutputStream(this.bsocket.getOutputStream());
					receivemessage(binput, boutput);
					
				}

				if(command!="")
				{
					// for any other commands than get put quit; simply send the
					// command to the Server

					String inputString = "";
					if (shouldrun)
					{
						inputString = input.readUTF();
					}
					System.out.println(inputString);
					this.command="";
				}
				// this.command="";
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
			FileOutputStream fileoutput = new FileOutputStream(messageLogFileName);

			int characters;
			do {
				characters = Integer.parseInt(binput.readUTF());
				fileoutput.write(characters);    			

			} while (characters != -1);

			fileoutput.close();
			//System.out.println("ID IS:"+input.readUTF());
			System.out.println("Multicast message is received and copied to messageLogFile");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
