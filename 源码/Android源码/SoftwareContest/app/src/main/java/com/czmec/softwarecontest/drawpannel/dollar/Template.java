package com.czmec.softwarecontest.drawpannel.dollar;

import java.util.*;

/**
 * 模板
 */
class Template
{
	final String Name;
	Vector Points;

	Template(String name, Vector points) 
	{
		this.Name = name;
		this.Points = Utils.Resample(points, Recognizer.NumPoints);
		this.Points = Utils.RotateToZero(this.Points);
		this.Points = Utils.ScaleToSquare(this.Points, Recognizer.SquareSize);
		this.Points = Utils.TranslateToOrigin(this.Points);		
	}
}
