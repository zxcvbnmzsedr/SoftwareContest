package entity;

import java.awt.Color;
import java.io.Serializable;

@SuppressWarnings("serial")
public class OnePoint implements Serializable {
	private double x;
	private double y;
	private int tool;
	private Color c;
	private int border;
	private double leftX;
	private double leftY;
	private double screenzoom;
	private double boardrx;
	private double boardry;
	private boolean rect;
	private int ispoint;
	private String type;

	public OnePoint(double x, double y, int tool, Color cc, int border,
			boolean rect) {
		this.x = x;
		this.y = y;
		this.tool = tool;
		this.c = cc;
		this.border = border;
		this.rect = rect;
	}

	public OnePoint(double x, double y, int tool, Color cc, int border,
			double leftX, double leftY, double screenzoom, double boardrx,
			double boardry, String type, boolean rect) {
		this.x = x;
		this.y = y;
		this.tool = tool;
		this.c = cc;
		this.border = border;
		this.leftX = leftX;
		this.leftY = leftY;
		this.screenzoom = screenzoom;
		this.type = type;
		this.boardrx = boardrx;
		this.boardry = boardry;
		this.rect = rect;
	}

	public OnePoint(double x, double y, int tool, Color cc, int border,
			double leftX, double leftY, double screenzoom, int ispoint,
			double boardrx, double boardry, String type, boolean rect) {
		this.x = x;
		this.y = y;
		this.tool = tool;
		this.c = cc;
		this.border = border;
		this.leftX = leftX;
		this.leftY = leftY;
		this.screenzoom = screenzoom;
		this.ispoint = ispoint;
		this.type = type;
		this.boardrx = boardrx;
		this.boardry = boardry;
		this.rect = rect;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getTool() {
		return tool;
	}

	public void setTool(int tool) {
		this.tool = tool;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public double getLeftX() {
		return leftX;
	}

	public void setLeftX(double leftX) {
		this.leftX = leftX;
	}

	public double getLeftY() {
		return leftY;
	}

	public void setLeftY(double leftY) {
		this.leftY = leftY;
	}

	public double getScreenzoom() {
		return screenzoom;
	}

	public void setScreenzoom(double screenzoom) {
		this.screenzoom = screenzoom;
	}

	public int getIspoint() {
		return ispoint;
	}

	public void setIspoint(int ispoint) {
		this.ispoint = ispoint;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBoardrx() {
		return boardrx;
	}

	public void setBoardrx(double boardrx) {
		this.boardrx = boardrx;
	}

	public double getBoardry() {
		return boardry;
	}

	public void setBoardry(double boardry) {
		this.boardry = boardry;
	}

	public boolean isRect() {
		return rect;
	}

	public void setRect(boolean rect) {
		this.rect = rect;
	}
}