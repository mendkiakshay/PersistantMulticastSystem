import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.net.*;
public class CoordinatorProcess
{

	public static List<GroupParticipants> listParticipants = new ArrayList<GroupParticipants>();

	public GroupParticipants findParticipantById(int id)
	{
		for(GroupParticipants grp : listParticipants)
		{
			if(grp.id==id)
			{
				return grp;
			}
		}
		return null;
	}

	// Method to register participants
	public Boolean register(int pid, String ipAddress, int port)
	{
		try
		{
			Socket socket = new Socket(ipAddress,port);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream op = new DataOutputStream(socket.getOutputStream());
			GroupParticipants participant = new GroupParticipants(pid, ipAddress, port,socket,in,op);
			listParticipants.add(participant);
			// participant.outputStream.writeUTF("HI");
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public Boolean deregister(int pid)
	{
		GroupParticipants participantToDelete = findParticipantById(pid);
		if(participantToDelete != null)
		{
			try
			{
				participantToDelete.outputStream.close();
				participantToDelete.inputStream.close();
				participantToDelete.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listParticipants.remove(participantToDelete);
			for(GroupParticipants g : listParticipants)
			{
				System.out.println("sending message: ");
				System.out.println(g.id+" "+g.port+" "+g.ip);

			}
			return true;
		}
		else
			return false;

	}
	public Boolean disconnect(int pid)
	{
		try
		{
			GroupParticipants participantToDisconnect = findParticipantById(pid);
			participantToDisconnect.isDisconnected=true;
			participantToDisconnect.outputStream.close();
			participantToDisconnect.inputStream.close();
			participantToDisconnect.socket.close();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	public Boolean reconnect(int pid, String ipAddress, int port, int td)
	{
		try
		{
			Socket socket = new Socket(ipAddress,port);
			GroupParticipants participantToReconnect = findParticipantById(pid);
			participantToReconnect.port = port;
			participantToReconnect.outputStream =new DataOutputStream(socket.getOutputStream());
			participantToReconnect.inputStream =new DataInputStream(socket.getInputStream());
			
			for(StringDateTime messageToSend : participantToReconnect.savedMessage)
			{
				int currentTime =(int)(System.currentTimeMillis()/1000); 
				int timeDiff =currentTime -	messageToSend.timeStamp;
				if(td>=timeDiff)
				{
					//send the message!
					participantToReconnect.outputStream.writeUTF(messageToSend.message);
				}
			}						
			
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}

	}

	public Boolean msend(String message)
	{
		try
		{
			int i=0;
			for(GroupParticipants g : listParticipants)
			{
				if(!g.isDisconnected)
				{
					System.out.println("sending message: "+i);
					System.out.println(g.id+" "+g.port+" "+g.ip);
					g.outputStream.writeUTF(message);
					i++;
				}
				else
				{
					//LOGIC to SAVE data.
					System.out.println("Saving data");
					g.AddStringDateTime(g, message, (int)(System.currentTimeMillis()/1000));
					
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}

}
