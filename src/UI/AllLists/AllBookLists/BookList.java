package UI.AllLists.AllBookLists;

import AlertGenerator.AlertMaker;
import DataBase.DataBaseHandel;
import ICONMANAGER.LibraryAssistantIconManager;
import Models.Book;
import UI.addbook.BooksController;
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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookList implements Initializable {

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Book> tview;
    @FXML
    private TableColumn<Book,Integer>colID;
    @FXML
    private TableColumn<Book,String >colTitle;
    @FXML
    private TableColumn<Book,String >colAuthor;
    @FXML
    private TableColumn<Book,String>colPublished;
    @FXML
    private TableColumn<Book,Integer>colAvialabe;

    private DataBaseHandel dataBaseHandel;
    ObservableList<Book> bookLists = FXCollections.observableArrayList();
    Book  selectedbook=null;

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
        colTitle.setCellValueFactory(new PropertyValueFactory<>("TITLE"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("AUTHOR"));
        colPublished.setCellValueFactory(new PropertyValueFactory<>("PUBLISHED"));
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colAvialabe.setCellValueFactory(new PropertyValueFactory<>("ISAVAILABE"));
    }
    public void viewExistentBooks(){
        String query="Select ID ,AUTHOR ,TITLE,PUBLISHED,ISAVAILABE from Book";
        bookLists.clear();

        try{
            ResultSet resultSet = dataBaseHandel.queryExeutioner(query);
            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getInt("Id"));
                book.setAUTHOR(resultSet.getNString("AUTHOR"));
                book.setTITLE(resultSet.getNString("TITLE"));
                book.setPUBLISHED(resultSet.getNString("PUBLISHED"));
                book.setISAVAILABE(resultSet.getInt("ISAVAILABE"));
                bookLists.add(book);
            }
            tview.setItems(bookLists);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void handlebookdeleteoption(ActionEvent actionEvent) {
        selectedbook= selectedbook = tview.getSelectionModel().getSelectedItem();
        if(selectedbook==null){
            AlertMaker.showErrorAlert("DELETE BOOK","Select Book First");
        }else {
            Alert alert = AlertMaker.showConfirmationAlert("DELETE BOOK","Are You Sure ?");
            Optional<ButtonType> response =alert.showAndWait();
            if(response.get()==ButtonType.OK){
                String query ="DELETE FROM BOOK WHERE ID = '"+selectedbook.getId()+"'";
                if(dataBaseHandel.actionExecutioner(query)&& isBookAllReadyIssue(selectedbook.getId())){
                    AlertMaker.showInformationAlert("DELETE BOOK","Book Delete Successfully.");
                    bookLists.remove(selectedbook);
                }else {
                    AlertMaker.showErrorAlert("DELETE BOOK","Book Delete Failed!!");
                }

            }else {
                AlertMaker.showErrorAlert("DELETE BOOK","INTERNAL ERROR!!!");
            }
        }
    }

    public boolean isBookAllReadyIssue(int id){

        String query="SELECT COUNT(ID) FROM ISSUE WHERE ID ='"+id+"'";
        int count=0;
        try{
            ResultSet resultSet = dataBaseHandel.queryExeutioner(query);
            while (resultSet.next()){
                 count = resultSet.getInt("COUNT(ID)");

            }
        }catch (Exception ex){

        }
        if(count==0)
            return false;

        return true;
    }

    public void handlebookediteoption(ActionEvent actionEvent) {
        selectedbook = tview.getSelectionModel().getSelectedItem();
        if(selectedbook==null){
            AlertMaker.showErrorAlert("DELETE BOOK","Select Book First");
            return;
        }

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UI/addbook/addBookUI.fxml"));
            Parent root= loader.load();

            BooksController controller =(BooksController) loader.getController();
            controller.inflateUI(selectedbook);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("EDIT BOOK");
            LibraryAssistantIconManager.setStageIcon(stage);
            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnCloseRequest((e) -> {
                handlebookrefresheoption(new ActionEvent());
            });
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }


    }

    public void handlebookrefresheoption(ActionEvent actionEvent) {
        viewExistentBooks();
    }
}
