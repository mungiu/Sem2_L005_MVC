package Controller;

import Domain.Mediator.CdModel;
import Domain.Model.Cd;
import Domain.Model.CdList;
import View.CdView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CdController
{
	private CdModel model;
	private CdView view;

	public CdController(CdModel model, CdView view)
	{
		this.model = model;
		this.view = view;
	}

	public void execute(ActionEvent e)
	{
		if (!(e.getSource() instanceof JButton))
			return;

		if (((JButton) e.getSource()).getText().startsWith("List"))
		{
			view.show("" + model.getAll());
		}
		else if (((JButton) e.getSource()).getText().startsWith("Add"))
		{
			// TODO implement method
			String msg = "Add method - not implemented";
			view.show(msg);
		}
		else if (((JButton) e.getSource()).getText().startsWith("Remove"))
		{
			String input = view.get("title");
			if (input == null)
				return;
			String msg = "";
			Cd cd = model.removeCd(input);
			if (cd != null)
			{
				msg = "REMOVED: \n" + cd.toString();
			}
			else
			{
				msg = "No CD with title: \"" + input + "\" found";
			}
			view.show(msg);
		}
		else if (((JButton) e.getSource()).getText().startsWith("Search"))
		{
			String input = view.get("title");
			if (input == null)
				return;
			String msg = "";
			CdList list = model.getCds(input);
			for (int i = 0; i < list.getNumberOfCds(); i++)
			{
				msg += list.getCD(i) + "\n\n\n";
			}
			if (list.getNumberOfCds() == 0)
			{
				msg = "No CD with title: \"" + input + "\" found";
			}
			view.show(msg);
		}
		else if (((JButton) e.getSource()).getText().startsWith("Quit"))
		{
			System.exit(0);
		}
	}
}
