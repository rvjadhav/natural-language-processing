import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class BigramCompute {

	public static final String patternNumber = "\\d+";
	public static final String patternPunctuation = "\\p{Punct}+";
	public static final String patternNumberPunctuation = "\\d*\\p{Punct}+\\d*|\\p{Punct}*\\d+\\p{Punct}*";
	public ArrayList<String> corpusWordList;
	public static final String filePath = "/home/rahul/IdeaProjects/natural-language-processing/word-predictor/res/corpus.txt";;
	static String whiteSpace = " ";
	static int vocabularyCount = 0;
	public BigramCompute() {
		this.corpusWordList = readFile();
	}
	public static void main(String[] args) {
		BigramCompute bgramComp = new BigramCompute();
        ArrayList<String> suggestion = new ArrayList<String>();
        String input = "mere";
        
        suggestion = bgramComp.countBigram(input);
        for(String s: suggestion){
        	System.out.println(s);
        }
	}

	// Method cleans up the input sentence by removing punctuation and numbers
	// from it.
	public String[] getInput(String inputStr) {
		ArrayList<String> inputStrArr = new ArrayList<String>();

		for (String word : inputStr.split(whiteSpace)) {
			if (!word.matches(patternNumberPunctuation)) {
				inputStrArr.add(word);
			}
		}
		return inputStrArr.toArray(new String[inputStrArr.size()]);
	}

	public static ArrayList<String> readFile() {
		ArrayList<String> corpusWordList = new ArrayList<String>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = "";
			System.out.println("Reading Corpus File....");
			while ((line = br.readLine()) != null) {

				String[] lineArr = line.split(whiteSpace);
				for (int wordCount = 0; wordCount < lineArr.length; wordCount++) {
					if (lineArr[wordCount] != whiteSpace) {
						String word = lineArr[wordCount];
						// if a `'s` or `n't` character is found remove the
						// space between it and preceding word
						// and store as a single word
						if ((wordCount < lineArr.length - 1)
								&& (lineArr[wordCount + 1].equals("'s") || lineArr[wordCount + 1].equals("n't") || lineArr[wordCount + 1].equals("'re"))) {
							word = word + lineArr[wordCount + 1];
							wordCount = wordCount + 1;
						}
						// Increment vocabulary count for unique words.
						if (!word.matches(patternNumberPunctuation) && !corpusWordList.contains(word)) {
							vocabularyCount++;
						}
						// Punctuation characters and numbers not added in
						// retrieved words.
						if (word.equals(".") || (!word.matches(patternNumberPunctuation))) {
							corpusWordList.add(word);
						}

					}
				}
			}
			br.close();

		} catch (Exception ex) {
			System.out.println("Exception occured while reading file..." + ex.getMessage());
		}
		return corpusWordList;
	}


	public  ArrayList<String> countBigram(String inputStrArr) {
		System.out.println("Next word predictions for input:"+inputStrArr);
		HashMap<String, Integer> bcount = new HashMap<String, Integer>();
		for (int k = 0; k < corpusWordList.size(); k++) {
			// Check if the words occur together
			if (inputStrArr.equalsIgnoreCase(corpusWordList.get(k))) {
				if ((k < corpusWordList.size() - 1)) {
					String bgram = inputStrArr+" "+corpusWordList.get(k+1);
					Integer bicount = bcount.get(bgram);
					if(bicount != null){
						bcount.put(bgram, bicount+1);
					}else{
						bcount.put(bgram, 1);
					}
				}
			}
		}
		Set<Entry<String, Integer>> set = bcount.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Entry<String, Integer>>()
        {
            public int compare( Entry<String, Integer> o1, Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );	
        ArrayList<String> suggestion = new ArrayList<String>();
        for(Entry<String, Integer> entry:list){
        	String[] words = entry.getKey().split(whiteSpace);
        	for(int i=0; i<words.length;i++){
        		if(i==1){
        			suggestion.add(words[1]);        		        			
        		}
        	}
        }
		return suggestion;
	}

}