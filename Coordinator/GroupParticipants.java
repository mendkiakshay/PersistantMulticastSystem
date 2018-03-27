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
public class GroupParticipants
{
	public int id;
	public int port;
	public String ip;
	public Boolean isDisconnected;
	public Socket socket;
	public DataInputStream inputStream;
	public DataOutputStream outpuStream;
	public GroupParticipants(int id, String ip, int port, Socket socket,DataInputStream input, DataOutputStream output)
	{
		this.id=id;
		this.port=port;
		this.ip = ip;
		this.isDisconnected=false;
		this.socket = socket;
		this.inputStream = input;
		this.outpuStream = output;
	}
}
