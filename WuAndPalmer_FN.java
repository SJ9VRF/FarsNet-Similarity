import java.util.Vector;

import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.service.*;

public class WuAndPalmer_FN {
//	wuAndPalmer:
//		rel(s1,s2) = (2*depth(lcs)) / (dist(s1,lcs)+dist(s2,lcs)+2*depth(lcs))
	public WuAndPalmer_FN(){
		
	}
	
	public static double CompMeasure(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2, String word1, String word2){
		
		FN_LCSFinder lcsFinder= new FN_LCSFinder();
		FNS_tools tool = new FNS_tools();
		FN_depthFinder depth = new FN_depthFinder();
		double measure=0;

		long lcs = lcsFinder.lcsfinder(fnSynsets_1, fnSynsets_2);
		
		double depth_lcs = depth.depthFinder_min(lcs);
		
		FN_PathFinder pathfind = new FN_PathFinder();
		Vector<FNSynset> lcs_synset = new Vector<FNSynset>();
		FNSynsetService service = new FNSynsetService();
		lcs_synset.addElement(service.findSynsetById(lcs));
		measure = 2*depth_lcs/(pathfind.pathfinder_min(fnSynsets_1, lcs_synset)+pathfind.pathfinder_min(fnSynsets_2, lcs_synset)+2*depth_lcs);

		return measure;
	}
	
	public static void main(String[] args){
		
	//	System.out.println(CompMeasure("مصحف", "کتاب", "", "", "", ""));
		
	}
	
	
}
