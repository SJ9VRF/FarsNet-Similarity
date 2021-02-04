
import java.io.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;


public class getProbability {

//	Dictionary<String, Integer> probability = new Hashtable<String, Integer>(); 
//	Dictionary<String, Double> MyProbability = new Hashtable<String, Double>(); 
//	Vector<String> index = new Vector<String>(); 
//	Vector<Double> prob = new Vector<Double>();
	
	public static void main(){
		
		Vector<String> file_prob = new Vector<String>();
		
		
	}

	public static Dictionary<String, Double> editProb(Dictionary<String, Integer> probability, Vector<String> index, Vector<Double> prob, String inPutF, String outPutF){
		Dictionary<String, Double> TheProbability = new Hashtable<String, Double>();
		
		return TheProbability;
	}
	 
	 public static void mergeProb(Vector<String> file_prob){
		 
			int n = file_prob.size();
			for (int i=0; i<n; i++){
				mergePFile(file_prob.elementAt(i));
			}
				
	 }
	 
	 public static Dictionary<String, Double> readPFile(String file_prob){
		 
			Dictionary<String, Double> TheProbability = new Hashtable<String, Double>();
			Dictionary<String, Integer> probability = new Hashtable<String, Integer>(); 
			Vector<String> index = new Vector<String>(); 
			Vector<Double> prob = new Vector<Double>();
			
			try{
				File fileDir = new File(file_prob);
				BufferedReader in = new BufferedReader(
						new InputStreamReader( new FileInputStream(fileDir), "UTF8"));
				
				String line;
				
				while ((line = in.readLine()) != null) {
					String[] Line = line.split(" ");
					TheProbability.put(Line[0], Double.parseDouble(Line[1]));
					
				}
				
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
			//open file
			
			//read file
			
			//write in dictionary
			
			//close file
			
			return TheProbability;		 
		 
	 }
	 
	 public static void mergePFile(String file_prob){
		 
		 
		 
	 }
	
}
