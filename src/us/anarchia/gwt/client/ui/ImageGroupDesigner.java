package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Label;
import us.anarchia.obj.Image;
import us.anarchia.obj.ImageGroup;

public class ImageGroupDesigner extends Designer<ImageGroup> {
    public ImageGroupDesigner(ImageGroup imageGroup) {
        super(imageGroup);

        Label designerLabel = new Label();
        designerLabel.setText("ImageGroupDesigner label");
        panel.add(designerLabel);

        defaultCaption = new LabelEdit("Default Caption");
        defaultCaption.setReadOnly(true);
        defaultCaption.setText(getModel().getCaption());
        panel.add(defaultCaption);

        defaultURL = new LabelEdit("Default URL");
        defaultURL.setReadOnly(true);
        defaultURL.setText(getModel().getDefaultImageURL());
        panel.add(defaultURL);
    }

    @Override
    public void onAddChildren(){
    /*    System.out.println("--- in onAddChildren " + getClass().getName());
        ImageListDesigner imageListDesigner = new ImageListDesigner(getModel().getImages());
        imageListDesigner.setCaption("Image");
        imageListDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "ImageGroupOAC");
        imageListDesigner.getTNode().showAddChildButton("Add Image OAC");
    */
    }

    LabelEdit defaultCaption;
    LabelEdit defaultURL;

}
