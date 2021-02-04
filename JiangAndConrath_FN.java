import java.util.Vector;
//rel(s1,s2) = max_dist - (IC(c1) + IC(c2) − 2*IC(lcs))

import ir.sbu.nlp.wordnet.data.model.FNSynset;

public class JiangAndConrath_FN {

	public JiangAndConrath_FN(){
		
	}
	
	public static double CompMeasure(Vector<FNSynset> synset1, Vector<FNSynset> synset2, String word1, String word2){
		
		FNS_tools tool = new FNS_tools();
		int synset_num = 20432;
		int max_dist = 2*synset_num;
		double measure=0;
		
		//measure = tool.depOfTax(word1);
		
		double measure_lcs=0;
		
		FN_LCSFinder lcsFinder= new FN_LCSFinder();
		long lcs = lcsFinder.lcsfinder(synset1, synset2);
		Vector<String> lcs_str = tool.getWordsOfSynsetById(lcs);
		for (int i = 0; i < lcs_str.size(); i++) {
			measure = tool.IC(lcs_str.elementAt(i));
		}
		measure = 2*measure/lcs_str.size();
		measure = max_dist - (tool.IC(word1)+tool.IC(word2)) + measure;
		
		return measure;
	}
	
	public static void main(String[] args){
		
	//	System.out.println(CompMeasure("مصحف", "کتاب", "", "", "", ""));
		
	}
	
}

