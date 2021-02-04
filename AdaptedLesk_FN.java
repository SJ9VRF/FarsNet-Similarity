import java.util.Vector;

import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation;
import ir.sbu.nlp.wordnet.service.FNSynsetService;

public class AdaptedLesk_FN {

	public AdaptedLesk_FN(){
		
	}
	
	public static double CompMeasure(String word1, String word2, String POS1_s, String POS2_s, String Sense_num_1_s, String Sense_num_2_s){
		double measure = 20;

		FNSynsetService service=new FNSynsetService();         //find all synset contained the word         
		Vector<FNSynset> fnSynsets1=service.FindSynsetsByWord(word1);         //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2=service.FindSynsetsByWord(word2);          //compute all synsets of word2

		FNS_tools tool = new FNS_tools();
		
		Vector<Vector<FNSynsetsRelation>> rel1 = tool.getRelation(fnSynsets1);
		Vector<Vector<FNSynsetsRelation>> rel2 = tool.getRelation(fnSynsets2);
		Vector<FNSynset>fnSynsets_11 = tool.getReSyn(rel1, 1);
		Vector<FNSynset>fnSynsets_12 = tool.getReSyn(rel1, 2);
		Vector<FNSynset>fnSynsets_21 = tool.getReSyn(rel2, 1);
		Vector<FNSynset>fnSynsets_22 = tool.getReSyn(rel2, 2);
		
		int fnSyn_s1=fnSynsets1.size();
		int fnSyn_s2=fnSynsets2.size();		
		
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSyn_s1; i++)
			for (int j=0; j<fnSyn_s2; j++){
				measure = 0.12;
				//measure = Math.max(Lesk_com(fnSynsets1.elementAt(i), fnSynsets2.elementAt(j)), measure);
			}

		
		return measure;
	}
	
	public double CompMeasure_2(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2){
		double measure=0;
		System.out.println(fnSynsets_1.size()*fnSynsets_2.size()+" pairs of synsets available");
		ALesk_FN lesk = new ALesk_FN();	
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSynsets_1.size(); i++)
			for (int j=0; j<fnSynsets_2.size(); j++){
				measure = Math.max(measure, lesk.AdaptedLesk(fnSynsets_1.elementAt(i), fnSynsets_2.elementAt(j)));
			}

		return measure;
	}
	
	public static double GroupLesk(Vector<Vector<FNSynset>> a){
		double d = 0;
		
		
		return d;
	}
	
	public static void main(String[] args){
		
	}
	
}

