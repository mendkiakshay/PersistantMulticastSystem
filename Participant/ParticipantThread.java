import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    String port;

		public void sendDataToServer(String mycommand)
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
		ParticipantThread(ServerSocket bserSocket ,int portB)
		{
			this.bserSocket = bserSocket;
			this.bsocket = this.bserSocket.accept();
			binput = new DataInputStream(this.bsocket.getInputStream());
			boutput = new DataOutputStream(this.bsocket.getOutputStream());
		}

    ParticipantThread(Socket socket, String port)
		{
        try
				{
        	this.port=port;
        	if(this.port=="nport")
        	{
	            this.socket = socket;
	            input = new DataInputStream(socket.getInputStream());
	            output = new DataOutputStream(socket.getOutputStream());
        	}
        	}
        catch (IOException ex)
        {
            Logger.getLogger(ParticipantThread.class.getName()).log(Level.SEVERE, null, ex);
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
									//e.printStackTrace();
								Thread.currentThread().interrupt();
							}
						}
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


}
