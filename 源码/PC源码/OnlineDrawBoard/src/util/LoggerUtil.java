package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import run.Run;
import android.javamail.Mail;
import android.javamail.MailAccount;
import android.javamail.MailItem;

public class LoggerUtil {

	/** 存放的文件夹 **/
	private String fileName = "Log";
	private MailAccount ma = new MailAccount("724384860@qq.com",
			"qshjvksyngvmbeda", "465", "465", "smtp.qq.com");
	private MailItem mi = new MailItem("724384860@qq.com",
			new String[] { "xb950830@163.com" }, "故障日志", "报错了！");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String savepath = JarTool.getJarPath();

	// 得到要记录的日志的路径及文件名称

	private String getLogName() {
		if (savepath == null) {
			savepath = JarTool.getJarPath();
		}
		savepath += ("\\" + fileName);
		CreateFile.file(savepath);
		savepath += "\\" + sdf.format(new Date()) + ".log";

		return savepath;
	}

	// 配置Logger对象输出日志文件路径

	// 在日志文件中输出level级别以上的信息

	public void setLogingProperties(Logger logger) {
		FileHandler fh;
		try {
			fh = new FileHandler(getLogName(), true);
			logger.setLevel(Level.SEVERE);
			logger.addHandler(fh);// 日志输出文件
			fh.setFormatter(new SimpleFormatter());// 输出格式
			logger.addHandler(new ConsoleHandler());// 输出到控制台
			new Thread(() -> sendLog()).start();

		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "安全性错误", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "读取文件日志错误", e);
		}
	}

	public Logger log() {
		Logger logger = Logger.getLogger("sgg");
		try {
			setLogingProperties(logger);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "配置错误", e);
		}
		return logger;
	}

	public void sendLog() {
		IsOutPing iop = new IsOutPing();
		boolean status = iop.isOut();
		if (status) {
			String logpath = "D:\\OnlineDraw\\Log\\" + sdf.format(new Date())
					+ ".log.1";
			File file = new File(logpath);
			if (file.exists()) {
				try {
					mi.addAttachment(logpath);
				} catch (Exception e) {
					Run.getStart().getLu().log()
							.log(Level.SEVERE, "添加日志文件失败", e);
				}
				Mail mail = new Mail();
				try {
					mail.send(ma, mi);
				} catch (Exception e) {
					Run.getStart().getLu().log().log(Level.SEVERE, "发送邮件失败", e);
				}
			}
		}

	}

	public String getSavepath() {
		return savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
}