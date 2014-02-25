

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Class for interacting with and getting data from Google's imap API
 *
 */
public class GoogleReader {
    String user;
    String pw;
    String contents;
    String from;
    String to;
    String to_cc;
    String to_bcc;
    String subject;
    int message_number;
    Date date;
    Folder inbox;
    static String INCLUDED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String SQUASHED_CHARS = "";

    // constructor
    GoogleReader(String user, String pw, int ignoreTillMessageNumber)
	    throws Exception {
	this.user = user;
	this.pw = pw;
	message_number = ignoreTillMessageNumber;

	// create a session object
	Properties prop_imap = new Properties();
	prop_imap.setProperty("class", "com.sun.mail.imap.IMAPStore");
	prop_imap.setProperty("mail.imap.ssl.enable", "true");
	Session session = Session.getInstance(prop_imap);
	Store store = session.getStore("imaps");
	store.connect("imap.gmail.com", user, pw);

	inbox = store.getFolder("Inbox");

	inbox.open(Folder.READ_ONLY);

    }
/**
 * Fetches the next message in the inbox
 * @return
 * @throws Exception
 */
    MyMailMessage getNextMessage() throws Exception {

	message_number++;
	Message message = inbox.getMessage(message_number);

	from = InternetAddress.toString(message.getFrom());
	to = InternetAddress.toString(message
		.getRecipients(Message.RecipientType.TO));
	to_bcc = InternetAddress.toString(message
		.getRecipients(Message.RecipientType.BCC));
	to_cc = InternetAddress.toString(message
		.getRecipients(Message.RecipientType.CC));
	System.out.println("====>Content type:" + message.getContentType());
	subject = message.getSubject();
	date = message.getReceivedDate();
	if (message instanceof MimeMessage) {
	    MimeMessage mimeMessage = (MimeMessage) message;
	    Object content = mimeMessage.getContent();
	    if (content instanceof InputStream) {
		InputStream inp = (InputStream) content;
		int n = inp.available();
		byte[] buffer = new byte[n];
		inp.read(buffer, 0, n);
		inp.close();
		contents = new String(buffer);

	    } else if (content instanceof Multipart) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		((Multipart) content).writeTo(os);
		contents = new String(os.toByteArray());
	    } else if (content instanceof String) {
		contents = (String) content;
	    }
	    return new MyMailMessage() {
		@Override
		public int getMessageNumber() {
		    return message_number;
		}

		@Override
		public Date getReceivedDate() {
		    return date;
		}

		@Override
		public String getSubject() {
		    return subject;
		}

		@Override
		public String getContent() {
		    return cleanup(contents);
		}
		
		@Override
		public String getOriginalContent() {
			return contents;
		}

		@Override
		public String getFrom() {
		    return from;
		}

		@Override
		public String getTo() {
		    return to;
		}

		@Override
		public String getToCC() {
		    return to_cc;
		}

		@Override
		public String getToBCC() {
		    return to_bcc;
		}
	    };

	}
	return null;

    }
/**
 * Gets rid of markup tags in messages. For example, '<b> hello<i> world</i></b>' returns as 'hello world'
 * Also converts characters not part of a word to '_' to make parsing easier later on
 * @param s Input string
 * @return string without markup tags and other characters converted to '_'
 */
    public static String cleanup(String s) {
	StringBuffer sb = new StringBuffer();
	String lt = "<";
	String gt = ">";

	int len = s.length();
	Stack<Integer> stack = new Stack<Integer>();
	for (int i = 0; i < len; i++) {
	    String single = s.substring(i, i + 1).toString();
	    if (single.equalsIgnoreCase(lt)) {
		sb.append(lt);
		stack.push(sb.length());
	    } else if (single.equalsIgnoreCase(gt)) {
		if (stack.isEmpty()) {
		    sb.append(single);
		    continue;
		}
		int size = stack.pop();
		sb.setLength(size - 1);

	    } else {
		if (single == "=") {
		    continue;
		}
		if (!(INCLUDED_CHARS.contains(single))) {
		    sb.append("_");
		    continue;
		}

		sb.append(single);
	    }

	}
	String result = sb.toString();

	return result;

    }


}
