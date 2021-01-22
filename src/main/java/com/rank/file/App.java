package com.rank.file;

/**
 * File Ranker.
 *
 */
public class App {

	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("No directory given to index.");
		}

		IndexService indexService = new IndexService();
		SearchEngine searchEngine = new SearchEngine();
		
		searchEngine.startRankerConsole(indexService.getIndexedDir(args[0]),2);

	}
}
