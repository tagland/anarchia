package us.anarchia.gwt.client;

import us.anarchia.obj.Author;
import us.anarchia.obj.Frame;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AnarchiaServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	 void storeAuthor(Author author, AsyncCallback<Author> callback);
	 void getAuthor(Author authorQuery, AsyncCallback<Author> callback);
	 void storeFrame(Author author, Frame frame, AsyncCallback<Frame> callback);
}
