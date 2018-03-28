import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class Participant
{
	public static String takeInput() throws Exception
	{
		System.out.print("Participant> ");
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(reader);
		return buffer.readLine();
	}

	@SuppressWarnings("SleepWhileInLoop")
	public static void main(String[] args) throws Exception
	{
		InetAddress localhost = InetAddress.getLocalHost();
		Socket nclientSocket=null;
		int participantId = 0, port = 0;
		String messageLogFile = null, coordinatorip=null, participantIp = localhost.getHostAddress();
		try {
			Scanner inputFile = new Scanner(new File(args[0]));
			String input = inputFile.nextLine();

			Scanner myscanner = new Scanner(input);

			if(myscanner.hasNext())
			{
				participantId = Integer.parseInt(myscanner.next());
			}

			input = inputFile.nextLine();
			myscanner = new Scanner(input);

			if(myscanner.hasNext()){
				messageLogFile = myscanner.next().toString();

			}

			input = inputFile.nextLine();
			myscanner = new Scanner(input);


			if(myscanner.hasNext()){
				coordinatorip = myscanner.next().toString();
				System.out.println(coordinatorip);
				// coordinatorip = ipandPort.split(" ")[0];
				// coordinatorport = ipandPort.split(" ")[1];
			}

			if(myscanner.hasNext()){
				port = Integer.parseInt(myscanner.next());
				System.out.println(port);
				// coordinatorip = ipandPort.split(" ")[0];
				// coordinatorport = ipandPort.split(" ")[1];
			}

		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
		}

		// System.out.println("participantId is: "+participantId);
		// System.out.println("messageLogFile is: "+messageLogFile);
		//configure normal port
		nclientSocket = new Socket(coordinatorip, port);
		ParticipantThread participantthread = new ParticipantThread(nclientSocket);
		participantthread.start();

		//taking input
		while (true)
		{
			Thread.sleep(510);
			String command = takeInput();
			if(command.contains("register"))
			{
				int portB =Integer.parseInt(command.split(" ")[1]);
				ServerSocket serSocketB = new ServerSocket(portB);
				ParticipantThread myparticipant = new ParticipantThread(serSocketB, portB, command+" "+participantId+" "+participantIp);
				myparticipant.messageLogFileName = messageLogFile;
				myparticipant.start();
				//myparticipant.sendDataToCoordinator(command+" "+participantId+" "+participantIp);
			}
			
			participantthread.sendDataToCoordinator(command+" "+participantId+" "+participantIp);
		}
	}

}
