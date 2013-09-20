package us.anarchia.gwt.client.ui;

import us.anarchia.obj.Image;
import us.anarchia.obj.Link;

import java.util.List;

public class ImageListDesigner extends ListDesigner<Image> {
    public ImageListDesigner(List list) {
        super(list);
    }
    public ContainerNode createChildContainerNode (){
        Image image = new Image();
        getModel().add(image);
        Designer<Image> imageDesigner = new ImageDesigner(image);
        imageDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "Image");
        onModelChanged();
        return imageDesigner.getTNode();
    }
}
