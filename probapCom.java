import java.io.*;
import java.util.*;
public class probapCom {
	
	public static void main(String[] args){
		
		String input_F = "corpus_fa/Mizan_Fa.txt";
		String output_F = "prob_result/Prpb_Fa_Mizan.txt"; 
		
	//	String input_F = "corpus_fa/persica.txt";
	//	String output_F = "prob_result/Prpb_persica_t.txt"; 
		
		com_pro(input_F, output_F);
	}
	
	public static void com_pro(String input_F, String output_F){
		
		Dictionary<String, Integer> probability = new Hashtable<String, Integer>(); 
		Vector<String> index = new Vector<String>(); 
		Vector<Double> prob = new Vector<Double>();
		String fileName = output_F;
		
		int totalNum=0;
		
		try {
			File fileDir = new File(input_F);

			BufferedReader in = new BufferedReader(
			new InputStreamReader( new FileInputStream(fileDir), "UTF8"));

			String str;
			
			while ((str = in.readLine()) != null) {
				str = str.trim();
				str = str.replace('ي', 'ی');

				char[] symbol = {',',':','.','،','!','؟','«','»',')','(','ـ','-',';','؛','“','/','*','÷','+','~'};
				char[] numbers = {'1','2','3','4','5','6','7','8','9','0','۱','۲','۳','۴','۵','۶','۷','۸','۹','۰'};
				
				int m_num = numbers.length;
				for (int i=0; i<m_num; i++)
					str = str.replace(numbers[i], ' ');
				m_num = symbol.length;
				for (int i=0; i<m_num; i++)
					str = str.replace(symbol[i], ' ');
				
				str = str.trim();				
			    String[] token = str.split(" ");
			    int n = token.length;
			    for(int i=0; i<n; i++){
			    	totalNum++;
			    	token[i]=token[i].trim();
			    	if(probability.get(token[i])!= null){
			    		prob.set(probability.get(token[i]), 1+prob.get(probability.get(token[i])));
			    	}
			    	else{
			    		int m = probability.size();
			    		probability.put(token[i], m);
			    		index.addElement(token[i]);
			    		prob.addElement(1.0);
			    	}
			}
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
		
		//normalize to probability
		int n = prob.size();
		for(int i=0; i<n; i++)
			prob.set(i, prob.get(i)/totalNum);


		
		writeFile(index, prob, fileName);
		

		
	}
	
	public static void writeFile(Vector<String> index, Vector<Double> prob, String output_F){
		int n = prob.size();
		
		try {


			File file = new File(output_F);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i=0; i<n; i++){
				bw.write(index.elementAt(i)+" "+prob.elementAt(i)+"\n");
			}
			
			
			bw.close();
			System.out.println("Write in to file sucessfully!!");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public  Dictionary<String, Double> readFile_p(String input_F){
		
		Dictionary<String, Double> probability = new Hashtable<String, Double>();
		
		
		try {
			File fileDir = new File(input_F);

			BufferedReader in = new BufferedReader(
			new InputStreamReader( new FileInputStream(fileDir), "UTF8"));

			String str;

			while ((str = in.readLine()) != null) {
				str = str.trim();
			    String[] token = str.split(" ");
			    if (token.length>=2){
			    probability.put(token[0].trim(), Double.parseDouble(token[1].trim()));
			 //   System.out.println(token[0].trim());
			  //  System.out.println(Double.parseDouble(token[1].trim()));
			    }
			    
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
		
		
		return probability;
	}

}
