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
            BufferedReader in;
            String word;
            /*
            in = new BufferedReader(new FileReader(file));
            while ((line=in.readLine())!=null){
                String[] words= line.split(" ");
                //System.out.println(line);
                for (int i=0; i<words.length;i++){
                    if(spamWords.containsKey(words[i])){
                        rau=rau+(Math.log(1-spamWords.get(words[i]))-Math.log(spamWords.get(words[i])));
                    }
                }
            }
            */
            Scanner scan=new Scanner(file);
            while (scan.hasNext()){
                word=scan.next();
                if(isWord(word)){
                    if(spamWords.containsKey(word)){
                        rau=rau+(Math.log(1-spamWords.get(word))-Math.log(spamWords.get(word)));
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        double spamProbability=1/(1+Math.pow(Math.E,rau));
        return spamProbability;
    }

    private boolean isWord(String str){
        String pattern = "^[a-zA-Z]*$";
        if (str.matches(pattern)){
            return true;
        }
        return false;
    }
}
