package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Label;
import us.anarchia.obj.Author;
import us.anarchia.obj.Copyright;

public class AuthorDesigner extends Designer<Author> {
    public AuthorDesigner(Author author) {
        super(author);

        Label designerLabel = new Label();
        designerLabel.setText("Author");
        panel.add(designerLabel);

        familiarName = new LabelEdit("Familiar Name") {
            public void onStringValueChanged(String newValue) {
                getModel().setFamiliarName(newValue);
            }
        };
        familiarName.setText(getModel().getFamiliarName());
        panel.add(familiarName);

        fullName = new LabelEdit("Full Name") {
            public void onStringValueChanged(String newValue) {
                getModel().setFullName(newValue);
            }
        };
        fullName.setText(getModel().getFullName());
        panel.add(fullName);
    }

    @Override
    public void onAddChildren(){
        System.out.println("--- in onAddChildren " + getClass().getName());
        Designer<Copyright> copyrightDesigner = new CopyrightDesigner(getModel().getCopyright());
        copyrightDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "Copyright");

        LinkListDesigner linkListDesigner = new LinkListDesigner(getModel().getWebsites());
        linkListDesigner.setCaption("Link");
        linkListDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "Websites");
        linkListDesigner.getTNode().showAddChildButton("Add Link");

        Author au = getModel();

        if (au.getImageGroups() == null){
            au.initImageGroups();
        }
        try {
            ImageGroupListDesigner imageGroupListDesigner = new ImageGroupListDesigner(getModel().getImageGroups());
        imageGroupListDesigner.setCaption("ImageGroup");
        imageGroupListDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "ImageGroups");
        imageGroupListDesigner.getTNode().showAddChildButton("Add ImageGroup");
        } catch (Throwable t){
            System.out.println("ERROR creating imageGroupList"+t.toString());
        }
    }

    LabelEdit familiarName;
    LabelEdit fullName;

}
