/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;

public class Copyright extends StorableComposite {
	public Copyright() {
		super("", "copyright");
	}
	private Date date;

	public Date getDate() {
		return date;
	}
	public void setDate(Date value) {
		date = value;
	}
    public String author;
    public String[] authorities = {"Library of Congress","Creative Commons","U.S. Copyright Office","Other"};

    private String licenseName;
    public String getLicenseName() {
        return licenseName;
    }
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

	

}
