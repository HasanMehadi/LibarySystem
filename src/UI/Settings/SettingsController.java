package UI.Settings;

import Models.MasterSetting;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{
    @FXML
    public JFXTextField txtfineday;
    @FXML
    public JFXTextField txtfineamount;
    @FXML
    public JFXTextField txtusername;
    @FXML
    public JFXPasswordField txtpassword;
    @FXML
    public JFXButton btnsave;
    @FXML
    public JFXButton btncancel;
    @FXML
    public void saveaction(ActionEvent actionEvent) {

        Preferences preferences = new Preferences();
        MasterSetting setting = preferences.getMasterSetting();
        setting.setnDaywithoutfine(Integer.parseInt(txtfineday.getText()));
        setting.setFineperday(Float.parseFloat(txtfineamount.getText()));
        setting.setUsername(txtusername.getText());
        setting.setPassword(txtpassword.getText());
        preferences.setMasterSetting(setting);
    }
    @FXML
    public void cancelaction(ActionEvent actionEvent) {
        ((Stage)txtfineday.getScene().getWindow()).close();
    }

    public void initDefaultValues(){
        MasterSetting masterSetting = new MasterSetting();
        txtfineday.setText(String.valueOf(masterSetting.getnDaywithoutfine()));
        txtfineamount.setText(String.valueOf(masterSetting.getFineperday()));
        txtusername.setText(String.valueOf(masterSetting.getUsername()));
        txtpassword.setText(String .valueOf(masterSetting.getPassword()));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDefaultValues();
    }
}
