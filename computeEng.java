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

public class computeEng {
	
	public  computeEng()
	{
		
	}
	
	public Vector<String> getEngWN(String word){
		System.out.println(word);
		Vector<String> ws_eng = new Vector<String>();
		FNSynsetService service=new FNSynsetService();
		Vector<FNSynset> fnSynset = service.FindSynsetsByWord(word);
		int n = fnSynset.size();
		System.out.println(n);
		System.out.println(fnSynset.toString());
		for (int i=0; i<n; i++)
	//	if (fnSynset.elementAt(i).hasEnglishMappWord(fnSynset))
		{
			//System.out.println(word+" Hip Hip "+ fnSynset.elementAt(i).getMappedISynsets().toString());
			ws_eng.add(fnSynset.elementAt(i).getMappedISynsets().toString());
		}
		return ws_eng;
	}
	
	public Vector<String> extEngW(Vector<String> WN){
		int n = WN.size();
		Vector<String> ws_eng_ex = new Vector<String>();
		 
	//	[SYNSET{SID-08649345-N : Words[W-08649345-N-1-side]}]

		for (int i=0; i<n; i++){
			String word=WN.elementAt(i);
			
			if (word!=null){
			
				if (word.contains("-") && word.contains("}")){
					int A = word.lastIndexOf('-');
					int B = word.lastIndexOf('}')-1;
					if (A<B)
						ws_eng_ex.add(word.substring(A+1, B));
				}
			}
			
			
		}
		
		return ws_eng_ex;
	}
}
