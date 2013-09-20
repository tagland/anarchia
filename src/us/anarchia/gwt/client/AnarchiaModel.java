/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */

package us.anarchia.gwt.client;

import us.anarchia.obj.Author;
import us.anarchia.obj.Copyright;
import us.anarchia.obj.Frame;
import us.anarchia.obj.Metadata;

public class AnarchiaModel {
	public static Author newAuthor(){
		Author a = new Author();
		a.setMetadata(newMetadata());
        a.setCopyright(newCopyright(a));
		return a;
	}

	public static Copyright newCopyright(Author author){
		Copyright c = new Copyright();
		if (author!=null){
			if (author.getMetadata() == null){
				author.setMetadata(newMetadata());
			}
			author.getMetadata().copyright = c;    //todo, this is kinda whack, and is NOT the canonical location for the Author's copyright....
		}
		return c;
	}
	
	public static Metadata newMetadata(){
		return Metadata.defaultMetatdata();
	}
	
	public static Frame newFrame(){
		Frame f = new Frame();
		return f;		
	}
}
