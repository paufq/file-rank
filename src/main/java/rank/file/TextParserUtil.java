package rank.file;

import org.apache.commons.lang3.StringUtils;

public class TextParserUtil {
	
	/**
	 * Remove strange characters.
	 * 
	 * @param word
	 * @return
	 */
	public static String cleanWord(String inWord) {
		return StringUtils.stripAccents(inWord).trim().toLowerCase().replaceAll("[^\\p{L}\\p{Nd}]+", "");
	}

}
