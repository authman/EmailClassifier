

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
/**
 * 
 * Checks if keywords exist in a string, and writes the result to a text file that can be used
 * by the Octave scripts
 * Needs to be refactored
 */
public class Counter {

	private HashMap<String, Integer> lookup;
	private final int featureCount;
	private final Writer o;
/**
 * 
 * @param keywords text file with each word and its stems in a single line
 * @param output output file where 
 * @throws FileNotFoundException
 * @throws IOException
 */
	public Counter(File keywords, File output) throws FileNotFoundException,
			IOException {
		lookup = new HashMap<String, Integer>();
		BufferedReader in = new BufferedReader(new FileReader(keywords));
		o = new BufferedWriter(new FileWriter(output));
		int i = -1;
		while (true) {
			String s = in.readLine();
			if (s == null)
				break;

			String s1 = s.trim();

			if (s1.length() == 0)
				continue; // ignore blank lines
			String[] tokens = s1.split("[^abcdefghijklmnopqrstuvwxyz\"\']+"); //splits only on '_' 
			i++;
			for (String token : tokens) {
				if (token.length() > 1) {
					lookup.put(token, i); //adds keyword tokens to Hashmap
					System.out.println("Put token " + token + " in position "
							+ i);
				}
			}

		}
		in.close();
		featureCount = i + 1;
		System.out.println("Feature count is " + featureCount);
	}
/**
 * Creates array corresponding to whether tokens in email match keywords
 * @param inp input string (email message)
 * @return array of 1s and 0s that correspond to whether a keyword exists in a message
 */
	public int[] matchResults(String inp) {
		String[] tokens = inp.split("_+");
		int[] results = new int[featureCount];
		for (String token : tokens) {
			System.out.println("Looking up: " + token);
			Integer lookedup = lookup.get(token);
			if (lookedup == null)
				continue;
			results[lookedup] = 1;
		}
		return results;
	}
/**
 * Matches the input with keywords, outputs results in file that can be read by Octave scripts
 * @param inp Input
 * @throws IOException
 */
	public void writeOutput(String inp) throws IOException {
		int[] results = matchResults(inp);

		for (int i = 0; i < results.length; i++) {
			o.write(48 + results[i]); // converts 0 1 to ASCII "0" or "1"
			o.write(32); // space o.write('\n');

		}
		o.write('\n');
		System.out.println("Record written to file");

	}
/**
 * Closes IO streams
 * @throws IOException
 */
	public void close() throws IOException {

		o.flush();
		o.close();
		lookup = null;
	}
}
