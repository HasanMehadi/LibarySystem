package UI.MemberAdd;

import Models.Member;
import javafx.stage.Stage;
import AlertGenerator.AlertMaker;
import DataBase.DataBaseHandel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberController implements Initializable{

    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtphone;
    @FXML
    private JFXTextField txtusername;
    @FXML
    private JFXPasswordField txtpassword;

    private DataBaseHandel dataBaseHandel;

    private Boolean isInEditMode = false;
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBaseHandel = DataBaseHandel.getDataBaseHandelInstance();
    }

    @FXML
    public void saveMember(){

        String Id = txtId.getText();
        String Name = txtName.getText();
        String Email = txtEmail.getText();
        String Phone = txtphone.getText();
        String UserName = txtusername.getText();
        String Password = txtpassword.getText();
        String query="";
        if (isInEditMode) {
            handleUpdateMember();
            return;
        }
        if(!Id.isEmpty()||!Name.isEmpty()||!Email.isEmpty()||!Phone.isEmpty()||!UserName.isEmpty()||!Password.isEmpty()){
            query = "INSERT INTO MEMBER VALUES('"+Integer.parseInt(Id)+"','"+Name+"','"+Email+"','"+Phone+"','"+UserName+"','"+Password+"')";
            System.out.println(query);
        }else {

            AlertMaker.showErrorAlert("Member Save","Enter All Values");
        }

        if(dataBaseHandel.actionExecutioner(query)){

            AlertMaker.showInformationAlert("Member Save","Member Saved in Database");
            clearEntries();
        }else {

            AlertMaker.showErrorAlert("Member Save","Internal Error Occurred");
        }
    }
    @FXML
    public void cancelMember(){
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
    private void handleUpdateMember() {
        Member member = new Member(Integer.parseInt(txtId.getText()),txtName.getText(),txtEmail.getText(),txtphone.getText(),txtusername.getText(),txtpassword.getText());
        if (DataBaseHandel.getDataBaseHandelInstance().updateMember(member)) {
            AlertMaker.showInformationAlert("Success", "Member Updated");
            clearEntries();
        } else {
            AlertMaker.showErrorAlert("Failed", "Cant update member");
        }
    }
    public void infalteUI(Member member) {
        txtName.setText(member.getName());
        txtId.setText(Integer.toString(member.getId()));
        txtId.setEditable(false);
        txtphone.setText(member.getPhone());
        txtEmail.setText(member.getEmail());
        txtusername.setText(member.getUserName());
        txtpassword.setText(member.getPassword());

        isInEditMode = Boolean.TRUE;
    }
    private void clearEntries() {
        txtName.clear();
        txtId.clear();
        txtphone.clear();
        txtEmail.clear();
        txtusername.clear();
        txtpassword.clear();
    }

}
