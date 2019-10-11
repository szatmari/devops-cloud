package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private static final String USER = "root";
    private static final String PASSWORD = "devops";
    private static final String HOSTNAME = System.getenv().get("DBHOST");
    private static final int PORT = 3306;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/sql")
    public ArrayList<String> sql()
    {
        final String url = String.format(
            "jdbc:mysql://%s:%d/golf?allowPublicKeyRetrieval=true&useSSL=false",
            HOSTNAME,
            PORT
        );

        ArrayList<String> myList = new ArrayList<String>();

        try (final Connection connection = DriverManager.getConnection(url, USER, PASSWORD)) {
            try (final Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT DISTINCT outlook FROM golf")) {
                    while (rs.next()) {
                        myList.add(rs.getString(1));
                    }
                }
            }
        } catch (Exception e)
        {
            myList.add("Error in database connection");
        }
        return myList;
    }

}
