package us.anarchia.gwt.server;

import us.anarchia.db.AnarchiaDB;
import us.anarchia.gwt.client.AnarchiaService;
import us.anarchia.gwt.shared.FieldVerifier;
import us.anarchia.obj.Author;
import us.anarchia.obj.Frame;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AnarchiaServiceImpl extends RemoteServiceServlet implements AnarchiaService {

	private AnarchiaDB anarchiaDB = new AnarchiaDB();

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
	
	public Author storeAuthor(Author author) throws IllegalArgumentException {
		Author result = anarchiaDB.storeAuthor(author);
        return result;
    }

    public Author getAuthor(Author authorQuery) throws IllegalArgumentException {
        System.out.println("getAuthor looking for authorQuery:"+authorQuery);
        Author result = anarchiaDB.getAuthor(authorQuery);
        System.out.println("Author result: =========="+result);
        //result.setFamiliarName(result.getFamiliarName() + "_onServer:" + id);
        return result;
    }

    public Frame storeFrame(Author author, Frame frame) throws IllegalArgumentException {
		String id = anarchiaDB.storeFrame(author, frame);
		Frame result = anarchiaDB.getFrame(id);
        return result;
    }


	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
