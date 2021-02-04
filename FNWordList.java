import ir.sbu.nlp.wordnet.data.model.FNSense;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.service.FNSynsetService;
import java.util.*;
import java.io.*;

/**
Write words in FarseNet to text file
*/
public class FNWordList {

	public static void listWords(){
		
		long SynsetID = 20432;
		Set<String> allwords = new HashSet<String>();
		//28629
		int num_word = 0;
		FNSynsetService service = new FNSynsetService();
		FNSynset fnSynsets;
		for (long id = 0; id < SynsetID; id++) {
			fnSynsets = service.findSynsetById(id);
			if (fnSynsets!=null){
				for (int i = 0; i < fnSynsets.getSenses().size(); i++) {
					FNSense fnSense = fnSynsets.getSenses().elementAt(i);
					allwords.add(fnSense.getWord().getValue().elementAt(0).trim());
					num_word++;
					System.out.println(num_word+" "+fnSense.getWord().getValue().elementAt(0).trim());
				}
			}
		}	
		System.out.println("End of Reading Word from FarsNet");
		writeSet2File(allwords, "FNWords.txt");
		
	}
	
	public static void writeSet2File(Set<String> set, String filename){
		
		try{
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String string : set) {
				bw.write(string+"\n");
			}	
			bw.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		System.out.println("End of Writing Words to FarsNet");
		// 21613
		System.out.println("Total Number of words: "+set.size());
	}
	public static void main(String[] args){
		listWords();
	}
}
