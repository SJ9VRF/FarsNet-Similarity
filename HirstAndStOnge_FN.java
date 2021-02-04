
//hirstAndStOnge: rel(s1,s2) = 15
//for strong Relations
//rel(s1,s2) = C-pathLength-k*directionChanges

public class HirstAndStOnge_FN {

	public HirstAndStOnge_FN(){
		
	}
	
	public static double CompMeasure(String word1, String word2, String POS1_s, String POS2_s, String Sense_num_1_s, String Sense_num_2_s){
		double measure;
		double C = 1;
		double k = 1;
		
		FNS_tools tool = new FNS_tools();
		
		measure = C - tool.min_dist(word1, word2)- k*tool.changDir(word1, word2);

		return measure;
	}
	
	public static void main(String[] args){
		
	}
	
}

