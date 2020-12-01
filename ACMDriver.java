import java.util.Scanner;
public class ACMDriver {

	public static void main(String [] args) {
		displayMenu();
		Scanner scnr= new Scanner(System.in);
		Acm acm = new Acm();

		String userInput=scnr.nextLine().trim();
		while(!userInput.equals("8")) {
			
			String interInput; // interactive input in a mode
			switch(userInput) {
				case "1":
					System.out.println("->Create subject s:");
					modeArrow();
					interInput=scnr.nextLine().trim();
				try {
					acm.createSubject(interInput);
				} catch (InvalidPreCondException e) {}
				
				
				
					break;
				case "2":
					System.out.println("->Create object o:");
					modeArrow();
					interInput=scnr.nextLine().trim();
				try {
					acm.createObject(interInput);
				} catch (InvalidPreCondException e) {}
				
				
					break;
				case "3":
					modeArrow();
					System.out.println("->Enter Right r");
					System.out.println("enter desired subject: ");
					String desSubj=scnr.nextLine().trim();
					
					modeArrow();
					System.out.println("enter desired object: ");
					String desObj=scnr.nextLine().trim();
					
					modeArrow();
					System.out.println("enter desired right r ('r', 'w', 'o', or 'e') ");
					String right=scnr.nextLine().trim();
					
					try {
						acm.enterRight(desSubj, desObj, right);
					}
					catch(InvalidPreCondException e) {}
					
					
					
					
					break;
				case "4":
					System.out.println("->delete right r:");
					
					System.out.println("enter desired subject: ");
					String dSubj=scnr.nextLine().trim();
					
					modeArrow();
					System.out.println("enter desired object: ");
					String dObj=scnr.nextLine().trim();
					
					modeArrow();
					System.out.println("enter desired right r ('r', 'w', 'o', or 'e') ");
					String dRight=scnr.nextLine().trim();
					
					try {
						acm.deleteRight(dSubj, dObj, dRight);
					}
					catch(InvalidPreCondException e) {}
					
					
					break;
				case "5":
					System.out.println("->Destroy subject s:");
					modeArrow();
					interInput=scnr.nextLine().trim();
				try {
					acm.destroySubject(interInput);
				} catch (InvalidPreCondException e) {}
				
				
					break;
				case "6":
					System.out.println("->Destroy object o:");
					modeArrow();
					interInput=scnr.nextLine().trim();
					
				try {
					acm.destroyObject(interInput, false); // false means we didnt come from subj method
					
				} catch (InvalidPreCondException e) {}
				
				
					break;
				case "7":
					System.out.println("->Display ACM:");
					acm.displayAcm();
					break;
				case "8":
					break;
//				case "t":
//					acm.showObjRow();
//					break;
				case "s":
					acm.printS();
					break;
				default:
					modeArrow();
					System.err.println(userInput+ " is not a menu option.");
					break;
			}
			System.out.println("->Please enter another menu option: \r\n");
			modeArrow();
			userInput=scnr.nextLine().trim();
		
		}
		
		System.out.println("Exit");
		
		scnr.close();
		

		
	}
	
	public static void displayMenu() {
		System.out.println(""
			+  "1) create subject\r\n" + 
				"2) create object\r\n" + 
				"3) enter right\r\n" + 
				"4) delete right\r\n" + 
				"5) destroy subject\r\n" + 
				"6) delete object\r\n" + 
				"7) display ACM\r\n" + 
				"8) exit\r\n");
		
		System.out.println("Please enter a menu option: \r\n");
		modeArrow();
	}
	
	public static void modeArrow() {
		System.out.print("->");
	}
	
}
