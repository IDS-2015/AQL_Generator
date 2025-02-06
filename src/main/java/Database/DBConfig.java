package Database;

public class DBConfig {
    
    private boolean connectToServer = false;

    // URL, USER, and PASSWORD fields
    public static final String URL;
    public static final String USER;
    public static final String PASSWORD;

    static {
        // Check the boolean to determine which connection to use
        if (new DBConfig().connectToServer) {
            // Server connection
            URL = "jdbc:mysql://20.81.45.81:3306/ids_aql_generator";
            USER = "coc_user";
            PASSWORD = "m3fzx2gG4HMbpCWKBdQuJt";
        } else {
            // Local connection
            URL = "jdbc:mysql://localhost:3306/ids_aql_generator";
            USER = "root";
            PASSWORD = "rootuser";
        }
    }

    // Constructor
    public DBConfig() {
    }
}
