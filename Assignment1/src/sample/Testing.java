package sample;

import java.io.*;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Testing {
    private Map<String, Double> spamWords;

    public Testing(Map<String, Double> spamWords){
        this.spamWords=spamWords;
    }

    public double spamFile(File file, Map<String, Double> spamWords){
        double rau=0;
        try{
            String word;
            Scanner scan=new Scanner(file);//scans every word of file
            while (scan.hasNext()){
                word=scan.next();
                if(isWord(word)){
                    if(spamWords.containsKey(word)){//if the word is in the spam map
                        rau=rau+(Math.log(1-spamWords.get(word))-Math.log(spamWords.get(word)));
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        double spamProbability=1/(1+Math.pow(Math.E,rau));//calculate spam probability of file
        return spamProbability;
    }

    private boolean isWord(String str){//determines words
        String pattern = "^[a-zA-Z]*$";
        if (str.matches(pattern)){
            return true;
        }
        return false;
    }
}
