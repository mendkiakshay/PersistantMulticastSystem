import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Participant {

	public static String takeInput() throws Exception {
        System.out.print("mytftp> ");
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
        return buffer.readLine();
    }

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws Exception
		{

        int participantId = 0;
    	String messageLogFile = null, coordinatorport = null;
    	try {
			Scanner inputFile = new Scanner(new File(args[0]));
			String input = inputFile.nextLine();

			Scanner myscanner = new Scanner(input);

			if(myscanner.hasNext()){
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
				coordinatorport = myscanner.next().toString();
			}

		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
		}

        //configure normal port
        /*Socket nclientSocket = new Socket(machineName, nportNumber);
        ParticipantThread participantthread = new ParticipantThread(nclientSocket,"nport");
        participantthread.start();
*/

        while (true)
				{
            Thread.sleep(510);
            String command = takeInput();
			participantthread.sendDataToServer(command);
        }
    }

}
