import java.sql.*;

public class conn {

   Connection c;
   Statement s;
    public conn() {
        try{
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankmanagementsystem", "database_name", "database_password");
            s = c.createStatement();

        } catch (Exception e){
            System.out.println(e);
        }
    }
}
