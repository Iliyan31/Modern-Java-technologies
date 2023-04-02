package bg.sofia.uni.fmi.mjt.news.feed.queries;

import bg.sofia.uni.fmi.mjt.news.feed.categories.Category;
import bg.sofia.uni.fmi.mjt.news.feed.countries.Country;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageNumberException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageSizeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryParametersTest {
    @Test
    void testNullKeyword() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("null", null).build(),
            "The query cannot have null keyword!");
    }

    @Test
    void testEmptyKeyword() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("null", "").build(),
            "The query cannot have empty keyword!");
    }

    @Test
    void testBlankKeyword() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("null", " ").build(),
            "The query cannot have blank keyword!");
    }

    @Test
    void testKeywordsLength() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("null").build(),
            "At least one keyword should be passed to the query!");
    }

    @Test
    void testNullApiKey() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder(null, "null").build(),
            "The query cannot have null api key!");
    }

    @Test
    void testEmptyApiKey() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder(" ", "A").build(),
            "The query cannot have empty api key!");
    }

    @Test
    void testBlankApiKey() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder(" ", "A").build(),
            "The query cannot have blank api key!");
    }

    @Test
    void testNullCategory() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("a", "null").setCategory(null).build(),
            "The query cannot have null category!");
    }

    @Test
    void testEmptyCategory() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("a", "null").setCategory("").build(),
            "The query cannot have empty category!");
    }

    @Test
    void testBlankCategory() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("a", "null").setCategory(" ").build(),
            "The query cannot have blank category!");
    }

    @Test
    void testNullCountry() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("a", "null").setCountry(null).build(),
            "The query cannot have null country!");
    }

    @Test
    void testEmptyCountry() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("a", "null").setCountry("").build(),
            "The query cannot have empty country!");
    }

    @Test
    void testBlankCountry() {
        assertThrows(IllegalArgumentException.class, () -> Query.builder("a", "null").setCountry(" ").build(),
            "The query cannot have blank country!");
    }

    @Test
    void testQueryOnlyKeyword() {
        assertEquals("q=one&apiKey=hi", Query.builder("hi", "one").build().getQueryString(),
            "The system should correctly create the query with only keyword!");
    }

    @Test
    void testQueryWithKeywordAndCategory() {
        assertEquals("q=one&category=business&apiKey=hi",
            Query.builder("hi", "one")
                .setCategory(Category.getCategoryName(Category.BUSINESS))
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword and category!");
    }

    @Test
    void testQueryWithKeywordAndCountry() {
        assertEquals("q=one&country=bg&apiKey=hi",
            Query.builder("hi", "one")
                .setCountry(Country.getCountry(Country.BG))
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword and country!");
    }

    @Test
    void testQueryWithKeywordCategoryAndCountry() {
        assertEquals("q=one&category=technology&country=bg&apiKey=hi",
            Query.builder("hi", "one")
                .setCategory(Category.getCategoryName(Category.TECHNOLOGY))
                .setCountry(Country.getCountry(Country.BG))
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword, category and country!");
    }

    @Test
    void testQueryWithMoreKeywordsCategoryAndCountry() {
        assertEquals("q=one+two+three&category=technology&country=bg&apiKey=hi",
            Query.builder("hi", "one", "two", "three")
                .setCategory(Category.getCategoryName(Category.TECHNOLOGY))
                .setCountry(Country.getCountry(Country.BG))
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword, category and country!");
    }

    @Test
    void testQueryWithMoreKeywordsCategoryAndCountryAndAddMoreKeywords() {
        assertEquals("q=one+two+three+four+five&category=technology&country=bg&apiKey=hi",
            Query.builder("hi", "one", "two", "three")
                .setCategory(Category.getCategoryName(Category.TECHNOLOGY))
                .setCountry(Country.getCountry(Country.BG))
                .setKeyword("four")
                .setKeyword("five")
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword, category and country!");
    }

    @Test
    void testQueryWithKeywordsCategoryCountryAndPageNumber() {
        assertEquals("q=one+two+three+four+five&category=technology&country=bg&page=1&apiKey=hi",
            Query.builder("hi", "one", "two", "three")
                .setCategory(Category.getCategoryName(Category.TECHNOLOGY))
                .setCountry(Country.getCountry(Country.BG))
                .setKeyword("four")
                .setKeyword("five")
                .setPage(1)
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword, category, country and page number!");
    }

    @Test
    void testQueryWithKeywordsCategoryCountryAndPageSize() {
        assertEquals("q=one+two+three+four+five&category=technology&country=bg&pageSize=20&apiKey=hi",
            Query.builder("hi", "one", "two", "three")
                .setCategory(Category.getCategoryName(Category.TECHNOLOGY))
                .setCountry(Country.getCountry(Country.BG))
                .setKeyword("four")
                .setKeyword("five")
                .setPageSize(20)
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword, category, country and page size!");
    }

    @Test
    void testQueryWithKeywordsCategoryCountryPageNumberAndPageSize() {
        assertEquals("q=one+two+three+four+five&category=technology&country=bg&page=1&pageSize=20&apiKey=hi",
            Query.builder("hi", "one", "two", "three")
                .setCategory(Category.getCategoryName(Category.TECHNOLOGY))
                .setCountry(Country.getCountry(Country.BG))
                .setKeyword("four")
                .setKeyword("five")
                .setPageSize(20)
                .setPage(1)
                .build()
                .getQueryString(),
            "The system should correctly create the query with keyword, category, country, page number and page size!");
    }

    @Test
    void testNegativePageNumber() {
        assertThrows(PageNumberException.class, () -> Query.builder("a", "null").setPage(-1).build(),
            "The query cannot have negative page number!");
    }

    @Test
    void testPageNumberAbove3() {
        assertThrows(PageNumberException.class, () -> Query.builder("a", "null").setPage(4).build(),
            "The query cannot have page number above 3!");
    }

    @Test
    void testNegativePageSize() {
        assertThrows(PageSizeException.class, () -> Query.builder("a", "null").setPageSize(-1).build(),
            "The query cannot have negative page size!");
    }

    @Test
    void testPageSizeAbove50() {
        assertThrows(PageSizeException.class, () -> Query.builder("a", "null").setPageSize(51).build(),
            "The query cannot have page size above 3!");
    }
}