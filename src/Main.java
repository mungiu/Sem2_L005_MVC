import Controller.CdController;
import Domain.Mediator.CdModel;
import Domain.Mediator.CdModelManager;
import View.CdGUI;
import View.CdView;

public class Main
{
	public static void main(String args[])
	{
		CdModel model = (CdModel) new CdModelManager();
		CdView view = new CdGUI();
		CdController controller = new CdController(model, view);
		view.start(controller);
	}
}
