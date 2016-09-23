package util;

import java.io.File;
import java.util.logging.Level;

import run.Run;

public class JarTool {
	static final String ADDRESS = "\\OnlineDrawBoard";

	private JarTool() {
	}

	// 获取jar绝对路径
	public static String getJarPath() {
		File file = getFile();
		if (file == null) {
			return null;
		}
		return getrealPath(file.getAbsolutePath());
	}

	// 对于绝对路径进行处理
	public static String getrealPath(String absolutepath) {
		if (absolutepath.endsWith("OnlineDrawBoard\\.")
				|| absolutepath.endsWith("OnlineDrawBoard")) {
			CreateFile.file(absolutepath + "\\Log");
			return absolutepath;
		} else {
			CreateFile.file(absolutepath + ADDRESS);
			CreateFile.file(absolutepath + ADDRESS + "\\Log");
			return absolutepath + ADDRESS;
		}
	}

	// 获取jar目录
	public static String getJarDir() {
		File file = getFile();
		if (file == null) {
			return null;
		}
		return getFile().getParent();
	}

	// 获取jar包名
	public static String getJarName() {
		File file = getFile();
		if (file == null) {
			return null;
		}
		return getFile().getName();
	}

	private static File getFile() {
		// 获取文件绝对路径
		String path = new File(".").getAbsolutePath();
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8");// 转换处理中文及空格
		} catch (java.io.UnsupportedEncodingException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "处理中文字符出错", e);
			return null;
		}
		return new File(path);
	}

}
