// Project CSI2120/CSI2520
// Winter 2026
// Robert Laganiere, uottawa.ca

// this is the (incomplete) Resident class
public class Resident {
	
	private int residentID;
	private String firstname;
	private String lastname;
	private String[] rol;// program IDs in order of preference for this resident
	private String matchedProgramID;//Reference to the matched program ID
	private int matchedRank;// Rank of the matched program in the resident's rol
	
	// constructs a Resident
    public Resident(int id, String fname, String lname) {
		residentID= id;
		firstname= fname;
		lastname= lname;
	}

    // the rol in order of preference
	public void setROL(String[] rol) {
		
		this.rol= rol;
	}
	
	// string representation
	public String toString() {
      
       return "["+residentID+"]: "+firstname+" "+ lastname+" ("+rol.length+")";	  
	}
	public int getResidentID() {
		return residentID;
	}
	public boolean  isMatched(){
		return matchedProgramID != null;
	}
	public int  getRank() {
		if (this.isMatched()) {
			return matchedRank;
		} else {
			return -1;
		}
	}
	public String[] getRol() {
		return rol;
	}
	
	public String  matchedProgramID() {
		if (this.isMatched()) {
			return matchedProgramID;
		} else {
			return null;
		}
	}
	public void setMatchedProgramID(String programID) {
		this.matchedProgramID= programID;
		if (programID == null) {
			matchedRank= -1;
		}
	}
	public int getMatchedRank() {
		if (matchedProgramID == null) {
			return -1;
		}
		for (int i=0; i < rol.length; i++) {
			if (rol[i].equals(matchedProgramID)) {
				matchedRank= i+1;
				break;
			}
		}

		return matchedRank;
	}
}