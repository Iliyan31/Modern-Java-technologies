import java.util.Arrays;

public class PrefixExtractor {
    public static String getLongestCommonPrefix(String[] words) {
        if(words == null || words.length == 0) return "";
        if(words.length == 1) return words[0];
        Arrays.sort(words);
        String word1 = words[0];
        String word2 = words[words.length - 1];
        if(word1.length() == 0 || word2.length() == 0) return "";
        StringBuilder result = new StringBuilder("");
        int max = (word1.length() > word2.length() ? word2.length() : word1.length());
        int i = 0;
        while(i < max && word1.charAt(i) == word2.charAt(i)) {
                result.append(words[0].charAt(i));
                i++;
        }

        return String.valueOf(result);
   }
}
