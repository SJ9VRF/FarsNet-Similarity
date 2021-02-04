import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Dictionary;
import java.util.Hashtable;

import ir.sbu.nlp.wordnet.data.model.FNSensesRelation;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSense;
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation;
import ir.sbu.nlp.wordnet.service.FNSynsetService;

//find dist
//find lcs
//find depth


public class FNS_tools {
	
	Dictionary<String, Double> MyProbability = new Hashtable<String, Double>(); 
	public FNSynsetService service = new FNSynsetService();
	
	FNS_tools(){
		probapCom prb = new probapCom();
		String inputF = "prob_result/Prpb_Fa_Mizan.txt";
	//	String inputF = "prob_result/Prpb_persica_t.txt";
		MyProbability = prb.readFile_p(inputF);

	}
	
	public static void main (String args[]){
		
	}

	public Vector<String> lcs(String word1, String word2){
		System.out.println("... compute lcs, please wait :)");
		String lcs_s=null;
		int len = -1;
		
		Vector<FNSynset> fnSynsets1;         //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2;          //compute all synsets of word2
		fnSynsets1 =service.FindSynsetsByWord(word1);         //compute all synsets of word1      
		fnSynsets2 =service.FindSynsetsByWord(word2);          //compute all synsets of word2
		
		//father words
		Vector<Vector<FNSynset>> fnSynsets_fa1 = getGrndFa(fnSynsets1);
		Vector<Vector<FNSynset>> fnSynsets_fa2 = getGrndFa(fnSynsets2);
		
		Vector<Vector<Vector<FNSynset>>> anc1 = new Vector<Vector<Vector<FNSynset>>>();
		Vector<Vector<Vector<FNSynset>>> anc2 = new Vector<Vector<Vector<FNSynset>>>();
		
		Vector<Vector<FNSynset>> com_LCS = new Vector<Vector<FNSynset>>();
		Vector<Vector<FNSynset>> com_LCS_h = new Vector<Vector<FNSynset>>();
		
	// compute intersection between fathers
		anc1.addElement(fnSynsets_fa1);
		anc2.addElement(fnSynsets_fa2);
		
		com_LCS_h = whatFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2);
		
		if (hasFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2)){
			len = 1;
			com_LCS = com_LCS_h;
		}
		else{
			boolean hasFaB1 = hasFatherV(fnSynsets_fa1);
			boolean hasFaB2 = hasFatherV(fnSynsets_fa2);
			Vector<Vector<FNSynset>> grndFa1;
			Vector<Vector<FNSynset>> grndFa2;
			while(hasFaB1 || hasFaB2){
				if ( hasFaB1 ){
					grndFa1 = getGrndFaV(fnSynsets_fa1);
					anc1.addElement(grndFa1);
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
									com_LCS = com_LCS_h;
								}
						}
					if (len==m+n+2){
						len=-1;
					}
					}
				
				if ( hasFaB2 ){
					grndFa2 = getGrndFaV(fnSynsets_fa2);
					anc2.addElement(grndFa2);
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
									com_LCS = com_LCS_h;
								}
						}
					if (len==m+n+2){
						len=-1;
					}
					}
				if(len == -1){
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
									com_LCS = com_LCS_h;
								}
						}
					if (len==m+n+2){
						len=-1;
					}
				}
			
				
				}
				}

		if (len==-1){
			len = 0;
			com_LCS = null;
		}
		
		Vector<String> my_lcs = new Vector<String>();
		System.out.println("... finished... lcs");
		int n = com_LCS.size();
		for(int i=0; i<n; i++){
			int m = com_LCS.elementAt(i).size();
			for(int j=0; j<m; j++){
				FNSynset myfnSynset = com_LCS.elementAt(i).elementAt(j);
				int o = myfnSynset.getSenses().size();
				for(int k=0; k<o; k++){
					FNSense fnSense = myfnSynset.getSenses().elementAt(k);
					Vector<String> words = fnSense.getWord().getValue();
					int p = words.size();
					for(int l=0; l<p; l++)
						my_lcs.addElement(words.elementAt(l));
				}
			}
		}
	//	com_LCS.elementAt(i).elementAt(j).getId();
		
		return my_lcs;
		
	}

	//Does two synset have intersection
	public boolean hasFaIntersectionV(Vector<Vector<FNSynset>>fa1, Vector<Vector<FNSynset>>fa2){
		System.out.println("... compute has father intersection?, please wait");		
		boolean common = false;
		
		int n = fa1.size();
		int m = fa2.size();
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				if(hasFaIntersection(fa1.elementAt(i), fa2.elementAt(j))){
					common = true;
					i=n+1;
					j=m+1;
				}
			
		System.out.println("... FINISHED... has father intersection?? " +common);	
		return common;
	}
	
	//getInterSection of two Synset
	public Vector<Vector<FNSynset>> whatFaIntersectionV(Vector<Vector<FNSynset>>fa1, Vector<Vector<FNSynset>>fa2){
		System.out.println("... compute who father intersection?, please wait");
		Vector<Vector<FNSynset>> common = new Vector<Vector<FNSynset>>();
		
		int n = fa1.size();
		int m = fa2.size();
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++){
				// I'm not sure this equality works correctly
				Vector<FNSynset> common_aux = whatFaIntersection(fa1.elementAt(i), fa2.elementAt(j));
				if(common_aux.size() >= 1)
					common.addElement(common_aux);
			}
		System.out.println("... FINISHED... who father intersection?? " +common.size());
		return common;
	}
	
	//Does Vector of Synset, has father?
	public boolean hasFatherV(Vector<Vector<FNSynset>> Syn){
		System.out.println("... compute... has Father?");
		boolean hasFa = false;
		
		int n = Syn.size();
		for (int i=0; i<n; i++)
			if (hasFather(Syn.elementAt(i))){
				hasFa = true;
				i=n+1;
			}
		
		System.out.println("... FINISHED... has GrandFather? " +hasFa);
		return hasFa;
	}
	
	//Does two synset have intersection
	public boolean hasFaIntersection(Vector<FNSynset>fa1, Vector<FNSynset>fa2){
		System.out.println("... compute has father intersection?, please wait");		
		boolean common = false;
		
		int n = fa1.size();
		int m = fa2.size();
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				// I'm not sure this equality works correctly
				if(fa1.elementAt(i).equals(fa2.elementAt(j))){
					common = true;
					i=n+1;
					j=m+1;
				}
		System.out.println("... FINISHED... has father intersection?? " +common);	
		return common;
	}
	
	//getInterSection of two Synset
	public Vector<FNSynset> whatFaIntersection(Vector<FNSynset>fa1, Vector<FNSynset>fa2){
		System.out.println("... compute who father intersection?, please wait");
		Vector<FNSynset> common = new Vector<FNSynset>();
		
		int n = fa1.size();
		int m = fa2.size();
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				// I'm not sure this equality works correctly
				if(fa1.elementAt(i).equals(fa2.elementAt(j)))
					common.addElement(fa1.elementAt(i));
		
		System.out.println("... FINISHED... who father intersection?? " +common.size());
		return common;
	}
	
	//Does Vector of Synset, has father?
	public boolean hasFather(Vector<FNSynset> Syn){
		System.out.println("... compute... has Father?");
		boolean hasFa = false;
		
		int n = Syn.size();
		for (int i=0; i<n; i++)
			if (! Syn.elementAt(i).isNofahter()){
				hasFa = true;
				i=n+1;
			}
		
		System.out.println("... FINISHED... has GrandFather? " +hasFa);
		return hasFa;
	}
	
	//Does Vector of Synset, contain Root?
	public boolean hasRoot(Vector<FNSynset> Syn){
		System.out.println("... compute... has Father?");
		boolean hasFa = false;
		
		int n = Syn.size();
		for (int i=0; i<n; i++)
			if (Syn.elementAt(i).isNofahter()){
				hasFa = true;
				i=n+1;
			}
		
		System.out.println("... FINISHED... Root? " +hasFa);
		return hasFa;
	}
	
	// get GrandFa of Vector Synset
	public Vector<Vector<FNSynset>> getGrndFa(Vector<FNSynset> child){
		System.out.println("... compute add GrandFather, please wait");
		Vector<Vector<FNSynset>> grndFa = new Vector<Vector<FNSynset>>();
		
		int n = child.size();
		for(int i=0; i<n; i++){
			Long id =  child.elementAt(i).getId();
			grndFa.addElement(service.FindFahtersOfSynset(id));
		}
		
		System.out.println("... FINISHED... add GrandFather? " +grndFa.size());		
		return grndFa;
	}
	
	public Vector<Vector<FNSynset>> getGrndFaV(Vector<Vector<FNSynset>> child){
		System.out.println("... compute add GrandFather, please wait");
		Vector<Vector<FNSynset>> grndFa = new Vector<Vector<FNSynset>>();
		
		int n = child.size();
		for(int i=0; i<n; i++){
			int m = child.elementAt(i).size();
			for(int j=0; j<m; j++){
				Long id =  (child.elementAt(i)).elementAt(j).getId();
				grndFa.addElement(service.FindFahtersOfSynset(id));
			}
		}
		
		System.out.println("... FINISHED... add GrandFather? " +grndFa.size());		
		return grndFa;
	}
	
	public double avg_dist(String word1, String word2){
		System.out.println("... compute avg distance, please wait :)");
		double len = -1;
		int lcs_num = 0;
		
			
		Vector<FNSynset> fnSynsets1;         //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2;          //compute all synsets of word2
		fnSynsets1 =service.FindSynsetsByWord(word1);         //compute all synsets of word1      
		fnSynsets2 =service.FindSynsetsByWord(word2);          //compute all synsets of word2
		
		//father words
		Vector<Vector<FNSynset>> fnSynsets_fa1 = getGrndFa(fnSynsets1);
		Vector<Vector<FNSynset>> fnSynsets_fa2 = getGrndFa(fnSynsets2);
		
		Vector<Vector<Vector<FNSynset>>> anc1 = new Vector<Vector<Vector<FNSynset>>>();
		Vector<Vector<Vector<FNSynset>>> anc2 = new Vector<Vector<Vector<FNSynset>>>();
		
		Vector<Vector<FNSynset>> com_LCS_h = new Vector<Vector<FNSynset>>();
		
	// compute intersection between fathers
		anc1.addElement(fnSynsets_fa1);
		anc2.addElement(fnSynsets_fa2);
		
		com_LCS_h = whatFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2);
		
		if (hasFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2)){
			len = 1;
		}
		else{
			boolean hasFaB1 = hasFatherV(fnSynsets_fa1);
			boolean hasFaB2 = hasFatherV(fnSynsets_fa2);
			Vector<Vector<FNSynset>> grndFa1;
			Vector<Vector<FNSynset>> grndFa2;
			while(hasFaB1 || hasFaB2){
				if ( hasFaB1 ){
					grndFa1 = getGrndFaV(fnSynsets_fa1);
					anc1.addElement(grndFa1);
					int n = anc1.size();
					int m = anc2.size();
					for(int i=0; i<n; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1){
									len = len + i+j+2;
									lcs_num ++;
							}
						}
					}
				
				if ( hasFaB2 ){
					grndFa2 = getGrndFaV(fnSynsets_fa2);
					anc2.addElement(grndFa2);
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1){
									len = len + i+j+2;
									lcs_num ++;
							}
								
						}
					}
				if(len == -1){
					int n = anc1.size();
					int m = anc2.size();
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
								}
						}
				}
				}
				}

		if (len==-1){
			len = 0;
		}
		
		if (lcs_num!=0)
			len = len / lcs_num ;
		
		System.out.println("... FINISHED . compute avg distance :)");
		
		return len;
	}
	
	public double max_dist(String word1, String word2){
		System.out.println("... compute max distance, please wait :)");
		double len = -1;
		
		String lcs_s=null;
			
		Vector<FNSynset> fnSynsets1;         //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2;          //compute all synsets of word2
		fnSynsets1 =service.FindSynsetsByWord(word1);         //compute all synsets of word1      
		fnSynsets2 =service.FindSynsetsByWord(word2);          //compute all synsets of word2
		
		//father words
		Vector<Vector<FNSynset>> fnSynsets_fa1 = getGrndFa(fnSynsets1);
		Vector<Vector<FNSynset>> fnSynsets_fa2 = getGrndFa(fnSynsets2);
		
		Vector<Vector<Vector<FNSynset>>> anc1 = new Vector<Vector<Vector<FNSynset>>>();
		Vector<Vector<Vector<FNSynset>>> anc2 = new Vector<Vector<Vector<FNSynset>>>();
		
		Vector<Vector<FNSynset>> com_LCS_h = new Vector<Vector<FNSynset>>();
		
	// compute intersection between fathers
		anc1.addElement(fnSynsets_fa1);
		anc2.addElement(fnSynsets_fa2);
		
		com_LCS_h = whatFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2);
		
		if (hasFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2)){
			len = 1;
		}
		else{
			boolean hasFaB1 = hasFatherV(fnSynsets_fa1);
			boolean hasFaB2 = hasFatherV(fnSynsets_fa2);
			Vector<Vector<FNSynset>> grndFa1;
			Vector<Vector<FNSynset>> grndFa2;
			while(hasFaB1 || hasFaB2){
				if ( hasFaB1 ){
					grndFa1 = getGrndFaV(fnSynsets_fa1);
					anc1.addElement(grndFa1);
					int n = anc1.size();
					int m = anc2.size();
					for(int i=0; i<n; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2>len){
									len = i+j+2;
								}
						}
					}
				
				if ( hasFaB2 ){
					grndFa2 = getGrndFaV(fnSynsets_fa2);
					anc2.addElement(grndFa2);
					int n = anc1.size();
					int m = anc2.size();
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2>len){
									len = i+j+2;
								}
						}
					}
				if(len == -1){
					int n = anc1.size();
					int m = anc2.size();
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2>len){
									len = i+j+2;
								}
						}
				}
			
				
				}
				}

		if (len==-1){
			len = 0;
		}
		System.out.println("... FINISHED . compute max distance :)");
		
		return len;
	}
	
	public double min_dist(String word1, String word2){
		System.out.println("... compute min distance, please wait :)");
		double len = -1;
			
		Vector<FNSynset> fnSynsets1;         //compute all synsets of word1      
		Vector<FNSynset> fnSynsets2;          //compute all synsets of word2
		fnSynsets1 =service.FindSynsetsByWord(word1);         //compute all synsets of word1      
		fnSynsets2 =service.FindSynsetsByWord(word2);          //compute all synsets of word2
		
		//father words
		Vector<Vector<FNSynset>> fnSynsets_fa1 = getGrndFa(fnSynsets1);
		Vector<Vector<FNSynset>> fnSynsets_fa2 = getGrndFa(fnSynsets2);
		
		Vector<Vector<Vector<FNSynset>>> anc1 = new Vector<Vector<Vector<FNSynset>>>();
		Vector<Vector<Vector<FNSynset>>> anc2 = new Vector<Vector<Vector<FNSynset>>>();
		
		Vector<Vector<FNSynset>> com_LCS_h = new Vector<Vector<FNSynset>>();
		
	// compute intersection between fathers
		anc1.addElement(fnSynsets_fa1);
		anc2.addElement(fnSynsets_fa2);
		
		com_LCS_h = whatFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2);
		
		if (hasFaIntersectionV(fnSynsets_fa1, fnSynsets_fa2)){
			len = 1;
		}
		else{
			boolean hasFaB1 = hasFatherV(fnSynsets_fa1);
			boolean hasFaB2 = hasFatherV(fnSynsets_fa2);
			Vector<Vector<FNSynset>> grndFa1;
			Vector<Vector<FNSynset>> grndFa2;
			while(hasFaB1 || hasFaB2){
				if ( hasFaB1 ){
					grndFa1 = getGrndFaV(fnSynsets_fa1);
					anc1.addElement(grndFa1);
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
								}
						}
					if (len==m+n+2){
						len=-1;
					}
					}
				
				if ( hasFaB2 ){
					grndFa2 = getGrndFaV(fnSynsets_fa2);
					anc2.addElement(grndFa2);
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
								}
						}
					if (len==m+n+2){
						len=-1;
					}
					}
				if(len == -1){
					int n = anc1.size();
					int m = anc2.size();
					if(len==-1)
						len=m+n+2;
					for(int i=0; i<n-1; i++)
						for(int j=0; j<m; j++){
							com_LCS_h = whatFaIntersectionV(anc1.elementAt(i), anc2.elementAt(j));
							if (com_LCS_h.size() >= 1)
								if (i+j+2<len){
									len = i+j+2;
								}
						}
					if (len==m+n+2){
						len=-1;
					}
				}
			
				
				}
				}

		
		System.out.println("... FINISHED . compute min distance :)");
		return len;
	}
	
	public double min_dist_lcs(String word, Vector<String> lcs){
		double measure = 0.0;
		int num=0;
		int n = lcs.size();
		
		for(int i=0; i<n; i++){
			if(lcs.elementAt(i)!=null){
				num++;
				measure = measure + min_dist(word, lcs.elementAt(i));
			}
		}
		
		if(num!=0)
			measure = measure / num;
		
		return measure;
	}
	
	public double avg_dep(String word1){
		double av_dep = 0.0;
		
		return av_dep;
	}
	
	public double max_dep(String word1){
		double ma_dep = 0.0;
		

		
		return ma_dep;
	}
	
	
	public Vector<Vector<FNSynsetsRelation>> getRelation(Vector<FNSynset> fnSynsets1){
		int n = fnSynsets1.size();
		Vector<Vector<FNSynsetsRelation>> fnSynsetsRelations1 = new Vector<Vector<FNSynsetsRelation>>();
		for(int i=0; i<n; i++)
			fnSynsetsRelations1.addElement(fnSynsets1.elementAt(i).getSynsetsRelations());
		
		return fnSynsetsRelations1;
		
	}
	
	// depth of Taxomony
	
	public double depOfTax(String word1){
		System.out.println("... max depth of Taxamony, please wait :)");
		double ma_dep = 0.0;
		
		Vector<FNSynset> fnSynsets;         //compute all synsets of word1     
		
		fnSynsets = service.FindSynsetsByWord(word1);         //compute all synsets of word1 
		
		Vector<Vector<FNSynsetsRelation>> fnSynsetsRelations = getRelation(fnSynsets);
		Vector<Vector<FNSynsetsRelation>> fnSynsetsRelationsChild = new Vector<Vector<FNSynsetsRelation>>();
		
		Vector<FNSynset>fnSynsets_1 = new Vector<FNSynset>();
		Vector<FNSynset>fnSynsets_2 = new Vector<FNSynset>();
		
		Vector<FNSynset>main = new Vector<FNSynset>();
		Vector<FNSynset>other = new Vector<FNSynset>();
		
		Vector<FNSynset>main_child = new Vector<FNSynset>();
		Vector<FNSynset>other_child = new Vector<FNSynset>();
		
		boolean findleave = false;
		
		while(!findleave){
			
			int n = fnSynsetsRelations.size();
			
			for(int i=0; i<n;i++){
				int m = fnSynsetsRelations.elementAt(i).size();
				for(int j=0; j<m; j++){
					fnSynsets_1.addElement(fnSynsetsRelations.elementAt(i).elementAt(j).getSynset_1());
					fnSynsets_2.addElement(fnSynsetsRelations.elementAt(i).elementAt(j).getSynset_2());
				}
			}
			
			main = fnSynsets;
			other = fnSynsets_1;
	
			if(interSec(fnSynsets_1, fnSynsets)){
				main = fnSynsets_1;
				other = fnSynsets;
			}
		
			
			fnSynsets_1.clear();
			fnSynsets_2.clear();
			
			fnSynsetsRelationsChild = getRelation(other);
	
			n = fnSynsetsRelationsChild.size();
			for(int i=0; i<n;i++){
				int m = fnSynsetsRelationsChild.elementAt(i).size();
				for(int j=0; j<m; j++){
					fnSynsets_1.addElement(fnSynsetsRelationsChild.elementAt(i).elementAt(j).getSynset_1());
					fnSynsets_2.addElement(fnSynsetsRelationsChild.elementAt(i).elementAt(j).getSynset_2());
				}
			}
			
			main_child = fnSynsets;
			other_child = fnSynsets_1;
			
			if(interSec(fnSynsets_1, fnSynsets)){
				main_child = fnSynsets_1;
				other_child = fnSynsets;
			}

			ma_dep++;
			//mainchid equality to other equality
			int mc = main_child.size();
			int o = other.size();
			for(int i=0; i<mc; i++)
				for(int j=0; j<o; j++)
					if(main_child.elementAt(i).equals(other.elementAt(j))){
						findleave = true;
						j = o + 1 ;
						i = mc + 1;
					}
			
			fnSynsetsRelations = fnSynsetsRelationsChild;
			fnSynsets = main_child;
			
		}
		
		System.out.println("... FINISHED . compute leaf of Taxamony :)"+fnSynsets);
		
		ma_dep = min_depFs(fnSynsets);
			
		System.out.println("... FINISHED . compute max depth of Taxamony :)"+ma_dep);
		
		return ma_dep;
	}
	
	public boolean interSec(Vector<FNSynset> fnSynsets1, Vector<FNSynset> fnSynsets2){
		boolean IS = false;
		
		int n = fnSynsets1.size();
		
		for(int i=0; i<n; i++){
			int m = fnSynsets2.size();
			for(int j=0; j<m; j++){		
				if(fnSynsets1.elementAt(i).equals(fnSynsets2.elementAt(j))){
					return true;
				}
			}
		}
		
		return IS;
	}
	
	
	public double min_dep(String word1){
		double mi_dep = 0.0;
		Vector<FNSynset> fnSynsets1;         //compute all synsets of word1     
		fnSynsets1 = service.FindSynsetsByWord(word1);         //compute all synsets of word1    
		Vector<Vector<FNSynset>> fnSynsetsFA; 
		while(!hasRoot(fnSynsets1)){
			fnSynsetsFA = getGrndFa(fnSynsets1);
			fnSynsets1 = transformation(fnSynsetsFA);
			mi_dep++;
		}

		return mi_dep;
	}
	
	public double min_dep_lcs(Vector<String> lcs){
		double mi_dep = 0.0;

		int n = lcs.size();
		for(int i=0; i<n; i++){
			if (mi_dep == 0)
				mi_dep = min_dep(lcs.elementAt(i));
			mi_dep = Math.min(min_dep(lcs.elementAt(i)), mi_dep);
		}
		return mi_dep;
	}
	
	public double min_depF(FNSynset fnSynsets){
		double mi_dep = 0.0;

		Vector<FNSynset> fnSynsets1 = new Vector<FNSynset>();         //compute all synsets of word1     
		fnSynsets1.addElement(fnSynsets);
		
		Vector<Vector<FNSynset>> fnSynsetsFA; 
		
		while(!hasRoot(fnSynsets1)){
			fnSynsetsFA = getGrndFa(fnSynsets1);
			fnSynsets1 = transformation(fnSynsetsFA);
			mi_dep++;
		}

		return mi_dep;
	}
	
	public double min_depFs(Vector<FNSynset> fnSynsets){
		double mi_dep = 0.0;

		
		Vector<Vector<FNSynset>> fnSynsetsFA; 
		
		while(!hasRoot(fnSynsets)){
			fnSynsetsFA = getGrndFa(fnSynsets);
			fnSynsets = transformation(fnSynsetsFA);
			mi_dep++;
		}

		return mi_dep;
	}
	
	public Vector<FNSynset> transformation(Vector<Vector<FNSynset>> fnSynsets){
		Vector<FNSynset> fnSynsets_t = new Vector<FNSynset>();
		int n = fnSynsets.size();
		
		for (int i=0; i<n; i++){
			int m = fnSynsets.elementAt(i).size();
			for(int j=0; j<m; j++){
				fnSynsets_t.addElement((fnSynsets.elementAt(i).elementAt(j)));
			}
		}
		
		return fnSynsets_t;
	}
	
	public Vector<String> transformationS(Vector<Vector<String>> str){
		Vector<String> str_t = new Vector<String>();
		int n = str.size();
		
		for (int i=0; i<n; i++){
			int m = str.elementAt(i).size();
			for(int j=0; j<m; j++){
				str_t.addElement((str.elementAt(i).elementAt(j)));
			}
		}
		
		return str_t;
	}
	
	public double p(String word1){
		double prob = 0.0;
		word1 = word1.trim();
		try{
		prob = MyProbability.get(word1);
		}
		catch(NullPointerException e){
			prob = -1;
		}
		return prob;
	}
	
	public double IC(String word1){
		double ic = 0.0;
		
		double prob = p(word1);
		
		if (prob == -1){
			ic = 0;
			return ic;
		}
		
		if (prob != 0)
			ic = -Math.log(prob);
		else
			ic = 0;
		
		return ic;
	}
	
	public double IC_lcs(Vector<String> lcs){
		double ic = 0.0;
		int num = 0;
		int n = lcs.size();
		for(int i=0; i<n ; i++)
			if ( lcs.elementAt(i)!=null ) {
				num++;
				ic = ic + IC(lcs.elementAt(i));
			}
		
		if(num!=0)
			ic = ic/num;
		
		return ic;
	}
	
	public Vector<String> lcs_LCS(String word, Vector<String> lcs){
		int n = lcs.size();
		Vector<Vector<String>> lcs_lcs = new Vector<Vector<String>>();
		for(int i=0; i<n; i++)
			lcs_lcs.addElement(lcs(word, lcs.elementAt(i)));
		
		return transformationS(lcs_lcs);
	}
	
	public int changDir(String word1, String word2){
		int dirChng = 0;
		
		return dirChng;
	}
		
	public int comDepth(String word){
		int com_depth = 0;
		
		
		Vector<FNSynset> fnSynsets;         //compute all synsets of word1    
		fnSynsets =service.FindSynsetsByWord(word); 
		int n = fnSynsets.size();
		
	
		return com_depth;
	}
	
	public int maxDepth(String word){
		int max_depth = 0;
		
		Vector<FNSynset> fnSynsets;         //compute all synsets of word1    
		Vector<FNSynset> fa = new Vector<FNSynset>(); 
		Vector<FNSensesRelation> fnSensesR;
		Vector<FNSynsetsRelation> fnSynsetR;
		fnSynsets =service.FindSynsetsByWord(word); 
		int n = fnSynsets.size();
		for (int i=0; i<n; i++)
			fnSynsetR = fnSynsets.elementAt(i).getSynsetsRelations();
		
		return max_depth;
	}
	
	public int minDepth(String word1){
		int min_depth = 0;
		return min_depth;
	}
	
	public Vector<String> str2vec_str(String str){
		Vector<String> vec = new Vector<String>();		
		str = rmStopWords(str);
		String[] vec_str = str.split(" ");  
		
		int n = vec_str.length;
		for(int i=0; i<n; i++){
			if(str.contains(vec_str[i])){
				vec.addElement(vec_str[i]);
				str = str.replaceAll(vec_str[i], "");
			}
		}
		
		return vec;
	}
	
	
	public double freq(String word, String str){
		double frq = 0;
		
		String[] sentence = str.split(" ");
		int n = sentence.length;
		
		for(int i=0; i<n; i++){
			if(sentence[i].equals(word))
				frq += 1;
		}
		
		if (n!=0)
			frq = frq/n;
		
		return frq;
	}
	
	// get Vector of String and return this Vector of String without stop words
	public Vector<String> strMain(Vector<String> str){
		Vector<String> main = new Vector<String>();
		int n = str.size();
		for(int i=0; i<n; i++){
			String aux = rmStopWords(str.elementAt(i));
			String[] splt = aux.split(" ");
			int m = splt.length;
			for(int j=0; j<m; j++){
				if (!main.contains(splt[j]))
					main.addElement(splt[j].trim());
						
				}
		}
			
		return main;
	}
	
	public Vector<Double> str2vec_dou(String str, Vector<String> main){
		Vector<Double> vec = new Vector<Double>();
		String[] vec_str = str.split(" ");  
		
		int n = main.size();
		for(int i=0; i<n; i++){
			vec.addElement(freq(main.elementAt(i), str));
		}
		
		
		return vec;
	}
	
	public double cosine(Vector<Double> vec1, Vector<Double> vec2){
		double measure = 0.0;
		int n = vec1.size();
		double N = 0;
		double M = 0;
	
		for(int i=0; i<n; i++){
			measure = measure + vec1.elementAt(i)*vec2.elementAt(i);
		}
		
		for(int i=0; i<n; i++){
			N = N+Math.pow(vec1.elementAt(i), 2);
			M = M+Math.pow(vec2.elementAt(i), 2);
		}		
			
		N = Math.pow(N, 0.5);
		M = Math.pow(M, 0.5);
		
		if (N!=0 && M!=0)
			measure = measure/(N*M);
		
		return measure;
	}
	
	public static int Overlap(String Str1, String Str2){
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
		
		for(int i=0; i<n-1; i++)
			for(int j=0; j<m-1; j++)
				if (A[i].equals(B[j]) && A[i+1].equals(B[j+1]))
					ovr += 4;
		
		for(int i=0; i<n-2; i++)
			for(int j=0; j<m-2; j++)
				if (A[i].equals(B[j]) && A[i+1].equals(B[j+1]) && A[i+2].equals(B[j+2]))
					ovr += 9;
		
		return ovr;
	}
	
	public static String rmStopWords(String str){
		Vector <String> StopWord = readStopWords();
		int n = StopWord.size();
		for(int i=0; i<n; i++)
			str.replaceAll(str, "");
		
		return str;
	}
	
	
	public static Vector<String> readStopWords(){
		Vector <String> StopWord=new Vector<String>();
		
		try {
			File fileDir = new File("stopwords_persian.txt");

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
	
	public double cosinS(Vector<String> all){
		double cos=0;
		// strMain remove stop words in all:string
		Vector<String> main = strMain(all);
		Vector<Vector<Double>> vec_all = new Vector<Vector<Double>>();
		int n = all.size();
		for(int i=0; i<n; i++){
			//str2vec_dou : compute probability of word in string
			vec_all.addElement(str2vec_dou(all.elementAt(i), main));
		}
		for(int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				cos += cosine(vec_all.elementAt(i), vec_all.elementAt(j));
			}
		}
		
		
		cos = cos/(n*(n+1)/2);
		
		return cos;
	}

	public String vecSpcStr(Vector<String> senW) {
		String str = new String();
		int n = senW.size();
		for(int i=0; i<n; i++){
			str += " "+senW.elementAt(i);
		}
		return str;
	}

	public Vector<FNSynset> getReSyn(Vector<Vector<FNSynsetsRelation>> rel, int relSide) {
		Vector<FNSynset>fnSynsets = new Vector<FNSynset>();
		int n = rel.size();
		
		if(relSide == 1)
		for(int i=0; i<n; i++){
			int m = rel.elementAt(i).size();
			for(int j=0; j<m; j++)
				fnSynsets.addElement(rel.elementAt(i).elementAt(j).getSynset_1());
		}
		
		if(relSide == 2)
		for(int i=0; i<n; i++){
			int m = rel.elementAt(i).size();
			for(int j=0; j<m; j++)
				fnSynsets.addElement(rel.elementAt(i).elementAt(j).getSynset_2());
		}
		
		return fnSynsets;
	}

	public Vector<String> getWordsOfSynsetById(long lcs) {
		Vector<String> words = new Vector<String>();
		FNSynset fnSynsets = service.findSynsetById(lcs); 
		Vector<FNSense> sense = fnSynsets.getSenses();
		for (int i = 0; i < sense.size(); i++) {
			words.addElement(sense.elementAt(i).getWord().getValue().elementAt(0));
		}
		
		return words;
	}
	
}
