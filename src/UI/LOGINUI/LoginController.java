package UI.LOGINUI;


import ICONMANAGER.LibraryAssistantIconManager;
import Models.MasterSetting;
import UI.Settings.Preferences;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    public JFXTextField txtusername;
    public JFXTextField txtpassword;
    public JFXButton btncancel;
    public JFXButton btnlogin;

    MasterSetting setting;
    public void loginaction(ActionEvent actionEvent) {

        String uname = txtusername.getText();
        String pass = DigestUtils.shaHex(txtpassword.getText());

        if(uname.equals(setting.getUsername())&& pass.equals(setting.getPassword())){
            ((Stage)txtusername.getScene().getWindow()).close();
            loadwindow("UI/MainUI/MainPageUI.fxml","LIBRARY SYSTEM");
        }else {
            txtusername.getStyleClass().add("wrong-credentials");
            txtpassword.getStyleClass().add("wrong-credentials");
        }
    }

    public void cancelaction(ActionEvent actionEvent) {
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setting = Preferences.getMasterSetting();

    }

    public void loadwindow(String location,String title){
        try{
            Parent root= FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            LibraryAssistantIconManager.setStageIcon(stage);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
