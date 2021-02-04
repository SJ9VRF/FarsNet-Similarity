import java.io.*;
import java.util.*;

public class Report {
	
	int word_num = 36445;
	 int synsets = 20432;
	 int SM_m = 11;
	 double[] limit = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	public Report(){
		
		
	}
	
	public static void pathFinder_min(String inFile, String outFile){
		
		Vector<String> myWords = new Vector<String>();
		//read vector from file
		myWords = readFile2Vec(inFile);
		//compute pathFinder
		FN_PathFinder pathf = new FN_PathFinder();
		Set<String> myWords_pathlen = new HashSet<String>();
		for (int i = 0; i < myWords.size(); i++) {
			 for (int j = i+1; j < myWords.size(); j++) {
			//	 myWords_pathlen.add(myWords.elementAt(i)+" "+myWords.elementAt(j)+" "+pathf.pathfinder_min(myWords.elementAt(i), myWords.elementAt(j)));
				 System.out.println("Hi");
			}
		}
		
		writeFile(myWords_pathlen, outFile);
		
	}

	private static void writeFile(Set<String> myWords_pathlen, String outFile) {
				
		try {
			FileWriter writefile;
			writefile = new FileWriter(outFile);
			
			BufferedWriter b = new BufferedWriter(writefile);
			for (Iterator iterator = myWords_pathlen.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				b.write(string);
			}
			b.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param inFile
	 * @return 
	 */
	public static Vector<String> readFile2Vec(String inFile) {
		FileReader readfile;
		Vector<String> words = new Vector<String>();
		try {
			readfile = new FileReader(inFile);
			BufferedReader b = new BufferedReader(readfile);
			String line;
			try {
				while((line = b.readLine() )!=null){
					line = line.trim();
					words.addElement(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return words;
	}
	
	public void getReport(String fileName_p, String fileName_o){
		
		//Number of synsets : 20432
		int n = synsets;
		//Number of measures
		int m = SM_m; 
		//
		double[][][] per_SM = new double[n][n][m];
		double[][][] eng_SM = new double[n][n][m];
		double[][][] def = new double[n][n][m];
		
		for (int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				for(int k=0; k<m; j++){
					per_SM[i][j][k] = per_comSM(i, j, k+1);
					eng_SM[i][j][k] = eng_comSM(i, j, k+1);
					def[i][j][k] = per_SM[i][j][k]-eng_SM[i][j][k];
				}
			}
		}
		
		print_mat(per_SM, "report/perSM.txt");
		print_mat(eng_SM, "report/engSM.txt");
		print_mat(def, "report/diffrence.txt");
	}
		
		public void print_mat(double[][][] mat, String fileName){
			
			try {


				File file = new File(fileName);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				for (int i =0; i<synsets; i++)
					for (int j =0; j<synsets; j++)
						for(int k=0; k<SM_m; k++){
							String str = Integer.toString(i)+","+Integer.toString(j)+","+Integer.toString(k)+","+mat[i][j][k];
							bw.write(str+"\n");
							
						}
				bw.close();
				System.out.println("Write in to file sucessfully!!");

			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
		
		public void errorRec(double[][][] mat, double[] limit){
			boolean[][][] error = new boolean[synsets][synsets][SM_m];
			
			for (int i=0; i< synsets; i++){
				for (int j=0; j<synsets; j++){
					for(int k=0; k<SM_m; k++){
						if (Math.abs(mat[i][j][k])>limit[k]){
							error[i][j][k] = true;
						}
					}
				}
			}
			print_mat(mat, "report/error.txt");
		}
		
		public double per_comSM(int i, int j, int k){
			double sim_m = 0;
			String word1 = "";
			String word2 = "";
			
			/*
			switch(k){
			case 1:
				Lesk_FN Lesk_c = new Lesk_FN();
				sim_m = Lesk_c.CompMeasure(word1, word2, "", "", "", "");
			
			case 2:
				JWSRandom_FN JWSRandom_c = new JWSRandom_FN();
				sim_m = JWSRandom_c.CompMeasure(word1, word2, "", "", "", "");
			
			case 3:
				Resnik_FN Resnik_c = new Resnik_FN();
				sim_m = Resnik_c.CompMeasure(word1, word2, "", "", "", "", MyProbability);
			
			case 4:
				LeacockAndChodorowlch_FN LeacockAndChodorowlch_c = new LeacockAndChodorowlch_FN();
				sim_m = LeacockAndChodorowlch_c.CompMeasure(word1, word2, "", "", "", "");	
				
			case 5:
				AdaptedLesk_FN AdaptedLesk_c = new AdaptedLesk_FN();
				sim_m = AdaptedLesk_c.CompMeasure(word1, word2, "", "", "", "");
				
			case 6:
				AdaptedLeskTanimoto_FN AdaptedLeskTanimoto_c = new AdaptedLeskTanimoto_FN();
				sim_m = AdaptedLeskTanimoto_c.CompMeasure(word1, word2, "", "", "", "");
				
			case 7:
				AdaptedLeskTanimotoNoHyponyms_FN AdaptedLeskTanimotoNoHyponyms_c = new AdaptedLeskTanimotoNoHyponyms_FN();
				sim_m = AdaptedLeskTanimotoNoHyponyms_c.CompMeasure(word1, word2, "", "", "", "");
				
			case 8:
				HirstAndStOnge_FN HirstAndStOnge_c = new HirstAndStOnge_FN();
				sim_m = HirstAndStOnge_c.CompMeasure(word1, word2, "", "", "", "");
				
			case 9:
				JiangAndConrath_FN JiangAndConrath_c = new JiangAndConrath_FN();
				sim_m = JiangAndConrath_c.CompMeasure(word1, word2, "", "", "", "");
				
			case 10:
				Lin_FN Lin_c = new Lin_FN();
				sim_m = Lin_c.CompMeasure(word1, word2, "", "", "", "");
				
			case 11:
				WuAndPalmer_FN WuAndPalmer_c = new  WuAndPalmer_FN();
				sim_m = WuAndPalmer_c.CompMeasure(word1, word2, "", "", "", "");
				
			default:
				sim_m = -1;
			}
				
				*/
			return sim_m;
		}
		
		// must be corrected due english wordnet
		public double eng_comSM(int i, int j, int k){
			
		double sim_m = 0;
		String word1 = "";
		String word2 = "";
		
		/*
		switch(k){
		case 1:
			Lesk_FN Lesk_c = new Lesk_FN();
			sim_m = Lesk_c.CompMeasure(word1, word2, "", "", "", "");
		
		case 2:
			JWSRandom_FN JWSRandom_c = new JWSRandom_FN();
			sim_m = JWSRandom_c.CompMeasure(word1, word2, "", "", "", "");
		
		case 3:
			Resnik_FN Resnik_c = new Resnik_FN();
			sim_m = Resnik_c.CompMeasure(word1, word2, "", "", "", "");
		
		case 4:
			LeacockAndChodorowlch_FN LeacockAndChodorowlch_c = new LeacockAndChodorowlch_FN();
			sim_m = LeacockAndChodorowlch_c.CompMeasure(word1, word2, "", "", "", "");	
			
		case 5:
			AdaptedLesk_FN AdaptedLesk_c = new AdaptedLesk_FN();
			sim_m = AdaptedLesk_c.CompMeasure(word1, word2, "", "", "", "");
			
		case 6:
			AdaptedLeskTanimoto_FN AdaptedLeskTanimoto_c = new AdaptedLeskTanimoto_FN();
			sim_m = AdaptedLeskTanimoto_c.CompMeasure(word1, word2, "", "", "", "");
			
		case 7:
			AdaptedLeskTanimotoNoHyponyms_FN AdaptedLeskTanimotoNoHyponyms_c = new AdaptedLeskTanimotoNoHyponyms_FN();
			sim_m = AdaptedLeskTanimotoNoHyponyms_c.CompMeasure(word1, word2, "", "", "", "");
			
		case 8:
			HirstAndStOnge_FN HirstAndStOnge_c = new HirstAndStOnge_FN();
			sim_m = HirstAndStOnge_c.CompMeasure(word1, word2, "", "", "", "");
			
		case 9:
			JiangAndConrath_FN JiangAndConrath_c = new JiangAndConrath_FN();
			sim_m = JiangAndConrath_c.CompMeasure(word1, word2, "", "", "", "");
			
		case 10:
			Lin_FN Lin_c = new Lin_FN();
			sim_m = Lin_c.CompMeasure(word1, word2, "", "", "", "");
			
		case 11:
			WuAndPalmer_FN WuAndPalmer_c = new  WuAndPalmer_FN();
			sim_m = WuAndPalmer_c.CompMeasure(word1, word2, "", "", "", "");
			
		default:
			sim_m = -1;
			
		}
			*/
		return sim_m;
	}
	
		
	public static void main(String[] args){
		
	}
	
}
