package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class inventoryController {

    @FXML
    Label productNameLabel, companyNameLabel, warningLabel;

    @FXML
    Button searchButton;

    @FXML
    RadioButton buyPriceRadioButton;


    database db;
    String username;

    private void setDefault(){

    }

    @FXML
    public void initialize(){
        try{
            setDefault();
        }catch (Exception e){
            e.printStackTrace();
        }

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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected database getDatabase(){
        return db;
    }
}
