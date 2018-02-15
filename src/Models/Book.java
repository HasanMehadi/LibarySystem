package Models;

public class Book {
    private int Id;
    private String AUTHOR ;
    private String  TITLE;
    private String  PUBLISHED;
    private int ISAVAILABE;

    public Book() {

    }

    public Book(int id, String AUTHOR, String TITLE, String PUBLISHED, int ISAVAILABE) {
        Id = id;
        this.AUTHOR = AUTHOR;
        this.TITLE = TITLE;
        this.PUBLISHED = PUBLISHED;
        this.ISAVAILABE = ISAVAILABE;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getPUBLISHED() {
        return PUBLISHED;
    }

    public void setPUBLISHED(String PUBLISHED) {
        this.PUBLISHED = PUBLISHED;
    }

    public int getISAVAILABE() {
        return ISAVAILABE;
    }

    public void setISAVAILABE(int ISAVAILABE) {
        this.ISAVAILABE = ISAVAILABE;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + Id +
                ", AUTHOR='" + AUTHOR + '\'' +
                ", TITLE='" + TITLE + '\'' +
                ", PUBLISHED='" + PUBLISHED + '\'' +
                ", ISAVAILABE=" + ISAVAILABE +
                '}';
    }
}
