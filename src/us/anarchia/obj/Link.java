/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;


/** Link identity is provided by the URL field. If two links have equal url's, then they are equal, even if description is different.
 *   Note that java.net.URL is not used because it is not serializable by GWt.
 */
public class Link extends Storable {
	public Link(){
	}

	public Link(String urlString, String description){
		this.urlString = urlString;
		this.description = description;
	}

    public String urlString;
	public String description;

    public boolean equals(Object other){
		if (null != other && other instanceof Link){
			Link ol = (Link)other;
			if (ol.urlString.equals(this.urlString)){
				return true;			
			}
		}
		return false;
	}
	public int hashCode(){
		return urlString.hashCode();
	}
	public String toString(){
		String urlID = "null";
		if (null!=urlString){
            urlID = urlString;
		}
		return "Link["+urlID+"]:"+description;
	}
	
}
