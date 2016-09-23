package com.czmec.softwarecontest.drawpannel.touch;

public interface TouchListener{
	void pointerPressed(float x, float y);
	
	void pointerReleased();

	void pointerDragged(float x, float y);

}