package util;

import java.util.logging.Level;
import run.Run;
import entity.Mouse;
import entity.RGB;
import entity.XYFamily;
import json.JSONException;
import json.JSONObject;

public class GetValue {
	public XYFamily get(JSONObject js) {
		XYFamily xy = new XYFamily();
		RGB rgb = new RGB();
		try {
			if (!js.isNull("result")) {
				xy.setResult(js.getString("result"));
			}
			if (!js.isNull("x")) {
				xy.setX(js.getDouble("x"));
			}
			if (!js.isNull("y")) {
				xy.setY(js.getDouble("y"));
			}
			if (!js.isNull("type")) {
				xy.setType(js.getString("type"));
			}
			if (!js.isNull("RGB")) {
				rgb.setColor(js.getInt("RGB"));
				xy.setRgb(rgb);
			}
			if (!js.isNull("border")) {
				xy.setBorder(js.getString("border"));
			}
			if (!js.isNull("sx")) {
				xy.setSx(Double.valueOf(js.getString("sx")));
			}
			if (!js.isNull("sy")) {
				xy.setSy(Double.valueOf(js.getString("sy")));
			}
		} catch (JSONException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "解析json错误", e);
		}
		return xy;
	}

	public Mouse getMouse(JSONObject js) {
		Mouse ms = new Mouse();
		try {
			if (!js.isNull("type")) {
				ms.setType(js.getString("type"));
			}
			if (!js.isNull("x")) {
				ms.setX(js.getString("x"));
			}
			if (!js.isNull("y")) {
				ms.setY(js.getString("y"));
			}
			if (!js.isNull("state")) {
				ms.setState(js.getString("state"));
			}
			if (!js.isNull("mousewheel")) {
				ms.setMousewheel(js.getString("mousewheel"));
			}
			if (!js.isNull("key")) {
				ms.setKey(js.getJSONObject("key"));
			}
		} catch (JSONException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "解析json错误", e);
		}
		return ms;
	}
}
