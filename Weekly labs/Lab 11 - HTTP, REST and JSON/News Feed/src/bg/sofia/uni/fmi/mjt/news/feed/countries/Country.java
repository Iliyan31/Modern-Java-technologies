package bg.sofia.uni.fmi.mjt.news.feed.countries;

public enum Country {
    AE, AR, AT, AU, BE, BG, BR, CA, CH, CN,
    CO, CU, CZ, DE, EG, FR, GB, GR, HK, HU,
    ID, IE, IL, IN, IT, JP, KR, LT, LV, MA,
    MX, MY, NG, NL, NO, NZ, PH, PL, PT, RO,
    RS, RU, SA, SE, SG, SI, SK, TH, TR, TW,
    UA, US, VE, ZA;

    public static String getCountry(Country country) {
        return country.toString().toLowerCase();
    }
}