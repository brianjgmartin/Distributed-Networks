import MemoryApp.*; // The package containing generated stubs.
import org.omg.CORBA.*; // All CORBA
// needed for output to the file system.
import java.io.*;

public class MemoryClient {
	
	static boolean flag = true;

	// Add the main method here in the next step.

	public static void main(String args[]) {
		// Put the try-catch block here in the next step.
		try { // Add the rest of the FundraisingClient code here.

			//Initialize the ORB
			ORB orb = ORB.init(args, null);
			//Read the object Reference for the FundraisingServant
			BufferedReader br = new BufferedReader(new FileReader("MemoryIOR"));
			String ior = br.readLine();
			//Convert the string object reference to an object
			org.omg.CORBA.Object obj = orb.string_to_object(ior);
			//Convert the object to the correct type i.e. Fundraising
			Memory MemoryRef = MemoryHelper.narrow(obj);
			//Call theoperation on the servant

			// fundraisingRef.donate(10);
			// MemoryRef.register("nn","kk");

			//double  balance = fundraisingRef.getBalance();
			//System.out.println(balance);

			BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
			char menuChoice;

			do  {
						System.out.println("Please login or Register");
						System.out.println("1 Register;2 Login:;");
						menuChoice = (char)(System.in.read());
			            userEntry.readLine(); //Need to clear the out the buffer
						if (menuChoice==(char)'1') {
							System.out.println("Please enter Your UserName");
		                    String username = userEntry.readLine();
		                    System.out.println("Please enter your password");
		                    String password = userEntry.readLine();
							MemoryRef.register(username,password);
						}
						if (menuChoice==(char)'2') {
							System.out.println("Please enter Your UserName");
		                    String username = userEntry.readLine();
		                    System.out.println("Please enter your password");
		                    String password = userEntry.readLine();
		                    flag = MemoryRef.login(username,password);
						}
//						if (menuChoice==(char)'3') {
//							System.out.println("Please enter Your UserName");
//		                    String username = userEntry.readLine();
//		                    System.out.println("Please enter your Memory");
//		                    String memory = userEntry.readLine();
//							MemoryRef.addmemory(memory,username);
//						}
//						if (menuChoice==(char)'4') {
//							System.out.println("Please enter Your resource");
//		                    String resource = userEntry.readLine();
//		                    System.out.println("Please enter your Memory");
//		                    String memory = userEntry.readLine();
//							MemoryRef.addResource(resource,memory);
//						}
						

			} while (flag=false );
			do{
				System.out.println("Menu");
				System.out.println("1 Add Memory: 2 Add Resource to Memory: 3 Quit");
				menuChoice = (char)(System.in.read());
	            userEntry.readLine(); //Need to clear the out the buffe
	            if (menuChoice==(char)'1') {
					System.out.println("Please enter Your UserName");
                    String username = userEntry.readLine();
                    System.out.println("Please enter your Memory");
                    String memory = userEntry.readLine();
					MemoryRef.addmemory(memory,username);
				}
				if (menuChoice==(char)'2') {
					System.out.println("Please enter Your resource");
                    String resource = userEntry.readLine();
                    System.out.println("Please enter your Memory");
                    String memory = userEntry.readLine();
					MemoryRef.addResource(resource,memory);
				}
			}
			while(menuChoice!=(char)'3');
		
			

			
			
		}
		catch(Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}
