import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WordSense {

	private String word;	
	private String senseType;
	List<String> definitionList;
	List<Set<String>> signatureList;
	List<Integer> matchCount;
	public WordSense(){}
	public WordSense(String word){
		this.word = word;
		this.signatureList = new ArrayList<Set<String>>();
		this.definitionList = new ArrayList<String>();
		this.matchCount = new ArrayList<Integer>();		
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getSenseType() {
		return senseType;
	}
	public void setSenseType(String senseType) {
		this.senseType = senseType;
	}

}