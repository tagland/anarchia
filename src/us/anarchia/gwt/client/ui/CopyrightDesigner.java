package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Label;
import us.anarchia.obj.Copyright;
import us.anarchia.obj.Date;

public class CopyrightDesigner extends Designer<Copyright> {
        public CopyrightDesigner(Copyright aCopyright) {
            super(aCopyright);
            this.copyright = aCopyright;
            Label designerLabel = new Label();
            designerLabel.setText("Copyright");
            panel.add(designerLabel);

            fullName = new LabelEdit("Copyright Name"){
                 public void onStringValueChanged(String newValue){
                    copyright.setName(newValue);
                }
            };
            panel.add(fullName);

            date = new LabelEdit("Copyright Date"){
                 public void onStringValueChanged(String newValue){
                     Date aDate = new Date();
                     aDate.setDate(newValue);
                    copyright.setDate(aDate);
                }
            };
            panel.add(date);

            licenseName = new LabelEdit("License Name"){
                 public void onStringValueChanged(String newValue){
                    copyright.setLicenseName(newValue);
                }
            };
            panel.add(licenseName);
        }
        public void onModelChanged(){
            licenseName.setText(copyright.getLicenseName());
            fullName.setText(copyright.getName());
            date.setText(copyright.getDate().toString());
        }
        LabelEdit licenseName;
        LabelEdit fullName;
        LabelEdit date;
        private Copyright copyright;
    }
