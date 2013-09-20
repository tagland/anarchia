package us.anarchia.gwt.client.ui;

import us.anarchia.obj.Link;

import java.util.List;

public class LinkListDesigner extends ListDesigner<Link> {
    public LinkListDesigner(List list) {
        super(list);
    }
    public ContainerNode createChildContainerNode (){
        Link link = new Link();
        getModel().add(link);
        Designer<Link> linkDesigner = new LinkDesigner(link);
        linkDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), "Link");
        onModelChanged();
        return linkDesigner.getTNode();
    }
}
