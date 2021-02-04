//path: 
//rel(s1,s2) = (max_dist-dist(s1,s2))/max_dist 
public class Path_FN {
	
	public double CompMeasure(String word1, String word2, String POS1_s, String POS2_s, String Sense_num_1_s, String Sense_num_2_s){
		
		FNS_tools tool = new FNS_tools();
		
		double measure;
		
		measure = (tool.max_dep(word1)+tool.max_dep(word2) - tool.min_dist(word1, word2)) / (tool.max_dep(word1)+tool.max_dep(word2));
		
		return measure;
	}
	

}
