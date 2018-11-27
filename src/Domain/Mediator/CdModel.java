package Domain.Mediator;

import Domain.Model.Cd;
import Domain.Model.CdList;

public interface CdModel
{
	CdList getAll();

	void addCd(Cd cd);

	Cd removeCd(String title);

	Cd getCd(int index);

	CdList getCds(String title);

	int getNumberOfCds();
}
