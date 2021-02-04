import java.util.Vector;

import ir.sbu.nlp.wordnet.data.model.FNSynset;

//leacockAndChodorow: 
//rel(s1,s2) = -log(dist(s1,s2)/2*max_depth)

public class LeacockAndChodorowlch_FN {

	public LeacockAndChodorowlch_FN(){
		
	}
	
	public static double CompMeasure(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2, String word1, String word2){
		
		FNS_tools tool = new FNS_tools();
		int synset_num = 20432;
		int max_depth = 2*synset_num;
		double measure=0;
	/*	
		double measure1 =  tool.max_dist(word1, word2);
		double measure2 =  tool.avg_dist(word1, word2);
		double measure3 =  tool.min_dist(word1, word2);
		double measure4 = tool.min_dep(word1);
		double  measure5 = tool.max_dep(word2);
		System.out.println("max_dist "+measure1);
		System.out.println("avg_dist "+measure2);
		System.out.println("min_dist "+measure3);
		System.out.println("min_dep"+measure4);
		System.out.println(""+measure5);
	*/	
		
	
	FN_PathFinder path_find = new FN_PathFinder();
	
	measure = -Math.log(path_find.pathfinder_min(fnSynsets_1, fnSynsets_2) / max_depth);
		
		return measure;
	}
	
	public static void main(String[] args){
		
	//	System.out.println(CompMeasure("مصحف", "کتاب", "", "", "", ""));
		
		
	}


}
