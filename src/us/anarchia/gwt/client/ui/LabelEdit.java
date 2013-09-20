package us.anarchia.gwt.client.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class LabelEdit extends Composite implements ValueChangeHandler<String> {
    public LabelEdit(String caption) {
        Panel panel = new HorizontalPanel();
        initWidget(panel);

        label = new Label();
        label.setText(caption + ": ");
        panel.add(label);
        textBox = new TextBox();
        panel.add(textBox);
        textBox.addValueChangeHandler(this);
    }

    Label label;
    TextBox textBox;

    public TextBox getTextBox() {
        return textBox;
    }
    public String getText(){
        return textBox.getText();
    }
    public void setText(String text){
        textBox.setText(text);
    }

    public void setCaption(String caption) {
        label.setText(caption);
    }

    public void setReadOnly(boolean readOnly){
        textBox.setReadOnly(readOnly);
    }

    public void onValueChange(ValueChangeEvent<String> event){
        onStringValueChanged(event.getValue());
    }

    public void onStringValueChanged(String newValue){
    }
}
