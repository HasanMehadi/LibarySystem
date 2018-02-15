package DataBase;

import Models.Book;
import Models.Member;

import javax.swing.*;
import java.sql.*;

public class DataBaseHandel {

    public static DataBaseHandel dataBaseHandel=null;
    public Statement statement;
    public Connection connection;
    String tableName1 ="Book";
    String tableName2 ="Member";
    String tableName3 ="Issue";
    String BookTableStatement="CREATE TABLE "+tableName1+"( id INT PRIMARY KEY ,title VARCHAR(200) Not NULL,author VARCHAR(100) NOT NULL ,published VARCHAR(100) NOT NULL,isAvailabe INT )";
    String MemberTableStatement ="CREATE TABLE"+tableName2+"( Id INT PRIMARY KEY , Name VARCHAR(200) NOT NULL, Email VARCHAR(100) NOT NULL ,Phone VARCHAR(100) NOT NULL,UserName VARCHAR(100) NOT NULL ,Password VARCHAR(100) NOT NULL )";
    String IssueTableStatement ="CREATE TABLE"+tableName3+"(Id INT PRIMARY KEY, BOOKID INT NOT NULL, MEMBERID INT NOT NULL, ISSUETIME DATE NOT NULL, RENEW_COUNT INT)";
    private DataBaseHandel(){
        connection = ConnectionManager.getConnection();

        setupTable(tableName2,MemberTableStatement);
        setupTable(tableName1,BookTableStatement);
        setupTable(tableName3,IssueTableStatement);
    }

    public static DataBaseHandel getDataBaseHandelInstance(){
        if(dataBaseHandel==null){
            return new DataBaseHandel();
        }
        return dataBaseHandel;
    }
    public void setupTable(String tableName,String stm){

        try{
            statement = connection.createStatement();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null,null,tableName.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+tableName+" Already Exits. Good To go to next level");
            }else {
                statement.execute(stm);
            }
        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }

    }

    public ResultSet queryExeutioner(String query){

        ResultSet resultSet;
        try{

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
        return resultSet;

    }
    public boolean actionExecutioner(String query){

        try{
            statement = connection.createStatement();
            statement.execute(query);

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Error"+ex.getMessage(),"Error Occurred",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getLocalizedMessage());
            return false;
        }
        return true;
    }
    public boolean updateBook(Book book) {
        try {
            String update = "UPDATE BOOK SET TITLE=?, AUTHOR=?, PUBLISHER=?,ISAVAILABE=? WHERE ID=?";
            PreparedStatement stmt = connection.prepareStatement(update);
            stmt.setString(1, book.getTITLE());
            stmt.setString(2, book.getAUTHOR());
            stmt.setString(3, book.getPUBLISHED());
            stmt.setInt(4,book.getISAVAILABE());
            stmt.setInt(5, book.getId());
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {

        }
        return false;
    }
    public boolean updateMember(Member member) {
        try {
            String update = "UPDATE MEMBER SET NAME=?, EMAIL=?, Phone=?,UserName=?,Password=? WHERE ID=?";
            PreparedStatement stmt = connection.prepareStatement(update);
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPhone());
            stmt.setString(4, member.getUserName());
            stmt.setString(5, member.getPassword());
            stmt.setString(4, member.getPassword());
            stmt.setInt(6, member.getId());
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean deleteMember(Member member) {
        try {
            String deleteStatement = "DELETE FROM MEMBER WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteStatement);
            stmt.setInt(1, member.getId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {

        }
        return false;
    }
    public boolean isMemberHasAnyBooks(Member member) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM ISSUE WHERE memberID=?";
            PreparedStatement stmt = connection.prepareStatement(checkstmt);
            stmt.setInt(1, member.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
