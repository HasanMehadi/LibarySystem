package AlertGenerator;

import javafx.scene.control.Alert;

public class AlertMaker {

    public static void showInformationAlert(String title,String content){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorAlert(String title,String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static Alert showConfirmationAlert(String title,String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.showAndWait();
        return alert;
    }
}
