// Project CSI2120/CSI2520
// Winter 2026
// Robert Laganiere, uottawa.ca
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

// this is the (incomplete) class that will generate the resident and program maps
public class GaleShapley {
	
	public HashMap<Integer,Resident> residents;
	public HashMap<String,Program> programs;
	

	public GaleShapley(String residentsFilename, String programsFilename) throws IOException, 
													NumberFormatException {
		
		readResidents(residentsFilename);
		readPrograms(programsFilename);
	}
	
	// Reads the residents csv file
	// It populates the residents HashMap
    public void readResidents(String residentsFilename) throws IOException, 
													NumberFormatException {

        String line;
		residents= new HashMap<Integer,Resident>();
		BufferedReader br = new BufferedReader(new FileReader(residentsFilename)); 

		int residentID;
		String firstname;
		String lastname;
		String plist;
		String[] rol;

		// Read each line from the CSV file
		line = br.readLine(); // skipping first line
		while ((line = br.readLine()) != null && line.length() > 0) {

			int split;
			int i;

			// extracts the resident ID
			for (split=0; split < line.length(); split++) {
				if (line.charAt(split) == ',') {
					break;
				} 
			}
			if (split > line.length()-2)
				throw new IOException("Error: Invalid line format: " + line);

			residentID= Integer.parseInt(line.substring(0,split));
			split++;

			// extracts the resident firstname
			for (i= split ; i < line.length(); i++) {
				if (line.charAt(i) == ',') {
					break;
				} 
			}
			if (i > line.length()-2)
				throw new IOException("Error: Invalid line format: " + line);

			firstname= line.substring(split,i);
			split= i+1;
			
			// extracts the resident lastname
			for (i= split ; i < line.length(); i++) {
				if (line.charAt(i) == ',') {
					break;
				} 
			}
			if (i > line.length()-2)
				throw new IOException("Error: Invalid line format: " + line);

			lastname= line.substring(split,i);
			split= i+1;		
				
			Resident resident= new Resident(residentID,firstname,lastname);

			for (i= split ; i < line.length(); i++) {
				if (line.charAt(i) == '"') {
					break;
				} 
			}
			
			// extracts the program list
			plist= line.substring(i+2,line.length()-2);
			String delimiter = ","; // Assuming values are separated by commas
			rol = plist.split(delimiter);
			
			resident.setROL(rol);
			
			residents.put(residentID,resident);
		}	
    }

	
	// Reads the programs csv file
	// It populates the programs HashMap
    public void readPrograms(String programsFilename) throws IOException, 
													NumberFormatException {

        String line;
		programs= new HashMap<String,Program>();
		BufferedReader br = new BufferedReader(new FileReader(programsFilename)); 

		String programID;
		String name;
		int quota;
		String rlist;
		int[] rol;

		// Read each line from the CSV file
		line = br.readLine(); // skipping first line
		while ((line = br.readLine()) != null && line.length() > 0) {

			int split;
			int i;

			// extracts the program ID
			for (split=0; split < line.length(); split++) {
				if (line.charAt(split) == ',') {
					break;
				} 
			}			
			if (split > line.length()-2)
				throw new IOException("Error: Invalid line format: " + line);


			programID= line.substring(0,split);
			split++;

			// extracts the program name
			for (i= split ; i < line.length(); i++) {
				if (line.charAt(i) == ',') {
					break;
				} 
			}
			if (i > line.length()-2)
				throw new IOException("Error: Invalid line format: " + line);
			
			name= line.substring(split,i);
			split= i+1;
			
			// extracts the program quota
			for (i= split ; i < line.length(); i++) {
				if (line.charAt(i) == ',') {
					break;
				} 
			}
			if (i > line.length()-2)
				throw new IOException("Error: Invalid line format: " + line);

			quota= Integer.parseInt(line.substring(split,i));
			split= i+1;		
				
			Program program= new Program(programID,name,quota);

			for (i= split ; i < line.length(); i++) {
				if (line.charAt(i) == '"') {
					break;
				} 
			}
			
			// extracts the resident list
			rlist= line.substring(i+2,line.length()-2);
			String delimiter = ","; // Assuming values are separated by commas
			String[] rol_string = rlist.split(delimiter);
			rol= new int[rol_string.length];
			for (int j=0; j<rol_string.length; j++) {
				
				rol[j]= Integer.parseInt(rol_string[j]);
			}
			
			program.setROL(rol);
			
			programs.put(programID,program);
		}	
    }
	public HashMap<String, int[]> buildROL_Programms() {
    HashMap<String, int[]> rol_programms = new HashMap<>();
    for (String programID : programs.keySet()) {
        Program program = programs.get(programID);
        rol_programms.put(programID, program.getROL());
    }
    return rol_programms;
	}

	public HashMap<Integer, String[]> buildROL_Residents(Resident r) {
   	 HashMap<Integer, String[]> rol_residents = new HashMap<>();
    	for (Integer residentID : residents.keySet()) {
       	 Resident resident = residents.get(residentID);
       	 rol_residents.put(residentID, resident.getRol());
    		}
    	return rol_residents;
	
	}
	private boolean containsResident(int residentID, int[] rol) {
	for (int id : rol) {
		if (id == residentID) {
			return true;
		}
	}
	return false;
	}
	public void GaleShapleyMatch(HashMap<Integer,Resident> residents, HashMap<String,int[]> ROL_Programms, HashMap<Integer,String[]> ROL_Residents){
		HashMap<Resident, Program> Matched = new HashMap<>();
		HashMap<Resident, Program> UnMatched = new HashMap<>();
		 
   		//HashMap<Integer, String[]> ROL_Residents = buildROL_Residents();
		//verify that al residents has been matched or cant be 
		while(residents.size()!=(Matched.size()+UnMatched.size())){
			Resident PickedResident=null;
			for(Resident r: residents.values()){
				if(!Matched.contains(r.getResidentID())){
					PickedResident=r;
					residents.remove(r.getResidentID());// to verify
				}
			}
			for(String ProgramId:ROL_Residents.get(PickedResident.getResidentID())){
				Program p= programs.get(ProgramId);
				if(p.containsResident(PickedResident.getResidentID())){

					continue;
			}
				else if(!p.hasReachedQuota()){
					Matched.put(PickedResident, p);
					break;
				}
				else if( p.getLeastPreferredMatchedRank()<p.getMatchedRank(PickedResident.getResidentID())){
					Resident leastPreferredMatchedResident = p.getLeastPreferredMatchedResident();
					UnMatched.put(leastPreferredMatchedResident, p);
					Matched.remove(leastPreferredMatchedResident);
					Matched.put(PickedResident, p);
					break;
				}

		}
	}

	}
			

			
	

	public static void main(String[] args) {
		
		
		try {
			
			GaleShapley gs= new GaleShapley(args[0],args[1]);
			
			System.out.println(gs.residents);
			System.out.println(gs.programs);
			
        } catch (Exception e) {
			System.out.println("Usage: java GaleShapley residentsFile programsFile");
            System.err.println("Error reading the file : " + e.getMessage());
        }
	}
}
