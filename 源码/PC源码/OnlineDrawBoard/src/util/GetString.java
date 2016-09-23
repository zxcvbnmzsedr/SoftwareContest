package util;

import entity.FinalConstant;

public class GetString {
	private GetString() {

	}

	public static String getString(int[] num) {
		String text = "";
		for (int i = 0; i < num.length; i++) {
			text += FinalConstant.getTip()[num[i]];
		}
		return text;
	}
}
