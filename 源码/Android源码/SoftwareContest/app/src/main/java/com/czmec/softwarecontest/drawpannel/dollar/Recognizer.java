package com.czmec.softwarecontest.drawpannel.dollar;

import java.util.*;

/**
 * 识别
 */
class Recognizer
{
	//
	// Recognizer class constants
	//
	private final int NumTemplates = 16;
	public static final int NumPoints = 64;
	public static final double SquareSize = 250.0;
	private final double HalfDiagonal = 0.5 * Math.sqrt(250.0 * 250.0 + 250.0 * 250.0);
	public static final double Phi = 0.5 * (-1.0 + Math.sqrt(5.0)); // Golden Ratio

	public final Point centroid = new Point(0, 0);
	public final Rectangle boundingBox = new Rectangle(0, 0, 0, 0);
	final int[] bounds = { 0, 0, 0, 0 };
	private final Vector Templates = new Vector(NumTemplates);

	private static final int GESTURES_DEFAULT = 1;
	private static final int GESTURES_SIMPLE = 2;
	private static final int GESTURES_CIRCLES = 3;

	public Recognizer()
	{
		this(GESTURES_SIMPLE);
	}

	public Recognizer(int gestureSet)
	{
		switch(gestureSet)
		{
			case GESTURES_DEFAULT:
				loadTemplatesDefault(); break;

			case GESTURES_SIMPLE:
				loadTemplatesSimple();	break;

			case GESTURES_CIRCLES:
				loadTemplatesCircles();	break;
		}
	}

	private void loadTemplatesDefault()
	{
		Templates.addElement(loadTemplate("polygon", TemplateData.trianglePoints));
		Templates.addElement(loadTemplate("polygon", TemplateData.xPoints));
		Templates.addElement(loadTemplate("polygon", TemplateData.rectanglePointsCCW));
		Templates.addElement(loadTemplate("circle", TemplateData.circlePointsCCW));
		Templates.addElement(loadTemplate("check", TemplateData.checkPoints));
		Templates.addElement(loadTemplate("circle", TemplateData.caretPointsCW));
		Templates.addElement(loadTemplate("question", TemplateData.questionPoints));
		Templates.addElement(loadTemplate("arrow", TemplateData.arrowPoints));
		Templates.addElement(loadTemplate("line", TemplateData.leftSquareBracketPoints));
		Templates.addElement(loadTemplate("line", TemplateData.rightSquareBracketPoints));
		Templates.addElement(loadTemplate("v", TemplateData.vPoints));
		Templates.addElement(loadTemplate("circle", TemplateData.deletePoints));
		Templates.addElement(loadTemplate("line", TemplateData.leftCurlyBracePoints));
		Templates.addElement(loadTemplate("line", TemplateData.rightCurlyBracePoints));
		Templates.addElement(loadTemplate("polygon", TemplateData.starPoints));
		Templates.addElement(loadTemplate("line", TemplateData.pigTailPoints));
	}

	private void loadTemplatesSimple()
	{
		Templates.addElement(loadTemplate("circle", TemplateData.circlePointsCCW));
		Templates.addElement(loadTemplate("circle", TemplateData.circlePointsCW));
		Templates.addElement(loadTemplate("rectangle", TemplateData.rectanglePointsCCW));
		Templates.addElement(loadTemplate("rectangle", TemplateData.rectanglePointsCW));
		Templates.addElement(loadTemplate("caret", TemplateData.caretPointsCCW));
		Templates.addElement(loadTemplate("caret", TemplateData.caretPointsCW));
	}


	private void loadTemplatesCircles()
	{
		Templates.addElement(loadTemplate("circle", TemplateData.circlePointsCCW));
		Templates.addElement(loadTemplate("circle", TemplateData.circlePointsCW));
	}

	private Template loadTemplate(String name, int[] array)
	{
		return new Template(name, loadArray(array));
	}

	private Vector loadArray(int[] array)
	{
		Vector v = new Vector(array.length/2);
		for (int i = 0; i < array.length; i+= 2)
		{
			Point p = new Point(array[i], array[i+1]);
			v.addElement(p);
		}

	//	System.out.println(v.size() + " " + array.length);

		return v;
	}

	public Result Recognize(Vector points)
	{
		points = Utils.Resample(points, NumPoints);
		points = Utils.RotateToZero(points, centroid, boundingBox);
		points = Utils.ScaleToSquare(points, SquareSize);
		points = Utils.TranslateToOrigin(points);

		bounds[0] = (int)boundingBox.X;
		bounds[1] = (int)boundingBox.Y;
		bounds[2] = (int)boundingBox.X + (int)boundingBox.Width;
		bounds[3] = (int)boundingBox.Y + (int)boundingBox.Height;

		int t = 0;

		double b = Double.MAX_VALUE;
		for (int i = 0; i < Templates.size(); i++)
		{
			double angleRange = 45.0;
			double anglePrecision = 2.0;
			double d = Utils.DistanceAtBestAngle(points, (Template)Templates.elementAt(i), -angleRange, angleRange, anglePrecision);
			if (d < b)
			{
				b = d;
				t = i;
			}
		}
		double score = 1.0 - (b / HalfDiagonal);
		return new Result(((Template)Templates.elementAt(t)).Name, score, t);
	}

	int AddTemplate(String name, Vector points)
	{
		Templates.addElement(new Template(name, points));
		return Templates.size();
	}

	int DeleteUserTemplates()
	{
		for (int i = Templates.size()-NumTemplates; i > 0; i--)
		{
			Templates.removeElementAt(Templates.size()-1);
		}

		return Templates.size();
	}

}
