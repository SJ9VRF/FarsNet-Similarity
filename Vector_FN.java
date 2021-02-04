
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



public class Vector_FN {

	public Vector_FN(){
		
	}
	
	public static double CompMeasure3(String word1, String word2, String POS1_s, String POS2_s, String Sense_num_1_s, String Sense_num_2_s){
		double measure = 0;
		
		FNSynsetService service=new FNSynsetService();         				//find all synset contained the word         
		Vector<FNSynset> fnSynsets1=service.FindSynsetsByWord(word1);       //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2=service.FindSynsetsByWord(word2);       //compute all synsets of word2
		
		System.out.println(fnSynsets1.size()*fnSynsets2.size()+" pairs of synsets available");
		
		int fnSyn_s1=fnSynsets1.size();
		int fnSyn_s2=fnSynsets2.size();		
		
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSyn_s1; i++)
			for (int j=0; j<fnSyn_s2; j++){
				//measure = Math.max(Vec_com(fnSynsets1.elementAt(i), fnSynsets2.elementAt(j)), measure);
				measure = -1;
			}
		
		
		return measure;
	}
	

	
	public static void main(String[] args){
	
		String word1="پا";
		String word2="دست";
		String POS1_s="n";
		String POS2_s="";
		String Sense_num_1_s="1";
		String Sense_num_2_s="1";
		//System.out.println("The Vector measure is: "+CompMeasure(word1, word2, POS1_s, POS2_s, Sense_num_1_s, Sense_num_2_s));
		
	}
	
}

