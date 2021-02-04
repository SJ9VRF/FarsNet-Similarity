import ir.sbu.nlp.wordnet.data.model.FNSense;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation;
import ir.sbu.nlp.wordnet.data.model.FNSensesRelation;
import ir.sbu.nlp.wordnet.service.FNSynsetService;
import java.util.*;
import java.io.*;

public class FN_depthFinder {

	public static long depthFinder_min(long mySynsetId) {
		long depth = -1;
		if (mySynsetId != -1){
			FNSynsetService service = new FNSynsetService();
			Vector<FNSynset> father_Hyper = new Vector<FNSynset>();
			father_Hyper.addElement(service.findSynsetById(mySynsetId));
			
			int synset_num = 20432;
			while (((father_Hyper = getFather_Hyper(father_Hyper))!= null) && father_Hyper.size()>0 && depth<synset_num) {
				depth ++;
			}
			
			if (depth != -1)
				depth++;
		}
		
		return depth;
	}
	
	/**
	 * @param fnSynsets
	 */
	public static Vector<FNSynset> getFather_Hyper(Vector<FNSynset> fnSynsets) {
		Vector<FNSynset> father_Hype = new Vector<FNSynset>();
		//System.out.println("Hi.. "+father_Hype.size());
		for (int i = 0; i < fnSynsets.size(); i++) {
			FNSynset fnSynset_1 = fnSynsets.elementAt(i);
			Vector<FNSynsetsRelation> fathers = fnSynset_1.getSynsetsRelations();
		//	System.out.println("Bye.. "+fathers.size());
			for (int j = 0; j < fathers.size(); j++) {
				if (fathers.elementAt(j).getSynset_1().getId().equals(fnSynset_1.getId()) && fathers.elementAt(j).getRelType().equalsIgnoreCase("Hypernym"))
					father_Hype.add(fathers.elementAt(j).getSynset_2());
			}
		}
		System.out.println(father_Hype.size());
		return father_Hype;
	}
	
	public static void main(String[] args) {
		String w1 = "دست";
		String w2 = "پا";
		System.out.println("Minimum Path between "+w2+","+w1+"is:"+depthFinder_min(32));

	}
	
}
