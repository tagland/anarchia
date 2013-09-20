package us.anarchia.gwt.client;

import us.anarchia.gwt.client.ui.AuthorDesigner;
import us.anarchia.gwt.client.ui.MasterDetailTree;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import us.anarchia.obj.Author;
import us.anarchia.obj.Frame;

public class AnarchiaEntry implements EntryPoint {
    private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

    private final AnarchiaServiceAsync anarchiaService = GWT.create(AnarchiaService.class);

    private Author rootAuthor;

    private MasterDetailTree masterDetailTree;

    private void createMasterDetailTree() {
        if (masterDetailTree == null) {
            masterDetailTree = new MasterDetailTree();
            masterDetailTree.initGUI();
            RootPanel.get().add(masterDetailTree);
        }
    }

    protected void resetMasterDetailTree(Author author) {
        System.out.println("--- in resetMasterDetailTree " + getClass().getName());
        masterDetailTree.getMasterTree().removeItems();
        AuthorDesigner authorDesigner = new AuthorDesigner(author);
        authorDesigner.wireToTNode(masterDetailTree.getMasterTree(), null, "Author");
    }

    protected void resetMasterDetailTree(Frame frame) {
        //TODO: make this work like   resetMasterDetailTree(Author)
    }

    public void onModuleLoad() {
        final Button sendAuthorButton = new Button("Send Author");
        final Button sendFrameButton = new Button("Send Frame");
        final Button getAuthorButton = new Button("Get Author");
        final TextBox nameField = new TextBox();
        nameField.setText("Laramie");
        final Label errorLabel = new Label();

        sendAuthorButton.addStyleName("sendAuthorButton");

        // Add the nameField and sendAuthorButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("nameFieldContainer").add(nameField);
        RootPanel.get("sendButtonContainer").add(getAuthorButton);
        RootPanel.get("sendButtonContainer").add(sendAuthorButton);
        RootPanel.get("sendButtonContainer").add(sendFrameButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);
        nameField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        //==============================================================

        createMasterDetailTree();

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendAuthorButton.setEnabled(true);
                sendAuthorButton.setFocus(true);
            }
        });

        // Create a handler for the sendAuthorButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {
            public void onClick(ClickEvent event) {
                Object source = event.getSource();
                if (source.equals(sendAuthorButton)) {
                    storeAuthor();
                } else if (source.equals(getAuthorButton)) {
                    Author authorQuery = new Author();
                    authorQuery.setFamiliarName(nameField.getValue());
                    getAuthor(authorQuery);
                } else if (source.equals(sendFrameButton)) {
                    storeFrame();
                }
            }

            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    storeAuthor();
                }
            }

            public void showResponse(String html, boolean succeeded) {
                dialogBox.setText("Remote Procedure Call - " + (succeeded ? "Success" : "Failure"));
                if (succeeded) {
                    serverResponseLabel.removeStyleName("serverResponseLabelError");
                } else {
                    serverResponseLabel.addStyleName("serverResponseLabelError");
                }
                serverResponseLabel.setHTML(html);
                dialogBox.center();
                closeButton.setFocus(true);
            }

            private void storeAuthor() {
                errorLabel.setText("");
                sendAuthorButton.setEnabled(false);
                serverResponseLabel.setText("");

                anarchiaService.storeAuthor(rootAuthor, new AsyncCallback<Author>() {
                    public void onFailure(Throwable caught) {
                        showResponse(SERVER_ERROR, false);
                    }

                    public void onSuccess(Author result) {
                        rootAuthor = result;
                        resetMasterDetailTree(rootAuthor);
                        showResponse(result.toString(), true);
                    }
                });
            }

            private void getAuthor(Author authorQuery) {
                anarchiaService.getAuthor(authorQuery, new AsyncCallback<Author>() {
                    public void onFailure(Throwable caught) {
                        showResponse(SERVER_ERROR, false);
                    }
                    public void onSuccess(Author result) {
                        rootAuthor = result;
                        resetMasterDetailTree(rootAuthor);
                        showResponse(result.toString(), true);
                    }
                });
            }

            private void storeFrame() {
                Frame frame = AnarchiaModel.newFrame();
                Author author = AnarchiaModel.newAuthor();
                anarchiaService.storeFrame(author, frame, new AsyncCallback<Frame>() {
                    public void onFailure(Throwable caught) {
                        showResponse(SERVER_ERROR, false);
                    }
                    public void onSuccess(Frame result) {
                        resetMasterDetailTree(result);
                        showResponse(result.toString(), true);
                    }
                });
            }
        }
        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendAuthorButton.addClickHandler(handler);
        getAuthorButton.addClickHandler(handler);
        sendFrameButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);
    }
}

//============ OLD =======================================

/*
I think we could do this:

 TREE                 | EDIT PANEL
-----------------------------------------------------
Author               |     Selected Field: Author :: Frames :: frame-2
 +-- fields          |
 +-- metadata        |     Panels: [+]
 +-- Frames [+]      |     ID:
   +-- frame-1       |     Title:
==>  +-- frame-2       |     Description:
   +-- frame-3       |     Contributors:
---------------------|--------------------------------
 [Save All]          |     [Save This Edit Panel]
------------------------------------------------------

 Then, show all the changed tree items (edit panels) in another color, and when they click "Save All" all the edit panels get saved, and tree is updated.

 So you will need Edit Panels for Author, Metadata, Frame, Panel
 Anything that is a List or Collection would get a [+] button, everything else gets a field, or one entry in the tree.
*/
//==============================================================

/*     REMOVED after version 7.

TreeItem root = new TreeItem("root");
root.addItem("item0");
root.addItem("item1");
root.addItem("item2");

// Add a CheckBox to the tree
TreeItem item = new TreeItem(new CheckBox("item3"));
root.addItem(item);

ContainerNode cn = new ContainerNode();
cn.setCaption("A test ContainerNode");
cn.initGUI();
TreeItem item2 = ContainerNode.createTreeItem(cn);
root.addItem(item2);

TreeItem item3 = new TreeItem(new TextBox());
item2.addItem(item3);


Tree t = new Tree();
t.addItem(root);

// Add it to the root panel.
RootPanel.get().add(t);
*/

/*
                anarchiaService.greetServer(textToServer,
                        new AsyncCallback<String>() {
                            public void onFailure(Throwable caught) {
                                // Show the RPC error message to the user
                                dialogBox.setText("Remote Procedure Call - Failure");
                                serverResponseLabel.addStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML(SERVER_ERROR);
                                dialogBox.center();
                                closeButton.setFocus(true);
                            }

                            public void onSuccess(String result) {
                                dialogBox.setText("Remote Procedure Call");
                                serverResponseLabel.removeStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML(result);
                                dialogBox.center();
                                closeButton.setFocus(true);
                            }
                        });
                        */
