package it.assessment.pet.interfaces;

public class PathConfig {

    // common
    private static final String API_VERSION_1 = "/v1";

    private static final String BASE_PATH = "/api";

    public static final String BASE_PATH_V1 = BASE_PATH + API_VERSION_1;

    // pet
    public static final String PET_PATH = "/pets";

    private PathConfig() {
        throw new IllegalStateException("Utility class");
    }
}
