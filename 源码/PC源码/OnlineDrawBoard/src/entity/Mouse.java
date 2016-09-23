package entity;

import json.JSONObject;

public class Mouse {
	private String type;
	private String x;
	private String y;
	private String state;
	private String mousewheel;
	private JSONObject key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMousewheel() {
		return mousewheel;
	}

	public void setMousewheel(String mousewheel) {
		this.mousewheel = mousewheel;
	}

	public JSONObject getKey() {
		return key;
	}

	public void setKey(JSONObject key) {
		this.key = key;
	}
}
