package sample;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by pkmn-master on 03/03/16.
 */
public class Probabilities {
    private Map<String, Double> hamWord;
    private Map<String, Double> spamWord;
    public Probabilities(Map<String, Double> hamWord, Map<String, Double> spamWord){
        this.hamWord=hamWord;
        this.spamWord=spamWord;
    }

    public Map<String, Double> spamProbability(Map<String, Double> hamWord, Map<String, Double> spamWord){
        Map<String, Double> spam = new TreeMap<>();
        
        return null;
    }


}
