import java.sql.*;
import java.util.ArrayList;

public class MAINCreateCdDatabase
{
   public static void main(String[] args) throws SQLException,
         ClassNotFoundException
   {
      MyDatabase db = new MyDatabase("org.postgresql.Driver",
            "jdbc:postgresql://localhost:5432/postgres", "postgres", "vis");

      String sql = "CREATE SCHEMA IF NOT EXISTS CDList;";
      db.update(sql);

      sql = "CREATE TABLE IF NOT EXISTS CDList.cd ("
            + "  ID SERIAL PRIMARY KEY,"
            + "  Artist CHARACTER VARYING (50) NOT NULL,"
            + "  Title CHARACTER VARYING (50) NOT NULL" + ");";
      db.update(sql);

      sql = "CREATE TABLE IF NOT EXISTS CDList.track ("
            + "  ID SERIAL PRIMARY KEY," + "  cdID int NOT NULL,"
            + "  Artist CHARACTER VARYING (50) NOT NULL,"
            + "  Title CHARACTER VARYING (50) NOT NULL,"
            + "  Length int NOT NULL,"
            + "  FOREIGN KEY (cdID) REFERENCES CDList.cd(ID)" + ");";
      db.update(sql);

      String[] artists = { "Beatles", "Various Artists", "AC/DC", "Bob" };
      String[] titles = { "Best Of", "Mixed", "Best Of", "My House" };
      int[] ids = new int[artists.length];

      for (int i = 0; i < artists.length; i++)
      {
         sql = "INSERT INTO CdList.cd (Artist, Title) " + "VALUES (? , ?);";
         db.update(sql, artists[i], titles[i]);
         sql = "SELECT ID From CdList.cd WHERE Artist = ? AND Title = ?;";
         ArrayList<Object[]> idRes = db.query(sql, artists[i], titles[i]);
         ids[i] = Integer.parseInt(idRes.get(0)[0].toString());
      }

      sql = "INSERT INTO CdList.track (cdID, Artist, Title, Length) "
            + "VALUES (? , ?, ?, ?);";

      db.update(sql, ids[0], "Beatles", "Help", 3 * 60 + 32);
      db.update(sql, ids[0], "Beatles", "She loves you", 2 * 60 + 51);
      db.update(sql, ids[0], "Beatles", "Michelle", 3 * 60 + 5);
      db.update(sql, ids[1], "Kiss", "A World without heroes", 2*60 + 13);
      db.update(sql, ids[1], "Indigo Girls", " Galileo ", 3 * 60 + 50);
      db.update(sql, ids[1], "Elton John", "Circle of Life", 4 * 60 + 34);
      db.update(sql, ids[2], "AC/DC", "Thunderstruck", 4 * 60 + 15);
      db.update(sql, ids[2], "AC/DC", "Back in Black", 4 * 60 + 23);
      db.update(sql, ids[3], "Bob and Wendy", "See me", 8 * 60 + 59);
      db.update(sql, ids[3], "Wendy", "See you", 9 * 60 + 25);

      // Check result:

      sql = "SELECT cd.Artist, cd.Title, track.Artist, track.Title, Length FROM CDList.cd, CDList.track "
            + " WHERE CDList.track.cdID = CDList.cd.ID;";
      ArrayList<Object[]> results = db.query(sql);
      for (int i = 0; i < results.size(); i++)
      {
         Object[] row = results.get(i);
         for (int j = 0; j < row.length; j++)
         {
            System.out.print(row[j] + ", ");
         }
         System.out.println();
      }
   }

}
