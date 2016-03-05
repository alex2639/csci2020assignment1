package sample;

import java.io.*;
import java.io.IOException;
import java.util.Map;

public class Testing {
    private Map<String, Double> spamWords;

    public Testing(Map<String, Double> spamWords){
        this.spamWords=spamWords;
    }

    public double spamFile(File file, Map<String, Double> spamWords){
        double rau=0;
        try{
            BufferedReader in;
            String line;
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
        }catch (IOException e){
            e.printStackTrace();
        }
        double spamProbability=1/(1+Math.pow(Math.E,rau));
        return spamProbability;
    }
}
