import MemoryApp.*; // The package containing generated stubs.
import org.omg.CORBA.*; // All CORBA
// needed for output to the file system.
import java.io.*;

public class MemoryClient {

	static boolean flag = false;
	static boolean invites = false;
	static int counter = 0;

	// Add the main method here in the next step.

	public static void main(String args[]) {
		// Put the try-catch block here in the next step.
		try { // Add the rest of the FundraisingClient code here.

			// Initialize the ORB
			ORB orb = ORB.init(args, null);
			// Read the object Reference for the FundraisingServant
			BufferedReader br = new BufferedReader(new FileReader("MemoryIOR"));
			String ior = br.readLine();
			// Convert the string object reference to an object
			org.omg.CORBA.Object obj = orb.string_to_object(ior);
			// Convert the object to the correct type i.e. Fundraising
			Memory MemoryRef = MemoryHelper.narrow(obj);
			// Call theoperation on the servant

			BufferedReader userEntry = new BufferedReader(
					new InputStreamReader(System.in));
			char menuChoice;
			

			do {
				System.out.println("Please login or Register");
				System.out.println("1 Register;2 Login:;");
				menuChoice = (char) (System.in.read());
				userEntry.readLine(); // Need to clear the out the buffer
				if (menuChoice == (char) '1') {
					System.out.println("Please enter Your UserName");
					String username = userEntry.readLine();
					System.out.println("Please enter your password");
					String password = userEntry.readLine();
					MemoryRef.register(username, password);
				}
				if (menuChoice == (char) '2') {
					System.out.println("Please enter Your UserName");
					String username = userEntry.readLine();
					System.out.println("Please enter your password");
					String password = userEntry.readLine();
					flag = MemoryRef.login(username, password);
					if (flag == false) {
						System.out.println(username
								+ " is not Registered on the system");
						System.out
								.println("You must Register before logging in");
					} else {
						System.out.println("Welcome " + username);
					}
				}

			} while (flag == false);
			

			do {
				invites = MemoryRef.viewInvites();
				counter++;
				if (invites == true) {
					System.out.println("You have an Invite from '"
							+ MemoryRef.viewInviteSender() + "'");
					System.out.println("Press 1 to accept or 2 to decline");
					menuChoice = (char) (System.in.read());
					userEntry.readLine();
					if (menuChoice == (char) '1') {
						MemoryRef.acceptInvite();
					}

				}
			} while (counter == 0);

			do {
				counter = 0;
				System.out.println("Menu");
				System.out.println("1 Add Memory: 2 Add Resource to Memory: 3 Share Memory 4 Delete Memory: 5 Delete Resource");
				System.out.println(" 6 View Your Memories : 7 Invite a Friend: 8 View points: 9 Transfer Points : 0 Quit");
				menuChoice = (char) (System.in.read());
				userEntry.readLine(); // Need to clear the out the buffe
				if (menuChoice == (char) '1') {
					System.out.println("Please enter your Memory");
					String memory = userEntry.readLine();
					MemoryRef.addMemory(memory);
				}
				if (menuChoice == (char) '2') {
					System.out.println("Please enter Your resource");
					String resource = userEntry.readLine();
					System.out.println("Please enter your Memory");
					String memory = userEntry.readLine();
					MemoryRef.addResource(resource, memory);
				}
				if (menuChoice == (char) '3') {
					System.out.println("Please enter The Memory you would like to Share");
					String memory = userEntry.readLine();
					System.out.println("Please enter The User You would like to share memory with1"
							+ "");
					String username = userEntry.readLine();
					MemoryRef.shareMemory(memory,username);
				}
				if (menuChoice == (char) '4') {
					System.out.println("Please enter The Memory you would like to remove");
					String memory = userEntry.readLine();
					MemoryRef.deleteMemory(memory);
				}
				if (menuChoice == (char) '5') {
					System.out.println("Please enter a descrption of the resource you would like to delete");
					String resource = userEntry.readLine();
					MemoryRef.deleteResource(resource);
				}
				if (menuChoice == (char) '6') {
					System.out.println(MemoryRef.viewMemories());
				}
				if (menuChoice == (char) '7') {
					System.out.println("Please enter Your friends Name");
					String friendUserName = userEntry.readLine();
					MemoryRef.sendInvite(friendUserName);
				}
				if (menuChoice == (char) '8') {
					System.out.println(MemoryRef.viewPoints());
				}
				if (menuChoice == (char) '9') {
					System.out.println("Please enter the amount of points you would like to transfer");
					String mypoints = userEntry.readLine();
					int i = Integer.parseInt(mypoints);
					System.out.println("Please enter the User you would like to award points to");
					String username = userEntry.readLine();
					MemoryRef.transferPoints(i,username);
				}

			} while (menuChoice != (char) '0');

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}
