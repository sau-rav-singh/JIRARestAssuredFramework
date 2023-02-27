package testUtilities;

/**
 * The TestConstants class contains static final string constants that are used by tests in the project.
 * These constants provide convenient access to file paths and other resources that are commonly used in tests.
 * By using constants, the codebase is more consistent and maintainable, and it is easier to update paths
 * or resources that may change over time.
 */
public final class TestConstants {

    /**
     * The directory where log files are stored.
     */
    public static final String LOG_FILE_PATH = "logs/";

    /**
     * The directory where test data files are stored.
     */
    public static final String DATA_FILE_PATH = "src/test/resources/data/";

    /**
     * The file path of the configuration properties file used in tests.
     */
    public static final String CONFIG_PROPERTIES_PATH = "src/test/resources/properties/config.properties";

    /**
     * The file path of the JSON payload used for adding comments in tests.
     */
    public static final String ADD_COMMENT_JSON_PAYLOAD_PATH = "src/test/resources/data/json/addComment.json";

    /**
     * The file path of an invalid JSON payload used for testing error handling in adding comments.
     */
    public static final String INVALID_ADD_COMMENT_JSON_PAYLOAD_PATH = "src/test/resources/data/json/invalidAddComment.json";
    
    /**
     * Private constructor to prevent instantiation of the class.
     */
    private TestConstants() {
        throw new AssertionError("Cannot instantiate TestConstants");
    }
}
