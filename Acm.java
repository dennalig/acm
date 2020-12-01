
public class Acm{
	//Dennali Grissom
	private Box[][] matrix;// actual matrix
	private Subject[] subjects; //subject row
	private String [] objects;// object row
	
	private int subjCounter;
	private int objCounter;
	
	public Acm() {
		matrix= new Box[1000][1000]; // default is 1000
		subjects=new Subject[1000];
		objects= new String[1000];
		subjCounter=-1;
		objCounter=-1;
		
	}
	


	private class Box{
		String row;
		String column;
		
		boolean read,write,execute,own;
		
		public Box() {
			this.row="";
			this.column="";
			
			read=false;
			write=false;
			own=false;
			execute=false;
			
			getRow();
			getColumn();
			isRead();
			isWrite();
			isExecute();
			isOwn();
		}
		
		
		public String getRow() {
			return row;
		}
		public void setRow(String row) {
			this.row = row;
		}
		public String getColumn() {
			return column;
		}
		public void setColumn(String column) {
			this.column = column;
		}
		public boolean isRead() {
			return read;
		}
		public void setRead(boolean read) {
			this.read = read;
		}
		public boolean isWrite() {
			return write;
		}
		public void setWrite(boolean write) {
			this.write = write;
		}
		public boolean isExecute() {
			return execute;
		}
		public void setExecute(boolean execute) {
			this.execute = execute;
		}
		public boolean isOwn() {
			return own;
		}
		public void setOwn(boolean own) {
			this.own = own;
		}
	}
	
	//end of box object
	
	
	//start subj object
	
	//start of subj class 
	private class Subject{
		String name; //subj name
	
		int rowPos;// row position
		int colPos; // column position because this may differ
		public Subject(String name) {
			this.name=name;
			
			getName();
			getRowPos();
			getColPos();
			
		}
		
		//getter setters
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getRowPos() {
			return rowPos;
		}

		public void setRowPos(int rowPos) {
			this.rowPos = rowPos;
		}

		public int getColPos() {
			return colPos;
		}

		public void setColPos(int colPos) {
			this.colPos = colPos;
		}
		
	}
	
	//end subj object
	public void createSubject(String name) throws InvalidPreCondException{//1
		int alreadySubj= existPreCondition(name, getSubjects()); // checks if it exits 
		
		int alreadyObj=existPreCondition(name,getObjects()); // checks if already an object
		
		if(alreadyObj!=-1) {
			modeArrow();
			throw new InvalidPreCondException(name);//throw the object constructor
		}
//		System.out.println(alreadySubj);
		
		if(alreadySubj==-1) { //-1 means it does not exist in matrix
			
			for(int i=0;i<subjects.length;i++) {
				if(subjects[i]==null) { // we go until we hit the first null 
					subjects[i]=new Subject(name); // make it not null
					
					subjCounter++; // we will need this later
					
					createObject(name);//call create object for subject entry int the column
					
					addToMatrix(subjects[i]);
					return;
				}
			}
			
		}
		else { // throw invalid precondition exception 
			
			modeArrow();
			//System.err.println("->" +name+" is already a subject.");
			throw new InvalidPreCondException(name, name);
		}
		
		
		
		
	}
	
	public void createObject(String name)throws InvalidPreCondException {//2
		
		int alreadyObj= existPreCondition(name, getObjects()); // checks if it exits 
//		System.out.println(alreadySubj);
		
		int alreadySubj = existPreCondition(name, getSubjects());
		
		if(alreadySubj!=-1) {
			
		}
		
		// must check for conflicting names
		
		if(alreadyObj==-1) {//-1 means it does not exist in matrix
			
			for(int i=0;i<objects.length;i++) {
				if(objects[i]==null) { // we go until we hit the first null 
					objects[i]=name; // make it not null
					
					objCounter++;// we will also need this later
					
					int actuallySubject=existPreCondition(name, getSubjects());// if the passed in object is actually a subject from our createSubject method
					
					if(actuallySubject!=-1) {// if the subject does exist
						//System.out.println(actuallySubject);
						subjects[actuallySubject].colPos=i; // keeps track of subject's spot as an object
						//System.out.println(i);
						
						
						
					}
					addToMatrix(objects[i]);
					
					
					return;
				}
			}
			
		}
		else { // throw invalid precondition exception 
			//System.err.println("->" +name+" is already an object.");
			
			modeArrow();
			
			throw new InvalidPreCondException(name);
		}
		

		
	}
	
	public void enterRight(String subj, String obj, String right) throws InvalidPreCondException {//3
		// make error msg later 
		
		int existSubj=this.existPreCondition(subj, getSubjects());
		
		int existObj= this.existPreCondition(obj, getObjects());
		
		
		if(existSubj!=-1 && existObj!=-1) {
			switch(right) {
			case "r":
				// make invalid cases for if it is already satisfied
				if(matrix[existSubj][existObj].read){
					modeArrow();
					throw new InvalidPreCondException(right, subj,obj);
				}
				else {
					matrix[existSubj][existObj].read=true;
				}
			
				break;
			case "w":
				if(matrix[existSubj][existObj].write){
					modeArrow();
					throw new InvalidPreCondException(right, subj,obj);
				}
				else {
					matrix[existSubj][existObj].write=true;
				}
			
				break;
			case "e":
				if(matrix[existSubj][existObj].execute){
					modeArrow();
					throw new InvalidPreCondException(right, subj,obj);
				}
				else {
					matrix[existSubj][existObj].execute=true;
				}
			
				break;
			case "o":
				if(matrix[existSubj][existObj].own){
					modeArrow();
					throw new InvalidPreCondException(right, subj,obj);
				}
				else { // own case if we are allowing the obj to own the obj
					matrix[existSubj][existObj].own=true;
					matrix[existSubj][existObj].read=true;
					matrix[existSubj][existObj].write=true;
					matrix[existSubj][existObj].execute=true;
				}
			
				break;
			default:
				modeArrow();
//				System.err.println(right+ " Invalid right");
				throw new InvalidPreCondException(false, right);
				
			}
		}
		else {
			
			if(existSubj==-1) { //subj doesnt exist
				modeArrow();
				throw new InvalidPreCondException(subj, existSubj);
			}
			else { // obj doesnt exist
				modeArrow();
				throw new InvalidPreCondException(existObj, obj);
				
			}
			
		}
	
	
	}
	
	public void deleteRight(String subj, String obj, String right) throws InvalidPreCondException{//4
		
		int existSubj=existPreCondition(subj, getSubjects());
		
		int existObj= existPreCondition(obj, getObjects());
		
		if(existSubj!=-1 && existObj!=-1) {
			switch(right) {
			case "r":
				// make invalid cases for if it is already satisfied
				if(!matrix[existSubj][existObj].read){
					modeArrow();
					throw new InvalidPreCondException(right, subj, obj,false);
				}
				else {
					matrix[existSubj][existObj].read=false;
				}
			
				break;
			case "w":
				if(!matrix[existSubj][existObj].write){
					modeArrow();
					throw new InvalidPreCondException(right, subj, obj,false);
				}
				else {
					matrix[existSubj][existObj].write=false;
				}
				break;
			case "e":
				if(!matrix[existSubj][existObj].execute){
					modeArrow();
					throw new InvalidPreCondException(right, subj, obj,false);
				}
				else {
					matrix[existSubj][existObj].execute=false;
				}
				break;
			case "o":
				if(!matrix[existSubj][existObj].own){
					modeArrow();
					throw new InvalidPreCondException(right, subj, obj,false);
				}
				else {
					matrix[existSubj][existObj].own=false;
				}
				break;
			default:
				modeArrow();
				throw new InvalidPreCondException(false, right);
				
			}
		}
		else {
			
			if(existSubj==-1) { //subj doesnt exist
				modeArrow();
				throw new InvalidPreCondException(subj, existSubj);
			}
			else { // obj doesnt exist
				modeArrow();
				throw new InvalidPreCondException(existObj, obj);
				
			}
		}
	
	}
	
	public Subject destroySubject(String name) throws InvalidPreCondException{//5 set return type to subject later 
		
		int subjExist= existPreCondition(name, getSubjects());
		
		if(subjExist!=-1) {
			
			
			destroyObject(name,true); // gathers the object of the subject to destroy based on the stored location of the column position within the object field
			//entering in true because we dont want to mistake this as an wrong deletion of the subject from the object source
			Subject subj= subjects[subjExist];
			subjects[subjExist]=null;
			subjects= shiftSubjArr(subjects,subjExist); // new subj of arrays 
			
			int jInd=0;
			
			while(jInd<=objCounter) {
				matrix[subjExist][jInd]=null; // assigns a whole row a null if it is deleted 
				jInd++;
			}
			
			shiftMatrixRows(subjExist);
			
			subjCounter--;
			return subj;
		}
		else {
			modeArrow();
			throw new InvalidPreCondException(name, subjExist);
//			System.err.println(name+ " is not a subject ");
			
//			return null;
		}

	}
	
	public String destroyObject(String name, boolean initSubj) throws InvalidPreCondException{//6
		
		int objExist=existPreCondition(name, getObjects());
		
		int isSubj=existPreCondition(name, getSubjects());
		
		if(isSubj!=-1 && (!initSubj)) { // means it is actually a subject so we are not going to allow a delete in the form of deleting the object
			
			modeArrow();
			throw new InvalidPreCondException(name, name, isSubj);
			
		}
		
		if(objExist!=-1) {
			objects[objExist]=null;// remove object from 
			objects=shiftObjArr(objects, objExist, name);
			
			for(int i=0;i<=subjCounter;i++) {
				matrix[i][objExist]=null; // setting boxes back to null 
				
			}
			shiftMatrixCols(objExist);// shifts the actual matrix to insert nulls
		}
		else {
			modeArrow();
			
			throw new InvalidPreCondException(objExist, name);
			
//			return null;
		}
		
		objCounter--; // decrement object counter
		return name;
	}
	
	public void displayAcm() {//7
		// while loop to regard nulls
		
		Box[][]matrix=getMatrix();
		
		int counterI=0;
		int counterJ=0;
		
		int total= counterI+ counterJ;
		total=total+0;
		showObjRow();
		
		for(int i=0;i<matrix.length;i++) {
			if(subjects[i]!=null) {
				System.out.print(subjects[i].name+"|");
			}
			else {
				return;
			}
			for(int j=0;j<matrix[i].length;j++) {
				if(objects[j]!=null) {
					System.out.print("{"+j+"}");
					if(matrix[i][j].read)
						System.out.print("r");
					if(matrix[i][j].write)
						System.out.print("w");
					if(matrix[i][j].execute)
						System.out.print("e");
					if(matrix[i][j].own)
						System.out.print("o");
				}
				else {
					break;
				}
				
				System.out.print("   ");
				
				
			}
			System.out.println();
		}
		
		
	
	
		
		
	}
	

	
	public void exit() {//8
		
		modeArrow();
		System.out.println("Exit...");
		
	}
	
	
	
	private void showObjRow() { //prints objs before subjs called by display acm method
		
		System.out.print("    ");
		for(int i=0;i<objects.length;i++) {
			if(objects[i]!=null) {
				System.out.print("|"+"{"+i+"}"+objects[i]+"|");
			}
			else {
				System.out.println();
				return;
			}
		}
	}
	
	private int existPreCondition(String name,Subject[] subjs) { // checking if entered val already exists 
		for(int i=0;i<=subjCounter;i++) {
			if(subjs[i]!=null) {
				if(subjs[i].name.equals(name)) {
					return i;
				}
			}
			else {
				return -1; // means we dont have it, we hit the end
			}
		}
		return -1; // shouldnt get here
	}
	
	private int existPreCondition(String name,String[] objs) { // checking if entered val already exists in objs
		for(int i=0;i<=objCounter;i++) {
			if(objs[i]!=null) {
				if(objs[i].equals(name)) {
					return i;
				}
			}
			else {
				return -1; // means we dont have it, we hit the null ends
			}
		}
		return -1; // shouldnt get here
		
		
	}
	
	private void addToMatrix(Subject s) { // edit the size of the matrix in terms of a subject change
		
		for(int i=0;i<matrix.length;i++) { // going down each row and making new boxes for each object that exists
			if(i>=subjCounter) { // if we are past the subject counter it means we are at the end 
				//then we add the new subject's rows
				
				for(int j=0;j<matrix[i].length;j++) {
					if(j<=objCounter) { // if we are not at the end of the object list
						matrix[i][j]= new Box();
						
						if(j==s.colPos) { // if we are at the object that is object entry of a subject then we assign it all its rights
							
							matrix[i][j].execute=true;
							matrix[i][j].own=true;
							matrix[i][j].read=true;
							matrix[i][j].write=true;
							
						}
					}
					else {
						break;
					}
					
				}
			}
		}
		
		
	}
	
	private void addToMatrix(String obj) { // give a new box to every subject (a new column for obj's)
		
		for(int i=0;i<matrix.length;i++) {
			if(i<=subjCounter) {
				matrix[i][objCounter]= new Box(); // we go to the objcounter ind in j of every val in i
			}
			else {
				return;
			}
			
		}
		
		
	}
	
	private Subject[] shiftSubjArr(Subject[] subs, int ind) { // 
		
		int i=ind;
		
		while(i<=subjCounter) { // temp is null in this 
			//copied from shift obj array
			Subject temp=subs[i];
			subs[i]=subs[i+1];
			subs[i+1]=temp;

			i++;
			
		}
		
		return subs;
	}
	
	private String[] shiftObjArr(String [] obj, int ind, String name) {
		
		int i=ind;
		
		while(i<=objCounter) { // temp is null in this 
			String temp=obj[i];
			obj[i]=obj[i+1];
			obj[i+1]=temp;
			
			int actuallySubj= existPreCondition(name, subjects);
			
			if(actuallySubj!=-1) {
				subjects[actuallySubj].colPos=i;// we must reset the column position for each given object that is actually a subject that has been shifted 
			}
			i++;
			
		}
		
		return obj;
	}
	
	public void shiftMatrixRows(int iInd) {

		int i=iInd;
		
		while(i<=subjCounter) { // we will do this for each one past 
			
			int j=0;// this will reset 
			
			while(j<=objCounter) {
				Box temp=matrix[i][j];
				matrix[i][j]=matrix[i+1][j];
				matrix[i+1][j]=temp;
				
				j++; // move column
			}
			
			
			i++;// move row
			
		}
		
	}
	
	
	public void shiftMatrixCols(int jInd){
		
		
		for(int i=0;i<matrix.length;i++) {// nested example of the shift obj array method 
			if(i<=subjCounter) {
				
				int j=jInd;
//				System.out.println(i);
				while(j<=objCounter) {
					Box temp= matrix[i][j];
					matrix[i][j]=matrix[i][j+1];
					matrix[i][j+1]=temp;
					
					
					
					j++;
				}
				
			}
			else {
				return;
			}
		}
		
	}
	
	public void printS() { // print method for subjects 
		
		for(int i=0;i<subjects.length;i++) {
			if(i<=subjCounter) {
				System.out.println(subjects[i].name+" : "+ subjects[i].colPos);
			}
			else {
				return;
			}
		}
	}
	
	

	
	// getters and setters for acm
	
	public Box[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(Box[][] matrix) {
		this.matrix = matrix;
	}
	
	

	public Subject[] getSubjects() {
		return subjects;
	}

	public void setSubjects(Subject[] subjects) {
		this.subjects = subjects;
	}

	public String [] getObjects() {
		return objects;
	}

	public void setObjects(String [] objects) {
		this.objects = objects;
	}
	
	
	public void modeArrow() {
		System.out.print("->");
	}

}
