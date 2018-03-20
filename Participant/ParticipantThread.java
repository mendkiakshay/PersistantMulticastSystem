import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantThread extends Thread {
	
	Socket socket,tsocket;
    boolean terminated = false;
    DataInputStream input,tinput;
    DataOutputStream output,toutput;
    String command = "";
    boolean shouldrun = true;
    String port;

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
