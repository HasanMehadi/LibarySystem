package UI.Settings;

import Models.MasterSetting;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Preferences {

    public static void initSetting(){
        MasterSetting setting=null;
        Writer writer=null;
        Gson gson=null;
        try {
            setting = new MasterSetting();
            writer = new FileWriter(MasterSetting.CONFIG_FILE);
            gson = new Gson();
            gson.toJson(setting,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MasterSetting getMasterSetting(){
        MasterSetting setting=new MasterSetting();

        Gson gson=null;
        try{
            gson = new Gson();
            setting = gson.fromJson(new FileReader(MasterSetting.CONFIG_FILE),MasterSetting.class);
            return setting;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return setting;
    }

    public static void setMasterSetting(MasterSetting setting){

        Writer writer=null;
        Gson gson=null;
        try {

            writer = new FileWriter(MasterSetting.CONFIG_FILE);
            gson = new Gson();
            gson.toJson(setting,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
