import ir.sbu.nlp.wordnet.data.model.FNSense; 
import ir.sbu.nlp.wordnet.data.model.FNSensesRelation;
import ir.sbu.nlp.wordnet.data.model.FNSynset; 
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation; 
import ir.sbu.nlp.wordnet.service.FNSynsetService;

import java.util.*;
import java.io.*;

public class ALesk_FN {

	public  double Similarity_word(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2) {         	
		double value=0;
		//Find all Synsets:
	//	FNSynsetService service=new FNSynsetService();                 
	//	Vector<FNSynset> fnSynsets_1=service.FindSynsetsByWord(word_1);         //compute all synsets of word_1      
	//	Vector<FNSynset> fnSynsets_2=service.FindSynsetsByWord(word_2);         //compute all synsets of word_2
		
		System.out.println(fnSynsets_1.size()*fnSynsets_2.size()+" pairs of synsets available");
		
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSynsets_1.size(); i++)
			for (int j=0; j<fnSynsets_2.size(); j++){
				value = Math.max(value, AdaptedLeskTanimotoNoHyponyms(fnSynsets_1.elementAt(i), fnSynsets_2.elementAt(j)));
			}

		return value;
	}
	
	
	public  double overlap(String word_1, String word_2) {         	
		double value=0;

		return value;
	}
	
	public  double AdaptedLesk(FNSynset synset_1, FNSynset synset_2) {           
		double value=0;
		String allGloss_1;
		String allGloss_2;
		//get all Senses gloss
		allGloss_1 = getGloss(synset_1);
		allGloss_2 = getGloss(synset_2);
		//get all Relation to Synset_2 Gloss
		allGloss_1 = allGloss_1+" "+getallDirGloss(synset_1);
		allGloss_2 = allGloss_2+" "+getallDirGloss(synset_2);
		//remove StopWords of Gloss
		allGloss_1 = rmStopWords(allGloss_1);
		allGloss_2 = rmStopWords(allGloss_2);
		//compute Jaccard-Tanimoto Coefficient 
		double[][] gloss = makeVector(allGloss_1, allGloss_2);
		value = cosine(gloss);
		
		return value;
	}
	
	
	public double CompMeasure(Vector<FNSynset> fnSynsets_1, Vector<FNSynset> fnSynsets_2){
		double measure=0;
		System.out.println(fnSynsets_1.size()*fnSynsets_2.size()+" pairs of synsets available");
		ALesk_FN lesk = new ALesk_FN();	
		//Do the following for each pair, return the maximum
		for (int i=0; i<fnSynsets_1.size(); i++)
			for (int j=0; j<fnSynsets_2.size(); j++){
				measure = Math.max(measure, lesk.Lesk(fnSynsets_1.elementAt(i), fnSynsets_2.elementAt(j)));
			}

		return measure;
	}
	
	public  double Lesk(FNSynset synset_1, FNSynset synset_2) {           
		double value=0;
		String allGloss_1;
		String allGloss_2;
		//get all Senses gloss
		allGloss_1 = getGloss(synset_1);
		allGloss_2 = getGloss(synset_2);
		//remove StopWords of Gloss
		allGloss_1 = rmStopWords(allGloss_1);
		allGloss_2 = rmStopWords(allGloss_2);

		FNS_tools tool = new FNS_tools();
		value = tool.Overlap(allGloss_1, allGloss_2);
		return value;
	}
	
	public  double AdaptedLeskTanimoto(FNSynset synset_1, FNSynset synset_2) {           
		double value=0;
		String allGloss_1;
		String allGloss_2;
		//get all Senses gloss
		allGloss_1 = getGloss(synset_1);
		allGloss_2 = getGloss(synset_2);
		allGloss_1 += " "+getallDirGloss_sense(synset_1)+getallDirGloss_hypoHyper(synset_1);
		allGloss_2 += " "+getallDirGloss_sense(synset_2)+getallDirGloss_hypoHyper(synset_2);
		//remove StopWords of Gloss
		allGloss_1 = rmStopWords(allGloss_1);
		allGloss_2 = rmStopWords(allGloss_2);
		//compute Jaccard-Tanimoto Coefficient 
		double[][] gloss = makeVector(allGloss_1, allGloss_2);
		value = Jaccard_Tanimoto(gloss);
		return value;
	}
	
	public  double Vector(FNSynset synset_1, FNSynset synset_2) {           
		double value=0;
		String allGloss_1;
		String allGloss_2;
		//get all Senses gloss
		allGloss_1 = getGloss(synset_1);
		allGloss_2 = getGloss(synset_2);
		allGloss_1 += " "+getallDirGloss_sense(synset_1)+getallDirGloss_hypoHyper(synset_1);
		allGloss_2 += " "+getallDirGloss_sense(synset_2)+getallDirGloss_hypoHyper(synset_2);
		//remove StopWords of Gloss
		allGloss_1 = rmStopWords(allGloss_1);
		allGloss_2 = rmStopWords(allGloss_2);
		//compute Jaccard-Tanimoto Coefficient 
		double[][] gloss = makeVector(allGloss_1, allGloss_2);
		value = cosine(gloss);
		return value;
	}
	
	public  double Jaccard_Tanimoto(double[][] vector) {
		// TODO Auto-generated method stub
		double N_a = 0;
		double N_b = 0;
		double N_c = 0;
		for (int i = 0; i < vector[1].length; i++) {
			if(vector[0][i]!=0)
				N_a++;
			if(vector[1][i]!=0)
				N_b++;
			if(vector[1][i]!=0 && vector[1][i]!=0)
				N_c++;
		}
		return ((N_a+N_b)/N_c);
	}

	public  double cosine(double[][] vector) {
		// TODO Auto-generated method stub
		double value = 0;
		double dot  = 0;
		for (int i = 0; i < vector[1].length; i++) {
			dot +=  vector[0][i]* vector[1][i];
		}
		double norm_a = norm(vector[0]);
		double norm_b = norm(vector[1]);
		
		value = dot / (norm_a*norm_b);
		
		return value;
	}

	/**
	 * @param vector
	 */
	public  double norm(double[] vector) {
		double norm_vec = 0;
		for (int i = 0; i < vector.length; i++) {
			norm_vec += Math.pow(vector[i], 2);
		}
		norm_vec = Math.pow(norm_vec, 0.5);
		return norm_vec;
	}
	
	public  double[][] makeVector(String gloss_1, String gloss_2) {
		String[] gloss_1_vec = gloss_1.split(" ");
		String[] gloss_2_vec = gloss_2.split(" ");
		Set<String> vocabulary = new HashSet<String>();
		for (int i = 0; i < gloss_1_vec.length; i++) {
			gloss_1_vec[i] = gloss_1_vec[i].trim();
			vocabulary.add(gloss_1_vec[i]);
		}
		for (int i = 0; i < gloss_2_vec.length; i++) {
			gloss_2_vec[i] = gloss_2_vec[i].trim();
			vocabulary.add(gloss_2_vec[i]);
		}
		
		Map<String, Integer> Dictionaty_1 = new HashMap<String, Integer>();
		Map<String, Integer> Dictionaty_2 = new HashMap<String, Integer>();
		for (Iterator iterator = vocabulary.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			Dictionaty_1.put(str, 0);
			Dictionaty_2.put(str, 0);
		}
		
		for (int i = 0; i < gloss_1_vec.length; i++) {
			Dictionaty_1.replace(gloss_1_vec[i], Dictionaty_1.get(gloss_1_vec[i])+1);
		}
		for (int i = 0; i < gloss_2_vec.length; i++) {
			Dictionaty_2.replace(gloss_2_vec[i], Dictionaty_2.get(gloss_2_vec[i])+1);
		}
		
		double[][] gloss_vector = new double[2][vocabulary.size()];
		int i = 0;
		for (Iterator iterator = vocabulary.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			gloss_vector[0][i]=Dictionaty_1.get(str);
			gloss_vector[1][i]=Dictionaty_2.get(str);
			i++;
		}
		
		for (int j = 0; j < vocabulary.size(); j++) {
			gloss_vector[0][j]=gloss_vector[0][j]/vocabulary.size();
			gloss_vector[1][j]=gloss_vector[1][j]/vocabulary.size();
		}
		
		return gloss_vector;
	}

	public  String getallDirGloss_sense(FNSynset synset) {
		String gloss = "";
		Vector<FNSense> sense = synset.getSenses();
		for (int i = 0; i < sense.size(); i++) {
			gloss += sense.elementAt(i).getSynset().getGloss(); 
		}
		return gloss;
	}

	public  double AdaptedLeskTanimotoNoHyponyms(FNSynset synset_1, FNSynset synset_2) {           
		double value=0;
		String allGloss_1;
		String allGloss_2;
		//get all Senses gloss
		allGloss_1 = getGloss(synset_1);
		allGloss_2 = getGloss(synset_2);
		//get all Relation to Synset_2 Gloss
		allGloss_1 += " "+getallDirGloss_sense(synset_1);
		allGloss_2 += " "+getallDirGloss_sense(synset_2);
		//remove StopWords of Gloss
		allGloss_1 = rmStopWords(allGloss_1);
		allGloss_2 = rmStopWords(allGloss_2);
		//compute Jaccard-Tanimoto Coefficient 
		double[][] gloss = makeVector(allGloss_1, allGloss_2);
		value = Jaccard_Tanimoto(gloss);
		return value;
	}
	
	public  String getallDirGloss(FNSynset synset) {
		String alldirGloss="";
		Vector<FNSynsetsRelation> synRelation= synset.getSynsetsRelations();
		for (int i = 0; i < synRelation.size(); i++) {
			if(synRelation.elementAt(i).getSynset_1().getId().equals(synset.getId())){
				alldirGloss += " " + synRelation.elementAt(i).getSynset_2().getGloss();
			}
			else
				alldirGloss += " " + synRelation.elementAt(i).getSynset_1().getGloss();
		}
		return alldirGloss;
	}
	

	public  String getallDirGloss_hypoHyper(FNSynset synset) {
		String alldirGloss="";
		Vector<FNSense> senses = synset.getSenses();
		for (int i = 0; i < senses.size(); i++) {
			Vector<FNSensesRelation> senseRelation= senses.elementAt(i).getSensesRelations();
			for (int j = 0; j < senseRelation.size(); j++) {
				if(senseRelation.elementAt(j).getRelationType().equalsIgnoreCase("Hypernym")){
					if(senseRelation.elementAt(j).getFirstSense().getId().equals(senses.elementAt(i).getId())){
						alldirGloss += " " +getHyponimSenseGloss(senseRelation.elementAt(j).getSecondSense());
					}
					else{
						alldirGloss += " " +getHyponimSenseGloss(senseRelation.elementAt(j).getFirstSense());
					}
				}				
			}
		}
		return alldirGloss;
	}

	/**
	 * @param alldirGloss
	 * @param senseRelation
	 * @param sense_Hyper
	 * @param senseRelation_Hyper
	 * @return
	 */
	public  String getHyponimSenseGloss(FNSense sense){
		String gloss=""; 
		Vector<FNSensesRelation> senseRelation_Hyper = sense.getSensesRelations();
		for (int i = 0; i < senseRelation_Hyper.size(); i++) {
			if(senseRelation_Hyper.elementAt(i).getRelationType().equalsIgnoreCase("Hyponym") &&
				senseRelation_Hyper.elementAt(i).getFirstSense().getId().equals(sense.getId())){
					gloss += " " +senseRelation_Hyper.elementAt(i).getSecondSense().getSynset().getGloss();
				}
				else{
					gloss += " "+ senseRelation_Hyper.elementAt(i).getFirstSense().getSynset().getGloss();
				}
		}
		System.out.println(gloss);
		return gloss;
	}
	
	public  String getGloss(FNSynset Synset) {
		return Synset.getGloss();
	}

	public  Set<Set> GetAllRelations(FNSynset sys1, FNSynset sys2){
		Set<Set> allRelations = new HashSet<Set>();
		Vector<FNSense> sen = sys1.getSenses();
		//Vector<FNSensesRelation> allRelation = sen.getSensesRelations();
		Set<FNSynset> A = new HashSet<FNSynset>();
		A.add(sys1);
		A.add(sys2);
		Set<Set> B = new HashSet<Set>();
		B.add(A);
		allRelations.add(B);
		return allRelations;
	}

	
	public  double Lesk_com(Set<FNSynset> relation){
		Iterator itr = relation.iterator();
		FNSynset sense1 = (FNSynset) itr.next();
		FNSynset sense2 = (FNSynset) itr.next();
	    		
		double value = 0;
		
		String s1 = sense1.toString();
		String s2 = sense2.toString();
	
		
		//Get Glass
		String g1 = sense1.getGloss();
		String g2 = sense2.getGloss();
		
		//Get Examples
		String e1 = sense1.getExample();
		String e2 = sense2.getExample();
		
		//Compute Overlap
		value += Overlap(e1, e2);
		value += Overlap(g1, g2);
		value += Overlap(e1, g2);
		value += Overlap(g1, e2);
		value += Overlap(s1, g2);
		value += Overlap(s1, e2);
		value += Overlap(s2, g2);
		value += Overlap(s2, e2);
		
		//Compute all  Relations
		
		
		//Compute Overall Lesk parts
		
		return value;
	}
	
	public  int Overlap(String Str1, String Str2){
		int ovr = 0;
		String[] A = Str1.split(" ");
		String[] B = Str2.split(" ");
		int n = A.length;
		int m = B.length;

		for(int i=0; i<n; i++)
			A[i] = A[i].trim();
		for(int i=0; i<m; i++)
			B[i] = B[i].trim();
			
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				if (A[i].equals(B[j]))
					ovr += 1;
		return ovr;
	}
	
	public  String rmStopWords(String str){
		Vector <String> StopWord = readStopWords();
		int n = StopWord.size();
		for(int i=0; i<n; i++)
			str.replaceAll(str, "");
		return str;
	}
	
	public  Vector<String> readStopWords(){
		Vector <String> StopWord=new Vector<String>();
		
		try {
			File fileDir = new File("persian.txt");
			BufferedReader in = new BufferedReader(
			new InputStreamReader( new FileInputStream(fileDir), "UTF8"));
			String str;
			while ((str = in.readLine()) != null) {
				str = str.trim();
			    StopWord.add(str);
			}
	        in.close();
		    }
		    catch (UnsupportedEncodingException e)
		    {
				System.out.println(e.getMessage());
		    }
		    catch (IOException e)
		    {
				System.out.println(e.getMessage());
		    }
		    catch (Exception e)
		    {
				System.out.println(e.getMessage());
		    }
		
		return StopWord;
	}
	
	public static void main(String args[]){
		String word1="کتاب";
	//	String word2="مصحف";
		String word2="دست";
		String POS1="n";
		String POS2="";
		int sence1=1;
		int sence2=1;
	//	System.out.println("The Lesk measure is: "+Similarity_word(word1, word2));
	}
}

  