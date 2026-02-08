// Project CSI2120/CSI2520
// Winter 2026
// Robert Laganiere, uottawa.ca

import java.util.HashMap;

// Class to hold the results of the Gale-Shapley matching algorithm
public class MatchResult {
    private HashMap<Resident, Program> matched;
    private HashMap<Resident, Program> unmatched;
    
    public MatchResult(HashMap<Resident, Program> matched, HashMap<Resident, Program> unmatched) {
        this.matched = matched;
        this.unmatched = unmatched;
    }
    
    public HashMap<Resident, Program> getMatched() {
        return matched;
    }
    
    public HashMap<Resident, Program> getUnmatched() {
        return unmatched;
    }
}
