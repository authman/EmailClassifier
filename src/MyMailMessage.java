

import java.util.Date;
/**
 * An interface for email messages
 *
 */
public interface MyMailMessage {
	String getSubject();

	String getContent();

	String getFrom();

	String getTo();

	String getToCC();

	String getToBCC();

	int getMessageNumber();

	Date getReceivedDate();

	String getOriginalContent();

}
