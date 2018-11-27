package View;

import Controller.CdController;

public interface CdView
{
	void start(CdController cdController);

	void show(String value);

	String get(String what);
}
