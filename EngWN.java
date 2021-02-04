import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import edu.sussex.nlp.jws.*;

public class EngWN {

	JWS ws = new JWS("./lib", "3.0");
	
	public static void main(String args[]){
	}
	public double WNS(String word1, String word2, String partOfSpeech, int index){
		
		double measure = -1;
		
		index = index+1;

		
		switch(index){
		case 1:{
			JWSRandom jws = new JWSRandom(ws.getDictionary(), true, 16.0);//random number for baseline
			measure = jws.max(word1, word2, partOfSpeech);
			System.out.println("JWSRandom"+": "+measure);
			return measure;
		}
		
		case 2:{
			Resnik res = ws.getResnik();
			measure = res.max(word1, word2, partOfSpeech);
			System.out.println("Resnik"+": "+measure);	
			return measure;
		}
			
		case 3:{
			LeacockAndChodorow LeacockAndChodorowlch2 = ws.getLeacockAndChodorow();
			measure = LeacockAndChodorowlch2.max(word1, word2, partOfSpeech);
			System.out.println("LeacockAndChodorow"+": "+ measure);
			return measure;
		}
			
		case 4:{
			AdaptedLesk adLesk = ws.getAdaptedLesk();
			measure = adLesk.max(word1, word2, partOfSpeech);
			System.out.println("AdaptedLesk"+": "+ measure);
			return measure;
		}
			
		case 5:{
			AdaptedLeskTanimoto alt = ws.getAdaptedLeskTanimoto();
			measure = alt.max(word1, word2, partOfSpeech);
			System.out.println("AdaptedLeskTanimoto"+": "+ measure);
			return measure;
		}
			
		case 6:{
			AdaptedLeskTanimotoNoHyponyms altnh = ws.getAdaptedLeskTanimotoNoHyponyms();
			measure = altnh.max(word1, word2, partOfSpeech);
			System.out.println("AdaptedLeskTanimotoNoHyponyms"+": "+ measure);
			return measure;
		}
			
		case 7:{
			HirstAndStOnge hso = ws.getHirstAndStOnge();
			measure = hso.max(word1, word2, partOfSpeech);
			System.out.println("HirstAndStOnge"+": "+ measure);
			return measure;
		}
			
		case 8:{
			JiangAndConrath jcn = ws.getJiangAndConrath();
			measure = jcn.max(word1, word2, partOfSpeech);
			System.out.println("JiangAndConrath"+": "+ measure);
			return measure;
		}
		
		case 9:{
			Lin lin = ws.getLin();
			measure = lin.max(word1, word2, partOfSpeech);
			System.out.println("Lin"+": "+ measure);
			return measure;
		}
			
		case 10:{
			WuAndPalmer wup = ws.getWuAndPalmer();
			measure = wup.max(word1, word2, partOfSpeech);
			System.out.println("WuAndPalmer"+": "+ measure);
			return measure;
		}

		}
		

	//	TreeMap<String, Double> scores1 = res.res(word1, word2, partOfSpeech);
	//	for(Entry<String, Double> e: scores1.entrySet())
	//	    System.out.println(e.getKey() + "\t" + e.getValue());
	//	System.out.println("\nhighest score\t=\t" + res.max(word1, word2, partOfSpeech) + "\n\n\n");
		return measure;

	}
	
	public double avgWNS(Vector<String> word1, Vector<String> word2, String partOfSpeech, int index){
		
		double measure=0;

		int n = word1.size();
		int m = word2.size();

		
		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++){
				System.out.println("......"+"Similarity"+"("+word1.elementAt(i)+","+word2.elementAt(j)+") :");
				measure = measure + WNS(word1.elementAt(i), word2.elementAt(j), partOfSpeech, index);
			}

		//max could be used too...
		if (m!=0 && n!=0)
			measure = measure/(m*n);
		else
			measure = 0;
		
		return measure;

	}
	
	public Double[] allMeasure(Vector<String> str1_e, Vector<String> str2_e,String pos){
		//number of measures
		int n = 12;
		Double[] measures = new Double[n];
		
		for(int i=0; i<n; i++){
			//measures[i] = Math.round(avgWNS(str1_e, str2_e, pos, i)*100.0)/100.0;
			measures[i] = avgWNS(str1_e, str2_e, pos, i+1);
		}
		
		return measures;
	}
	
}
