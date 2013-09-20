package us.anarchia.gwt.client.ui;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class MasterTree extends Tree  {
    public MasterTree(final MasterDetailTree owner) {
        this.owner = owner;
        addSelectionHandler(new SelectionHandler<TreeItem>() {
                public void onSelection(SelectionEvent<TreeItem> event) {
                    TreeItem treeItem = event.getSelectedItem();
                    Widget widget = treeItem.getWidget();
                    if (widget instanceof ContainerNode){
                        Composite designer = ((ContainerNode)widget) .getDesigner();
                        owner.setDesigner(designer);
                    }
                }
        });
    }

    private MasterDetailTree owner;
}
