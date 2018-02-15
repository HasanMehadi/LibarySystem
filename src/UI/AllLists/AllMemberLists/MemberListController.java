package UI.AllLists.AllMemberLists;

import AlertGenerator.AlertMaker;
import DataBase.DataBaseHandel;
import ICONMANAGER.LibraryAssistantIconManager;
import Models.Member;
import UI.MemberAdd.MemberController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberListController implements Initializable{

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Member> tview;
    @FXML
    private TableColumn<Member,Integer> colID;
    @FXML
    private TableColumn<Member,String >colUser;
    @FXML
    private TableColumn<Member,String >colEmail;
    @FXML
    private TableColumn<Member,String>colPhone;
    @FXML
    private TableColumn<Member,String>colusername;

    private DataBaseHandel dataBaseHandel;
    private ObservableList<Member> memberLists;

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
        initColumn();
        viewExistentBooks();
    }

    private void initColumn(){
        colUser.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colusername.setCellValueFactory(new PropertyValueFactory<>("UserName"));
    }
    public void viewExistentBooks(){
        memberLists.clear();
        String query="Select ID ,Name ,Email,Phone,UserName from Member";

        memberLists = FXCollections.observableArrayList();
        try{
            ResultSet resultSet = dataBaseHandel.queryExeutioner(query);
            while (resultSet.next()){
                Member member = new Member();
                member.setId(resultSet.getInt("Id"));
                member.setName(resultSet.getNString("Name"));
                member.setEmail(resultSet.getNString("Email"));
                member.setPhone(resultSet.getNString("Phone"));
                member.setPassword(resultSet.getNString("UserName"));
                memberLists.add(member);
            }
            tview.setItems(memberLists);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void refreshmenu(ActionEvent actionEvent) {
        viewExistentBooks();
    }


    public void updatemembermenu(ActionEvent actionEvent) {
        //Fetch the selected row
        Member selectedForEdit = tview.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorAlert("No member selected", "Please select a member for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/addmember/member_add.fxml"));
            Parent parent = loader.load();

            MemberController controller = (MemberController) loader.getController();
            controller.infalteUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Member");
            stage.setScene(new Scene(parent));
            stage.show();
            LibraryAssistantIconManager.setStageIcon(stage);

            stage.setOnCloseRequest((e)->{
                refreshmenu(new ActionEvent());
            });

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deletemembermenu(ActionEvent actionEvent) {
        //Fetch the selected row
        Member selectedForDeletion = tview.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorAlert("No member selected", "Please select a member for deletion.");
            return;
        }
        if(DataBaseHandel.getDataBaseHandelInstance().isMemberHasAnyBooks(selectedForDeletion))
        {
            AlertMaker.showErrorAlert("Cant be deleted", "This member has some books.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete " + selectedForDeletion.getName()+ " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DataBaseHandel.getDataBaseHandelInstance().deleteMember(selectedForDeletion);
            if (result) {
                AlertMaker.showInformationAlert("Book deleted", selectedForDeletion.getName()+ " was deleted successfully.");
                memberLists.remove(selectedForDeletion);
            } else {
                AlertMaker.showErrorAlert("Failed", selectedForDeletion.getName()+ " could not be deleted");
            }
        } else {
            AlertMaker.showErrorAlert("Deletion cancelled", "Deletion process cancelled");
        }
    }



}
