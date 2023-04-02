package bg.sofia.uni.fmi.mjt.netflix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class NetflixRecommender {

    private final Set<Content> netflixContent;

    public NetflixRecommender(Reader reader) {
        this.netflixContent = new HashSet<>();

        String line;
        try (var bufferedReader = new BufferedReader(reader)) {
            line = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                addDataToContentList(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("There was problem while reading from the reader", e);
        }
    }

    public List<Content> getAllContent() {
        return List.copyOf(netflixContent);
    }

    public List<String> getAllGenres() {
        List<String> list = netflixContent.stream()
            .map(Content::genres)
            .collect(ArrayList::new, List::addAll, List::addAll);

        return list.stream()
            .distinct()
            .toList();
    }

    public Content getTheLongestMovie() {
        return netflixContent.stream()
            .filter(c -> c.type() == ContentType.MOVIE)
            .max(Comparator.comparingInt(Content::runtime))
            .orElseThrow(() -> new NoSuchElementException("There are no such movies!"));
    }

    public Map<ContentType, Set<Content>> groupContentByType() {
        Map<ContentType, Set<Content>> result = new HashMap<>();

        result.put(ContentType.MOVIE, netflixContent.stream()
            .filter(c -> c.type() == ContentType.MOVIE)
            .collect(Collectors.toSet()));

        result.put(ContentType.SHOW, netflixContent.stream()
            .filter(c -> c.type() == ContentType.SHOW)
            .collect(Collectors.toSet()));

        return result;
    }

    public List<Content> getTopNRatedContent(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The top n cannot be below zero!");
        }

        final int tunable = 10_000;

        double c = netflixContent.stream()
            .mapToDouble(Content::imdbScore)
            .average()
            .getAsDouble();


        return netflixContent.stream()
            .sorted((g1, g2) -> Double.compare(
                ((g2.imdbVotes() / (g2.imdbVotes() + tunable)) * (g2.imdbVotes() == 0 ? 0 : g2.imdbScore()) +
                    (tunable / (g2.imdbVotes() + tunable)) * c),
                ((g1.imdbVotes() / (g1.imdbVotes() + tunable)) * (g1.imdbVotes() == 0 ? 0 : g1.imdbScore()) +
                    (tunable / (g1.imdbVotes() + tunable)) * c)))
            .limit(n)
            .toList();
    }

    public List<Content> getSimilarContent(Content content) {
        return netflixContent.stream()
            .filter(c -> c.type() == content.type())
            .sorted((g1, g2) -> Integer.compare(getSimilarities(g2.genres(), content.genres()),
                getSimilarities(g1.genres(), content.genres())))
            .collect(Collectors.toList());
    }

    private int getSimilarities(List<String> list1, List<String> list2) {
        int similarities = 0;

        for (String genre : list1) {
            if (list2.contains(genre)) {
                similarities++;
            }
        }

        return similarities;
    }

    public Set<Content> getContentByKeywords(String... keywords) {
        return Set.copyOf(netflixContent.stream()
            .filter(c -> Arrays.stream(keywords)
                .allMatch(k -> c.description().toLowerCase().matches(".*\\b" + k.toLowerCase() + "\\b.*")))
            .collect(Collectors.toSet()));
    }

    private void addDataToContentList(String[] data) {
        String id = data[TypesOfContentData.ID.getNum()];
        String title = data[TypesOfContentData.title.getNum()];
        ContentType type = convertContentType(data[TypesOfContentData.type.getNum()]);
        String description = data[TypesOfContentData.description.getNum()];
        int releaseYear = Integer.parseInt(data[TypesOfContentData.releaseYear.getNum()]);
        int runtime = Integer.parseInt(data[TypesOfContentData.runtime.getNum()]);
        List<String> genres = convertToList(data[TypesOfContentData.genres.getNum()]);
        int seasons = Integer.parseInt(data[TypesOfContentData.seasons.getNum()]);
        String imdbId = data[TypesOfContentData.imdbId.getNum()];
        double imdbScore = Double.parseDouble(data[TypesOfContentData.imdbScore.getNum()]);
        double imdbVotes = Double.parseDouble(data[TypesOfContentData.imdbVotes.getNum()]);

        Content content =
            new Content(id, title, type, description, releaseYear, runtime, genres, seasons, imdbId, imdbScore,
                imdbVotes);

        netflixContent.add(content);
    }

    private ContentType convertContentType(String type) {
        if (type.equals("MOVIE")) {
            return ContentType.MOVIE;
        } else if (type.equals("SHOW")) {
            return ContentType.SHOW;
        }
        throw new IllegalArgumentException("The content type can only be MOVIE, or SHOW");
    }

    private List<String> convertToList(String list) {
        list = list.replace("[", "").replace("]", "")
            .replaceAll("'", "").replaceAll("\\s", "");

        String[] genresList = list.split(";");
        return List.of(genresList);
    }

}