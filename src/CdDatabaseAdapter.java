import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Domain.Model.Cd;
import Domain.Model.CdList;
import Domain.Mediator.CdPersistence;
import Domain.Model.CdTrack;
import Domain.Model.Time;

public class CdDatabaseAdapter implements CdPersistence
{
   private MyDatabase db;
   private static final String DRIVER = "org.postgresql.Driver";
   private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
   private static final String USER = "postgres";
   private static final String PASSWORD = "vis";

   public CdDatabaseAdapter() throws ClassNotFoundException
   {
      this.db = new MyDatabase(DRIVER, URL, USER, PASSWORD);
   }

   @Override
   public CdList load() throws IOException
   {
      String sql = "SELECT cd.ID, cd.Artist, cd.Title, track.Artist, track.Title,"
            + " Length FROM CDList.cd, CDList.track "
            + " WHERE CDList.track.cdID = CDList.cd.ID ORDER BY cd.ID;";
      ArrayList<Object[]> results;
      CdList cds = new CdList();
      ArrayList<CdTrack> tracks = new ArrayList<>();
      String artist = "?", cdArtist = "?";
      String title = "?", cdTitle = "?";
      int oldId = 0;
      try
      {
         results = db.query(sql);
         for (int i = 0; i < results.size(); i++)
         {
            Object[] row = results.get(i);
             int id = Integer.parseInt(row[0].toString());
            cdArtist = artist;
            cdTitle = title;
            artist = row[1].toString();
            title = row[2].toString();
            String trackArtist = row[3].toString();
            String trackTitle = row[4].toString();
            int length = Integer.parseInt(row[5].toString());
            Time time = new Time(length);
            CdTrack track = new CdTrack(trackTitle, trackArtist, time);
            if (i == results.size() - 1)
            {
               if (id != oldId)
               {
                  tracks.clear();
               }
               CdTrack[] dummy = new CdTrack[0];
               tracks.add(track);
               Cd cd = new Cd(cdTitle, cdArtist, tracks.toArray(dummy));
               cds.addCd(cd);
            }
            if (i > 0 && id != oldId)
            {
               CdTrack[] dummy = new CdTrack[0];
               Cd cd = new Cd(cdTitle, cdArtist, tracks.toArray(dummy));
               cds.addCd(cd);
               tracks.clear();
               tracks.add(track);
            }
            else
            {
               tracks.add(track);
            }
            oldId = id;
         }

      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }

      return cds;
   }

   @Override
   public synchronized void save(CdList cdList) throws IOException
   {
      for (int i = 0; i < cdList.getNumberOfCds(); i++)
      {
         save(cdList.getCD(i));
      }
   }

   @Override
   public synchronized void save(Cd cd) throws IOException
   {

      try
      {
         String sql = "SELECT ID From CdList.cd WHERE Artist = ? AND Title = ?;";
         ArrayList<Object[]> results = db.query(sql, cd.getArtist(),
               cd.getTitle());

         if (results.size() > 0) // not a new cd
         {
            return; // do nothing
         }

         sql = "INSERT INTO CdList.cd (Artist, Title) " + "VALUES (? , ?);";
         db.update(sql, cd.getArtist(), cd.getTitle());

         sql = "SELECT ID From CdList.cd WHERE Artist = ? AND Title = ?;";
         results = db.query(sql, cd.getArtist(), cd.getTitle());
         int id = Integer.parseInt(results.get(0)[0].toString());

         sql = "INSERT INTO CdList.track (CdID, Artist, Title, Length) "
               + "VALUES (? , ?, ?, ?);";

         for (int i = 0; i < cd.getnumberOfTracks(); i++)
         {
            CdTrack track = cd.getTrack(i);
            int length = track.getLength().getTimeInSeconds();
            db.update(sql, id, track.getArtist(), track.getTitle(), length);
         }
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
   }

   @Override
   public void remove(Cd cd) throws IOException
   {
      try
      {
         String sql = "DELETE FROM CdList.track WHERE exists("
               + " SELECT 1 FROM CdList.cd WHERE CdList.track.CdID = CdList.cd.ID "
               + " AND CdList.cd.title = ? AND CdList.cd.artist = ?);";
         db.update(sql, cd.getTitle(), cd.getArtist());
         sql = "DELETE FROM CdList.cd WHERE CdList.cd.title = ? AND CdList.cd.artist = ?;";
         db.update(sql, cd.getTitle(), cd.getArtist());
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
   }

   @Override
   public void clear() throws IOException
   {
      try
      {
         String sql = "TRUNCATE TABLE CdList.track CASCADE;";
         db.update(sql);
         sql = "TRUNCATE TABLE CdList.cd CASCADE;";
         db.update(sql);
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
   }

}
