package bg.sofia.uni.fmi.mjt.netflix;

import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NetflixRecommenderTest {
    Reader reader = new StringReader(
        "id,title,type,description,release_year,runtime,genres,seasons,imdb_id,imdb_score,imdb_votes" +
            System.lineSeparator() +
            "tm84618,Taxi Driver,MOVIE,A mentally unstable Vietnam War veteran works as a night-time taxi driver in New York City where the perceived decadence and sleaze feed his urge for violent action.,1976,114,['drama'; 'crime'],-1,tt0075314,8.2,808582.0" +
            System.lineSeparator() +
            "tm119281,Bonnie and Clyde,MOVIE,In the 1930s; bored waitress Bonnie Parker falls in love with an ex-con named Clyde Barrow and together they start a violent crime spree through the country; stealing cars and robbing banks.,1967,110,['crime'; 'drama'; 'action'],-1,tt0061418,7.7,112048.0" +
            System.lineSeparator() +
            "ts22164,Monty Python's Flying Circus,SHOW,A British sketch comedy series with the shows being composed of surreality; risqué or innuendo-laden humour; sight gags and observational sketches without punchlines.,1969,30,['comedy'; 'european'],1,tt0063929,8.8,73424.0");
    NetflixRecommender netflixRecommender = new NetflixRecommender(reader);

    @Test
    void testConstructorBadReader() {
        assertThrows(RuntimeException.class, () -> new NetflixRecommender(new StringReader(null)),
            "Bad reader passed. Cannot invoke dataset!");
    }

    @Test
    void testConstructorWithWrongContentType() {
        Reader reader = new StringReader(
            "id,title,type,description,release_year,runtime,genres,seasons,imdb_id,imdb_score,imdb_votes" +
                System.lineSeparator() +
                "ts22164,Monty Python's Flying Circus,CINEMA,A British sketch comedy series with the shows being composed of surreality; risqué or innuendo-laden humour; sight gags and observational sketches without punchlines.,1969,30,['comedy'; 'european'],1,tt0063929,8.8,73424.0");
        assertThrows(IllegalArgumentException.class, () -> new NetflixRecommender(reader),
            "Wrong Content type in the dataset!");
    }

    @Test
    void testGetTheLongestMovie() {
        assertEquals(114, netflixRecommender.getTheLongestMovie().runtime(),
            "The recommender should return the longest movie correctly");
    }

    @Test
    void testGetTheLongestMovieForNoSuchElementException() {
        NetflixRecommender netflixRecommender1 = new NetflixRecommender(new StringReader(
            "id,title,type,description,release_year,runtime,genres,seasons,imdb_id,imdb_score,imdb_votes" +
                System.lineSeparator() +
                "ts22164,Monty Python's Flying Circus,SHOW,A British sketch comedy series with the shows being composed of surreality; risqué or innuendo-laden humour; sight gags and observational sketches without punchlines.,1969,30,['comedy'; 'european'],1,tt0063929,8.8,73424.0"));
        ;
        assertThrows(NoSuchElementException.class, () -> netflixRecommender1.getTheLongestMovie(),
            "The recommender should return the longest movie correctly");
    }

    @Test
    void testGetAllContent() {
        assertEquals(3, netflixRecommender.getAllContent().size(),
            "The recommender should return all content correctly");
    }

    @Test
    void testGetAllGenres() {
        assertEquals(5, netflixRecommender.getAllGenres().size());
    }

    @Test
    void testGroupContentByType() {
        assertEquals(2, netflixRecommender.groupContentByType().get(ContentType.MOVIE).size(),
            "The recommender should correctly group all content by type!");
    }

    @Test
    void testGetContentByKeywords() {
        assertEquals(0, netflixRecommender.getContentByKeywords("wAr", "CrImE").size(),
            "The recommender should correctly get contents by keywords!");
    }

    @Test
    void testGetTopNRatedContent() {
        assertEquals("Monty Python's Flying Circus", netflixRecommender.getTopNRatedContent(2).get(0).title(),
            "Should correctly return the first N elements!");
    }

    @Test
    void testGetTopNRatedContentForNegativeN() {
        assertThrows(IllegalArgumentException.class, () -> netflixRecommender.getTopNRatedContent(-1),
            "The first N elements cannot be below 0");
    }

    @Test
    void testGetSimilarContent() {
        List<String> genres = new ArrayList<>();
        genres.add("drama");
        genres.add("crime");
        assertEquals(2,
            netflixRecommender.getSimilarContent(new Content("", "", ContentType.MOVIE, "", 0, 1, genres, -1, "", 1, 1))
                .size(),
            "The recommender should correctly get similar contents from the given content!");
    }

}
