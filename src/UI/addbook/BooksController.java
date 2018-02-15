package UI.addbook;

import AlertGenerator.AlertMaker;
import DataBase.DataBaseHandel;
import Models.Book;
import UI.AllLists.AllBookLists.BookList;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BooksController implements Initializable{
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */

    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField txtTitle;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtAuthor;
    @FXML
    private JFXTextField txtPublished;
    @FXML
    private AnchorPane rootpane;

    public DataBaseHandel dataBaseHandel;
    private Boolean ineditmode = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBaseHandel = DataBaseHandel.getDataBaseHandelInstance();
        String query="select Count(id) from Book";
        int count=0;
        try{
            ResultSet rs = dataBaseHandel.queryExeutioner(query);

            while (rs.next()){
                count = rs.getInt("Count(id)");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        txtId.setText(Integer.toString(count+1));
        txtId.setPromptText("");
        txtId.setEditable(false);
    }

    @FXML
    public void saveBook(ActionEvent event){

        String id = txtId.getText();
        String title = txtTitle.getText();
        String  author = txtAuthor.getText();
        String published = txtPublished.getText();
        String query="";
        if(ineditmode==true){
            handleEditOperation();
            return;
        }
        if(!id.isEmpty()||!title.isEmpty()||!author.isEmpty()||published.isEmpty()){
            query = "INSERT INTO BOOK VALUES('"+Integer.parseInt(id)+"','"+title+"','"+author+"','"+published+"',"+1+")";
            System.out.println(query);
        }else {

            AlertMaker.showErrorAlert("SAVE BOOK","Enter All Values");
        }

        if(dataBaseHandel.actionExecutioner(query)){

            AlertMaker.showErrorAlert("SAVE BOOK","Book Saved");
        }else {

            AlertMaker.showErrorAlert("SAVE BOOK","Internal Error Occurred");
        }
    }
    @FXML
    public void cancelBook(ActionEvent event){
/*        txtId.setPromptText("Book Id");
        txtAuthor.setPromptText("Book Author");
        txtPublished.setPromptText("Published");
        txtTitle.setPromptText("Book Title");*/

        Stage stage =(Stage) rootpane.getScene().getWindow();
        stage.close();
    }

    public void inflateUI(Book book){
        txtAuthor.setText(book.getAUTHOR());
        txtId.setText(Integer.toString(book.getId()));
        txtTitle.setText(book.getTITLE());
        txtPublished.setText(book.getPUBLISHED());
        txtId.setEditable(false);
        ineditmode = true;
    }
    private void handleEditOperation() {
        Book book = new Book(Integer.parseInt(txtId.getText()),txtAuthor.getText(),txtTitle.getText(),  txtPublished.getText(), 1);
        if (dataBaseHandel.updateBook(book)) {
            AlertMaker.showInformationAlert("Success", "Book Updated");
        } else {
            AlertMaker.showErrorAlert("Failed", "Cant update book");
        }
    }
}
