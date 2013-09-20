package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

public class MasterDetailTree extends Composite {

    private Grid grid;
    private MasterTree masterTree;
    public MasterTree getMasterTree(){
        return masterTree;
    }
    private Panel panel;

    public void initGUI() {
        panel = new HorizontalPanel();
        grid = new Grid(1, 2);  //row,col
        this.masterTree = new MasterTree(this);
        grid.setWidget(0, 0, masterTree);   //row,col, zero-based.
        // You can use the CellFormatter to affect the layout of the grid's cells.
        grid.getCellFormatter().setWidth(0, 0, "256px");
        grid.getCellFormatter().setWidth(0, 1, "100%");
        panel.add(grid);
        initWidget(panel);
    }

    public void setDesigner(Composite designer) {
        grid.setWidget(0, 1, designer);
    }
}
