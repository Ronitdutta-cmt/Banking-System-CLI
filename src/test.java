import java.sql.Connection;
import java.sql.DriverManager;

public class test {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/banking_system",
            "root",
            "lolrofl"
        );

        System.out.println("Connected!");
        con.close();
    }
}