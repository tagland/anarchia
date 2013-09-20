package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Label;
import us.anarchia.obj.Link;
import java.util.List;

public class ListDesigner<Model> extends Designer<List> {
    public ListDesigner(List list) {
        super(list);
        this.list = list;
        Label designerLabel = new Label();
        designerLabel.setText("List");
        panel.add(designerLabel);

        itemsList = new Label();
        itemsList.setText("List: 0 items");
        panel.add(itemsList);
    }

    Label itemsList;

    private String caption = "...";
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getCaption() {
        return caption;
    }

    public List<Model> list;

    public void onModelChanged() {
        itemsList.setText("List: "+list.size()+" items");
    }
    //TODO: this is still using Link ...
    public void onAddChildren(){
        System.out.println("--- in onAddChildren " + getClass().getName());
        for(Model link: list){ //websites){
            System.out.println("--- in onAddChildren in loop over links " + getClass().getName());
            LinkDesigner linkDesigner = new LinkDesigner((Link)link);
            linkDesigner.wireToTNode(null, getTNode().getContainingTreeItem(), caption);
        }
        onModelChanged();
    }
}
