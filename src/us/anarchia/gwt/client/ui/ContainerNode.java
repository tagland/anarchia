package us.anarchia.gwt.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TreeItem;

public class ContainerNode extends Composite {

    static int tid = 0;

    public static TreeItem createTreeItem(ContainerNode node) {
        TreeItem item = new TreeItem(node);
        item.setTitle("TreeItem_"+(tid++)+"_createTreeItem");
        node.setContainingTreeItem(item);
        item.addStyleName("anarchiaTreeItem");
        if (item.getText() == null || item.getText().length() == 0){
            item.setText("TreeItem...");
        }
        return item;
    }

    public ContainerNode() {
        // All composites must call initWidget() in their constructors.
        initWidget(panel);
        label = new Label();
        label.setText("caption");
        panel.add(label);
    }

    public ContainerNode(String caption) {
        // All composites must call initWidget() in their constructors.
        initWidget(panel);
        this.caption = caption;
        label = new Label();
        label.setText(caption);
        getPanel().add(label);
    }

    HorizontalPanel panel = new HorizontalPanel();
    private Label label;  //only used if subclass doesn't create its own UI.

    public Panel getPanel() {
        return panel;
    }

    //We have to hold a reference to this because getParent() is the Tree itself, not any of its items.
    private TreeItem containingTreeItem = null;

    public void setContainingTreeItem(TreeItem containingTreeItem) {
        this.containingTreeItem = containingTreeItem;
    }

    public TreeItem getContainingTreeItem() {
        return containingTreeItem;
    }

    public int getChildCount() {
        return getContainingTreeItem().getChildCount();
    }

    private String caption = "ContainerNode";

    public void setCaption(String caption) {
        this.caption = caption;
        if (ContainerNode.this.label != null) {
            this.label.setText(caption);
        }
    }

    public String getCaption() {
        return caption;
    }

    /**
     * Subclasses will get called with this when we have been inserted into a tree, so adding child container nodes is allowed.
     */
    public void onAddChildren() {
    }

    /**
     * Subclasses of Designer override Designer.createChildContainerNode() to provide the correct kind of ContainerNode that knows about its domain POJO.
     */
    public void addChild() {
        System.out.println("--- in addChild " + getClass().getName());
        ContainerNode cn = createChildContainerNode();
        TreeItem child = ContainerNode.createTreeItem(cn);
        getContainingTreeItem().addItem(child);
    }

    private Designer designer;

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    private Button addChildButton;

    public void showAddChildButton(String caption) {
        if (caption == null || caption.length() == 0) {
            addChildButton = null;
            return;
        }
        addChildButton = new Button(caption);
        addChildButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                addChild();
            }
        });
        getPanel().add(addChildButton);
    }

    public ContainerNode createChildContainerNode() {
        return getDesigner().createChildContainerNode();
    }

}
