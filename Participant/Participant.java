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
    public static void main(String[] args) throws Exception {

        String machineName = args[0];
    		String nportNo = args[1];
    		// String tportNo = args[2];
    		int nportNumber = Integer.parseInt(nportNo);
    		// int tportNumber = Integer.parseInt(tportNo);

        //configure normal port
        Socket nclientSocket = new Socket(machineName, nportNumber);
        ParticipantThread participantthread = new ParticipantThread(nclientSocket,"nport");
        participantthread.start();

        while (true) {
            Thread.sleep(510);
            String command = takeInput();



        }
    }

}
