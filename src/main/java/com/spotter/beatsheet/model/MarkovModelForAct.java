package com.spotter.beatsheet.model;

import java.util.*;

class ActContext {
    private final List<Long> acts;

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

    public MarkovModelForAct(int order) {
        transitions = new HashMap<>();
        List<Long> actIds = new ArrayList<>();
        int numsGen =0;
        while(numsGen<1000){
            Random rnd = new Random();
            Long val = rnd.nextLong(10-1)+1;
            actIds.add(val);
            numsGen++;
        }
        buildModel(actIds,order);
    }

    private void buildModel(List<Long> acts, int order) {
        for (int i = 0; i < acts.size() - order; i++) {
            List<Long> context = acts.subList(i, i + order);
            ActContext contextObj = new ActContext(context);
            String contextKey = contextObj.toString();
            Long nextAct = acts.get(i + order);

            transitions.computeIfAbsent(contextKey, k -> new ArrayList<>()).add(String.valueOf(nextAct));
        }
    }

    public String predictNextAct(List<Long> context) {
        ActContext contextObj = new ActContext(context);
        String contextKey = contextObj.toString();
        List<String> nextActs = transitions.get(contextKey);
        if (nextActs != null) {
            Random random = new Random();
            return nextActs.get(random.nextInt(nextActs.size()));
        } else {
            return String.valueOf(' ');
        }
    }
}
