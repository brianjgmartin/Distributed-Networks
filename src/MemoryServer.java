// The package containing our stubs.
import MemoryApp.*;
// All CORBA applications need these classes.
import org.omg.CORBA.*;
// needed for output to the file system.
import java.io.*;


public class MemoryServer {
	// Add the main method here in the next step.

	public static void main(String args[]) {
		// Add the try-catch block here in the next step.

		try {
			//Initialize the ORB
			ORB orb = ORB.init(args, null);
			//Instantiate the HelloServant on the server
			MemoryServant MemoryRef = new MemoryServant();
			//Connect the HelloServant to the orb
			orb.connect(MemoryRef);
			//Store an object Reference to the HelloServant in a
			//String format
			String ior = orb.object_to_string(MemoryRef);
			//Write the object reference to the helloServant to a
			//file called HelloIOR
			FileOutputStream fos = new FileOutputStream("MemoryIOR");
			PrintStream ps = new PrintStream(fos);
			ps.print(ior);
			ps.close();
			//Run the orb so that it waits for requests from the
			//client
			orb.run();

		}
		catch(Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}
}