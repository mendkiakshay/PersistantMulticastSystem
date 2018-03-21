import java.net.ServerSocket;

public class Coordinator {

	public static void main(String[] args) {
        //Taking command-line parameters
        String snport = args[0];
        int nport = Integer.parseInt(snport);

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
