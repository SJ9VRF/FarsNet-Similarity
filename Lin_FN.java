import java.util.Dictionary;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation;
import ir.sbu.nlp.wordnet.service.FNSynsetService;
import java.util.Vector;

import ir.sbu.nlp.wordnet.data.model.FNSynset;

//rel(s1,s2) = 2*IC(lcs) / (IC(s1) + IC(s2))

public class Lin_FN {

	public Lin_FN(){
		
	}
	
	public double CompMeasure(Vector<FNSynset> synset1, Vector<FNSynset> synset2, String word1, String word2){
		FN_LCSFinder lcsFinder= new FN_LCSFinder();
		FNS_tools tool = new FNS_tools();
		double measure = 0;
		long lcs = lcsFinder.lcsfinder(synset1, synset2);
		Vector<String> lcs_str = tool.getWordsOfSynsetById(lcs);
		for (int i = 0; i < lcs_str.size(); i++) {
			measure = 2*tool.IC(lcs_str.elementAt(i)) / (tool.IC(word1)+tool.IC(word1));
		}
		measure = measure/lcs_str.size();
		return measure;
	}
	
	public static void main(String[] args){
		
	//	System.out.println(CompMeasure("مصحف", "کتاب", "", "", "", ""));
		
	}
	
}

