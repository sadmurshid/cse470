package project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class userPageController {

    public Button userPageLogOutButton;
    private Stage window, primaryStage;
    private String userName;
    private database db;

    @FXML private BorderPane borderPaneUserPage;
    @FXML private GridPane borderPaneCenterGridpane;
    @FXML private Button userPageInvoiceButton, userPageInventoryButton;
    @FXML private Label userPageUsernameLabel;
    @FXML private Button userPageBuyProductButton=new Button(), userPageNewProductButton=new Button(),
            userPageAddSrButton=new Button();

    public userPageController()
    {
        try{
            db=new database();
            primaryStage= new Stage();
            userPageUsernameLabel=new Label();
            borderPaneUserPage=new BorderPane();
            userPageInvoiceButton=new Button("INVOICE");
            userPageInventoryButton=new Button("INVENTORY");
            userPageBuyProductButton.setText("BUY PRODUCT");
            userPageNewProductButton.setText("NEW PRODUCT");



            System.out.println("user page controller constructor");


        } catch (Exception e){
            e.printStackTrace();
        }


    }


    protected boolean setUserName(String name)
    {
        try{
            userName=name;

            System.out.println(userName);
            userPageUsernameLabel.setText("USERNAME : "+userName);
//            System.out.println(db.userTableGetBackgroundColor(userName));
            String fx="-fx-background-color: "+db.userTableGetBackgroundColor(userName)+";";
//            System.out.println(fx);
            borderPaneUserPage.setStyle(fx);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void initialize()
    {

        window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(" USER ");

        userPageInvoiceButton.setText("INVOICE");
        userPageInventoryButton.setText("INVENTORY");


    }

    @FXML
    public void setUserPageInvoiceButton(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("invoiceView.fxml"));
            Parent root=loader.load();
            InvoiceViewController invoiceViewController = loader.getController();
            primaryStage.setTitle("INVOICE");
            Scene scene=new Scene(root, 1300, 700);
//            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            invoiceViewController.setDatabase(db);
            invoiceViewController.setUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void setUserPageNewProductButton(){
        try{
//            newProductGridPaneController gridPaneController=new newProductGridPaneController(db);
            FXMLLoader loader=new FXMLLoader(getClass().getResource("newProductGridPane.fxml"));
//            loader.setController(gridPaneController);
            GridPane pane = loader.load();
            borderPaneCenterGridpane=pane;
            borderPaneUserPage.setCenter(borderPaneCenterGridpane);

            newProductGridPaneController gridPaneController=loader.getController();

            gridPaneController.setDefault();
//            System.out.println(gridPaneController);
            gridPaneController.setDatabase(db);
//            System.out.println(db.getOwnerName());
//            System.out.println(gridPaneController.getDatabase());
            gridPaneController.setUserName(userName);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    public void setUserPageAddCompanyButton(){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("addCompanyGridPane.fxml"));
//            loader.setController(gridPaneController);
            GridPane pane = loader.load();
            borderPaneCenterGridpane=pane;
            borderPaneUserPage.setCenter(borderPaneCenterGridpane);

            addCompanyGridPaneController gridPaneController=loader.getController();
//            System.out.println(gridPaneController);
            gridPaneController.setDatabase(db);
//            System.out.println(db.getOwnerName());
//            System.out.println(gridPaneController.getDatabase());

            gridPaneController.setUserName(userName);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void setUserPageAddSrButton(){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("addSRGridPane.fxml"));
            GridPane pane = loader.load();
            borderPaneCenterGridpane=pane;
            borderPaneUserPage.setCenter(borderPaneCenterGridpane);

            addSRGridPaneController gridPaneController=loader.getController();
            gridPaneController.setDatabase(db);

            gridPaneController.setUserName(userName);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
