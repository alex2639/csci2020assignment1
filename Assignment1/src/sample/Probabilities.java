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
        while (keyIterator.hasNext()){
            spam.put(keyIterator.next(),spamWord.get(keyIterator.next())/(spamWord.get(keyIterator.next()+hamWord.get(keyIterator.next()))));
        }
        return spam;
    }
}
