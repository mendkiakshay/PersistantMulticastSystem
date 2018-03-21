import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantThread extends Thread
{

		Socket socket,tsocket;
    boolean terminated = false;
    DataInputStream input,tinput;
    DataOutputStream output,toutput;
    String command = "";
    boolean shouldrun = true;
    String port;

		public void sendDataToServer(String mycommand)
		{
				try {
						//use appropriate ports
						if(port == "nport")
						{
							this.command = mycommand;
							System.out.println("Inside sendDataToServer Method; command is: "+this.command);
							output.writeUTF(command);
							output.flush();
					}
					else
					{
						// System.out.println("Inside sendDataToServer Method of TPORT");
						command = mycommand;
						toutput.writeUTF(command);
						toutput.flush();
						System.out.println(tinput.readUTF());
					}
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}
		}

    ParticipantThread(Socket socket, String port) {
        try {
        	this.port=port;

        	if(this.port=="nport")
        	{
	            this.socket = socket;
	            input = new DataInputStream(socket.getInputStream());
	            output = new DataOutputStream(socket.getOutputStream());
        	}
        	else
        	{
        			this.tsocket = socket;
 	            tinput = new DataInputStream(tsocket.getInputStream());
 	            toutput = new DataOutputStream(tsocket.getOutputStream());
              // start();
        	}
        	}
        catch (IOException ex)
        {
            Logger.getLogger(ParticipantThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
