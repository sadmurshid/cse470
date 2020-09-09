package project;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.sql.DriverManager;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        try {
            Runtime.getRuntime().exec("cmd /c start H:\\Work\\\"Shop Managment System\"\\src\\database\\start.bat", null, new File("H:\\Work\\Shop Managment System\\src\\database\\"));
//        Thread.sleep(5000);
//            ProcessBuilder p = new ProcessBuilder();
//            System.out.println("Started EXE");
//            p.command("F:\\Work\\Shop Management System\\src\\database\\start.bat");
//            p.start();


            project.database d = new project.database("jdbc:mysql://localhost:3311", "root", "root");


            splash();

            this.primaryStage = primaryStage;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void splash()
    {
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("WELCOME");

            Parent root = FXMLLoader.load(getClass().getResource("splash.fxml"));

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> fadeOut.play());
            fadeOut.setOnFinished((e) -> {
                try {
                    window.close();
                    loginPage();
                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }

            });


            Scene scene = new Scene(root, 352, 339);
            window.setScene(scene);
            window.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loginPage()
    {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            primaryStage.setTitle("LOGIN");
            Scene scene = new Scene(root, 590, 455);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        new loginPageController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}