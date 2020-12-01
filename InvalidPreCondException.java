
public class InvalidPreCondException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPreCondException(String name, String s) { //already exists as a subject
		System.err.println(name + " already is a subject. ");
	}
	
	public InvalidPreCondException(String name, int ind) { //does not exist as a subject
		System.err.println(name + " is not a subject. ");
	}
	
	public InvalidPreCondException(String name, String s, int ind) { // exception for trying to delete subject as an object, as we will not allow
		
		System.err.println(name+" is actually an object, please delete "+ name+ " as a subject with command 5.");
		
	}
	

	
	

	
	public InvalidPreCondException(String name) { //already exists as an object
		System.err.println(name + " already is an object. ");
	}
	
	public InvalidPreCondException(int ind, String name) { //does not exist as an object
		System.err.println(name+ " is not an object. ");
	}
	
	
	// rights exceptions
	
	public InvalidPreCondException(String right, String subjName, String objName) { //right already given
		System.err.println(right + " is already given for "+subjName +" to "+right+" to "+objName);
	}
	
	public InvalidPreCondException(String right, String subjName, String objName, boolean r) { //right is already nonexistent 
		System.err.println(right + " already does not exist for "+subjName +" to "+right+" to "+objName);
	}
	
	public InvalidPreCondException(boolean r, String right) { //right is already nonexistent 
		System.err.println(right + "is not a valid right. Only use  'r', 'w', 'o', or 'e'.");
	}
	
	
	
	
	
	

}
