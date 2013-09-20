package us.anarchia.gwt.client.ui;

import us.anarchia.obj.Image;
import us.anarchia.obj.ImageGroup;

import java.util.List;

public class ImageGroupListDesigner extends ListDesigner<ImageGroup> {
    public ImageGroupListDesigner(List list) {
        super(list);
    }
    public ContainerNode createChildContainerNode (){
        System.out.println("--- in createChildContainerNode " + getClass().getName());
        ImageGroup imageGroup = new ImageGroup();
        getModel().add(imageGroup);
        Designer<ImageGroup> imageGroupDesigner = new ImageGroupDesigner(imageGroup);
        imageGroupDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "ImageGroupCC");
        onModelChanged();
        return imageGroupDesigner.getTNode();
    }
}
