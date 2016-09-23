package entity;

public class FinalConstant {
	private static final String[] imageList = { "/img/save1.png",
			"/img/save2.png", "/img/save3.png",// 保存0,1,2
			"/img/open1.png", "/img/open2.png", "/img/open3.png",// 打开3,4,5
			"/img/graphics1.png", "/img/graphics2.png", "/img/graphics3.png",// 调色板6,7,8
			"/img/undo1.png", "/img/undo2.png", "/img/undo3.png", // 撤销9,10,11
			"/img/redo1.png", "/img/redo2.png", "/img/redo3.png", // 还原12,13,14
			"/img/limit1.png", "/img/limit2.png", "/img/limit3.png" // 规定绘制区域15,16,17
			, "/img/clean1.png", "/img/clean2.png", "/img/clean3.png", // 清除18,19,20
			"/img/pensize1.png", "/img/pensize2.png", "/img/pensize3.png", // 粗细21,22,23
			"/img/ok1.png", "/img/ok2.png", "/img/ok3.png" }; // 确定24,25,26
	private static final String[] tip = { "<html><center>",
			"</center><br/><center>",
			"</center></html>",// 0,1,2
			"连接成功!",
			"链接的IP是:",// 3,4
			"连接已断开,请重新连接!",
			"端口被占用",// 5,6
			"程序异常已发送报告至开发者", "即将关闭，请重启本程序",
			"如对您造成不便非常抱歉",// 7,8,9
			"签名获取失败,", "1987", "1988", "9898",
			"9999",// 10,11,12,13,14
			"数据接收失败,", "文件写入失败,请重新尝试",
			"数据解析失败,",// 15,16,17
			"请检查后重启本程序!", "本程序默认自动保存在安装目录下", "图片可手动另存,建议连外网使用",
			"以便故障可以及时通知改进,谢谢!"// 18,19,20,21
	};

	private FinalConstant() {
		// Do nothing because of X and Y.
	}

	public static String[] getImageList() {
		return imageList;
	}

	public static String[] getTip() {
		return tip;
	}
}
