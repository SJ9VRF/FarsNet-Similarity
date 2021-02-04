import ir.sbu.nlp.wordnet.data.model.FNSense; 
import ir.sbu.nlp.wordnet.data.model.FNSensesRelation;
import ir.sbu.nlp.wordnet.data.model.FNSynset; 
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation; 
import ir.sbu.nlp.wordnet.service.FNSynsetService; 

import java.util.HashSet;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.io.*;

//resnik: 
//rel(s1,s2) = -log(p(lcs)) = IC(lcs)
public class Resnik_FN {

	public Resnik_FN(){
		
	}
	
	public static double CompMeasure(Vector<FNSynset> synset1, Vector<FNSynset> synset2, String word1, String word2){
		
		FNS_tools tool = new FNS_tools();
		FN_LCSFinder lcsFinder= new FN_LCSFinder();
		long lcs = lcsFinder.lcsfinder(synset1, synset2);
		double measure = 0;
		Vector<String> lcs_str = tool.getWordsOfSynsetById(lcs);
		for (int i = 0; i < lcs_str.size(); i++) {
			measure = 2*tool.IC(lcs_str.elementAt(i));
		}
		measure = measure/lcs_str.size();
		return measure;
	}
	
	public static void main(String[] args){
		
	//	System.out.println(CompMeasure("مصحف", "کتاب", "", "", "", ""));
		
	}
	
}

