package View;

import Controller.CdController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener
{
	private CdGUI gui;
	CdController cdController;

	public ButtonHandler(CdController cdController, CdGUI gui)
	{
		this.gui = gui;
		this.cdController = cdController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		cdController.execute(e);
	}
}