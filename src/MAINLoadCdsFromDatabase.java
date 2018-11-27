import Domain.Model.CdList;
import Domain.Mediator.CdPersistence;
import Domain.Mediator.CdTextFile;

import java.io.IOException;

public class MAINLoadCdsFromDatabase
{
   public static void main(String args[]) 
   {
      CdPersistence storage;
      CdList list;
      try
      {
         storage = new CdDatabaseAdapter(); // load from database
         list = storage.load();
         System.out.println("LOADED FROM DATABASE:");
      }
      catch (IOException | ClassNotFoundException e)
      {
         try
         {
            storage = new CdTextFile("src/cds.txt"); // load from file
            list = storage.load();
            System.out.println("LOADED FROM FILE:");
         }
         catch (IOException e2)
         {
            list = new CdList(); // empty
            System.out.println("EMPTY LIST (something went wrong)");
         }
      }
      if (list == null)
      {
         list = new CdList(); // empty
      }
      
      System.out.println(list);
   }
}
