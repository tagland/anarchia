package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Label;
import us.anarchia.obj.Link;

public class LinkDesigner extends Designer<Link> {
    public LinkDesigner(Link link){
        super(link);
    }

    LabelEdit urlString;
    LabelEdit description;

    @Override
    public void createGUI() {
        Label designerLabel = new Label();
        designerLabel.setText("Website");
        panel.add(designerLabel);

        urlString = new LabelEdit("URL String") {
            public void onStringValueChanged(String newValue) {
                if (getModel() != null) {
                    getModel().urlString = newValue;
                }
            }
        };
        panel.add(urlString);

        description = new LabelEdit("Description") {
            public void onStringValueChanged(String newValue) {
                if (getModel() != null) {
                    getModel().description = newValue;
                }
            }
        };
        panel.add(description);

    }

    @Override
    public void onModelChanged() {
        urlString.setText(getModel().urlString);
        description.setText(getModel().description);
    }


}