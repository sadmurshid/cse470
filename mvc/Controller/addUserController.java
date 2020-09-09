package project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class addUserController {

    @FXML GridPane addUserGridPane;
    @FXML TextField nameTextField=new TextField();
    @FXML PasswordField passwordField=new PasswordField();
    @FXML PasswordField rePasswordField=new PasswordField();
    @FXML TextField phoneTextField=new TextField();
    @FXML TextField addressTextField=new TextField();
    @FXML TextField emailTextField=new TextField();
    @FXML ColorPicker backgroundColorPicker=new ColorPicker();
    @FXML Label warningLabelField=new Label("");
    @FXML Button saveButton=new Button();
    @FXML RadioButton inventoryYesRadio, inventoryNoRadio, batchYesRadio, batchNoRadio, orderYesRadio, orderNoRadio, debitsYesRadio,
                    debitsNoRadio, userTypeManagerRadio, userTypeSalesmanRadio;
    @FXML Label nameWarningLabel, passwordWarningLabel, rePasswordWarningLabel, phoneNumberWarningLabel, addressWarningLabel,
                emailWarningLabel;


    @FXML ToggleGroup inventoryAccess, batchAccess, orderAccess, debitsAccess, userType;

    database db=new database();

    public addUserController(){


    }

    private void setDefault(){
        nameTextField.setText("");
        passwordField.setText("");
        rePasswordField.setText("");
        phoneTextField.setText("");
        addressTextField.setText("");
        nameWarningLabel.setText("");
        passwordWarningLabel.setText("");
        rePasswordWarningLabel.setText("");
        phoneNumberWarningLabel.setText("");

        warningLabelField.setText("* items are mandatory");


    }

    @FXML
    private boolean nameExist()
    {
        try{
            String name=nameTextField.getText();

            if(containIllegalCharacters(name)){
                nameWarningLabel.setText("Illegal Character Found");
                nameWarningLabel.setTextFill(Color.RED);
                return true;
            }
            else if(name.length()<3 || name.length()>32) {
                nameWarningLabel.setText("MUST CONTAIN 3-32 LETTERS");
                nameWarningLabel.setTextFill(Color.RED);
                return true;
            }
            else if(db.nameExist(name))
            {
                nameWarningLabel.setText("NAME EXIST");
                nameWarningLabel.setTextFill(Color.RED);
                return true;
            }
            else{
                nameWarningLabel.setText("You Can Save This Name");
                nameWarningLabel.setTextFill(Color.GREEN);
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @FXML
    private boolean passwordWarning(){
        try{
            String pass=passwordField.getText();
            if(containIllegalCharacters(pass))
            {
                passwordWarningLabel.setText("Illegal Character Detected");
                passwordWarningLabel.setTextFill(Color.RED);
                return true;
            }
            else if(pass.length()>32 || pass.length()<6){
                passwordWarningLabel.setText("Password must contain 6-32 characters");
                passwordWarningLabel.setTextFill(Color.RED);
                return true;
            }
            else{
                passwordWarningLabel.setText("OK");
                passwordWarningLabel.setTextFill(Color.GREEN);
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    @FXML
    boolean rePasswordKeyPressed(){
        String passStr=passwordField.getText();
        String rePassStr=rePasswordField.getText();
        try{
            if(passStr.equals(rePassStr)){
                rePasswordWarningLabel.setText("Password match");
                rePasswordWarningLabel.setTextFill(Color.GREEN);
                return true;
            }
            rePasswordWarningLabel.setText("Password didn't match");
            rePasswordWarningLabel.setTextFill(Color.RED);
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private int inventoryValue(){
        try{

            if(inventoryYesRadio.isSelected() && !inventoryNoRadio.isSelected())
                return 1;
            else if (inventoryNoRadio.isSelected() && !inventoryYesRadio.isSelected())
                return 0;
            else
                return 96;
        }catch (Exception e){
            e.printStackTrace();
            return  90;
        }
    }

    private int batchValue(){
        try{

            if(batchYesRadio.isSelected() && !batchNoRadio.isSelected())
                return 1;
            else if (batchNoRadio.isSelected() && !batchYesRadio.isSelected())
                return 0;
            else
                return 97;

        }catch (Exception e){
            e.printStackTrace();
            return  90;
        }
    }

    private int orderValue(){
        try{

            if(orderYesRadio.isSelected() && !orderNoRadio.isSelected())
                return 1;
            else if (orderNoRadio.isSelected() && !orderYesRadio.isSelected())
                return 0;
            else
                return 98;

        }catch (Exception e){
            e.printStackTrace();
            return  90;
        }
    }

    private int debitsValue(){
        try{

            if(debitsYesRadio.isSelected() && !debitsNoRadio.isSelected())
                return 1;
            else if (debitsNoRadio.isSelected() && !debitsYesRadio.isSelected())
                return 0;
            else
                return 99;

        }catch (Exception e){
            e.printStackTrace();
            return  90;
        }
    }

    private String userTypeValue(){
        try{
            if(userTypeManagerRadio.isSelected() && !userTypeSalesmanRadio.isSelected())
                return "MANAGER";
            else if(!userTypeManagerRadio.isSelected() && userTypeSalesmanRadio.isSelected())
                return "SALESMAN";
            else
                return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean containIllegalCharacters(String str){
        try{
            int length=str.length(), charInt;
            for(int i=0; i<length; i++){
                charInt=(int)str.charAt(i);
//                System.out.println(charInt);
                if(!(((charInt>47)&&(charInt<58)) || ((charInt>64)&&(charInt<91)) || ((charInt>96)&&(charInt<122)) || (charInt==32))){
//                    System.out.println("Illegal Char Found : "+str.charAt(i));
                    warningLabelField.setText("only A-Z, a-z and 0-9 are allowed");
                    warningLabelField.setTextFill(Color.RED);
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            warningLabelField.setText("Exception Occur Please Contact Admin");
            warningLabelField.setTextFill(Color.RED);
            return true;
        }
    }

    private String getColor(String str){
        String color=null;
        try{
            color=str.substring(2, 8);
//            System.out.println(color);
        }catch (Exception e){
            e.printStackTrace();
        }


        return color;
    }

    @FXML
    void saveButtonClicked(){
        try {
            String name = nameTextField.getText();
            String password = passwordField.getText();
            String phone_number = phoneTextField.getText();
            String address = addressTextField.getText();
            String email = emailTextField.getText();

            int inventoryInt=inventoryValue(), batchInt=batchValue(), orderInt=orderValue(), debitsInt=debitsValue();
            String userType=userTypeValue();

            if(nameExist()){
                warningLabelField.setText("Name warning");
                warningLabelField.setTextFill(Color.RED);
            }
            else if(passwordWarning() || !rePasswordKeyPressed()){
                warningLabelField.setText("Password warning");
                warningLabelField.setTextFill(Color.RED);
            }
            else if(inventoryInt==90 || batchInt==90 || orderInt==90 || debitsInt==90){
                warningLabelField.setText("An Exception Occur On Access Level Please Contact ADMIN");
                warningLabelField.setTextFill(Color.RED);
            }
            else if(inventoryInt==96 || batchInt==97 || orderInt==98 || debitsInt==99){
                warningLabelField.setText("One or more Access option wasn't chose");
                warningLabelField.setTextFill(Color.RED);
            }
            else{
                String temp=backgroundColorPicker.getValue().toString();
                System.out.println(temp);
                String bgColor="#"+getColor(temp);
//                System.out.println(bgColor);
//                String bgColor="#"+Integer.toHexString(backgroundColorPicker.getValue().hashCode()).substring(0, 8).toUpperCase();
//                System.out.println(bgColor+"  "+backgroundColorPicker);


                if(db.addUser( name, password, phone_number, address, email, bgColor, inventoryInt, batchInt, orderInt,
                        debitsInt, 1, userType)) {
                    System.out.println("new user successfully added");
                    warningLabelField.setText("new user added successfully");
                    warningLabelField.setTextFill(Color.GREEN);
                    Thread.sleep(1000);
                    setDefault();
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void initialProperty()
    {
    }



    @FXML
    public void initialize()
    {
        warningLabelField.setText(" ");


    }

}
