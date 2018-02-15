package Models;

import java.util.Date;

public class Issue {
    private int ID;
    private int BOOKID;
    private int MEMBERID;
    private Date ISSUETIME;
    private int RENEW_COUNT;

    public Issue() {
    }

    public Issue(int ID, int BOOKID, int MEMBERID, Date ISSUETIME, int RENEW_COUNT) {
        this.ID = ID;
        this.BOOKID = BOOKID;
        this.MEMBERID = MEMBERID;
        this.ISSUETIME = ISSUETIME;
        this.RENEW_COUNT = RENEW_COUNT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBOOKID() {
        return BOOKID;
    }

    public void setBOOKID(int BOOKID) {
        this.BOOKID = BOOKID;
    }

    public int getMEMBERID() {
        return MEMBERID;
    }

    public void setMEMBERID(int MEMBERID) {
        this.MEMBERID = MEMBERID;
    }

    public Date getISSUETIME() {
        return ISSUETIME;
    }

    public void setISSUETIME(Date ISSUETIME) {
        this.ISSUETIME = ISSUETIME;
    }

    public int getRENEW_COUNT() {
        return RENEW_COUNT;
    }

    public void setRENEW_COUNT(int RENEW_COUNT) {
        this.RENEW_COUNT = RENEW_COUNT;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "ID=" + ID +
                ", BOOKID=" + BOOKID +
                ", MEMBERID=" + MEMBERID +
                ", ISSUETIME=" + ISSUETIME +
                ", RENEW_COUNT=" + RENEW_COUNT +
                '}';
    }
}
