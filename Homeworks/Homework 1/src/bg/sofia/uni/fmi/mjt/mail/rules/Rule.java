package bg.sofia.uni.fmi.mjt.mail.rules;

import java.util.Objects;
import java.util.Set;

public record Rule(Set<String> subjectIncludes, Set<String> subjectOrBodyIncludes, Set<String> recipientsIncludes,
                   String from, RulePriority priority, String folderPath) implements Comparable<Rule> {
    public Rule {
        if (subjectIncludes == null || subjectOrBodyIncludes == null || recipientsIncludes == null) {
            throw new IllegalArgumentException(
                "subjectIncludes, subjectOrBodyIncludes, recipientsIncludes cannot be null!");
        }

        if (from == null) {
            throw new IllegalArgumentException("From cannot be null!");
        }

        if (priority == null) {
            throw new IllegalArgumentException("The priority cannot be null");
        }

        if (folderPath == null || folderPath.isEmpty() || folderPath.isBlank()) {
            throw new IllegalArgumentException("Folder path cannot be null, empty ot blank!");
        }
    }

    public static Rule of(Set<String> subjectIncludes, Set<String> subjectOrBodyIncludes,
                          Set<String> recipientsIncludes,
                          String from, RulePriority priority, String folderPath) {
        return new Rule(subjectIncludes, subjectOrBodyIncludes, recipientsIncludes, from, priority, folderPath);
    }

    @Override
    public int compareTo(Rule other) {
        return Integer.compare(this.priority().getPriority(), other.priority().getPriority());
    }

    /**
     * This is done because we don't need folderPath in order to compare that two rules are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(subjectIncludes, rule.subjectIncludes) &&
            Objects.equals(subjectOrBodyIncludes, rule.subjectOrBodyIncludes) &&
            Objects.equals(recipientsIncludes, rule.recipientsIncludes) &&
            Objects.equals(from, rule.from) && priority == rule.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectIncludes, subjectOrBodyIncludes, recipientsIncludes, from, priority);
    }
}
