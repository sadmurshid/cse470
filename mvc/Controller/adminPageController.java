package project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;




public class adminPageController  {

    @FXML private BorderPane adminBorderPane;
    @FXML HBox adminTopHbox;
    @FXML VBox adminLeftVbox;
    @FXML GridPane adminCenter;
    @FXML Button settingsButton, logoutButton, addUserButton, removeUserButton, inventoryButton, ordersButton;
    @FXML Button batchButton, debitsButton, accountsButton , basicButton;



    private Stage window;

    public adminPageController() {
        try{
            settingsButton=new Button("SETTINGS");
            addUserButton=new Button("ADD USER");

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

//    public void change(){
//        addUserButton.setText("ADD USER...");
//    }

    @FXML
    public void initialize()
    {
        database db=new database();
        window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("SYSTEM ADMIN");

//        inventoryButton.setText("inventory");

        adminBorderPane.setStyle("-fx-background-color: "+db.userTableGetBackgroundColor("SYSTEM_ADMIN")+";");

        addUserButton.setOnAction(event -> {
            try {
                GridPane pane = FXMLLoader.load(getClass().getResource("addUserPanel.fxml"));
                adminCenter=pane;
                adminBorderPane.setCenter(adminCenter);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        basicButton.setOnAction(event -> {
            try {
                GridPane pane = FXMLLoader.load(getClass().getResource("basicGridPane.fxml"));
                adminCenter=pane;
                adminBorderPane.setCenter(adminCenter);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}
