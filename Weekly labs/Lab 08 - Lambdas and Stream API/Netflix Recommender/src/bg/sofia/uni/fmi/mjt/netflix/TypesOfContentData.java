package bg.sofia.uni.fmi.mjt.netflix;

import java.util.List;

public enum TypesOfContentData {
    ID(0),
    title(1),
    type(2),
    description(3),
    releaseYear(4),
    runtime(5),
     genres(6),
    seasons(7),
    imdbId(8),
    imdbScore(9),
    imdbVotes(10);

    private int num;

    TypesOfContentData(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
