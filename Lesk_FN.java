
//for medium-strong Relations
//lesk: 
//rel(s1,s2) = sum(word_overlap(s1,s2)), extended by related synsets

import ir.sbu.nlp.wordnet.data.model.FNSense; 
import ir.sbu.nlp.wordnet.data.model.FNSensesRelation;
import ir.sbu.nlp.wordnet.data.model.FNSynset; 
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation; 
import ir.sbu.nlp.wordnet.service.FNSynsetService; 

import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.io.*;



public class Lesk_FN {
	public static void main(String args[]){
		String word1="پا";
		String word2="دست";
		String POS1_s="n";
		String POS2_s="";
		String Sense_num_1_s="1";
		String Sense_num_2_s="1";
		System.out.println("The Lesk measure is: "+CompMeasure(word1, word2, POS1_s, POS2_s, Sense_num_1_s, Sense_num_2_s));
	}


	public double CompMeasure_2(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2){
		double measure=0;
		System.out.println(fnSynsets_1.size()*fnSynsets_2.size()+" pairs of synsets available");
		ALesk_FN lesk = new ALesk_FN();	
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSynsets_1.size(); i++)
			for (int j=0; j<fnSynsets_2.size(); j++){
				measure = Math.max(measure, lesk.Lesk(fnSynsets_1.elementAt(i), fnSynsets_2.elementAt(j)));
			}

		return measure;
	}
	
	public static double CompMeasure(String word1, String word2, String POS1_s, String POS2_s, String Sense_num_1_s, String Sense_num_2_s) {         	//instantiate a FNSynsetService  
		double measure=0;
		//Find all Synset

		FNSynsetService service=new FNSynsetService();         //find all synset contained the word         
		Vector<FNSynset> fnSynsets1=service.FindSynsetsByWord(word1);         //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2=service.FindSynsetsByWord(word2);          //compute all synsets of word2
		
		System.out.println(fnSynsets1.size()*fnSynsets2.size()+" pairs of synsets available");
		
		int fnSyn_s1=fnSynsets1.size();
		int fnSyn_s2=fnSynsets2.size();		
		
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSyn_s1; i++)
			for (int j=0; j<fnSyn_s2; j++){
				measure = Math.max(Lesk_com(fnSynsets1.elementAt(i), fnSynsets2.elementAt(j)), measure);
			}

		return measure;
	}
	
	
	public static double Lesk_com(FNSynset sense1, FNSynset sense2){
	    		
		double value = 0;
		FNS_tools tool = new FNS_tools();
		
		Vector<String> senW1 = sense1.getMappingWords();
		Vector<String> senW2 = sense2.getMappingWords();
		
		String s1 = tool.vecSpcStr(senW1);
		String s2 = tool.vecSpcStr(senW2);
		
		//Get Glass
		String g1 = sense1.getGloss();
		String g2 = sense2.getGloss();
		
		//Get Examples
		String e1 = sense1.getExample();
		String e2 = sense2.getExample();
		
		//Compute Overlap
		value += tool.Overlap(e1, e2);
		value += tool.Overlap(g1, g2);
		value += tool.Overlap(e1, g2);
		value += tool.Overlap(g1, e2);
		value += tool.Overlap(s1, g2);
		value += tool.Overlap(s1, e2);
		value += tool.Overlap(s2, g2);
		value += tool.Overlap(s2, e2);
		
		//Compute all  Relations
		
		
		//Compute Overall Lesk parts
		
		return value;
	}
	

}

