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
	int td=0;

	CoordinatorThread(ServerSocket sersocket, int td)
	{
		//Initialize the socket, DataInputStream, DataOutputStream for object
		try
		{
			this.socket = sersocket.accept();
			System.out.println("Participant connection arrived");
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
			this.td=td;
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

				if (splitCommand(inputString)[0].equalsIgnoreCase("deregister"))
				{
					System.out.println("Deregistration started");
					boolean result = mycommand.deregister(Integer.parseInt(splitCommand(inputString)[1]));
					output.writeUTF("deregister is complete");

				}

				if (splitCommand(inputString)[0].equalsIgnoreCase("msend"))
				{
					String msendMessage = splitCommand(inputString)[1];
					mycommand.msend(msendMessage);
				}
				if(splitCommand(inputString)[0].equalsIgnoreCase("disconnect"))
				{
					mycommand.disconnect(Integer.parseInt(splitCommand(inputString)[1]));
				}
				if(splitCommand(inputString)[0].equalsIgnoreCase("reconnect"))
				{
					mycommand.reconnect(Integer.parseInt(splitCommand(inputString)[2]), splitCommand(inputString)[3], Integer.parseInt(splitCommand(inputString)[1]),td);
					//mycommand.reconnect();
				}


			}
		}
		catch (Exception e)
		{
			System.out.println("exception:  "+e.getMessage());
		}
	}

}
