package Domain.Mediator;

import Domain.Model.Cd;
import Domain.Model.CdList;

import java.io.IOException;

/**
 * Keeps the Domain.Model's state - in this case only a CdList instance variable
 */
public class CdModelManager implements CdModel
{
	CdList cdList;
	CdPersistence storage;

	public CdModelManager()
	{
		try
		{
			storage = new CdTextFile("src/cds.txt");
			cdList = storage.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public CdList getAll()
	{
		return cdList;
	}

	@Override
	public void addCd(Cd cd)
	{
		cdList.addCd(cd);
	}

	@Override
	public Cd removeCd(String title)
	{
		cdList.removeFirstCdByTitle(title);
		return cdList.getCdsByTitle(title).getCD(0);
	}

	@Override
	public Cd getCd(int index)
	{
		return cdList.getCD(index);
	}

	@Override
	public CdList getCds(String title)
	{
		return cdList.getCdsByTitle(title);
	}

	@Override
	public int getNumberOfCds()
	{
		return 0;
	}
}
