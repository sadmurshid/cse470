package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicInteger;

public class addCompanyGridPaneController {

    @FXML GridPane addCompanyGridPane=new GridPane();
    @FXML TextField companyNameTextField=new TextField(), companyPhoneTextField=new TextField(), companyEmailTextField=new TextField(),
            companyAddressTextField=new TextField();
    @FXML Label warningLabel=new Label(),companyNameWarningLabel=new Label("") ,companyPhoneWarningLabel=new Label("") ,
            companyEmailWarningLabel =new Label("");
    @FXML Button saveButton=new Button();

    protected database db;
    private String username;
    @FXML
    public void initialize()
    {
        db=new database();
        warningLabel.setText("");
        companyNameWarningLabel.setText("");
        companyPhoneWarningLabel.setText("");
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
            System.out.println("add company "+username);
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
    /*
        Method Name: checkCompanyName
        Parameter  : N/A
        Purpose    : Check whether the company name contain illegal character or exist in database
        Return     : boolean true [if no error for this name] or false [if error with this name]
    */
    @FXML
    protected boolean checkCompanyName(){
        try{

            String companyName=companyNameTextField.getText();
            System.out.println(companyName);

            boolean illegalCharacter=containIllegalCharacters(companyName), nameExist=checkCompanyNameExistence(companyName);

            if(illegalCharacter)
            {
                companyNameWarningLabel.setText("Illegal Character Found");
                companyNameWarningLabel.setTextFill(Color.RED);
                return false;
            }else if(nameExist){
                if(!illegalCharacter){
                    warningLabel.setText("");
                }
                return false;
            }
            else{
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private boolean checkCompanyNameExistence(String companyName){
        try{
            if(db.companyNameExist(companyName)){
                companyNameWarningLabel.setText("Company Name Exists");
                companyNameWarningLabel.setTextFill(Color.RED);
            return true;
            }else{
                companyNameWarningLabel.setText("Company Name Available");
                companyNameWarningLabel.setTextFill(Color.GREEN);
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }
    /*
        Method Name: checkCompanyPhone
        Parameter  : N/A
        Purpose    : Check whether the company phone number is valid or not
        Return     : boolean true [if Phone number is valid] or false [if phone number is invalid]
    */
    @FXML
    protected boolean checkCompanyPhone(){
        try{
            String phoneNumber=companyPhoneTextField.getText();
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
                companyPhoneWarningLabel.setText("Illegal Phone Number");
                companyNameWarningLabel.setTextFill(Color.RED);
                return false;
            } else if(!(length<15)){
                companyPhoneWarningLabel.setText("Number is too long");
                companyNameWarningLabel.setTextFill(Color.RED);
                return false;
            }else{
                companyPhoneWarningLabel.setText("Phone can be saved");
                companyNameWarningLabel.setTextFill(Color.GREEN);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void saveButtonClicked(){
        try{
            String companyName, phoneNumber="", companyEmail="", companyAddress="";
            if(checkCompanyName()) {
                companyName = companyNameTextField.getText();
                if(checkCompanyPhone() && companyPhoneTextField.getText()!=null)
                    phoneNumber=companyPhoneTextField.getText();
                if(companyEmailTextField.getText()!=null)
                    companyEmail=companyEmailTextField.getText();
                if(companyAddressTextField.getText()!=null)
                    companyAddress=companyAddressTextField.getText();

                if(db.addNewCompany(companyName, phoneNumber, companyEmail, companyAddress, username))
                {
                    warningLabel.setText("New Company Name Added Successfully");
                    warningLabel.setTextFill(Color.GREEN);
                    companyNameTextField.setText("");
                    companyPhoneTextField.setText("");
                    companyEmailTextField.setText("");
                    companyAddressTextField.setText("");
                    companyNameWarningLabel.setText("");
                    companyPhoneWarningLabel.setText("");
                    companyEmailWarningLabel.setText("");
                }
            }else{
                warningLabel.setText("Error occur contact admin");
                warningLabel.setTextFill(Color.RED);
            }
        }catch (Exception e){
            e.printStackTrace();
            warningLabel.setText("Exception occur contact admin");
            warningLabel.setTextFill(Color.RED);
        }
    }
}
