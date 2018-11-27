package Domain.Mediator;

import Domain.Model.Cd;
import Domain.Model.CdList;

import java.io.IOException;

public interface CdPersistence
{
   public CdList load() throws IOException;
   public void save(CdList cdList) throws IOException;
   public void save(Cd cd) throws IOException;
   public void remove(Cd cd) throws IOException;
   public void clear() throws IOException;
}
