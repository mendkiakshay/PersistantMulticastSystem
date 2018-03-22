import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class Participant
{

	public static String takeInput() throws Exception
	{
        System.out.print("mytftp> ");
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
        return buffer.readLine();
    }

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws Exception
		{

			Socket nclientSocket=null;
      int participantId = 0, port = 0;
    	String messageLogFile = null, ip=null;
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
				ip = myscanner.next().toString();
				System.out.println(ip);
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
				nclientSocket = new Socket(ip, port);
        ParticipantThread participantthread = new ParticipantThread(nclientSocket,"nport");
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
							ParticipantThread participantthread = new ParticipantThread(serSocketB,portB);
			        participantthread.start();
						}
						participantthread.sendDataToServer(command);
        }
    }

}
