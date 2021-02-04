import java.util.Vector;

import ir.sbu.nlp.wordnet.data.model.FNSynset;

public class FN_Vector {

	public double CompMeasure(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2){
		double measure=0;
		System.out.println(fnSynsets_1.size()*fnSynsets_2.size()+" pairs of synsets available");
		ALesk_FN lesk = new ALesk_FN();	
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSynsets_1.size(); i++)
			for (int j=0; j<fnSynsets_2.size(); j++){
				measure = Math.max(measure, lesk.Vector(fnSynsets_1.elementAt(i), fnSynsets_2.elementAt(j)));
			}

		return measure;
	}
	
}
