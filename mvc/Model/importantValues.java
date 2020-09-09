package project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class importantValues {

    private final  StringProperty userName=new SimpleStringProperty();

    protected  StringProperty textProperty(){
        return userName;
    }

    protected String getUserName() {
        return textProperty().get();
    }

    protected void setUserName(String userName) {
        textProperty().set(userName);
    }



}
