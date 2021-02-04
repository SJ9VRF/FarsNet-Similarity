import ir.sbu.nlp.wordnet.data.model.FNSense; 
import ir.sbu.nlp.wordnet.data.model.FNSynset; 
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation; 
import ir.sbu.nlp.wordnet.service.FNSynsetService; 
import java.util.Vector;
public class Example {
	public static void main(String args[]){
		FNAPIUse("مصحف");
	}


	private static void FNAPIUse(String word) {         	//instantiate a FNSynsetService         
		FNSynsetService service=new FNSynsetService();         //find all synset containd the word         
		Vector<FNSynset> fnSynsets=service.FindSynsetsByWord(word);         //print every synset         
		for(int i=0;i<fnSynsets.size();i++){             
			System.out.print("Synset:{");             
			FNSynset fnSynset=fnSynsets.elementAt(i);             
			for(int j=0;j<fnSynset.getSenses().size();j++){                 
				FNSense fnSense=fnSynset.getSenses().elementAt(j);
				System.out.print(fnSense.getWord().getValue().elementAt(0)+"_"+fnSense.getId()+" ,");
		}             
		System.out.println("}");
		System.out.println("Father[s]:");
		Vector<FNSynsetsRelation> fathers=fnSynset.getSynsetsRelations();
		for(int k=0;k<fathers.size();k++)                 
			if(fathers.elementAt(k).getSynset_1().getId().equals(fnSynset.getId())	&&	fathers.elementAt(k).getRelType().equalsIgnoreCase("Hypernym")){                 System.out.print("Synset:{");                 FNSynset father=fathers.elementAt(k).getSynset_2();                 for(int j=0;j<father.getSenses().size();j++){                     
				FNSense fnSense=father.getSenses().elementAt(j);                     
			System.out.print(fnSense.getWord().getValue().elementAt(0)+"_"+fnSense.getId()+" ,");
			}            
			System.out.println("}");
			}             
		System.out.println("********************");
		}     
	} 
}

