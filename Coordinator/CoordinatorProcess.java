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
		if(participantToDelete != null){
			try {
				participantToDelete.outputStream.close();
				participantToDelete.inputStream.close();
				participantToDelete.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listParticipants.remove(participantToDelete);
			return true;
		}
		else
			return false;
		
	}
	public Boolean disconnect(int pid, String ipAddress, int port)
	{
		return true;
	}

	public Boolean reconnect(int pid, String ipAddress, int port)
	{
		return true;
	}

	public Boolean msend(String message)
	{
		return true;
	}

}
