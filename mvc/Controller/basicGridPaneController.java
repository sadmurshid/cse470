package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class basicGridPaneController {

    @FXML GridPane basicGridpane=new GridPane();
    @FXML Label shopnameLabel=new Label(), ownernameLabel=new Label(),
            addressLabel=new Label(), contactLabel=new Label(), warningLabel=new Label("");
    @FXML TextField shopnameTextfield=new TextField(),
            ownernameTextfield=new TextField(), addressTextfield=new TextField(), contactTextfield=new TextField();
    @FXML Button saveButton=new Button(), cancelButton=new Button();

    private database db;

    public basicGridPaneController(){

        db=new database();
    }


    @FXML
    public boolean saveButtonClicked()
    {
        boolean boo=false;
        try{
            String shopName=shopnameTextfield.getText(), ownerName=ownernameTextfield.getText(),
                    address=addressTextfield.getText(), contact=contactTextfield.getText();
            if(!checkString(shopName)){
                warningLabel.setText("This Shop name is not valid.");
            } else if(!checkString(ownerName)){
                warningLabel.setText("Please enter a valid name.");
            } else if(!checkString(address)){
                warningLabel.setText("Please enter a valid address.");
            }else if(!checkString(contact)){
                warningLabel.setText("contact info is not correct.");
            }else {
                if("Shop Name Wasn't Set.".equals(db.getShopName())){

                    if(db.setBasicInformation(shopName, ownerName, address, contact)){
                        warningLabel.setText("Update successful.");
                    }
                } else{
                    warningLabel.setText("Previous data have been deleted.");
                    if(db.setBasicInformation(shopName, ownerName, address, contact)){
                        warningLabel.setText("Update successful.");
                    }
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return boo;
    }

    private boolean checkString(String str){
        boolean boo=false;

        try{
            final String regex = "((\\w*|[']|[\\/]|\\s)+(\\s|[,]|[0-9])+(\\w*|[']|[\\/]|\\s))*";
            final String string = str;

            final Pattern pattern=Pattern.compile(regex);
            final Matcher matcher=pattern.matcher(string);

            if(matcher.find()){

                System.out.println("Full match: " + matcher.group(0));
                if(matcher.group(0).equals(str))
                    boo=true;
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    System.out.println("Group " + i + ": " + matcher.group(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  boo;
    }


}
