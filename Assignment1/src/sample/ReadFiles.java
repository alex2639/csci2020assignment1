package sample;

import java.io.*;
import java.util.*;

public class ReadFiles {
    private Map <String, Integer> trainFreq;
    private String directoryName;
    private int fileCount = 0;

    public ReadFiles(String directoryName, Map <String, Integer> trainFreq){
        this.directoryName=directoryName;
        this.trainFreq=trainFreq;
    }

    public Map <String, Integer> readFiles(File folder, Map<String, Integer> trainFreq){
        try{
            BufferedReader in;
            String word;
            for (File fileEntry : folder.listFiles()){
                Map <String, Integer>freq=new TreeMap<>();
                Scanner scan=new Scanner(fileEntry);
                while (scan.hasNext()){
                    word=scan.next();
                    if(isWord(word)){
                        if (!freq.containsKey(word)){
                            freq.put(word,1);
                            if(!trainFreq.containsKey(word)){
                                trainFreq.put(word,1);
                            } else {
                                trainFreq.put(word,trainFreq.get(word)+1);
                            }
                        }
                    }
                }
                /*
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
                */
                fileCount++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return trainFreq;
    }

    public int getCount(){
        return fileCount;
    }

    private boolean isWord(String str){
        String pattern = "^[a-zA-Z]*$";
        if (str.matches(pattern)){
            return true;
        }
        return false;
    }

    public Map <String, Double> getProbabilities(Map<String, Integer> map){
        Map <String, Double> probabilities = new TreeMap<>();
        /*
        int mapSize=map.size();
        for(int i=0;i<mapSize;i++){
            probabilities.put(map.keySet().toArray()[i].toString(), map.get(map.keySet().toArray()[i])/(double) getCount());
        }
        */
        Set<String> keys=map.keySet();
        Iterator<String> keyIterator=keys.iterator();
        while (keyIterator.hasNext()){
            probabilities.put(keyIterator.next(),map.get(keyIterator.next())/(double) getCount());
        }
        return probabilities;
    }
}
