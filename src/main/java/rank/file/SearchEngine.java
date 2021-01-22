package rank.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SearchEngine {

	private Map<String, String> rankingMap;
	private Map<String, ArrayList<String>> fileFrequenciesMap;
	private double searchSize; 
	private Set<String> words; 

	/**
	 * Provides search functionalities for a list of words.
	 */
	public SearchEngine() {
		this.rankingMap = new HashMap<String, String>();
		this.fileFrequenciesMap = new HashMap<String, ArrayList<String>>();
	}

	/**
	 * Starts the ranker console, receives input words and computes the search and
	 * ranking process.
	 * 
	 * @param indexedDir an object representing an indexed folder.
	 * @param dirSize    Number of files in the folder.
	 */
	public void startRankerConsole(Map<String, Map<String, ArrayList<String>>> indexedDir, Integer dirSize) {
		try (Scanner keyboard = new Scanner(System.in);) {
			while (true) {
				System.out.print("search>");
				final String line = keyboard.next();
				this.words = getUniqueWords(line);
				this.searchSize = words.size();
				computeFileFrequencies(indexedDir, words);
				computeRankingMap();
				if(rankingMap.isEmpty()) {
					System.out.println("There are no word matches!");
				} else {
					for(Entry<String, String> entry: rankingMap.entrySet()) {
						System.out.println(entry.getKey() + " => " + entry.getValue());
					}
				}
				resetMaps();
			}
		}
	}

	/**
	 * Removes word duplicates
	 * @param line
	 * @return
	 */
	private Set<String> getUniqueWords(String line) {
		//String[] uniqueWords = line.split(" ");
		Set<String> uniqueWords = new TreeSet<String>();
		uniqueWords.addAll(Arrays.asList(line.split(" ")));
		return uniqueWords;
	}

	/**
	 * Computes final file ranks.
	 * 
	 * @param dirSize number of files in input directory
	 */
	private void computeRankingMap() {
		for (Entry<String, ArrayList<String>> entry : fileFrequenciesMap.entrySet()) {
			rankingMap.put(entry.getKey(), String.valueOf(Math.round((entry.getValue().size() / searchSize)*100)) + "%");
		}
	}

	/**
	 * Finds input word occurrences for each file.
	 * 
	 * @param indexedDir the indexed dir object
	 * @param words       the CLI line
	 */
	private void computeFileFrequencies(Map<String, Map<String, ArrayList<String>>> indexedDir, final Set<String> words) {
		
		for (String word : words) {
			String inWord = TextParserUtil.cleanWord(word);
			String inPartition = inWord.substring(0, 1);
			if (indexedDir.get(inPartition) != null) {
				if (indexedDir.get(inPartition).get(inWord) != null) {

					for (String fileName : indexedDir.get(inPartition).get(inWord)) {
						ArrayList<String> wordList = new ArrayList<String>();
						if (fileFrequenciesMap.get(fileName) != null) {
							wordList = fileFrequenciesMap.get(fileName);
							wordList.add(inWord);
							fileFrequenciesMap.put(fileName, wordList);
						} else {
							wordList.add(inWord);
							fileFrequenciesMap.put(fileName, wordList);
						}
					}
				}
			}
		}
	}

	/**
	 * Delete maps data after ranking computation.
	 */
	private void resetMaps() {
		this.fileFrequenciesMap = new HashMap<String, ArrayList<String>>();
		this.rankingMap = new HashMap<String, String>();
	}

}
