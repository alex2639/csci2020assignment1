package sample;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Probabilities {
    private Map<String, Double> hamWord;
    private Map<String, Double> spamWord;
    public Probabilities(Map<String, Double> hamWord, Map<String, Double> spamWord){
        this.hamWord=hamWord;
        this.spamWord=spamWord;
    }

    public Map<String, Double> spamProbability(Map<String, Double> hamWord, Map<String, Double> spamWord){
        Map<String, Double> spam = new TreeMap<>();
        /*
        for (int i=0;i<spamWord.size();i++){
            spam.put(spamWord.keySet().toArray()[i].toString(),spamWord.get(spamWord.keySet().toArray()[i].toString())/(spamWord.get(spamWord.keySet().toArray()[i].toString())+spamWord.get(hamWord.keySet().toArray()[i].toString())));
        }
        */
        Set<String> keys=spamWord.keySet();
        Iterator<String> keyIterator=keys.iterator();
        //System.out.println("spam");
        String word;
        while (keyIterator.hasNext()){
            word=keyIterator.next();
            //System.out.println(keyIterator.next());
            //System.out.println(spamWord.get(keyIterator.next()));
            //System.out.println(hamWord.get(keyIterator.next()));
            if(!hamWord.containsKey(word)){
                spam.put(word,spamWord.get(word)/spamWord.get(word));
            } else{
                spam.put(word,spamWord.get(word)/(spamWord.get(word)+ hamWord.get(word)));
            }

        }
        return spam;
    }
}
