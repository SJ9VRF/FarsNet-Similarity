import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import edu.mit.jwi.item.ISynset;
import ir.sbu.nlp.wordnet.service.FNSynsetService;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSense;

// Graphical Interface for Similarity Measures
public class SGUI {	
	private JFrame mainFrame;
	private JPanel controlPanel_user;
	private JPanel controlPanel_opt;
	private JPanel controlPanel_enter;
	private JPanel controlPanel_result;
	private JPanel enterWord;
	private JPanel enterPOS;
	private JPanel enterSnc;
	private JPanel enterBut;
	
	private JLabel word1;
	private JLabel word2;
	private JTextField word1_F;
	private JTextField word2_F;
	private JLabel POS1_l;
	private JLabel POS2_l;
	private JComboBox POS1;
	private JComboBox POS2;
	private JLabel Sense_num_1;
	private JLabel Sense_num_2;	
	private JTextField Sense_num_1_F;
	private JTextField Sense_num_2_F;
	
	private JLabel col1;
	private JLabel col2;
	private JLabel col3;
	
	//Persian one
	private JLabel[] measures_label = new JLabel[12];
	private JLabel Lesk_l;
	private JLabel JWSRandom_l;
	private JLabel Resnik_l;
	private JLabel LeacockAndChodorowlch_l;
	private JLabel AdaptedLesk_l;
	private JLabel AdaptedLeskTanimoto_l;
	private JLabel AdaptedLeskTanimotoNoHyponyms_l;
	private JLabel HirstAndStOnge_l;
	private JLabel JiangAndConrath_l;
	private JLabel Lin_l;
	private JLabel WuAndPalmer_l;
	private JLabel Vector_l;
	
	private JLabel[] measures_label_f = new JLabel[12];
	private JLabel Lesk_f;
	private JLabel JWSRandom_f;
	private JLabel Resnik_f;
	private JLabel LeacockAndChodorowlch_f;
	private JLabel AdaptedLesk_f;
	private JLabel AdaptedLeskTanimoto_f;
	private JLabel AdaptedLeskTanimotoNoHyponyms_f;
	private JLabel HirstAndStOnge_f;
	private JLabel JiangAndConrath_f;
	private JLabel Lin_f;
	private JLabel WuAndPalmer_f;
	private JLabel Vector_f;
	//English one
	private JLabel[] measures_label_e = new JLabel[12];
	private JLabel Lesk_f_e;
	private JLabel JWSRandom_f_e;
	private JLabel Resnik_f_e;
	private JLabel LeacockAndChodorowlch_f_e;
	private JLabel AdaptedLesk_f_e;
	private JLabel AdaptedLeskTanimoto_f_e;
	private JLabel AdaptedLeskTanimotoNoHyponyms_f_e;
	private JLabel HirstAndStOnge_f_e;
	private JLabel JiangAndConrath_f_e;
	private JLabel Lin_f_e;
	private JLabel WuAndPalmer_f_e;
	private JLabel Vector_f_e;
	//value of Persian measures
	private Double[] measures_n = new Double[12];
	private Double Lesk_n;
	private Double JWSRandom_n;
	private Double Resnik_n;
	private Double LeacockAndChodorowlch_n;
	private Double AdaptedLesk_n;
	private Double AdaptedLeskTanimoto_n;
	private Double AdaptedLeskTanimotoNoHyponyms_n;
	private Double HirstAndStOnge_n;
	private Double JiangAndConrath_n;
	private Double Lin_n;
	private Double WuAndPalmer_n;
	private Double Vector_n;
	//value of English measures
	private Double[] measures_n_e = new Double[12];
	private Double Lesk_n_e;
	private Double JWSRandom_n_e;
	private Double Resnik_n_e;
	private Double LeacockAndChodorowlch_n_e;
	private Double AdaptedLesk_n_e;
	private Double AdaptedLeskTanimoto_n_e;
	private Double AdaptedLeskTanimotoNoHyponyms_n_e;
	private Double HirstAndStOnge_n_e;
	private Double JiangAndConrath_n_e;
	private Double Lin_n_e;
	private Double WuAndPalmer_n_e;
	private Double Vector_n_e;
	
	private Button Compute;
	private Button Clear;	

    public Object columnNames[] = { "معیار مشابهت", "نتایج معیارهای مشابهت", "نتایج معیار مشابهت-معادل انگلیسی" };
    
	public SGUI (){
		
		for (int i = 0; i < measures_label_f.length; i++) {
			measures_label_f[i] = new JLabel();
		}
		
		Lesk_f = new JLabel();
		JWSRandom_f= new JLabel();
		Resnik_f= new JLabel();
		LeacockAndChodorowlch_f= new JLabel();
		AdaptedLesk_f= new JLabel();
		AdaptedLeskTanimoto_f= new JLabel();
		AdaptedLeskTanimotoNoHyponyms_f= new JLabel();
		HirstAndStOnge_f= new JLabel();
		JiangAndConrath_f= new JLabel();
		Lin_f= new JLabel();
		WuAndPalmer_f= new JLabel();
		Vector_f= new JLabel();
		
		for (int i = 0; i < measures_label_e.length; i++) {
			measures_label_e[i] = new JLabel();
		}
		Lesk_f_e= new JLabel();
		JWSRandom_f_e= new JLabel();
		Resnik_f_e= new JLabel();
		LeacockAndChodorowlch_f_e= new JLabel();
		AdaptedLesk_f_e= new JLabel();
		AdaptedLeskTanimoto_f_e= new JLabel();
		AdaptedLeskTanimotoNoHyponyms_f_e= new JLabel();
		HirstAndStOnge_f_e= new JLabel();
		JiangAndConrath_f_e= new JLabel();
		Lin_f_e= new JLabel();
		WuAndPalmer_f_e = new JLabel();
		Vector_f_e = new JLabel();

		mainFrame = new JFrame();
		enterWord = new JPanel();
		enterPOS = new JPanel();
		enterSnc = new JPanel();
		enterBut = new JPanel();
		controlPanel_user = new JPanel();
		controlPanel_opt = new JPanel();
		controlPanel_result = new JPanel();
		controlPanel_enter = new JPanel();
		
		mainFrame.setLayout(new FlowLayout());
		controlPanel_user.setLayout (new GridLayout(2, 7));
		controlPanel_opt.setLayout (new GridLayout(2, 8));
		enterWord.setLayout (new GridLayout(2, 2));
		controlPanel_result.setLayout (new GridLayout(14, 2));
		enterPOS.setLayout (new GridLayout(2, 2));
		enterSnc.setLayout (new GridLayout(2, 2));
		enterBut.setLayout (new GridLayout(1, 2));
		
		word1 = new JLabel ("کلمه اول");
		word2 = new JLabel ("کلمه دوم");
		
		
		word1_F = new JTextField(10);
		word2_F = new JTextField(10);
		word1_F.setEditable(true);
		word2_F.setEditable(true);
		
		word1_F.setFont(new Font("X Koodak", Font.PLAIN, 16));
		word1.setFont(new Font("X Koodak", Font.PLAIN, 16));
		word2_F.setFont(new Font("X Koodak", Font.PLAIN, 16));
		word2.setFont(new Font("X Koodak", Font.PLAIN, 16));	
		
		word1_F.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		word1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		word2_F.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		word2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);	
		
		Color c = new Color(0, 102, 0);
		
		word1_F.setForeground(c);
		word2_F.setForeground(c);
		
		enterWord.add(word1_F);
		enterWord.add(word1);
		enterWord.add(word2_F);
		enterWord.add(word2);

		Vector<String> PerPOS = new Vector<String>();
		PerPOS.addElement("نامعلوم");
		PerPOS.addElement("اسم");
		PerPOS.addElement("فعل");
		PerPOS.addElement("صفت");
		PerPOS.addElement("قید");

		POS1_l = new JLabel("نقش کلمه اول");
		POS2_l = new JLabel("نقش کلمه دوم");
		POS1 = new JComboBox<String>(PerPOS);
		POS2 = new JComboBox<String>(PerPOS);
		POS1.setEditable(false);
		POS2.setEditable(false);
		
		Sense_num_1 = new JLabel("شماره سنس کلمه اول");
		Sense_num_2 = new JLabel("شماره سنس کلمه دوم");
		Sense_num_1_F = new JTextField("نامعلوم",5);
		Sense_num_2_F = new JTextField("نامعلوم",5);

		POS1.setFont(new Font("X Koodak", Font.PLAIN, 16));
		POS1_l.setFont(new Font("X Koodak", Font.PLAIN, 16));
		POS2.setFont(new Font("X Koodak", Font.PLAIN, 16));
		POS2_l.setFont(new Font("X Koodak", Font.PLAIN, 16));
		
		Sense_num_1_F.setFont(new Font("X Koodak", Font.PLAIN, 16));
		Sense_num_1.setFont(new Font("X Koodak", Font.PLAIN, 16));
		Sense_num_2_F.setFont(new Font("X Koodak", Font.PLAIN, 16));
		Sense_num_2.setFont(new Font("X Koodak", Font.PLAIN, 16));
		
		POS1.setFont(new Font("X Koodak", Font.PLAIN, 16));
		POS2.setFont(new Font("X Koodak", Font.PLAIN, 16));
		
		Sense_num_1_F.setFont(new Font("X Koodak", Font.PLAIN, 16));
		Sense_num_2_F.setFont(new Font("X Koodak", Font.PLAIN, 16));

		POS1.setForeground(c);
		POS2.setForeground(c);
		
		Sense_num_1_F.setForeground(c);
		Sense_num_2_F.setForeground(c);
		
		POS1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		POS1_l.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		POS2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		POS2_l.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		Sense_num_1_F.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		Sense_num_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		Sense_num_2_F.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		Sense_num_2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		enterPOS.add(POS1);
		enterPOS.add(POS1_l);
		enterPOS.add(POS2);
		enterPOS.add(POS2_l);
		
		enterSnc.add(Sense_num_1_F);
		enterSnc.add(Sense_num_1);
		enterSnc.add(Sense_num_2_F);
		enterSnc.add(Sense_num_2);
		
		Compute = new Button("محاسبه‌ی مشابهت");
		Clear = new Button("پاک کردن");

		Clear.setFont(new Font("X Koodak", Font.PLAIN, 16));
		Compute.setFont(new Font("X Koodak", Font.PLAIN, 16));
		
		enterBut.add(Clear);
		enterBut.add(Compute);
		controlPanel_user.add(enterWord);
		controlPanel_opt.add(enterPOS);
		controlPanel_opt.add(enterSnc);
		controlPanel_opt.add(enterSnc);
		controlPanel_user.add(enterBut);
				
		String[] label_measures = {"Lesk", "JWSRandom", "Resnik", "LeacockAndChodorowlch", "AdaptedLesk", "AdaptedLeskTanimoto", "AdaptedLeskTanimotoNoHyponyms", "HirstAndStOnge", "JiangAndConrath", "Lin", "WuAndPalmer", "Vector" };
		for (int i = 0; i < measures_label_f.length; i++) {
			measures_label[i] = new JLabel(label_measures[i]);
		}
		
		//??
		Lesk_l = new JLabel("Lesk");
		JWSRandom_l = new JLabel("JWSRandom");
		Resnik_l = new JLabel("Resnik");
		LeacockAndChodorowlch_l = new JLabel("LeacockAndChodorowlch");
		AdaptedLesk_l = new JLabel("AdaptedLesk");
		AdaptedLeskTanimoto_l = new JLabel("AdaptedLeskTanimoto");
		AdaptedLeskTanimotoNoHyponyms_l = new JLabel("AdaptedLeskTanimotoNoHyponyms");
		HirstAndStOnge_l = new JLabel("HirstAndStOnge");
		JiangAndConrath_l = new JLabel("JiangAndConrath");
		Lin_l = new JLabel("Lin");
		WuAndPalmer_l = new JLabel("WuAndPalmer");
		Vector_l = new JLabel("Vector");
	
		col1 = new JLabel("معیار مشابهت");
		col2 = new JLabel("نتیجه معیار در فارسی");
		col3 = new JLabel("نتیجه معیار در مشابه انگلیسی");
		
		for (int i = 0; i < measures_label_f.length; i++)
			measures_label_f[i] = new JLabel();
		
		for (int i = 0; i < measures_label_e.length; i++) 
			measures_label_e[i] = new JLabel();
		
		Lesk_f= new JLabel();
		JWSRandom_f= new JLabel();
		Resnik_f= new JLabel();
		LeacockAndChodorowlch_f= new JLabel();
		AdaptedLesk_f= new JLabel();
		AdaptedLeskTanimoto_f= new JLabel();
		AdaptedLeskTanimotoNoHyponyms_f= new JLabel();
		HirstAndStOnge_f= new JLabel();
		JiangAndConrath_f= new JLabel();
		Lin_f= new JLabel();
		WuAndPalmer_f= new JLabel();
		Vector_f= new JLabel();
		
		Lesk_f_e= new JLabel();
		JWSRandom_f_e= new JLabel();
		Resnik_f_e= new JLabel();
		LeacockAndChodorowlch_f_e= new JLabel();
		AdaptedLesk_f_e= new JLabel();
		AdaptedLeskTanimoto_f_e= new JLabel();
		AdaptedLeskTanimotoNoHyponyms_f_e= new JLabel();
		HirstAndStOnge_f_e= new JLabel();
		JiangAndConrath_f_e= new JLabel();
		Lin_f_e= new JLabel();
		WuAndPalmer_f_e = new JLabel();
		Vector_f_e = new JLabel();
		
		// Compute Action Listener
		Compute.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	  		String w1 = word1_F.getText();
			String w2 = word2_F.getText();
			String POS1_F = String.valueOf(POS1.getSelectedItem());
			String POS2_F = String.valueOf(POS2.getSelectedItem());
			String POS1_s = getPOS(POS1_F);
			String POS2_s = getPOS(POS2_F);
			String Sense_num_1_s = Sense_num_1_F.getText();
			String Sense_num_2_s = Sense_num_2_F.getText();
			
	  		String str1 = w1 + " با نقش " + POS1_F +" و"+" شماره سنس "+ Sense_num_1_s;
			String str2 = w2 + " با نقش " + POS2_F +" و"+" شماره سنس "+ Sense_num_2_s;
			
			int Sense_num_1 = string2number(Sense_num_1_s);
			int Sense_num_2 = string2number(Sense_num_2_s);
			
			JOptionPane.showMessageDialog(mainFrame,str1+"\n"+str2);
			
			computeEng comEng = new computeEng();
			//handle Null
			Vector<String> str1_e = comEng.extEngW(comEng.getEngWN(w1));
			Vector<String> str2_e = comEng.extEngW(comEng.getEngWN(w2));
			
			FNSynsetService service = new FNSynsetService();
			Vector<FNSynset> synset_1 = service.FindSynsetsByWord(w1);
			Vector<FNSynset> synset_2 = service.FindSynsetsByWord(w2);
			
			boolean computable = true;
			
			
			
			if(synset_1.size()<1){
				JOptionPane.showMessageDialog(mainFrame, " در فارس‌نت وجود ندارد!"+" "+w1+"کلمه");
				computable = false;
			}
			if(synset_2.size()<1){
				JOptionPane.showMessageDialog(mainFrame, " در فارس‌نت وجود ندارد!"+" "+w2+"کلمه");
				computable = false;
			}
			
			if (Sense_num_1!=-1){
				synset_1 = getSynset_via_SenseNumber(Sense_num_1, synset_1);
				if (synset_1.size()<1){
					JOptionPane.showMessageDialog(mainFrame," در فارس‌نت وجود ندارد!"+" "+Sense_num_1+" "+"شمازه سنس"+" "+w1+" "+"برای کلمه");
					computable = false;
				}
			}
			if (Sense_num_2!=-1){
				synset_2 = getSynset_via_SenseNumber(Sense_num_2, synset_2);
				if (synset_2.size()<1){
					JOptionPane.showMessageDialog(mainFrame," در فارس‌نت وجود ندارد!"+" "+Sense_num_2+" "+"شمازه سنس"+" "+w2+" "+"برای کلمه");
					computable = false;
				}
			}
			
			if(!POS1_F.equals("نامعلوم")){
				synset_1 = getSynset_via_POS(POS1_s, synset_1);
				if (synset_1.size()<1){
					JOptionPane.showMessageDialog(mainFrame," در فارس‌نت وجود ندارد!"+" "+Sense_num_1+" "+"نقش"+" "+w1+" "+"برای کلمه");
					computable = false;
				}
			}
			if(!POS2_F.equals("نامعلوم")){
				synset_2 = getSynset_via_POS(POS2_s, synset_2);
				if (synset_2.size()<1){
					JOptionPane.showMessageDialog(mainFrame," در فارس‌نت وجود ندارد!"+" "+Sense_num_2+" "+"نقش"+" "+w2+" "+"برای کلمه");
					computable = false;
				}
			}
			
			Vector<Vector<ISynset>> iSynset_1 = getEnglishSynset(synset_1);
			Vector<Vector<ISynset>> iSynset_2 = getEnglishSynset(synset_2);
			
			if (iSynset_1.size()<1){
				JOptionPane.showMessageDialog(mainFrame," معادل انگلیسی در فارس‌نت ندارد!"+" "+w1+" "+"برای کلمه");
				computable = false;
			}
			
			if (iSynset_2.size()<1){
				JOptionPane.showMessageDialog(mainFrame," معادل انگلیسی در فارس‌نت ندارد!"+" "+w2+" "+"برای کلمه");
				computable = false;
			}
			
			if(!POS1_F.equals("نامعلوم") && POS2_F.equals("نامعلوم") && POS1_F.equalsIgnoreCase(POS2_F))
				JOptionPane.showMessageDialog(mainFrame," در فارس‌نت تنها شباهت های گروه لسک را می‌توان محاسبه کرد!"+POS2_F+" و "+POS1_F+"با نقش‌های متفاوت "+" "+w2+" "+" و "+w1+" "+"بین دو کلمه");
			
			if(computable == true){
			
				
				JOptionPane.showMessageDialog(mainFrame,""+"\n"+"معادل انگلیسی کلمات ورودی:"+"\n"+" "+str1_e+"\n"+str2_e);
				
				Double[] ans = {-12.0, -12.0, -12.0, -12.0, -12.0, -12.0, -12.0, -12.0, -12.0, -12.0, 12.0, 12.0};
				
				Double[] sim_measuresـfa = new Double[12];
				Double[] sim_measures_en = new Double[12];
				FaFN faFN = new FaFN();
				EngWN engWN = new EngWN();
			//	sim_measuresـfa = faFN.allMeasure(synset_1, synset_2, w1, w2);
				sim_measuresـfa = ans;
				sim_measures_en = ans;
			//	sim_measures_en = engWN.allMeasure(str1_e, str2_e, "N");
				
				measures_n = sim_measuresـfa;
				measures_n_e = sim_measures_en;
				
		  		Lesk_n = sim_measuresـfa[0];
		  		Lesk_n_e = sim_measures_en[0];
		  		
				JWSRandom_n = sim_measuresـfa[1];
				JWSRandom_n_e = sim_measures_en[1];
				
				Resnik_n = sim_measuresـfa[2];
				Resnik_n_e =  sim_measures_en[2];
				
				LeacockAndChodorowlch_n =  sim_measuresـfa[3];
				LeacockAndChodorowlch_n_e =  sim_measures_en[3];
				
				AdaptedLesk_n =  sim_measuresـfa[4];
				AdaptedLesk_n_e =  sim_measures_en[4];
				
				AdaptedLeskTanimoto_n =  sim_measuresـfa[5];
				AdaptedLeskTanimoto_n_e =  sim_measures_en[5];
				
				AdaptedLeskTanimotoNoHyponyms_n = sim_measuresـfa[6];
				AdaptedLeskTanimotoNoHyponyms_n_e =  sim_measures_en[6];
				
				HirstAndStOnge_n =  sim_measuresـfa[7];
				HirstAndStOnge_n_e =  sim_measures_en[7];
				
				JiangAndConrath_n =  sim_measuresـfa[8];
				JiangAndConrath_n_e = sim_measures_en[8];
				
				Lin_n =  sim_measuresـfa[9];
				Lin_n_e =  sim_measures_en[9];
				
				WuAndPalmer_n =  sim_measuresـfa[10];
				WuAndPalmer_n_e =  sim_measures_en[10];
				
				Vector_n =  sim_measuresـfa[11];
				Vector_n_e =  sim_measures_en[11];
				// for each IC based, compute, google page rank based
			}
			for (int i = 0; i < measures_label_f.length; i++) {
				measures_label_f[i].setText(measures_n[i].toString());
				measures_label_e[i].setText(measures_n_e[i].toString());
			}
			
			Lesk_f.setText(Lesk_n.toString());
			JWSRandom_f.setText(JWSRandom_n.toString());
			Resnik_f.setText(Resnik_n.toString());
			LeacockAndChodorowlch_f.setText(LeacockAndChodorowlch_n.toString());
			AdaptedLesk_f.setText(AdaptedLesk_n.toString());
			AdaptedLeskTanimoto_f.setText(AdaptedLeskTanimoto_n.toString());
			AdaptedLeskTanimotoNoHyponyms_f.setText(AdaptedLeskTanimotoNoHyponyms_n.toString());
			HirstAndStOnge_f.setText(HirstAndStOnge_n.toString());
			JiangAndConrath_f.setText(JiangAndConrath_n.toString());
			Lin_f.setText(Lin_n.toString());
			WuAndPalmer_f.setText(WuAndPalmer_n.toString());
			Vector_f.setText(Vector_n.toString());
			
			Lesk_f_e.setText(Lesk_n_e.toString());
			JWSRandom_f_e.setText(JWSRandom_n_e.toString());
			Resnik_f_e.setText(Resnik_n_e.toString());
			LeacockAndChodorowlch_f_e.setText(LeacockAndChodorowlch_n_e.toString());
			AdaptedLesk_f_e.setText(AdaptedLesk_n_e.toString());
			AdaptedLeskTanimoto_f_e.setText(AdaptedLeskTanimoto_n_e.toString());
			AdaptedLeskTanimotoNoHyponyms_f_e.setText(AdaptedLeskTanimotoNoHyponyms_n_e.toString());
			HirstAndStOnge_f_e.setText(HirstAndStOnge_n_e.toString());
			JiangAndConrath_f_e.setText(JiangAndConrath_n_e.toString());
			Lin_f_e.setText(Lin_n_e.toString());
			WuAndPalmer_f_e.setText(WuAndPalmer_n_e.toString());
			Vector_f_e.setText(Vector_n_e.toString());
	      }

		private Vector<Vector<ISynset>> getEnglishSynset(Vector<FNSynset> synset) {
			 Vector<Vector<ISynset>> myIsynset = new Vector<Vector<ISynset>>();
			 for (int i = 0; i < synset.size(); i++) {
				 Vector<ISynset> isynset = synset.elementAt(i).getMappedISynsets();
				 if (isynset.size()>0)
					 myIsynset.addElement(isynset);
			}
			return myIsynset;
		}

		private Vector<FNSynset> getSynset_via_POS(String pos, Vector<FNSynset> synset) {
			Vector<FNSynset> mysynset = new Vector<FNSynset>();
			for (int i = 0; i < synset.size(); i++) {
				if(synset.elementAt(i).getPos().equalsIgnoreCase(pos))
					mysynset.addElement(synset.elementAt(i));
			}
			return mysynset;
		}

		private int string2number(String num_str) {
			num_str = num_str.trim();
			String persian_numbers = "۰۱۲۳۴۵۶۷۸۹";
			String english_numbers = "0123456789";
			for (int i = 0; i < persian_numbers.length(); i++) {
				num_str.replace(persian_numbers.charAt(i), english_numbers.charAt(i));
			}
			if(num_str.equals("نامعلوم"))
				return -1;
			//return Integer.parseInt(num_str);
			return -1;
		}

		/**
		 * @param Sense_num_1
		 * @param synset_1
		 */
		public Vector<FNSynset> getSynset_via_SenseNumber(int Sense_num, Vector<FNSynset> synset) {
			Vector<FNSynset> mysynset = new Vector<FNSynset>();
			for (int i = 0; i < synset.size(); i++) {
				if(synset.elementAt(i).getSenses().size()>=Sense_num)
					mysynset.addElement(synset.elementAt(i));
			}
			return mysynset;
		}
	    });
		
		//Clear button Listener
		Clear.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
			Sense_num_1_F.setText("نامعلوم");
			Sense_num_2_F.setText("نامعلوم");
			POS1.setSelectedItem("نامعلوم");
			POS2.setSelectedItem("نامعلوم");
			
			for (int i = 0; i < measures_label_f.length; i++) {
				measures_label_f[i].setText("");
			}
			
			for (int i = 0; i < measures_label_e.length; i++) {
				measures_label_e[i].setText("");
			}
			
			for (int i = 0; i < measures_label_f.length; i++) {
				measures_label_f[i].setText("");
				measures_label_e[i].setText("");
			}
			
			Lesk_f.setText("");
			JWSRandom_f.setText("");
			Resnik_f.setText("");
			LeacockAndChodorowlch_f.setText("");
			AdaptedLesk_f.setText("");
			AdaptedLeskTanimoto_f.setText("");
			AdaptedLeskTanimotoNoHyponyms_f.setText("");
			HirstAndStOnge_f.setText("");
			JiangAndConrath_f.setText("");
			Lin_f.setText("");
			WuAndPalmer_f.setText("");
			Vector_f.setText("");
			
			Lesk_f_e.setText("");
			JWSRandom_f_e.setText("");
			Resnik_f_e.setText("");
			LeacockAndChodorowlch_f_e.setText("");
			AdaptedLesk_f_e.setText("");
			AdaptedLeskTanimoto_f_e.setText("");
			AdaptedLeskTanimotoNoHyponyms_f_e.setText("");
			HirstAndStOnge_f_e.setText("");
			JiangAndConrath_f_e.setText("");
			Lin_f_e.setText("");
			WuAndPalmer_f_e.setText("");
			Vector_f_e.setText("");
			
			word1_F.setText("");
			word2_F.setText("");
	      }
	    });//Finish Action Listener

		
		{//set Font
			
		for (int i = 0; i < measures_label.length; i++) {
			measures_label[i].setFont(new Font("Marker Felt", Font.PLAIN,  20));
			measures_label_f[i].setFont(new Font("Marker Felt", Font.PLAIN,  20));
			measures_label_e[i].setFont(new Font("Marker Felt", Font.PLAIN,  20));
		}
		
			
			
		Lesk_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Lesk_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Lesk_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		JWSRandom_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		JWSRandom_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		JWSRandom_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Resnik_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Resnik_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Resnik_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		LeacockAndChodorowlch_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		LeacockAndChodorowlch_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		LeacockAndChodorowlch_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLesk_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLesk_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLesk_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLeskTanimoto_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLeskTanimoto_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLeskTanimoto_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLeskTanimotoNoHyponyms_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLeskTanimotoNoHyponyms_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		AdaptedLeskTanimotoNoHyponyms_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		HirstAndStOnge_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		HirstAndStOnge_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		HirstAndStOnge_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		JiangAndConrath_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		JiangAndConrath_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		JiangAndConrath_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Lin_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Lin_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		Lin_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		WuAndPalmer_l.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		WuAndPalmer_f.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		WuAndPalmer_f_e.setFont(new Font("Marker Felt", Font.PLAIN,  20));
		}; // finish set font
		
		for (int i = 0; i < measures_label.length; i++) {
			if(i%2 == 0){
				measures_label[i].setForeground(Color.white);
				measures_label_f[i].setForeground(Color.white);
				measures_label_e[i].setForeground(Color.white);
				
			}
			else{
				measures_label[i].setForeground(Color.yellow);
				measures_label_f[i].setForeground(Color.yellow);
				measures_label_e[i].setForeground(Color.yellow);
			}
		}
		
		
		{//Set Color
		Lesk_l.setForeground(Color.white);
		Lesk_f.setForeground(Color.white);
		Lesk_f_e.setForeground(Color.white);
		JWSRandom_l.setForeground(Color.yellow);
		JWSRandom_f.setForeground(Color.yellow);
		JWSRandom_f_e.setForeground(Color.yellow);
		Resnik_l.setForeground(Color.white);
		Resnik_f.setForeground(Color.white);
		Resnik_f_e.setForeground(Color.white);
		LeacockAndChodorowlch_l.setForeground(Color.yellow);
		LeacockAndChodorowlch_f.setForeground(Color.yellow);
		LeacockAndChodorowlch_f_e.setForeground(Color.yellow);
		AdaptedLesk_l.setForeground(Color.white);
		AdaptedLesk_f.setForeground(Color.white);
		AdaptedLesk_f_e.setForeground(Color.white);
		AdaptedLeskTanimoto_l.setForeground(Color.yellow);
		AdaptedLeskTanimoto_f.setForeground(Color.yellow);
		AdaptedLeskTanimoto_f_e.setForeground(Color.yellow);
		AdaptedLeskTanimotoNoHyponyms_l.setForeground(Color.white);
		AdaptedLeskTanimotoNoHyponyms_f.setForeground(Color.white);
		AdaptedLeskTanimotoNoHyponyms_f_e.setForeground(Color.white);
		HirstAndStOnge_l.setForeground(Color.yellow);
		HirstAndStOnge_f.setForeground(Color.yellow);
		HirstAndStOnge_f_e.setForeground(Color.yellow);
		JiangAndConrath_l.setForeground(Color.white);
		JiangAndConrath_f.setForeground(Color.white);
		JiangAndConrath_f_e.setForeground(Color.white);
		Lin_l.setForeground(Color.yellow);
		Lin_f.setForeground(Color.yellow);
		Lin_f_e.setForeground(Color.yellow);
		WuAndPalmer_l.setForeground(Color.white);
		WuAndPalmer_f.setForeground(Color.white);
		WuAndPalmer_f_e.setForeground(Color.white);
		}//finish set color
		
		
		for (int i = 0; i < measures_label.length; i++) {
			measures_label_f[i].setHorizontalAlignment(SwingConstants.CENTER);
			measures_label_e[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		{//set position
		Lesk_f.setHorizontalAlignment(SwingConstants.CENTER);
		Lesk_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		JWSRandom_f.setHorizontalAlignment(SwingConstants.CENTER);
		JWSRandom_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		Resnik_f.setHorizontalAlignment(SwingConstants.CENTER);
		Resnik_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		LeacockAndChodorowlch_f.setHorizontalAlignment(SwingConstants.CENTER);
		LeacockAndChodorowlch_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		AdaptedLesk_f.setHorizontalAlignment(SwingConstants.CENTER);
		AdaptedLesk_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		AdaptedLeskTanimoto_f.setHorizontalAlignment(SwingConstants.CENTER);
		AdaptedLeskTanimoto_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		AdaptedLeskTanimotoNoHyponyms_f.setHorizontalAlignment(SwingConstants.CENTER);
		AdaptedLeskTanimotoNoHyponyms_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		HirstAndStOnge_f.setHorizontalAlignment(SwingConstants.CENTER);
		HirstAndStOnge_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		JiangAndConrath_f.setHorizontalAlignment(SwingConstants.CENTER);
		JiangAndConrath_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		Lin_f.setHorizontalAlignment(SwingConstants.CENTER);
		Lin_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		WuAndPalmer_f.setHorizontalAlignment(SwingConstants.CENTER);
		WuAndPalmer_f_e.setHorizontalAlignment(SwingConstants.CENTER);
		}//finish set position
		
		Color purple = new Color(51, 0, 102);
		
		
		controlPanel_result.add(col3);
		controlPanel_result.add(col2);
		controlPanel_result.add(col1);
		
		col1.setFont(new Font("X Bijan", Font.PLAIN,  20));
		col2.setFont(new Font("X Bijan", Font.PLAIN,  20));
		col3.setFont(new Font("X Bijan", Font.PLAIN,  20));
		col1.setHorizontalAlignment(SwingConstants.CENTER);
		col2.setHorizontalAlignment(SwingConstants.CENTER);
		col3.setHorizontalAlignment(SwingConstants.CENTER);
		col1.setForeground(Color.cyan);
		col2.setForeground(Color.cyan);
		col3.setForeground(Color.cyan);
		
		for (int i = 0; i < measures_label.length; i++) {
			controlPanel_result.add(measures_label_e[i]);
			controlPanel_result.add(measures_label_f[i]);
			controlPanel_result.add(measures_label[i]);
			
		}
		/*
		controlPanel_result.add(Lesk_f_e);
		controlPanel_result.add(Lesk_f);
		controlPanel_result.add(Lesk_l);
		controlPanel_result.add(JWSRandom_f_e);
		controlPanel_result.add(JWSRandom_f);
		controlPanel_result.add(JWSRandom_l);
		controlPanel_result.add(Resnik_f_e);
		controlPanel_result.add(Resnik_f);
		controlPanel_result.add(Resnik_l);
		controlPanel_result.add(LeacockAndChodorowlch_f_e);
		controlPanel_result.add(LeacockAndChodorowlch_f);
		controlPanel_result.add(LeacockAndChodorowlch_l);
		controlPanel_result.add(AdaptedLesk_f_e);
		controlPanel_result.add(AdaptedLesk_f);
		controlPanel_result.add(AdaptedLesk_l);
		controlPanel_result.add(AdaptedLeskTanimoto_f_e);
		controlPanel_result.add(AdaptedLeskTanimoto_f);
		controlPanel_result.add(AdaptedLeskTanimoto_l);
		controlPanel_result.add(AdaptedLeskTanimotoNoHyponyms_f_e);
		controlPanel_result.add(AdaptedLeskTanimotoNoHyponyms_f);
		controlPanel_result.add(AdaptedLeskTanimotoNoHyponyms_l);
		controlPanel_result.add(HirstAndStOnge_f_e);
		controlPanel_result.add(HirstAndStOnge_f);
		controlPanel_result.add(HirstAndStOnge_l);
		controlPanel_result.add(JiangAndConrath_f_e);
		controlPanel_result.add(JiangAndConrath_f);
		controlPanel_result.add(JiangAndConrath_l);
		controlPanel_result.add(Lin_f_e);
		controlPanel_result.add(Lin_f);
		controlPanel_result.add(Lin_l);
		controlPanel_result.add(WuAndPalmer_f_e);
		controlPanel_result.add(WuAndPalmer_f);
		controlPanel_result.add(WuAndPalmer_l);
		*/
		controlPanel_result.setBackground(purple);	
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("محاسبه‌ی معیارهای مشابهت‌یابی در فارس‌نت");
		mainFrame.setFont(new Font("X Titre", Font.PLAIN,  20));
		mainFrame.setSize( 900, 600);
		mainFrame.add(controlPanel_opt);
		mainFrame.add(controlPanel_user);
		mainFrame.add(controlPanel_result);
		mainFrame.setVisible(true);
		mainFrame.setLocation(200, 100);
		controlPanel_enter.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mainFrame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
	}

	// get POS from main frame
	public String getPOS(String pos){
		if(pos.equals("اسم"))
			return "Noun";
		if(pos.equals("فعل"))
			return "Verb";
		if(pos.equals("قید"))
			return "Adverb";
		if(pos.equals("صفت"))
			return "Adjective";

		return "NotSpecified";
	}
	
	// main function
	public static void main(String[] args){
		SGUI app = new SGUI();
	}
	
}
