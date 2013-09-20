package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Designer<Model> extends Composite {
    public Designer(Model m) {
        createPanel();
        initWidget(panel);
        createGUI();
        this.model = m;
    }

    protected void createPanel() {
        panel = new VerticalPanel();
    }

    protected Panel panel;

    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model m) {
        this.model = m;
        onModelChanged();
    }

    private TNode tnode;

    public TNode getTNode() {
        return tnode;
    }

    public void setTNode(TNode tnode) {
        this.tnode = tnode;
    }

    protected void createGUI() {
    }

    public void onModelChanged() {
    }

    public ContainerNode createChildContainerNode (){
        return new ContainerNode();
    }

     public void onAddChildren(){
         System.out.println("--- empty method in onAddChildren " + getClass().getName());
     }

    static int i =0 ;

     public TNode wireToTNode(Tree tree, TreeItem treeItem, String aCaption){
        TNode aTNode = new TNode();
        aTNode.setCaption(aCaption+""+i); //"Author");
         i++;
        setTNode(aTNode);
        aTNode.setDesigner(this); //new AuthorDesigner(author));
         System.out.println("--- in wireToNode "+getClass().getName());

         //TODO there is some issue here with the treeItem and the one ContainerNode.createTreeItem creates......



        TreeItem authorItem = ContainerNode.createTreeItem(aTNode);
        if (tree!=null){     //this is only right for Root:
            tree.addItem(authorItem);
        } else {               //this is right for TreeItems
            treeItem.addItem(authorItem);
        }
         System.out.println("--- in wireToNode, calling onAddChildren " + getClass().getName());
        onAddChildren();
        onModelChanged();
        return aTNode;
    }

}
