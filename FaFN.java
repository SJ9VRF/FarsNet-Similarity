
import java.util.Vector;
import java.util.Dictionary;
import java.util.Hashtable;

import edu.mit.jwi.item.ISynset;
import ir.sbu.nlp.wordnet.service.FNSynsetService;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSense;

public class FaFN {

	Dictionary<String, Double> MyProbability = new Hashtable<String, Double>(); 
	
	public static void main(String args[]){
		
	}
	public double FNS(Vector<FNSynset> synset1, Vector<FNSynset> synset2, String word1, String word2, int index){
		double measure = -99;
		switch(index){
			case 0:{
				Lesk_FN Lesk_c = new Lesk_FN();
				measure = Lesk_c.CompMeasure_2(synset1, synset2);
				System.out.println("Lesk"+": "+ measure);
				return measure;
			}	
			case 1:{
		  		JWSRandom_FN JWSRandom_c = new JWSRandom_FN();
		  		measure = JWSRandom_FN.CompMeasure();
				System.out.println("JWSRandom"+": "+measure);
				return measure;
			}
			case 2:{
				Resnik_FN Resnik_c = new Resnik_FN();
				measure = Resnik_FN.CompMeasure(synset1, synset2,  word1,  word2);
				System.out.println("Resnik"+": "+measure);	
				return measure;
			}
			case 3:{
				LeacockAndChodorowlch_FN LeacockAndChodorowlch_c = new LeacockAndChodorowlch_FN();
				measure = LeacockAndChodorowlch_c.CompMeasure(synset1, synset2,  word1,  word2);
				System.out.println("LeacockAndChodorow"+": "+ measure);
				return measure;
			}
			case 4:{
				AdaptedLesk_FN AdaptedLesk_c = new AdaptedLesk_FN();
				measure = AdaptedLesk_c.CompMeasure_2(synset1, synset2);
				System.out.println("AdaptedLesk"+": "+ measure);
				return measure;
			}
			case 5:{
				AdaptedLeskTanimoto_FN AdaptedLeskTanimoto_c = new AdaptedLeskTanimoto_FN();
				measure = AdaptedLeskTanimoto_c.CompMeasure(synset1, synset2);
				System.out.println("AdaptedLeskTanimoto"+": "+ measure);
				return measure;
			}
			case 6:{
				AdaptedLeskTanimotoNoHyponyms_FN AdaptedLeskTanimotoNoHyponyms_c = new AdaptedLeskTanimotoNoHyponyms_FN();
				measure = AdaptedLeskTanimotoNoHyponyms_c.CompMeasure(synset1, synset2);
				System.out.println("AdaptedLeskTanimotoNoHyponyms"+": "+ measure);
				return measure;
			}
			case 7:{
				HirstAndStOnge_FN HirstAndStOnge_c = new HirstAndStOnge_FN();
		//2		measure = HirstAndStOnge_c.CompMeasure(synset1, synset2);
				System.out.println("HirstAndStOnge"+": "+ measure);
				return measure;
			}
			case 8:{
				JiangAndConrath_FN JiangAndConrath_c = new JiangAndConrath_FN();
				measure = JiangAndConrath_c.CompMeasure(synset1, synset2, word1, word2);
				System.out.println("JiangAndConrath"+": "+ measure);
				return measure;
			}
			case 9:{
				Lin_FN Lin_c = new Lin_FN();
				measure = Lin_c.CompMeasure(synset1, synset2,  word1,  word2);
				System.out.println("Lin"+": "+ measure);
				return measure;
			}
			case 10:{
				WuAndPalmer_FN WuAndPalmer_c = new  WuAndPalmer_FN();
				measure = WuAndPalmer_c.CompMeasure(synset1, synset2,  word1,  word2);
				System.out.println("WuAndPalmer"+": "+ measure);
				return measure;
			}
			case 11:{
				FN_Vector Vector_c = new  FN_Vector();
				measure = Vector_c.CompMeasure(synset1, synset2);
				System.out.println("Vector"+": "+ measure);
				return measure;
			}
		}
		return measure;
	}
	
	public double avgFNS(Vector<String> word1, Vector<String> word2, String POS1_s, String POS2_s, String Sense_num_1_s, String Sense_num_2_s, int index){
		double measure=0;
		int n = word1.size();
		int m = word2.size();
		n=1;
		m=2;
		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++)
				measure = 0;
				//measure = measure + FNS(word1.elementAt(i), word2.elementAt(j), POS1_s, POS2_s, Sense_num_1_s, Sense_num_2_s, index);
		//max could be used too...
		if (m!=0 && n!=0)
			measure = measure/(m*n);
		else
			measure = 0;
		return measure;
	}
	
	public Double[] allMeasure(Vector<FNSynset> synset1, Vector<FNSynset> synset2, String word_1, String word_2){
		//number of measures
		int n = 12;
		Double[] measures = new Double[n];
		for(int i=0; i<n; i++){
			measures[i] = FNS(synset1, synset2,  word_1,  word_2, i);
		}
		return measures;
	}

}
