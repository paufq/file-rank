package com.rank.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IndexService {

	private Map<String, Map<String, ArrayList<String>>> indexedDir = null;

	/**
	 * Provides indexing functionalities.
	 */
	public IndexService() {
		this.indexedDir = new HashMap<String, Map<String, ArrayList<String>>>();
	}

	/**
	 * Returns an indexed directory object.
	 * 
	 * @param directory input directory to index
	 * @return indexed directory objet.
	 */
	public Map<String, Map<String, ArrayList<String>>> getIndexedDir(String directory) {
		final File indexableDirectory = new File(directory);
		for (String file : indexableDirectory.list()) {
			updateIndexMap(directory + "\\" + file);
		}
		return this.indexedDir;
	}


	/**
	 * 
	 * Given a file, updates the indexed directory object according to word
	 * occurrences.
	 *
	 * @param file file to index
	 */
	private void updateIndexMap(String file) {
		try (Scanner s = new Scanner(new File(file))) {
			while (s.hasNext()) {
				String word = TextParserUtil.cleanWord(s.next());
				String partition = word.substring(0, 1);
				if (indexedDir.get(partition) == null) {
					insertPartitionEntry(file, word, partition);
				} else if (indexedDir.get(partition).get(word) == null) {
					insertWordEntry(file, word, partition);
				} else if (indexedDir.get(partition).get(word) != null) {
					updateFileList(file, word, partition);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Updates only the file list corresponding to the word.
	 * 
	 * @param file file to index
	 * @param word a word which is already present in the map
	 * @param partition corresponding partition to word
	 */
	private void updateFileList(String file, String word, String partition) {
		ArrayList<String> fileList = indexedDir.get(partition).get(word);
		if (!fileList.contains(file)) {
			fileList.add(file);
			indexedDir.get(partition).put(word, fileList);
		}
	}

	/**
	 * Updates word entry on the indexed directory map object.
	 * 
	 * @param file      file to index
	 * @param word      a word which partition is already present in the map
	 * @param partition corresponding partition to word
	 */
	private void insertWordEntry(String file, String word, String partition) {
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add(file);
		indexedDir.get(partition).put(word, fileList);
	}

	/**
	 * Updates full partition entry on the indexed directory map object.
	 * 
	 * @param file      file to index
	 * @param word      a word which is not present in the map
	 * @param partition corresponding partition to word
	 */
	private void insertPartitionEntry(String file, String word, String partition) {
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add(file);
		Map<String, ArrayList<String>> wordMap = new HashMap<String, ArrayList<String>>();
		wordMap.put(word, fileList);

		indexedDir.put(partition, wordMap);
	}

}
