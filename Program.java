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
	private ArrayList<Resident> matchedResidents;// Resident IDs of matched residents with this program


	
	// constructs a Program
    public Program(String id, String n, int q) {
	
		programID= id;
		name= n;
		quota= q;
		matchedResidents= new ArrayList<Resident>();
	}

    // the rol in order of preference
	public void setROL(int[] rol) {
		
		this.rol= rol;
	}
	public int[] getROL() {
		
		return rol;
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
			int rank= getMatchedRank(matchedResidents.get(i).getResidentID());
			if (rank > leastRank) {
				leastRank= rank;
			}
		}
		return leastRank;
	}
	public Resident getLeastPreferredMatchedResident(Program p) {//todo
		Program target= (p == null) ? this : p;
		int leastRank= target.getLeastPreferredMatchedRank();
		for (int i=0; i < matchedResidents.size(); i++) {
			Resident resident= matchedResidents.get(i);
			int rank= target.getMatchedRank(resident.getResidentID());
			if (rank == leastRank) {
				return resident;
			}
		}
		return null;
	}
	public boolean containsResident(int residentID) {
		for (int i=0;i<rol.length;i++) {
			if (rol[i] == residentID) {
				return true;
			}
		
			}
		return false;
		}
		public boolean hasReachedQuota() {
			return matchedResidents.size() >= quota;
		}
		

	
	public void addMatchedResident(Resident r) {
		if (r == null) {
			return;
		}
		if (!containsResident(r.getResidentID())) {
			return;
		}
		if (matchedResidents.contains(r)) {
			r.setMatchedProgramID(programID);
			return;
		}
		if (!hasReachedQuota()) {
			matchedResidents.add(r);
			r.setMatchedProgramID(programID);
			return;
		}
		int newRank= getMatchedRank(r.getResidentID());
		int leastRank= getLeastPreferredMatchedRank();
		if (newRank != -1 && leastRank != -1 && newRank < leastRank) {
			Resident leastPreferred= getLeastPreferredMatchedResident(this);
			if (leastPreferred != null) {
				matchedResidents.remove(leastPreferred);
				leastPreferred.setMatchedProgramID(null);
			}
			matchedResidents.add(r);
			r.setMatchedProgramID(programID);
		}
	}
}