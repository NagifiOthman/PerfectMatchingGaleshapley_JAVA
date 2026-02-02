// Project CSI2120/CSI2520
// Winter 2026
// Robert Laganiere, uottawa.ca

// this is the (incomplete) Program class
import java.util.*;
public class Program {
	
	private String programID;
	private String name;
	private int quota;
	private int[] rol;// resident IDs in order of preference
	private ArrayList<Integer> matchedResidents;// Resident IDs of matched residents with this program


	
	// constructs a Program
    public Program(String id, String n, int q) {
	
		programID= id;
		name= n;
		quota= q;
	}

    // the rol in order of preference
	public void setROL(int[] rol) {
		
		this.rol= rol;
	}
	
	// string representation
	public String toString() {
      
       return "["+programID+"]: "+name+" {"+ quota+ "}" +" ("+rol.length+")";	  
	}
	public boolean isMember(int residentID) {
		for (int i=0; i < rol.length; i++) {
			if (rol[i] == residentID) {
				return true;
			}
		}
		return false;
	}
	public int getMatchedRank(int residentID) {
		if (this.isMember(residentID)) {
			for (int i=0; i < rol.length; i++) {
				if (rol[i] == residentID) {
					return i+1;
				}
			}
		}
		return -1;
	}
	public int getLeastPreferredMatchedRank() {
		int leastRank= -1;
		for (int i=0; i < matchedResidents.size(); i++) {
			int rank= getMatchedRank(matchedResidents.get(i));
			if (rank > leastRank) {
				leastRank= rank;
			}
		}
		return leastRank;
	}
	public void addMatchedResident(int residentID) {
		// add residentID to matchedResidents TODO
		if (quota >0){
			this.matchedResidents.add(residentID);
			quota--;
		}
		int	LeastPreferredResident=this.getLeastPreferredMatchedRank();
		for(Integer Residents: matchedResidents){
			if(Residents == residentID){
				break;
			}
		}
		if(quota ==0){
			System.out.println("Program "+ this.programID + " has reached its quota.");
		}

	}
}