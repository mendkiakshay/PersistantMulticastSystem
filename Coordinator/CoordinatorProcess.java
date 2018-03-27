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
		GroupParticipants participant = new GroupParticipants(pid, ipAddress, port);
		listParticipants.add(participant);
		return true;
	}

	public Boolean deregister(int pid)
	{
		GroupParticipants participantToDelete = findParticipantById(pid);
		listParticipants.remove(participantToDelete);
		return true;
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
