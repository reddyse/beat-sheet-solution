package com.spotter.BeatSheet.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class BeatContext {
    private List<Long> beats;

    public BeatContext(List<Long> beats) {
        this.beats = beats;
    }

    @Override
    public int hashCode() {
        return beats.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BeatContext other = (BeatContext) obj;
        return beats.equals(other.beats);
    }

}
public class MarkovModelForBeat {
    private Map<String, List<String>> transitions;

    public MarkovModelForBeat(int order) {
        transitions = new HashMap<>();
        List<Long> beatIds = new ArrayList<>();
        int numsGen =0;
        while(numsGen<1000){
            Random rnd = new Random();
            Long val = rnd.nextLong(15-1)+1;
            beatIds.add(val);
            numsGen++;
        }
        buildModel(beatIds, order);
    }

    private void buildModel(List<Long> beats, int order) {
        for (int i = 0; i < beats.size() - order; i++) {
            List<Long> context = beats.subList(i, i + order);
            BeatContext contextObj = new BeatContext(context);
            String contextKey = contextObj.toString();
            Long nextBeat = beats.get(i + order);

            transitions.computeIfAbsent(contextKey, k -> new ArrayList<>()).add(String.valueOf(nextBeat));
        }
    }

    public String predictNextBeat(List<Long> context) {
        BeatContext contextObj = new BeatContext(context);
        String contextKey = contextObj.toString();
        List<String> nextBeats = transitions.get(contextKey);
        if (nextBeats != null) {
            Random random = new Random();
            return nextBeats.get(random.nextInt(nextBeats.size()));
        } else {
            return null;
        }
    }


/*    public static void main(String[] args) {
        List<Long> beatData = new ArrayList<>();
        int order = 2;  // Set the order for considering previous beats.

        MarkovModelForBeat model = new MarkovModelForBeat(order);

        List<Long> context = new ArrayList<>();
        context.add(1L);
        context.add(2L);  // You can set the context based on previous beats.

        String nextBeat = model.predictNextBeatV2(context);

        if (nextBeat != null) {
            System.out.println("The next beat following the context " + context + " is '" + nextBeat + "'.");
        } else {
            System.out.println("No information available for the given context.");
        }
    }*/

}
