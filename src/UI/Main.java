package UI;

import BasicAnimation.AnimationGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage curStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnimationGenerator.setRate(3.0);

        showStage(primaryStage,"VisuAlgo","Welcome");
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void showStage(Stage stage,String title,String name) throws Exception{
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/resources/logo.PNG")));
        stage.setResizable(false);
        stage.setTitle(title);
        FXMLLoader loader=new FXMLLoader(Main.class.getResource(name+"Scene.fxml"));
        AnchorPane root=loader.load();
        BasicController controller=loader.getController();
        controller.setMain(this);
        stage.setScene(new Scene(root));
        curStage=stage;

        stage.show();
    }

    public Stage getCurStage(){
        return curStage;
    }

    public void setCurStage(Stage stage){
        curStage=stage;
    }

}