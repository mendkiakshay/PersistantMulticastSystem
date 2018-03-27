import java.net.ServerSocket;
import java.util.*;
import java.io.*;

public class Coordinator {

	public static void main(String[] args) {
		//Taking command-line parameters
		int nport = 0, td = 0;

		//Taking command-line parameters
		try {
			Scanner inputFile = new Scanner(new File(args[0]));
			String input = inputFile.nextLine();

			Scanner myscanner = new Scanner(input);

			if(myscanner.hasNext()){
				nport = Integer.parseInt(myscanner.next());
			}

			input = inputFile.nextLine();
			myscanner = new Scanner(input);

			if(myscanner.hasNext()){
				td = Integer.parseInt(myscanner.next());
			}

		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
		}

		// create object of ServerProcess that executes the command
		System.out.println("Server Started");

		try {
			// Create nport and tport sockets
			ServerSocket nserSocket = new ServerSocket(nport);
			while (true)
			{
				//Creating thread object for nport
				CoordinatorThread coordinatorthread = new CoordinatorThread(nserSocket);
				//starting the thread
				coordinatorthread.start();
				System.out.println("Thread started");
			}
		}
		catch (Exception ex) {
			System.out.println("exceptionnn" + ex + " exception " + ex.getMessage());
		}
	}

}
