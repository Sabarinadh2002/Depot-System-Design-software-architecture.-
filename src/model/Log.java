package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {
    private static Log instance;
    private StringBuilder logData = new StringBuilder();

    private Log(){

    }
    public static synchronized Log getInstance(){
        if (instance == null){
            instance = new Log();
        }
        return instance;
    }
    public void append (String event){
        logData.append(event).append("\n");
    }

    public void writeToFile (String filename){
        try (PrintWriter pw = new PrintWriter (new FileWriter(filename))){
            pw.print(logData.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
