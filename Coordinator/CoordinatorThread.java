import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorThread extends Thread {
	Socket socket;
	DataInputStream input;
	DataOutputStream output;
	String inputString = null;

	CoordinatorThread(ServerSocket sersocket)
	{
		//Initialize the socket, DataInputStream, DataOutputStream for object
		try
		{
			this.socket = sersocket.accept();
			System.out.println("Client connection arrived");
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex)
		{
			Logger.getLogger(CoordinatorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void run()
	{
		try
		{
			// while (input.available() == 0)
			// {
			// 	try
			// 	{
			// 		Thread.sleep(1);
			// 	}
			// 	catch (InterruptedException e)
			// 	{
			// 		e.printStackTrace();
			// 	}
			// }
			// read the command
			while(true)
			{
				System.out.println("reading input");
				inputString = input.readUTF();
				System.out.println(inputString);
			}
		}
		catch (Exception e)
		{
			System.out.println("exception:  "+e.getMessage());
		}
	}

}
