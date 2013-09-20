package us.anarchia.gwt.client.ui;

import com.google.gwt.user.client.ui.Label;
import us.anarchia.obj.Copyright;
import us.anarchia.obj.Date;
import us.anarchia.obj.Image;

public class ImageDesigner extends Designer<Image> {
        public ImageDesigner(final Image image) {
            super(image);
            this.image = image;
            Label designerLabel = new Label();
            designerLabel.setText("Copyright");
            panel.add(designerLabel);

            imageUrl = new LabelEdit("Image URL"){
                 public void onStringValueChanged(String newValue){
                    image.setImgURL(newValue);
                }
            };
            panel.add(imageUrl);

            imageCaption = new LabelEdit("Image Caption"){
                 public void onStringValueChanged(String newValue){
                     image.setCaption(newValue);
                }
            };
            panel.add(imageCaption);
        }
        public void onModelChanged(){
            imageCaption.setText(image.getCaption());
            imageUrl.setText(image.getImgURL());
        }
        LabelEdit imageCaption;
        LabelEdit imageUrl;
        private Image image;
}