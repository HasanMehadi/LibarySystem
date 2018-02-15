package Models;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.*;

public class MasterSetting {

    public static final String CONFIG_FILE="CONFIG_FILE.txt";
    int nDaywithoutfine;
    float fineperday;
    String username;
    String password;

    public MasterSetting() {
        nDaywithoutfine=7;
        fineperday =0.50f;
        username="admin";
        setPassword("admin");
    }



    public int getnDaywithoutfine() {
        return nDaywithoutfine;
    }

    public void setnDaywithoutfine(int nDaywithoutfine) {
        this.nDaywithoutfine = nDaywithoutfine;
    }

    public float getFineperday() {
        return fineperday;
    }

    public void setFineperday(float fineperday) {
        this.fineperday = fineperday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(this.password.length()<16){
            this.password = DigestUtils.shaHex(password);
        }else {
            this.password=password;
        }
    }

    @Override
    public String toString() {
        return "MasterSetting{" +
                "nDaywithoutfine=" + nDaywithoutfine +
                ", fineperday=" + fineperday +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
