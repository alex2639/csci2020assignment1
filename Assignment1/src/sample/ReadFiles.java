package sample;

import java.io.*;
import java.util.*;

/**
 * Created by pkmn-master on 03/03/16.
 */
public class ReadFiles {
    private Map <String, Integer> trainFreq;
    private String directoryName;

    public ReadFiles(String directoryName, Map <String, Integer> trainFreq){
        this.directoryName=directoryName;
        this.trainFreq=trainFreq;
    }

    public Map <String, Integer> readFiles(File folder, Map<String, Integer> trainFreq){
        try{
            BufferedReader in;
            String line;
            for (File fileEntry : folder.listFiles()){
                Map <String, Integer>freq=new TreeMap<>();
                in = new BufferedReader(new FileReader(fileEntry));
                while ((line=in.readLine())!=null){
                    String[] words= line.split(" ");
                    //System.out.println(line);
                    for (int i=0; i<words.length;i++){
                        if (!freq.containsKey(words[i])){
                            freq.put(words[i],1);
                            if(!trainFreq.containsKey(words[i])){
                                trainFreq.put(words[i],1);
                            } else {
                                trainFreq.put(words[i],trainFreq.get(words[i])+1);
                            }
                        }
                    }
                    //System.out.println(words[0]);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return trainFreq;
    }
}
