import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class Lesk {
	static final String whiteSpace = "\\s+";
	static final String punctuationPattern = "\\p{Punct}+";
	static final String emptyString = "";
	static final String numericPattern = "\\d+";
	public static final List<String> stopWordList = new ArrayList<String>(Arrays.asList("a", "a's", "able", "about",
			"above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against",
			"ain't", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always",
			"am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything",
			"anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "aren't", "around",
			"as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "b", "be", "became",
			"because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe",
			"below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "c",
			"c'mon", "c's", "came", "can", "can't", "cannot", "cant", "cause", "causes", "certain", "certainly",
			"changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering",
			"contain", "containing", "contains", "corresponding", "could", "couldn't", "course", "currently", "d",
			"definitely", "described", "despite", "did", "didn't", "different", "do", "does", "doesn't", "doing",
			"don't", "done", "down", "downwards", "during", "e", "each", "edu", "eg", "eight", "either", "else",
			"elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody",
			"everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "f", "far", "few", "fifth",
			"first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from",
			"further", "furthermore", "g", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone",
			"got", "gotten", "greetings", "h", "had", "hadn't", "happens", "hardly", "has", "hasn't", "have", "haven't",
			"having", "he", "he's", "hello", "help", "hence", "her", "here", "here's", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit",
			"however", "i", "i'd", "i'll", "i'm", "i've", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc",
			"inc.", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward",
			"is", "isn't", "it", "it'd", "it'll", "it's", "its", "itself", "j", "just", "k", "keep", "keeps", "kept",
			"know", "knows", "known", "l", "last", "lately", "later", "latter", "latterly", "least", "less", "lest",
			"let", "let's", "little", "look", "looking", "looks", "ltd", "m", "mainly", "many", "may", "maybe", "me",
			"mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my",
			"myself", "n", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never",
			"nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not",
			"nothing", "novel", "now", "nowhere", "o", "obviously", "of", "off", "often", "oh", "ok", "okay", "old",
			"on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours",
			"ourselves", "out", "outside", "over", "overall", "own", "p", "particular", "particularly", "per",
			"perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "q", "que",
			"quite", "qv", "r", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards",
			"relatively", "respectively", "right", "s", "said", "same", "saw", "say", "saying", "says", "second",
			"secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible",
			"sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldn't", "since", "six",
			"so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat",
			"somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure",
			"t", "t's", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "that's",
			"thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "there's", "thereafter",
			"thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "they'd", "they'll", "they're",
			"they've", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through",
			"throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries",
			"truly", "try", "trying", "twice", "two", "u", "un", "under", "unfortunately", "unless", "unlikely",
			"until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "uucp", "v",
			"value", "various", "very", "via", "viz", "vs", "w", "want", "wants", "was", "wasn't", "way", "we", "we'd",
			"we'll", "we're", "we've", "welcome", "well", "went", "were", "weren't", "what", "what's", "whatever",
			"when", "whence", "whenever", "where", "where's", "whereafter", "whereas", "whereby", "wherein",
			"whereupon", "wherever", "whether", "which", "while", "whither", "who", "who's", "whoever", "whole", "whom",
			"whose", "why", "will", "willing", "wish", "with", "within", "without", "won't", "wonder", "would", "would",
			"wouldn't", "x", "y", "yes", "yet", "you", "you'd", "you'll", "you're", "you've", "your", "yours",
			"yourself", "yourselves", "z", "zero"));

	public static void main(String[] args) {
		System.setProperty("wordnet.database.dir", "C://Program Files (x86)//WordNet//2.1//dict");
		WordNetDatabase wordnetDB = WordNetDatabase.getFileInstance();
		System.out.print("Enter a sentence: ");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();  //"Time flies like an arrow"
		String[] inputArray = input.split(whiteSpace);
		List<WordSense> wordSenseList = new ArrayList<WordSense>();

		for (int k = 0; k < inputArray.length; k++) {

			String inputWordWithoutPunct = inputArray[k].toLowerCase().replaceAll(punctuationPattern, emptyString);
			if (!inputArray[k].matches(numericPattern) && !stopWordList.contains(inputArray[k])
					&& !stopWordList.contains(inputWordWithoutPunct)) {
				Synset[] synsets = wordnetDB.getSynsets(inputWordWithoutPunct);

				if (synsets.length > 0) {
					WordSense wordSense = new WordSense(inputWordWithoutPunct);
					for (int i = 0; i < synsets.length; i++) {

						Set<String> signatureSet = new HashSet<String>();
						updateSignature(signatureSet, synsets[i].getDefinition());
						
						String[] examples = synsets[i].getUsageExamples();
						for (int j = 0; j < examples.length; j++) {
							updateSignature(signatureSet, examples[j]);
						}
						wordSense.signatureList.add(i, signatureSet);
						wordSense.definitionList.add(i, synsets[i].getDefinition());
						wordSense.matchCount.add(i, 0);
					}
					wordSenseList.add(wordSense);
				}
			}
		}
		runLeskAlgorithm(wordSenseList);
		sc.close();
	}

	public static void runLeskAlgorithm(List<WordSense> wordSenseList) {
		int currentMatchCount = 0;
		int senseType = 0;
		for (int i = 0; i < wordSenseList.size(); i++) {
			int definitionType = 0;
			int maxMatchCount = -1;
			
			for (Set<String> signSetWord : wordSenseList.get(i).signatureList) {
				currentMatchCount = 0;
				for (int j = 0; j < wordSenseList.size(); j++) {
			
					if (i != j) {
						for (Set<String> signSetContext : wordSenseList.get(j).signatureList) {
							
							Set<String> matchingSet = new HashSet<String>(signSetWord);
							if (matchingSet.retainAll(signSetContext)) {
								currentMatchCount += matchingSet.size();
							}
							
						}
					}
				}
				if (maxMatchCount < currentMatchCount) {
					maxMatchCount = currentMatchCount;
					senseType = definitionType;
				}
				
				wordSenseList.get(i).matchCount.set(definitionType, maxMatchCount);
				definitionType++;
			}
			System.out.println(wordSenseList.get(i).getWord() + ": " + wordSenseList.get(i).definitionList.get(senseType));
		}
	}

	public static Set<String> updateSignature(Set<String> signSet, String wordSyn) {
		String[] wordArr = wordSyn.split(whiteSpace);
		for (String word : wordArr) {
			String wordWithoutPunct = word.toLowerCase().replaceAll(punctuationPattern, emptyString);
			if (!word.matches(numericPattern) && !stopWordList.contains(word) && !stopWordList.contains(wordWithoutPunct)) {
				signSet.add(wordWithoutPunct);
			}
		}
		return signSet;
	}

}