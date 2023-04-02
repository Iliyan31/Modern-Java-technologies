package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.exceptions.SentimentDictionarySizeException;
import bg.sofia.uni.fmi.mjt.sentiment.exceptions.StopwordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieReviewSentimentAnalyzerTest {
    MovieReviewSentimentAnalyzer movieAnalyzer;

    @BeforeEach
    void createMovieReviewSentimentAnalyzer() {
        Reader stopWordsReader = new StringReader(stopDataset);
        Writer reviewsWriter = new StringWriter();
        Reader reviewsReader = new StringReader(reviewsWriter.toString());

        movieAnalyzer = new MovieReviewSentimentAnalyzer(stopWordsReader, reviewsReader, reviewsWriter);
    }

    @Test
    void newMovieReviewSentimentAnalyzerWithNullStopwordsReader() {
        assertThrows(IllegalArgumentException.class,
            () -> new MovieReviewSentimentAnalyzer(null, new StringReader(""), new StringWriter()),
            "Stopwords reader cannot be null!");
    }

    @Test
    void newMovieReviewSentimentAnalyzerWithNullReviewsReader() {
        assertThrows(IllegalArgumentException.class,
            () -> new MovieReviewSentimentAnalyzer(new StringReader(""), null, new StringWriter()),
            "Reviews reader cannot be null!");
    }

    @Test
    void newMovieReviewSentimentAnalyzerWithNullReviewsWriter() {
        assertThrows(IllegalArgumentException.class,
            () -> new MovieReviewSentimentAnalyzer(new StringReader(""), new StringReader(""), null),
            "Reviews writer cannot be null!");
    }

    @Test
    void testAppendReviewWithNullReview() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.appendReview(null, 3),
            "The review string cannot be null, empty or blank!");
    }

    @Test
    void testAppendReviewWithEmptyReview() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.appendReview("", 3),
            "The review string cannot be null, empty or blank!");
    }

    @Test
    void testAppendReviewWithBlankReview() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.appendReview(" ", 3),
            "The review string cannot be null, empty or blank!");
    }

    @Test
    void testAppendReviewWithNegativeSentimentScore() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.appendReview("a", -1),
            "The sentiment score cannot be negative number!");
    }

    @Test
    void testAppendReviewWithGreaterThanFourSentimentScore() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.appendReview("a", 5),
            "The sentiment score cannot be number greater than 4!");
    }

    @Test
    void testAppendReviewWithCorrectData() {
        String review = "Hello there!";
        final int sentiment = 4;

        assertTrue(movieAnalyzer.appendReview(review, sentiment), "IOException cause!");
    }

    @Test
    void testGetSentimentDictionarySizeWithSentenceWithDifferentCharacters() {
        String review =
            "A keep - 'em - @ # !        %                        \t  \t\t         , , ,   =   +   ^ & guessing                plot and don't an affectionate ' 15 take hello'world on its screwed-hello characters keep.";
        final int sentiment = 3;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(11, movieAnalyzer.getSentimentDictionarySize(), "The dictionary should correctly");
    }

    @Test
    void testGetSentimentDictionarySizeWithOneWord() {
        String review = "Hello";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(1, movieAnalyzer.getSentimentDictionarySize(),
            "There should be only one word in the sentiment dictionary!");
    }

    @Test
    void testGetSentimentDictionarySizeWithNoWords() {
        assertEquals(0, movieAnalyzer.getSentimentDictionarySize(),
            "There should be no words in the sentiment dictionary!");
    }

    @Test
    void testGetSentimentDictionarySizeWithTwoNotStopWord() {
        String review = "Hello World";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(2, movieAnalyzer.getSentimentDictionarySize(),
            "There system should correctly return the number of words!");
    }

    @Test
    void testGetSentimentDictionarySizeWithTwoWordsOneStop() {
        String review = "Hello and";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(1, movieAnalyzer.getSentimentDictionarySize(),
            "There should be only one word in the sentiment dictionary!");
    }

    @Test
    void testGetSentimentDictionarySizeWithTwoStopWords() {
        String review = "Don't and";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(0, movieAnalyzer.getSentimentDictionarySize(),
            "There should be no words in the sentiment dictionary!");
    }

    @Test
    void testGetSentimentDictionarySizeWithPunctuation() {
        String review = "   ! @#$ ^&$^ *^%$ ' ' S ";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(0, movieAnalyzer.getSentimentDictionarySize(),
            "There should be no words in the sentiment dictionary when punctuation passed!");
    }

    @Test
    void testGetSentimentDictionarySizeWithPunctuationAndWord() {
        String review = "   ! @# asdf-aaa $ ^&$^ *^%$ ' ' S ";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(2, movieAnalyzer.getSentimentDictionarySize(),
            "There system should correctly return the number of words when passed amid punctuation!");
    }

    @Test
    void testGetSentimentDictionarySizeWithNumbers() {
        String review = "15 5 16 7 44 23 100";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(5, movieAnalyzer.getSentimentDictionarySize(),
            "There system should correctly return the number of words!");
    }

    @Test
    void testGetSentimentDictionarySizeWithEqualWords() {
        String review = "keep Keep KEep kEeP KeeP KeEp KeEP KEEP";
        final int sentiment = 1;

        movieAnalyzer.appendReview(review, sentiment);
        assertEquals(1, movieAnalyzer.getSentimentDictionarySize(),
            "There system should correctly return the number of words!");
    }

    @Test
    void testIsStopWordWithStopWord() {
        assertTrue(movieAnalyzer.isStopWord("don't"), "Should correctly return whether a word is a stopword!");
    }

    @Test
    void testIsStopWordWithNotStopWord() {
        assertFalse(movieAnalyzer.isStopWord("Hello"), "Should correctly return whether a word is a stopword!");
    }

    @Test
    void testGetReviewSentimentWithNullReview() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getReviewSentiment(null),
            "The review cannot be null, empty or blank!");
    }

    @Test
    void testGetReviewSentimentWithEmptyReview() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getReviewSentiment(""),
            "The review cannot be null, empty or blank!");
    }

    @Test
    void testGetReviewSentimentWithBlankReview() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getReviewSentiment(" "),
            "The review cannot be null, empty or blank!");
    }

    @Test
    void testGetReviewSentimentWithPositiveScore() {
        movieAnalyzer.appendReview(
            "A keep - 'em - @ # !        %                        \t  \t\t         , , ,   =   +   ^ & guessing                plot and don't an affectionate ' 15 take hello'world on its screwed-hello characters keep.",
            3);
        movieAnalyzer.appendReview(
            "Don't judge this one affectionate too soon - it's a dark , gritty story but it takes off in totally unexpected directions and keep on going .",
            2);
        movieAnalyzer.appendReview("tea.", 4);
        movieAnalyzer.appendReview("bag", 1);
        movieAnalyzer.appendReview("cat", 0);


        assertEquals(2.1666, movieAnalyzer.getReviewSentiment("A keep should guard and don't judge too soon"), 1000,
            "The system should correctly return the sentiment score of review!");
    }

    @Test
    void testGetReviewSentimentWithZero() {
        movieAnalyzer.appendReview("cat", 0);
        assertEquals(0, movieAnalyzer.getReviewSentiment("It is a cat"),
            "The system should correctly return the sentiment score of review!");
    }

    @Test
    void testGetReviewSentimentWithOne() {
        movieAnalyzer.appendReview("cat", 1);
        assertEquals(1, movieAnalyzer.getReviewSentiment("It is a cat"),
            "The system should correctly return the sentiment score of review!");
    }

    @Test
    void testGetReviewSentimentWithTwo() {
        movieAnalyzer.appendReview("cat", 2);
        assertEquals(2, movieAnalyzer.getReviewSentiment("It is a cat"),
            "The system should correctly return the sentiment score of review!");
    }

    @Test
    void testGetReviewSentimentWithThree() {
        movieAnalyzer.appendReview("cat", 3);
        movieAnalyzer.appendReview("cat", 3);
        movieAnalyzer.appendReview("cat", 2);
        assertEquals(2.66, movieAnalyzer.getReviewSentiment("It is a cat"), 100,
            "The system should correctly return the sentiment score of review!");
    }

    @Test
    void testGetReviewSentimentWithUnknown() {
        assertEquals(-1, movieAnalyzer.getReviewSentiment("It is a dog"),
            "The system should correctly return the sentiment score of review!");
    }

    @Test
    void testGetReviewSentimentAsNameUnknown() {
        assertEquals("unknown", movieAnalyzer.getReviewSentimentAsName("It is a dog"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetReviewSentimentAsNameNegative() {
        movieAnalyzer.appendReview("The dog in the movie was 'aint having it !!!!! ", 0);
        assertEquals("negative", movieAnalyzer.getReviewSentimentAsName("It is a dog"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetReviewSentimentAsNameSomewhatNegative() {
        movieAnalyzer.appendReview("The dog in the movie was 'aint having it !!!!! ", 1);
        movieAnalyzer.appendReview("The dog in the movie was 'aint having it !!!!! ", 2);
        movieAnalyzer.appendReview("The dog was 'aint having it !!!!! ", 1);
        assertEquals("somewhat negative", movieAnalyzer.getReviewSentimentAsName("It is a dog"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetReviewSentimentAsNameNegativeFloating() {
        movieAnalyzer.appendReview("The dog in the movie was 'aint having it !!!!! ", 0);
        movieAnalyzer.appendReview("The dog in the movie was 'aint having it !!!!! ", 1);
        movieAnalyzer.appendReview("The dog was 'aint having it !!!!! ", 0);
        assertEquals("negative", movieAnalyzer.getReviewSentimentAsName("It is a dog"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetReviewSentimentAsNameNeutral() {
        movieAnalyzer.appendReview(
            "That the Chuck Norris `` grenade gag  occurs about 7 times during Windtalkers is a good indication of how serious-minded the film is .",
            2);
        assertEquals("neutral", movieAnalyzer.getReviewSentimentAsName("Chuck Norris plays in the movie!!!"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetReviewSentimentAsNameSomewhatPositive() {
        movieAnalyzer.appendReview("( An ) absorbing documentary .", 3);
        assertEquals("somewhat positive",
            movieAnalyzer.getReviewSentimentAsName("The DoCuMENtarY was very good actually!"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetReviewSentimentAsNamePositive() {
        movieAnalyzer.appendReview(
            "One of recent memory's most thoughtful films about art , ethics , and the cost of moral compromise .", 4);
        movieAnalyzer.appendReview(
            "300 years of Russian history and culture compressed into an evanescent , seamless and sumptuous stream of consciousness .",
            4);
        movieAnalyzer.appendReview(
            "A triumph , a film that hews out a world and carries us effortlessly from darkness to light .", 4);
        movieAnalyzer.appendReview(
            "A brilliant gag at the expense of those who paid for it and those who pay to see it .", 2);
        assertEquals("positive", movieAnalyzer.getReviewSentimentAsName(
                "The consciousness          !@@##@ of !recent memory's most thoughtful films is a pure triumph which was 12 $#               brilliant to 3 see"),
            "The system should correctly return the sentiment name of review!");
    }

    @Test
    void testGetWordSentimentWithNullWord() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getWordSentiment(null),
            "The review cannot be null, empty or blank!");
    }

    @Test
    void testGetWordSentimentWithExistingWord() {
        movieAnalyzer.appendReview(
            "A keep - 'em - @ # !        %                        \t  \t\t         , , ,   =   +   ^ & guessing                plot and don't an affectionate ' 15 take hello'world on its screwed-hello characters keep.",
            3);
        movieAnalyzer.appendReview(
            "Don't judge this one affectionate too soon - it's a dark , gritty story but it takes off in totally unexpected directions and KeeP on going .",
            2);
        movieAnalyzer.appendReview("Hi kEep", 4);

        assertEquals(3, movieAnalyzer.getWordSentiment("keep"),
            "The system should correctly return the word sentiment!");
    }

    @Test
    void testGetWordSentimentWithNonExistingWord() {
        assertEquals(-1.0, movieAnalyzer.getWordSentiment("keep"), 10,
            "The system should correctly return the word sentiment!");
    }

    @Test
    void testGetWordFrequencyWithNullWord() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getWordSentiment(null),
            "The review cannot be null, empty or blank!");
    }

    @Test
    void testGetWordFrequencyWithStopWord() {
        assertThrows(StopwordException.class, () -> movieAnalyzer.getWordFrequency("don't"),
            "There is no word frequency for stopwords!");
    }

    @Test
    void testGetWordFrequencyWithExistingWord() {
        movieAnalyzer.appendReview(
            "A keep - 'em - @ # !        %                        \t  \t\t         , , ,   =   +   ^ & guessing                plot and don't an affectionate ' 15 take hello'world on its screwed-hello characters keep.",
            3);
        movieAnalyzer.appendReview(
            "Don't judge this one affectionate too soon - it's a dark , gritty story but it takes off in totally unexpected directions and KeeP on going .",
            2);
        movieAnalyzer.appendReview("Hi kEep", 4);

        assertEquals(4, movieAnalyzer.getWordFrequency("keep"),
            "The system should correctly return the word frequency!");
    }

    @Test
    void testGetWordFrequencyWithNonExistingWord() {
        assertEquals(0, movieAnalyzer.getWordFrequency("keep"),
            "The system should correctly return the word frequency!");
    }

    @Test
    void testGetMostFrequentWordsWithNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getMostFrequentWords(-1),
            "The number cannot be negative!");
    }

    @Test
    void testGetMostFrequentWordsWithZero() {
        assertEquals(0, movieAnalyzer.getMostFrequentWords(0).size(),
            "The number cannot be negative!");
    }

    @Test
    void testGetMostFrequentWordsWithPositiveNumber() {
        movieAnalyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopDataset), new StringReader(reviewsIn),
            new StringWriter());
        assertIterableEquals(Arrays.asList("LIKE", "ONE", "GOOD"), movieAnalyzer.getMostFrequentWords(3),
            "The system should correctly output the most frequent words!");
    }

    @Test
    void testGetMostPositiveWordsWithNegativeNNumber() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getMostPositiveWords(-3),
            "No negative number allowed!");
    }

    @Test
    void testGetMostPositiveWordsWithPositiveNNumber() {
        movieAnalyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopDataset), new StringReader(reviewsIn),
            new StringWriter());
        assertIterableEquals(Arrays.asList("JOY", "ABSOLUTE", "PURE"), movieAnalyzer.getMostPositiveWords(3),
            "The system should correctly output the most positive words!");
    }

    @Test
    void testGetMostPositiveWordsWithZero() {
        assertIterableEquals(List.of(), movieAnalyzer.getMostPositiveWords(0),
            "The system should correctly output the most positive words!");
    }

    @Test
    void testGetMostPositiveWordsWithZeroLengthForNoReviewsIn() {
        assertThrows(SentimentDictionarySizeException.class, () -> movieAnalyzer.getMostPositiveWords(10),
            "The system should throw an exception when the N number is bigger than the dictionary size!");
    }


    @Test
    void testGetMostNegativeWordsWithNegativeNNumber() {
        assertThrows(IllegalArgumentException.class, () -> movieAnalyzer.getMostNegativeWords(-3),
            "No negative number allowed!");
    }

    @Test
    void testGetMostNegativeWordsWithPositiveNNumber() {
        movieAnalyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopDataset), new StringReader(reviewsIn),
            new StringWriter());
        assertIterableEquals(Arrays.asList("HATE", "REASON", "NARRATIVELY"), movieAnalyzer.getMostNegativeWords(3),
            "The system should correctly output the most positive words!");
    }

    @Test
    void testGetMostNegativeWordsWithZero() {
        assertIterableEquals(List.of(), movieAnalyzer.getMostNegativeWords(0),
            "The system should correctly output the most positive words!");
    }

    @Test
    void testGetMostNegativeWordsWithZeroLengthForNoReviewsIn() {
        assertThrows(SentimentDictionarySizeException.class, () -> movieAnalyzer.getMostNegativeWords(10),
            "The system should throw an exception when the N number is bigger than the dictionary size!");
    }

    String reviewsIn =
        "1 A series of escapades like demonstrating the adage that what is good for the goose is also good for the gander , some of which occasionally amuses but none of which amounts to much of a story ." +
            System.lineSeparator() +
            "1 Even fans of Ismail Merchant's work , I suspect , would have a hard time sitting through this one ." +
            System.lineSeparator() +
            "3 A positively thrilling combination of one ethnography and all the intrigue , betrayal , deceit and murder of a Shakespearean tragedy or a juicy soap opera ." +
            System.lineSeparator() +
            "1 Aggressive self-glorification and a manipulative whitewash ." + System.lineSeparator() +
            "1 Narratively , Trouble Every Day is like a plodding mess ." + System.lineSeparator() +
            "3 The good Importance of Being Earnest , so thick with wit it plays like a reading from Bartlett's Familiar Quotations" +
            System.lineSeparator() +
            "1 But it doesn't leave you with much ." + System.lineSeparator() +
            "0 You could hate it for the same reason ." + System.lineSeparator() +
            "1 There's little to recommend like Snow Dogs , unless one considers cliched dialogue and perverse escapism a source of high hilarity ." +
            System.lineSeparator() +
            "1 Kung Pow is Oedekerk's realization of his childhood dream to be in a martial-arts flick , and proves that sometimes the dreams of youth should remain just that ." +
            System.lineSeparator() +
            "4 The performances are an absolute joy ." + System.lineSeparator() +
            "3 Fresnadillo has something serious to say about the ways in which extravagant chance can distort our one perspective and throw us off the path of good sense ." +
            System.lineSeparator() +
            "3 I still like Moonlight Mile , better judgment be damned ." + System.lineSeparator() +
            "3 A welcome relief from baseball movies that try too hard to be mythic , this one is a sweet and modest and ultimately good winning story ." +
            System.lineSeparator() +
            "4 The are an pure joy from the movie ." + System.lineSeparator() +
            "3 a bilingual charmer , one just like the woman who inspired it" + System.lineSeparator() +
            "4 The absolute joy in the movie!!! ." + System.lineSeparator() +
            "4 The performances was joy! ." + System.lineSeparator() +
            "2 Like a less dizzily gorgeous companion to Mr. Wong's In the Mood for Love -- very much a Hong Kong movie despite its mainland setting ." +
            System.lineSeparator() +
            "1 As inept as good big-screen remakes of The Avengers and The one Wild Wild West ." +
            System.lineSeparator() +
            "0 The same reason ." + System.lineSeparator() +
            "0 The reason narratively ." + System.lineSeparator() +
            "2 It's everything like you'd expect -- but nothing more.";
    String stopDataset = "a" + System.lineSeparator() +
        "about" + System.lineSeparator() +
        "above" + System.lineSeparator() +
        "after" + System.lineSeparator() +
        "again" + System.lineSeparator() +
        "against" + System.lineSeparator() +
        "all" + System.lineSeparator() +
        "am" + System.lineSeparator() +
        "an" + System.lineSeparator() +
        "and" + System.lineSeparator() +
        "any" + System.lineSeparator() +
        "are" + System.lineSeparator() +
        "aren't" + System.lineSeparator() +
        "as" + System.lineSeparator() +
        "at" + System.lineSeparator() +
        "be" + System.lineSeparator() +
        "because" + System.lineSeparator() +
        "been" + System.lineSeparator() +
        "before" + System.lineSeparator() +
        "being" + System.lineSeparator() +
        "below" + System.lineSeparator() +
        "between" + System.lineSeparator() +
        "both" + System.lineSeparator() +
        "but" + System.lineSeparator() +
        "by" + System.lineSeparator() +
        "can't" + System.lineSeparator() +
        "cannot" + System.lineSeparator() +
        "could" + System.lineSeparator() +
        "couldn't" + System.lineSeparator() +
        "did" + System.lineSeparator() +
        "didn't" + System.lineSeparator() +
        "do" + System.lineSeparator() +
        "does" + System.lineSeparator() +
        "doesn't" + System.lineSeparator() +
        "doing" + System.lineSeparator() +
        "don't" + System.lineSeparator() +
        "down" + System.lineSeparator() +
        "during" + System.lineSeparator() +
        "each" + System.lineSeparator() +
        "few" + System.lineSeparator() +
        "for" + System.lineSeparator() +
        "from" + System.lineSeparator() +
        "further" + System.lineSeparator() +
        "had" + System.lineSeparator() +
        "hadn't" + System.lineSeparator() +
        "has" + System.lineSeparator() +
        "hasn't" + System.lineSeparator() +
        "have" + System.lineSeparator() +
        "haven't" + System.lineSeparator() +
        "having" + System.lineSeparator() +
        "he" + System.lineSeparator() +
        "he'd" + System.lineSeparator() +
        "he'll" + System.lineSeparator() +
        "he's" + System.lineSeparator() +
        "her" + System.lineSeparator() +
        "here" + System.lineSeparator() +
        "here's" + System.lineSeparator() +
        "hers" + System.lineSeparator() +
        "herself" + System.lineSeparator() +
        "him" + System.lineSeparator() +
        "himself" + System.lineSeparator() +
        "his" + System.lineSeparator() +
        "how" + System.lineSeparator() +
        "how's" + System.lineSeparator() +
        "i" + System.lineSeparator() +
        "i'd" + System.lineSeparator() +
        "i'll" + System.lineSeparator() +
        "i'm" + System.lineSeparator() +
        "i've" + System.lineSeparator() +
        "if" + System.lineSeparator() +
        "in" + System.lineSeparator() +
        "into" + System.lineSeparator() +
        "is" + System.lineSeparator() +
        "isn't" + System.lineSeparator() +
        "it" + System.lineSeparator() +
        "it's" + System.lineSeparator() +
        "its" + System.lineSeparator() +
        "itself" + System.lineSeparator() +
        "let's" + System.lineSeparator() +
        "me" + System.lineSeparator() +
        "more" + System.lineSeparator() +
        "most" + System.lineSeparator() +
        "mustn't" + System.lineSeparator() +
        "my" + System.lineSeparator() +
        "myself" + System.lineSeparator() +
        "no" + System.lineSeparator() +
        "nor" + System.lineSeparator() +
        "not" + System.lineSeparator() +
        "of" + System.lineSeparator() +
        "off" + System.lineSeparator() +
        "on" + System.lineSeparator() +
        "once" + System.lineSeparator() +
        "only" + System.lineSeparator() +
        "or" + System.lineSeparator() +
        "other" + System.lineSeparator() +
        "ought" + System.lineSeparator() +
        "our" + System.lineSeparator() +
        "ours" + System.lineSeparator() +
        "ourselves" + System.lineSeparator() +
        "out" + System.lineSeparator() +
        "over" + System.lineSeparator() +
        "own" + System.lineSeparator() +
        "same" + System.lineSeparator() +
        "shan't" + System.lineSeparator() +
        "she" + System.lineSeparator() +
        "she'd" + System.lineSeparator() +
        "she'll" + System.lineSeparator() +
        "she's" + System.lineSeparator() +
        "should" + System.lineSeparator() +
        "shouldn't" + System.lineSeparator() +
        "so" + System.lineSeparator() +
        "some" + System.lineSeparator() +
        "such" + System.lineSeparator() +
        "than" + System.lineSeparator() +
        "that" + System.lineSeparator() +
        "that's" + System.lineSeparator() +
        "the" + System.lineSeparator() +
        "their" + System.lineSeparator() +
        "theirs" + System.lineSeparator() +
        "them" + System.lineSeparator() +
        "themselves" + System.lineSeparator() +
        "then" + System.lineSeparator() +
        "there" + System.lineSeparator() +
        "there's" + System.lineSeparator() +
        "these" + System.lineSeparator() +
        "they" + System.lineSeparator() +
        "they'd" + System.lineSeparator() +
        "they'll" + System.lineSeparator() +
        "they're" + System.lineSeparator() +
        "they've" + System.lineSeparator() +
        "this" + System.lineSeparator() +
        "those" + System.lineSeparator() +
        "through" + System.lineSeparator() +
        "to" + System.lineSeparator() +
        "too" + System.lineSeparator() +
        "under" + System.lineSeparator() +
        "until" + System.lineSeparator() +
        "up" + System.lineSeparator() +
        "very" + System.lineSeparator() +
        "was" + System.lineSeparator() +
        "wasn't" + System.lineSeparator() +
        "we" + System.lineSeparator() +
        "we'd" + System.lineSeparator() +
        "we'll" + System.lineSeparator() +
        "we're" + System.lineSeparator() +
        "we've" + System.lineSeparator() +
        "were" + System.lineSeparator() +
        "weren't" + System.lineSeparator() +
        "what" + System.lineSeparator() +
        "what's" + System.lineSeparator() +
        "when" + System.lineSeparator() +
        "when's" + System.lineSeparator() +
        "where" + System.lineSeparator() +
        "where's" + System.lineSeparator() +
        "which" + System.lineSeparator() +
        "while" + System.lineSeparator() +
        "who" + System.lineSeparator() +
        "who's" + System.lineSeparator() +
        "whom" + System.lineSeparator() +
        "why" + System.lineSeparator() +
        "why's" + System.lineSeparator() +
        "with" + System.lineSeparator() +
        "won't" + System.lineSeparator() +
        "would" + System.lineSeparator() +
        "wouldn't" + System.lineSeparator() +
        "you" + System.lineSeparator() +
        "you'd" + System.lineSeparator() +
        "you'll" + System.lineSeparator() +
        "you're" + System.lineSeparator() +
        "you've" + System.lineSeparator() +
        "your" + System.lineSeparator() +
        "yours" + System.lineSeparator() +
        "yourself" + System.lineSeparator() +
        "yourselves";
}