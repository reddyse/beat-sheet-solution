package com.spotter.beatsheet.model;

import java.util.*;

// BeatContext class encapsulates a list of Long values representing Beats.
class BeatContext {
    private final List<Long> beats;

    // Constructor for BeatContext that initializes the list of Beats.
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
    private final Map<String, List<String>> transitions;

    // Constructor for MarkovModelForBeat that initializes the transitions map based on the order.
    public MarkovModelForBeat(int order) {
        transitions = new HashMap<>();
        List<Long> beatIds = new ArrayList<>();
        int numsGen = 0;

        // Generate a list of random Long values (Beat IDs) for demonstration purposes.
        while (numsGen < 10000) {
            Random rnd = new Random();
            Long val = rnd.nextLong(300 - 1) + 1;
            beatIds.add(val);
            numsGen++;
        }

        // Build the Markov model based on the generated Beat IDs.
        buildModel(beatIds, order);
    }

    // Private method to build the Markov model based on a list of Beat IDs and the specified order.
    private void buildModel(List<Long> beats, int order) {
        for (int i = 0; i < beats.size() - order; i++) {
            List<Long> context = beats.subList(i, i + order);
            BeatContext contextObj = new BeatContext(context);
            String contextKey = contextObj.toString();
            Long nextBeat = beats.get(i + order);

            transitions.computeIfAbsent(contextKey, k -> new ArrayList<>()).add(String.valueOf(nextBeat));
        }
    }

    // Predict the next Beat based on a provided context (list of Beat IDs).
    public String predictNextBeat(List<Long> context) {
        BeatContext contextObj = new BeatContext(context);
        String contextKey = contextObj.toString();
        List<String> nextBeats = transitions.get(contextKey);

        if (nextBeats != null) {
            Random random = new Random();
            return nextBeats.get(random.nextInt(nextBeats.size()));
        } else {
            // Return null if no prediction is available.
            return null;
        }
    }
}
