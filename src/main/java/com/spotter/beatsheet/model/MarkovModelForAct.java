package com.spotter.beatsheet.model;

import java.util.*;

// ActContext class encapsulates a list of Long values representing Acts.
class ActContext {
    private final List<Long> acts;

    // Constructor for ActContext that initializes the list of Acts.
    public ActContext(List<Long> acts) {
        this.acts = acts;
    }

    @Override
    public int hashCode() {
        return acts.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActContext other = (ActContext) obj;
        return acts.equals(other.acts);
    }
}
public class MarkovModelForAct {
    private final Map<String, List<String>> transitions;

    // Constructor for MarkovModelForAct that initializes the transitions map based on the order.
    public MarkovModelForAct(int order) {
        transitions = new HashMap<>();
        List<Long> actIds = new ArrayList<>();
        int numsGen = 0;

        // Generate a list of random Long values (Act IDs) for demonstration purposes.
        while (numsGen < 10000) {
            Random rnd = new Random();
            Long val = rnd.nextLong(300 - 1) + 1;
            actIds.add(val);
            numsGen++;
        }

        // Build the Markov model based on the generated Act IDs.
        buildModel(actIds, order);
    }

    // Private method to build the Markov model based on a list of Act IDs and the specified order.
    private void buildModel(List<Long> acts, int order) {
        for (int i = 0; i < acts.size() - order; i++) {
            List<Long> context = acts.subList(i, i + order);
            ActContext contextObj = new ActContext(context);
            String contextKey = contextObj.toString();
            Long nextAct = acts.get(i + order);

            transitions.computeIfAbsent(contextKey, k -> new ArrayList<>()).add(String.valueOf(nextAct));
        }
    }

    // Predict the next Act based on a provided context (list of Act IDs).
    public String predictNextAct(List<Long> context) {
        ActContext contextObj = new ActContext(context);
        String contextKey = contextObj.toString();
        List<String> nextActs = transitions.get(contextKey);

        if (nextActs != null) {
            Random random = new Random();
            return nextActs.get(random.nextInt(nextActs.size()));
        } else {
            // Return a space character if no prediction is available.
            return String.valueOf(' ');
        }
    }
}
