package com.czmec.softwarecontest.drawpannel.dollar;

/**
 * 点
 */
public class Point
{
	public double X, Y;
	
	public Point(double x, double y)
	{	this.X = x; this.Y = y;	}
	
	public void copy(Point src)
	{
		X = src.X;
		Y = src.Y;
	}	
}
