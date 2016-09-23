package run;

import java.awt.image.BufferedImage;

import listen.Listen;
import socket.RemoteControlServer;
import socket.TCPListener;
import socket.UDPListener;
import util.GetFigure;
import util.GetScreenSize;
import util.GetString;
import util.GetValue;
import util.LoggerUtil;
import util.SendMessage;
import execution.Recognize;
import execution.SetImage;
import execution.TrayIconSet;
import execution.UndoOrRedo;
import execution.paint.PaintImage;

public class Start {
	private RemoteControlServer thread = new RemoteControlServer();
	private execution.paint.DrawingBoard board;
	private execution.paint.DrawingBoard boardcun1;
	private UDPListener connect = new UDPListener();
	private UndoOrRedo uor;
	private BufferedImage bitmap = null;
	private PaintImage pi = new PaintImage();
	private Recognize rz = new Recognize();
	private GetScreenSize gsz = new GetScreenSize();
	private SendMessage sm = new SendMessage();
	private SetImage si = new SetImage();
	private int count = 0;// 记录绘制区域选择
	private GetFigure gf = new GetFigure();
	private LoggerUtil lu = new LoggerUtil();
	private Listen listen = new Listen();
	private TrayIconSet tis = new TrayIconSet();
	private GetValue gv = new GetValue();
	private TCPListener tcpl = new TCPListener();
	private boolean activeflag = false;

	public void init() {
		lu = new LoggerUtil();
		// 主界面
		board = new execution.paint.DrawingBoard();
		board.setVisible(true);
		board.setResizable(false);
		bitmap = new BufferedImage(Run.getStart().getBoard().getWidth(), Run
				.getStart().getBoard().getHeight(), BufferedImage.TYPE_INT_ARGB);
		// 保存图片而创建的隐藏的界面(复制界面)
		boardcun1 = new execution.paint.DrawingBoard();
		// 设置系统托盘图标
		tis.icon();
		// 网络数据获取线程
		thread.start();
		thread.showText(GetString
				.getString(new int[] { 0, 19, 1, 20, 1, 21, 2 }));
		tcpl.start();
		uor = new UndoOrRedo();
		// 添加各种按键监听
		listen.listen();
		// 自动连接线程
		connect.uDPListen();

	}

	public RemoteControlServer getThread() {
		return thread;
	}

	public void setThread(RemoteControlServer thread) {
		this.thread = thread;
	}

	public execution.paint.DrawingBoard getBoard() {
		return board;
	}

	public void setBoard(execution.paint.DrawingBoard board) {
		this.board = board;
	}

	public execution.paint.DrawingBoard getBoardcun1() {
		return boardcun1;
	}

	public void setBoardcun1(execution.paint.DrawingBoard boardcun1) {
		this.boardcun1 = boardcun1;
	}

	public UDPListener getConnect() {
		return connect;
	}

	public void setConnect(UDPListener connect) {
		this.connect = connect;
	}

	public UndoOrRedo getUor() {
		return uor;
	}

	public void setUor(UndoOrRedo uor) {
		this.uor = uor;
	}

	public BufferedImage getBitmap() {
		return bitmap;
	}

	public void setBitmap(BufferedImage bitmap) {
		this.bitmap = bitmap;
	}

	public PaintImage getPi() {
		return pi;
	}

	public void setPi(PaintImage pi) {
		this.pi = pi;
	}

	public Recognize getRz() {
		return rz;
	}

	public void setRz(Recognize rz) {
		this.rz = rz;
	}

	public GetScreenSize getGsz() {
		return gsz;
	}

	public void setGsz(GetScreenSize gsz) {
		this.gsz = gsz;
	}

	public SendMessage getSm() {
		return sm;
	}

	public void setSm(SendMessage sm) {
		this.sm = sm;
	}

	public SetImage getSi() {
		return si;
	}

	public void setSi(SetImage si) {
		this.si = si;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public GetFigure getGf() {
		return gf;
	}

	public void setGf(GetFigure gf) {
		this.gf = gf;
	}

	public LoggerUtil getLu() {
		return lu;
	}

	public Listen getListen() {
		return listen;
	}

	public void setListen(Listen listen) {
		this.listen = listen;
	}

	public TrayIconSet getTis() {
		return tis;
	}

	public void setTis(TrayIconSet tis) {
		this.tis = tis;
	}

	public GetValue getGv() {
		return gv;
	}

	public void setGv(GetValue gv) {
		this.gv = gv;
	}

	public TCPListener getTcpl() {
		return tcpl;
	}

	public void setTcpl(TCPListener tcpl) {
		this.tcpl = tcpl;
	}

	public boolean isActiveflag() {
		return activeflag;
	}

	public void setActiveflag(boolean activeflag) {
		this.activeflag = activeflag;
	}

}
