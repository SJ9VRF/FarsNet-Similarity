import java.util.Vector;

import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation;
import ir.sbu.nlp.wordnet.service.FNSynsetService;

public class FN_LCSFinder {

	public long lcsfinder(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2){
		long lcs = -1;
		FNSynsetService service = new FNSynsetService();
	//	Vector<FNSynset> fnSynsets_1 = service.FindSynsetsByWord(w_1);
	//	Vector<FNSynset> fnSynsets_2 = service.FindSynsetsByWord(w_2);
		
		Vector<Vector<FNSynset>> Fathers_1 = new Vector<Vector<FNSynset>>();
		Vector<Vector<FNSynset>> Fathers_2 = new Vector<Vector<FNSynset>>();
		Fathers_1.addElement(fnSynsets_1);
		Fathers_2.addElement(fnSynsets_2);
		boolean hasHypFa_1 = true;
		boolean hasHypFa_2 = true;
		
		int synset_num = 20432;
		int counter = 0;
		while((lcs = ComputecommonLevel(Fathers_1, Fathers_2)) == -1 && (hasHypFa_1 || hasHypFa_2) && counter<synset_num){
			counter ++;
			// get fathers of w1
			Vector<FNSynset> hyp_fa = getFather_Hyper(fnSynsets_1);
			if (hyp_fa.size()>0 && hasHypFa_1)
				Fathers_1.addElement(hyp_fa);
			else
				hasHypFa_1 = false;
			//get fathers of w2
			hyp_fa = getFather_Hyper(fnSynsets_1);
			if (hyp_fa.size()>0 && hasHypFa_2)
				Fathers_2.addElement(getFather_Hyper(hyp_fa));
			else
				hasHypFa_1 = false;
		}

		if(lcs == -1){
			System.out.println("There is no path between ");
		}
		
		return lcs;
	}

	/**
	 * @param Fathers_1
	 * @param Fathers_2
	 */
	public static long ComputecommonLevel(Vector<Vector<FNSynset>> Fathers_1, Vector<Vector<FNSynset>> Fathers_2) {
		long com = -1;
		if ( (com = faHasCommon_uplevel(Fathers_1, Fathers_2)) != -1)
			return com;
		if ( (com = faHasCommon(Fathers_1, Fathers_2)) != -1) 
			return com;
		if ( (com = faHasCommon(Fathers_2, Fathers_1)) != -1) 
			return com;
		return com;
	}
	
	/**
	 * @param Fathers_1
	 * @param Fathers_2
	 */
	public static long faHasCommon_uplevel(Vector<Vector<FNSynset>> Fathers_static, Vector<Vector<FNSynset>> Fathers_updated) {
		long path_avail = -1;
		Vector<FNSynset> Fa_up_last = Fathers_updated.lastElement();
		Vector<FNSynset> Fa_static_last = Fathers_static.lastElement();
		for (int i = 0; i < Fa_up_last.size(); i++) {
			for (int j = 0; j < Fa_static_last.size(); j++) {
				if(Fa_up_last.elementAt(i).getId().equals(Fa_static_last.elementAt(j).getId())){
					return Fa_up_last.elementAt(i).getId();
				}
			}		
		}
		
		return path_avail;
	}

	/**
	 * @param Fathers_1
	 * @param Fathers_2
	 */
	public static long faHasCommon(Vector<Vector<FNSynset>> Fathers_static, Vector<Vector<FNSynset>> Fathers_updated) {
		long path_avail = -1;
		Vector<FNSynset> Fa_2_last = Fathers_updated.lastElement();
		for (int i = 0; i < Fathers_static.size()-1; i++) {
			for (int j = 0; j < Fathers_static.elementAt(i).size(); j++) {
				for (int k = 0; k < Fa_2_last.size(); k++) {
					if(Fa_2_last.elementAt(k).getId().equals(Fathers_static.elementAt(i).elementAt(j).getId())){
						return Fa_2_last.elementAt(k).getId();
					}
				}
			}
		}
		return path_avail;
	}

	/**
	 * @param fnSynsets
	 */
	public static Vector<FNSynset> getFather_Hyper(Vector<FNSynset> fnSynsets) {
		Vector<FNSynset> father_Hype = new Vector<FNSynset>();
		for (int i = 0; i < fnSynsets.size(); i++) {
			FNSynset fnSynset_1 = fnSynsets.elementAt(i);
			Vector<FNSynsetsRelation> fathers = fnSynset_1.getSynsetsRelations();
			for (int j = 0; j < fathers.size(); j++) {
				if (fathers.elementAt(j).getSynset_1().getId().equals(fnSynset_1.getId()) && fathers.elementAt(j).getRelType().equalsIgnoreCase("Hypernym"))
					father_Hype.add(fathers.elementAt(j).getSynset_2());
			}
		}
		return father_Hype;
	}
	
	/**
	 * @param fnSynsets
	 */
	public static void print(long mySynsetid) {
		System.out.println(mySynsetid);
		if (mySynsetid != -1){
			FNSynsetService service = new FNSynsetService();
			FNSynset myfnSynsets = service.findSynsetById(mySynsetid);
			for (int i = 0; i < myfnSynsets.getSenses().size(); i++) {
				System.out.println(myfnSynsets.getSenses().elementAt(i).getWord().getValue().elementAt(0));
			}
		}
		else
			System.out.println("There is no LCS");
	}

	public static void main(String[] args) {
		String w1 = "مصحف";
		String w2 = "کتاب";
		System.out.println("LCS between "+w2+","+w1+"is:");
	//	long lcs = lcsfinder(w1, w2);
	//	print(lcs);
	//	FN_depthFinder depth = new FN_depthFinder();
	//	System.out.println("Minimum Depth of lcs is: "+depth.depthFinder_min(lcs));
		
	}

}
