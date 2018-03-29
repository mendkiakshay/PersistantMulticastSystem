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
	CoordinatorProcess mycommand = new CoordinatorProcess();

	CoordinatorThread(ServerSocket sersocket)
	{
		//Initialize the socket, DataInputStream, DataOutputStream for object
		try
		{
			this.socket = sersocket.accept();
			System.out.println("Participant connection arrived");
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex)
		{
			Logger.getLogger(CoordinatorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//Method to split the input command
		public String[] splitCommand(String command)
		{
			return command.split(" ");
		}

	public void run()
	{
		try
		{
			// read the command
			while(true)
			{
				while (input.available() == 0)
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				System.out.println("reading input");
				inputString = input.readUTF();
				System.out.println(inputString);

				if (splitCommand(inputString)[0].equalsIgnoreCase("register"))
				{
					if(mycommand.register(Integer.parseInt(splitCommand(inputString)[2]), splitCommand(inputString)[3], Integer.parseInt(splitCommand(inputString)[1])))
						{
							System.out.println("logging register");
							output.writeUTF("register is received at Coordinator");
						}
				}
				
				if (splitCommand(inputString)[0].equalsIgnoreCase("deregister")){
					output.writeUTF("deregister is received at Coordinator");
					boolean result = mycommand.deregister(Integer.parseInt(splitCommand(inputString)[2]));
					System.out.println("Deregistration complete");
				}
				
				if (splitCommand(inputString)[0].equalsIgnoreCase("msend")){
					String msendMessage = splitCommand(inputString)[1];
					mycommand.msend(msendMessage);
				}

			}
		}
		catch (Exception e)
		{
			System.out.println("exception:  "+e.getMessage());
		}
	}

}
