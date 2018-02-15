package UI.MainUI;

import AlertGenerator.AlertMaker;
import DataBase.DataBaseHandel;
import ICONMANAGER.LibraryAssistantIconManager;
import Models.Book;
import Models.Member;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainPageController implements Initializable{

    public StackPane stackpaneid;
    @FXML
    private HBox book_info_hbox;

    @FXML
    private HBox member_info_hbox;

    @FXML
    private HBox issue_button_hbox;

    @FXML
    private Text textBookName;

    @FXML
    private Text textAuthor;

    @FXML
    private Text textpublished;

    @FXML
    private Text textavailable;

    @FXML
    private Text textMemberName;

    @FXML
    private Text textEmail;

    @FXML
    private Text textPhone;

    @FXML
    private Text textUsername;

    @FXML
    private JFXTextField txtsearchbookbyid;

    @FXML
    private JFXTextField txtsearchmemberbyid;

    @FXML
    private JFXTextField renewbookid;

    @FXML
    private JFXListView<String> listview;

    DataBaseHandel dataBaseHandel;
    Boolean readyforsubmission = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXDepthManager.setDepth(book_info_hbox,2);
        JFXDepthManager.setDepth(member_info_hbox,2);
        JFXDepthManager.setDepth(issue_button_hbox,2);
        dataBaseHandel = DataBaseHandel.getDataBaseHandelInstance();


    }

    @FXML
    private void loadaddbook(ActionEvent event){
        loadwindow("UI/addbook/addBookUI.fxml","ADD BOOK");
    }

    @FXML
    private void loadaddmember(ActionEvent event) {
        loadwindow("UI/MemberAdd/add_member.fxml","ADD MEMBER");
    }

    @FXML
    private void loadallbook(ActionEvent event) {
        loadwindow("UI/AllLists/AllBookLists/book_list.fxml","ALL BOOK");
    }

    @FXML
    private void loadallmember(ActionEvent event){
        loadwindow("UI/AllLists/AllMemberLists/member_list.fxml","ALL MEMBER");
    }

    @FXML
    private void loadsearchbook(ActionEvent event){

        /*loadwindow("UI/MemberAdd/add_member.fxml","SEARCH BOOK");*/
    }

    @FXML
    private void loadsearchmember(ActionEvent event){

        /*loadwindow("UI/MemberAdd/add_member.fxml","SEARCH MEMBER");*/
    }
    @FXML
    private void loadissuedbooks(ActionEvent event){

        /*loadwindow("UI/MemberAdd/add_member.fxml","ISSUED BOOK");*/
    }

    @FXML
    private void loadsetting(ActionEvent event){
        loadwindow("UI/Settings/settings.fxml","SETTINGS");
    }

    @FXML
    private void loadBookInfo(ActionEvent event){
        String bookid = txtsearchbookbyid.getText();
        String query = "SELECT * FROM BOOK WHERE NAME='"+bookid+"'";
        ResultSet resultSet = dataBaseHandel.queryExeutioner(query);
        Boolean flag= false;
        try{
            while (resultSet.next()){
                String title = resultSet.getNString("TITLE");
                String author = resultSet.getNString("AUTHOR");
                String published = resultSet.getNString("PUBLISHED");
                int avialible = resultSet.getInt("ISAVAILABE");

                textBookName.setText(title);
                textAuthor.setText(author);
                textpublished.setText(published);
                if(avialible==1){
                    textavailable.setText("Available");
                }else {
                    textavailable.setText("Not Available");
                }
                flag= true;

            }

            if(flag==false){
                txtsearchbookbyid.setText("No Book Found");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void loadMemberInfo(ActionEvent event){
        String memberid = txtsearchmemberbyid.getText();
        String query = "SELECT * FROM MEMBER WHERE ID='"+memberid+"'";
        ResultSet resultSet = dataBaseHandel.queryExeutioner(query);
        Boolean flag= false;

        try{
            while (resultSet.next()){
                String name = resultSet.getNString("Name");
                String email = resultSet.getNString("Email");
                String phone = resultSet.getNString("Phone");
                String username = resultSet.getNString("UserName");

                textUsername.setText(username);
                textMemberName.setText(name);
                textEmail.setText(email);
                textPhone.setText(phone);
                flag = true;
            }

            if(flag==false){
                txtsearchmemberbyid.setText("No Member Found");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void loadIssueOparation(ActionEvent event){
        String memberid= txtsearchmemberbyid.getText();
        String bookid = txtsearchmemberbyid.getText();

        Alert alert = AlertMaker.showConfirmationAlert("ISSUE BOOK OPERATION","Are you Sure to Issue Book "+textBookName.getText()+" to "+textMemberName.getText()+" ?");

        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            String query="select Count(Id) from ISSUE";
            int count=0;
            try {
                ResultSet rs = dataBaseHandel.queryExeutioner(query);

                while (rs.next()) {
                    count = rs.getInt("Count(id)");
                }

                String query1 = "INSERT INTO ISSUE VALUES('" + count + 1 + "','" + Integer.parseInt(txtsearchbookbyid.getText()) + "','" + Integer.parseInt(txtsearchmemberbyid.getText()) + "','sysdate','" + 1 + "')";
                String query2 = "UPDATE BOOK SET ISAVAILABE ='" + 0 + "' where id='" + Integer.parseInt(txtsearchbookbyid.getText()) + "'";

                if (dataBaseHandel.actionExecutioner(query1) && dataBaseHandel.actionExecutioner(query2)) {

                    AlertMaker.showInformationAlert("ISSUE BOOK OPERATION","Issued Book " + textBookName.getText() + " to " + textMemberName.getText() + ".");
                } else {

                    AlertMaker.showErrorAlert("ISSUE BOOK OPERATION","ISSUE FAILED!!");
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void renewsubmissionbook(ActionEvent event){

        ObservableList<String> issueDate = FXCollections.observableArrayList();
        readyforsubmission = false;
        String bookid = renewbookid.getText();
        String query1 ="SELECT * FORM ISSUE WHERE BOOKID='"+Integer.parseInt(bookid)+"'";
        try{
            ResultSet resultSet1 = dataBaseHandel.queryExeutioner(query1);
            while(resultSet1.next()){
                String Id = Integer.toString(resultSet1.getInt("ID"));
                int bookId = resultSet1.getInt("BOOKID");
                int memberId= resultSet1.getInt("MEMBERID");
                Timestamp issuetime = resultSet1.getTimestamp("ISSUETIME");
                int renewcount = resultSet1.getInt("RENEW_COUNT");

                issueDate.add("Issue Id "+Id);
                issueDate.add("Book Id "+Integer.toString(bookId));
                issueDate.add("Member Id "+Integer.toString(memberId));
                issueDate.add("Issue Date "+new SimpleDateFormat("dd/MMM/yyyy").format(issuetime));
                issueDate.add("Renew "+Integer.toString(renewcount));

                String query2 ="SELECT * FROM BOOK WHERE ID= '"+bookid+"'";

                ResultSet resultSet2 = dataBaseHandel.queryExeutioner(query2);
                while (resultSet2.next()){
                    String author = resultSet2.getNString("ISAVAILABE");
                    String title = resultSet2.getNString("TITLE");
                    String published = resultSet2.getNString("PUBLISHED");
                    int available = resultSet2.getInt("ISAVAILABE");

                    issueDate.add("Author "+author);
                    issueDate.add("Title"+title);
                    issueDate.add("Publish "+published);
                    issueDate.add("Available "+Integer.toString(available));
                }

                String query3 ="SELECT * FROM MEMBER WHERE Id='"+memberId+"'";
                ResultSet resultSet3 = dataBaseHandel.queryExeutioner(query3);
                while (resultSet3.next()){
                    String name = resultSet3.getNString("Name");
                    String email = resultSet3.getNString("Email");
                    String phone =resultSet3.getNString("Phone");
                    String username =resultSet3.getNString("UserName");

                    issueDate.add("Member Name "+name);
                    issueDate.add("Email "+email);
                    issueDate.add("Phone "+phone);
                    issueDate.add("UserName "+username);
                }
                readyforsubmission = true;
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        listview.getItems().setAll(issueDate);

    }

    @FXML
    private void renewbook(ActionEvent event){

        String bookid = renewbookid.getText();

        if(!readyforsubmission){

            AlertMaker.showErrorAlert("RENEW BOOK OPERATION","Please Select a Book to Renew!!");
        }

        Alert alert1 =  AlertMaker.showConfirmationAlert("RENEW BOOK OPERATION","Are you Sure to RENEW this Book ? ");
        Optional<ButtonType> response = alert1.showAndWait();
        if(response.get()==ButtonType.OK){

            String query="select RENEW_COUNT from ISSUE WHERE BOOKID='"+Integer.parseInt(bookid)+"'";
            int count=0;
            try{
                ResultSet rs = dataBaseHandel.queryExeutioner(query);

                while (rs.next()){
                    count = rs.getInt("RENEW_COUNT");
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }

            String query1= "UPDATE ISSUE SET ISSUETIME='SYSDATE',RENEW_COUNT='"+count+1+"' WHERE BOOKID='"+Integer.parseInt(bookid)+"'";
            if(dataBaseHandel.actionExecutioner(query1)){

                listview = null;
                AlertMaker.showInformationAlert("RENEW BOOK OPERATION","Book Submission Successfully.");
            }else {

                AlertMaker.showErrorAlert("RENEW BOOK OPERATION","Submission FAILED!!");
            }

        }else {

            AlertMaker.showErrorAlert("RENEW BOOK OPERATION","Internal Error !!");
        }
    }

    @FXML
    private void submissionbook(ActionEvent event){
        String bookid = renewbookid.getText();
        if(!readyforsubmission){

            AlertMaker.showErrorAlert("SUBMISSION BOOK OPERATION","Please Select a Book to Submission!!");
        }

        Alert alert1 = AlertMaker.showConfirmationAlert("SUBMISSION BOOK OPERATION","Are you Sure to Submit Book ? ");


        Optional<ButtonType> response = alert1.showAndWait();
        if(response.get()==ButtonType.OK) {
            String action1 = "DELETE FROM ISSUE WHERE BOOKID='"+Integer.parseInt(bookid)+"'";
            String action2 = "UPDATE BOOK SET ISAVAILABE='1' WHERE BOOKID= '"+Integer.parseInt(bookid)+"'";

            if(dataBaseHandel.actionExecutioner(action1)&& dataBaseHandel.actionExecutioner(action2)){

                AlertMaker.showInformationAlert("SUBMISSION BOOK OPERATION","Book Submission Successfully.");
            }else {

                AlertMaker.showErrorAlert("SUBMISSION BOOK OPERATION","Submission FAILED!!");
            }
        }else {

            AlertMaker.showErrorAlert("SUBMISSION BOOK OPERATION","Internal Error !!");
        }
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

    @FXML
    public void handlemenuclose(ActionEvent actionEvent) {
        ((Stage)stackpaneid.getScene().getWindow()).close();
    }

    @FXML
    public void menuaddmember(ActionEvent actionEvent) {
        loadwindow("UI/MemberAdd/add_member.fxml","ADD MEMBER");
    }

    @FXML
    public void menuaddbook(ActionEvent actionEvent) {
        loadwindow("UI/addbook/addBookUI.fxml","ADD BOOK");
    }

    public void menumemberlist(ActionEvent actionEvent) {
        loadwindow("UI/AllLists/AllMemberLists/member_list.fxml","ALL MEMBER");
    }

    public void menubooklist(ActionEvent actionEvent) {
        loadwindow("UI/AllLists/AllBookLists/book_list.fxml","ALL BOOK");
    }

    public void menufullscene(ActionEvent actionEvent) {

        Stage stage = ((Stage)stackpaneid.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }
}
