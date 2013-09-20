/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */

package us.anarchia.db;

import java.util.List;
import java.util.UUID;

import com.db4o.query.Predicate;
import us.anarchia.gwt.client.AnarchiaModel;
import us.anarchia.gwt.shared.Tools;
import us.anarchia.obj.Author;
import us.anarchia.obj.Frame;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.config.TSerializable;
import com.db4o.ta.TransparentActivationSupport;

public class AnarchiaDB {
    public static final String DB4OFILENAME = "/Users/laramie/tmp/_DB4o_gwt_anarchia.db";

    private ObjectContainer db;

    public static String newID() {
        return UUID.randomUUID().toString();
    }

    private void opendb() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass("Author").cascadeOnUpdate(true);
        config.common().objectClass("Link").cascadeOnUpdate(true);
        config.common().add(new TransparentActivationSupport());
        config.common().objectClass("java.net.URL").translate(new TSerializable());

        config.common().objectClass("us.anarchia.obj.Author").cascadeOnDelete(true);

        db = Db4oEmbedded.openFile(config, DB4OFILENAME);
    }

    private void closedb() {
        db.close();
        db = null;
    }

    protected List<Author> queryAuthor(Author authorQuery) {
        final String familiarName = authorQuery.getFamiliarName();
        final String id = authorQuery.getID();
        List<Author> resultList = db.query(new Predicate<Author>() {
            public boolean match(Author author) {
                return author.getFamiliarName().equalsIgnoreCase(familiarName)   //TODO: make this loginName
                        || author.getID().equals(id);
            }
        });
        return resultList;
    }

    public Author storeAuthor(Author author) {
        opendb();
        try {
            Author resultAuthor;
            List<Author> resultList = queryAuthor(author);
            if (resultList.size() == 0) {
                resultAuthor = author;
                String id = newID();
                resultAuthor.setID(id);
                System.out.println("storeAuthor() added author: " + resultAuthor);
            } else {
                resultAuthor = author;
                Author oldAuthor = resultList.get(0);
                db.delete(oldAuthor);
                if (Tools.isEmpty(resultAuthor.getID())) {
                    resultAuthor.setID(newID());
                    System.out.println("ID added to Author: " + resultAuthor.getID());
                }
            }
            db.store(resultAuthor);
            db.commit();
            System.out.println("storeAuthor DONE: " + resultAuthor);
            return resultAuthor;
        } finally {
            closedb();
        }
    }

    public Author getAuthor(Author authorQuery) {
        opendb();
        try {
            List<Author> resultList = queryAuthor(authorQuery);
            if (resultList.size() == 0) {
                Author resAuthor = AnarchiaModel.newAuthor();
                resAuthor.setDebugNotes("created:" + System.currentTimeMillis());
                resAuthor.setFamiliarName(authorQuery.getFamiliarName());
                System.out.println("Author created: " + resAuthor);
                return resAuthor;
            } else {
                Author author = resultList.get(0);
                System.out.println("Author found in DB: " + author);
                return author;
            }
        } finally {
            closedb();
            System.out.println("getAuthor() DONE.");
        }
    }

    /**
     * @return The ID of the stored frame, or empty string if not stored.
     */
    public String storeFrame(Author author, Frame frame) {
        opendb();
        try {
            ObjectSet result = db.queryByExample(frame);
            if (!result.hasNext()) {
                String id = frame.getID();
                if (id != null && id.length() == 0) {
                    id = newID();
                    frame.setID(id);
                }
                author.addFrameID(id);

                db.store(frame);
                db.commit();
                System.out.println("storeFrame() added frame: " + frame);
                return frame.getID();
            } else {
                Frame resFrame = (Frame) result.next();
                System.out.println("Frame alread in DB: " + resFrame);
                String id = resFrame.getID();
                if (!author.hasFrameID(id)) {
                    author.addFrameID(id);
                }
                return id;
            }
        } finally {
            closedb();
            System.out.println("storeFrame DONE.");
        }
    }

    public Frame getFrame(String id) {
        opendb();
        try {
            Frame template = new Frame();
            template.setID(id);
            ObjectSet result = db.queryByExample(template);
            if (!result.hasNext()) {
                return null;
            } else {
                Frame resFrame = (Frame) result.next();
                System.out.println("Frame found in DB: " + resFrame);
                return resFrame;
            }
        } finally {
            closedb();
            System.out.println("getFrame() DONE.");
        }
    }

}
