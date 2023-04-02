package bg.sofia.uni.fmi.mjt.mail.rules;

import java.util.Comparator;

public class RuleComparator implements Comparator<Rule> {
    @Override
    public int compare(Rule rule1, Rule rule2) {
        return Integer.compare(rule1.priority().getPriority(), rule2.priority().getPriority());
    }
}
