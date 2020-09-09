package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;

import java.util.concurrent.atomic.AtomicInteger;

public class addSRGridPaneController {

    @FXML
    GridPane addSRGridPane=new GridPane();

    @FXML
    TextField srNameTextField=new TextField(), srCompanyNameTextfield=new TextField(), srPhoneNumberTextfield=new TextField(),
            srEmailTextField=new TextField(), srAddressTextField=new TextField();
/*    @FXML
    ComboBox srCompanyNameComboBox=new ComboBox();
    */
    @FXML
    Label warningLabel=new Label(), srNameWarningLabel=new Label(), srCompanyNameWarningLabel=new Label(),
            srPhoneWarningLabel=new Label();
    @FXML
    Button saveButton=new Button();

    protected database db;
    private String username;
    @FXML
    public void initialize()
    {
        db=new database();
        warningLabel.setText("");
        srNameWarningLabel.setText("");
        srCompanyNameWarningLabel.setText("");
        srPhoneWarningLabel.setText("");
    }

    protected void setDatabase(database dt){
        try{
            db=dt;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void setUserName(String username){
        try{
            this.username=username;
            System.out.println("add SR Contact : "+username);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected database getDatabase(){
        return db;
    }
    private boolean containIllegalCharacters(String str){
        try{
            AtomicInteger length= new AtomicInteger(str.length());
            int charInt;
            for(int i = 0; i< length.get(); i++){
                charInt=(int)str.charAt(i);
                if(!(((charInt>47)&&(charInt<58)) || ((charInt>64)&&(charInt<91)) || ((charInt>96)&&(charInt<122)) || (charInt==32))){
                    warningLabel.setText("only A-Z, a-z and 0-9 are allowed");
                    warningLabel.setTextFill(Color.RED);
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            warningLabel.setText("Exception Occur Please Contact Admin");
            warningLabel.setTextFill(Color.RED);
            return true;
        }
    }

    @FXML
    protected void setSrCompanyName(){
        try{
            String [] companyNames=db.companyNames();

            TextFields.bindAutoCompletion(srCompanyNameTextfield, companyNames);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
        Method Name: checkSRPhone
        Parameter  : N/A
        Purpose    : Check whether the company phone number is valid or not
        Return     : boolean true [if Phone number is valid] or false [if phone number is invalid]
    */
    @FXML
    protected boolean checkSRPhone(){
        try{
            String phoneNumber=srPhoneNumberTextfield.getText();
            int length=phoneNumber.length(), charInt;
            boolean illegalNumber=false;
            for(int i=0; i<length; i++){
                charInt=(int)phoneNumber.charAt(i);
                if((charInt>47 && charInt<58) || (i==0 && charInt==43)){
                    illegalNumber=false;
                }else {
                    illegalNumber=true;
                    break;
                }
            }
            if(illegalNumber){
                srPhoneWarningLabel.setText("Illegal Phone Number");
                srPhoneWarningLabel.setTextFill(Color.RED);
                return false;
            } else if(!(length<15)){
                srPhoneWarningLabel.setText("Number is too long");
                srPhoneWarningLabel.setTextFill(Color.RED);
                return false;
            }else{
                srPhoneWarningLabel.setText("Phone can be saved");
                srPhoneWarningLabel.setTextFill(Color.GREEN);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkSRName(){
        boolean acceptableName=false;
        try{
            String name=srNameTextField.getText();
            if(containIllegalCharacters(name)){
                srNameWarningLabel.setText("Illegal Character Found");
                srNameWarningLabel.setTextFill(Color.RED);

            }else{
                srNameWarningLabel.setText("");
                srNameWarningLabel.setTextFill(Color.GREEN);
                acceptableName=true;
            }

            return acceptableName;
        }catch (Exception e){
            e.printStackTrace();
            srNameWarningLabel.setText("An Error Occur");
            srNameWarningLabel.setTextFill(Color.RED);
            return false;
        }
    }

    private boolean checkCompanyName(){
        boolean acceptable=false;
        try{
            String cName=srCompanyNameTextfield.getText();
            if(db.companyNameExist(cName)){
                srCompanyNameWarningLabel.setText("Company Name Acceptable");
                srCompanyNameWarningLabel.setTextFill(Color.GREEN);
                acceptable=true;
            }else{
                srCompanyNameWarningLabel.setText("Company Name not Stored");
                srCompanyNameWarningLabel.setTextFill(Color.RED);
                acceptable=false;
            }
            return acceptable;
        }catch (Exception e){
            e.printStackTrace();
            srCompanyNameWarningLabel.setText("An Error Occur");
            srCompanyNameWarningLabel.setTextFill(Color.RED);
            return false;
        }
    }
    @FXML
    protected void setSaveButton(){
        try{
            String SRName, companyName, phoneNumber, emailAddress, address;

            if(checkSRName()==true && checkSRPhone()==true && checkCompanyName()==true){
                SRName=srNameTextField.getText();
                companyName=srCompanyNameTextfield.getText();
                phoneNumber=srPhoneNumberTextfield.getText();
                emailAddress=srEmailTextField.getText();
                address=srAddressTextField.getText();
                if(db.addNewSrContact(SRName, companyName, phoneNumber, emailAddress, address, username)==true){
                    warningLabel.setText("SR contact information saved");
                    warningLabel.setTextFill(Color.GREEN);
                    Thread.sleep(100);
                    srNameWarningLabel.setText("");
                    srCompanyNameWarningLabel.setText("");
                    srPhoneWarningLabel.setText("");
                    srNameTextField.setText("");
                    srCompanyNameTextfield.setText("");
                    srPhoneNumberTextfield.setText("");
                    srEmailTextField.setText("");
                    srAddressTextField.setText("");
                }else{
                    warningLabel.setText("ERROR : Could not save in database");
                    warningLabel.setTextFill(Color.RED);
                }
            }else{
                warningLabel.setText("One or more field giving warning");
                warningLabel.setTextFill(Color.RED);
            }


        }catch (Exception e){
            e.printStackTrace();
            warningLabel.setText("ERROR : Contact ADMIN");
            warningLabel.setTextFill(Color.RED);
        }
    }



}
