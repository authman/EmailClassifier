
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Main {

/** Main method that loads other files, connects to gmail account, and creates output files for Octave scripts
 * Should probably be refactored 
 * 
 * @param args command line arguments (takes none at this point)
 * @throws Exception can throw exceptions from not finding files, subject line not being an int, etc.
 */
	public static void main(String[] args) throws Exception {
		File keywords = new File(Constants.KEYWORDS_FILE);
		File X = new File(Constants.X);
		File Y = new File(Constants.Y);

		Counter c = new Counter(keywords, X);
		BufferedOutputStream y = new BufferedOutputStream(new FileOutputStream(
				Y));
		GoogleReader gr = new GoogleReader(Constants.USERNAME, Constants.PASSWORD, 0);
		for (int i = Constants.START; i < Constants.END; i++) {
			System.out.println("Processing message " + i);
			MyMailMessage m = gr.getNextMessage();
			String subject = m.getSubject();
			int score = Integer.parseInt(subject); 
			score = (score < 6) ? 0 : 1; //writes 0 if number in email subject line was <6 (not urgent/important), otherwise writes 1
			y.write(48 + score); // converts 0 or 1 to ASCII "0" or "1"
			y.write('\n');
			String in = m.getContent().toLowerCase();
			int j = in.indexOf("subject");
			in = in.substring(j + 7); // move past subject lie of email
			c.writeOutput(in); //creates 0 and 1 matrix based on presence of features

		}
		c.close();
		y.close();
		System.out.println("Done");
	}
}
