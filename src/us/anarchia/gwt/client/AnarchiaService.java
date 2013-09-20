package us.anarchia.gwt.client;

import us.anarchia.obj.Author;
import us.anarchia.obj.Frame;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface AnarchiaService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	Author storeAuthor(Author author) throws IllegalArgumentException;
	Author getAuthor(Author authorQuery) throws IllegalArgumentException;
	Frame storeFrame(Author author, Frame frame) throws IllegalArgumentException;
}
