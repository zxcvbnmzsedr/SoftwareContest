package util;

import java.io.File;

public class CreateFile {
	private CreateFile() {

	}

	public static void file(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
