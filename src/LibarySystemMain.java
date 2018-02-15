import DataBase.DataBaseHandel;
import ICONMANAGER.LibraryAssistantIconManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibarySystemMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        try{

            Parent root= FXMLLoader.load(getClass().getResource("UI/LOGINUI/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            LibraryAssistantIconManager.setStageIcon(primaryStage);
            primaryStage.show();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();

        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
