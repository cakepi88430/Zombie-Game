package zombie;
import rank.*;

import music.*;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Zombie {
	static Zombiefrm frm;
	public static void run(){
		frm = new Zombiefrm();
		//frm.getContentPane().setBackground(Color.BLUE);
		frm.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent event) {
		    	frm=null;
		    	GameExit();
		    	main.frm.setVisible(true);
		}});
	}
	
	static void GameExit(){
		StartListener.time=-1;
		StartPan.btn.setVisible(true);
		StartListener.start_flag = false;
		KeyListener.keylisten_flag = false; 
		for(int i=0;i<20;i++)
			MainPan.lab[i].setIcon(null);
		for(int i=0;i<MainPan.key_lab.length;i++){
			MainPan.key_lab[i].setEnabled(false);
		}
		Zombiefrm.music.MusicClose();
		
	}
}

class Zombiefrm extends JFrame {
	static music music = new music();
	//static Icon img = new ImageIcon("images/zombien.png");
	static Icon img = new ImageIcon(Zombiefrm.class.getResource("images/zombie.png"));
	
	static StartPan startpan= new StartPan();
	static MainPan mainpan = new MainPan();
	static int lab_status[] = new int[20];
	static int count=0,combo=0,score=0,maxcombo=0;
	static int gamemode=0;
	static JLabel combo_lab = new JLabel("ComBo：0");
	Zombiefrm(){
		getContentPane().setBackground(Color.CYAN);
		for(int i=0;i<20;i++){
			lab_status[i] = 0;
		}
		setTitle ("打殭屍");
		setSize (450,660);
		setLocationRelativeTo(this); //設定視窗初始位置在螢幕中心
		setResizable(false);
		setVisible(true);
		setLayout(null);
		addKeyListener(new KeyListener());
		combo_lab.setBounds(200, 580, 100, 50);
		combo_lab.setVisible(false);
		add(startpan);
		add(mainpan);
		add(combo_lab);
	}

}
class StartPan extends JPanel{  
	static JButton btn = new JButton("");
	static JLabel timelab = new JLabel("時間");
	static JLabel countlab = new JLabel("分數");
	private JCheckBox mode = new JCheckBox("作弊");
	private Icon playimg = new ImageIcon(getClass().getResource("images/play.png"));
	StartPan(){
		setLayout(null);
		setBounds(0, 0, 450, 70);
		countlab.setBounds(10, 10, 50, 50);
		btn.setIcon(playimg);
		btn.setToolTipText("遊戲開始");
		btn.setBounds(150, 0, 120, 70);
		mode.setBounds(270, 10, 100, 50);
		mode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == 1)
					Zombiefrm.gamemode = 1;
				else 
					Zombiefrm.gamemode = 0;
				Zombie.frm.requestFocus();//將frm設為焦點
			}
		});
		timelab.setBounds(400, 10, 50, 50);
		btn.addActionListener(new StartListener());
		add(countlab);
		add(btn);
		add(timelab);
		add(mode);
	}
}
class MainPan extends JPanel{  
	private int y=0;
	static boolean forzen = false;
	static JLabel lab[] = new JLabel[20];
	
	/*private Icon key_icon[] = {new ImageIcon("images/key/d.png"),
			new ImageIcon("images/key/f.png"),
			new ImageIcon("images/key/j.png"),
			new ImageIcon("images/key/k.png")};*/

	private Icon key_icon[] = {new ImageIcon(MainPan.class.getResource("images/key/d.png")),
			new ImageIcon(getClass().getResource("images/key/f.png")),
			new ImageIcon(getClass().getResource("images/key/j.png")),
			new ImageIcon(getClass().getResource("images/key/k.png"))};
	static JLabel key_lab[] = new JLabel[4];
	static boolean punishf = false;
	MainPan(){
		setLayout(new GridLayout(6,4));
		setBounds(50, 75, 350, 500);
		for(int i=0;i<lab.length;i++){
			lab[i] = new JLabel("",0);
			add(lab[i]);
		}
		for(int i=0;i<key_lab.length;i++){
			key_lab[i] = new JLabel("",key_icon[i],SwingConstants.CENTER);
			key_lab[i].setEnabled(false);
			add(key_lab[i]);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(punishf){
			this.setBackground(Color.RED);
		} else {
			this.setBackground(Color.GRAY);
		}
		
		if(forzen)
			g.setColor(Color.BLUE);
		else 
			g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 350, y);
		
		if(!forzen) //計算能量
			y = (Zombiefrm.count / 5) * 90;
		
		if(y>=500 && !forzen){ //當能量滿時把時間暫停
			y=500;
			forzen = true;
			forzentime();
		}
	}
	void setY(int y){
		this.y=y;
	}
	void forzentime(){
		try {
			Zombiefrm.music.getforzenMusic();
		} catch (IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Zombiefrm.music.BGMstop();
		Timer forzen_timer = new Timer();
		forzen_timer.schedule(new TimerTask(){
			public void run(){
				y-=100;
				repaint();
				if(y<=0){
					Zombiefrm.music.forzenstop();
					Zombiefrm.music.BGMstart();
					y=0;
					Zombiefrm.count = 0;
					forzen = false;
					forzen_timer.cancel();
				}
			}
		}, 800, 1000);
	}
}
class StartListener implements ActionListener { // 開始按鈕觸發處理事件
	static boolean start_flag = false; //遊戲開始旗標
	static int time;
	public void actionPerformed(ActionEvent e) {
		time=30;
		
		try {
			Zombiefrm.music.getStartMusic();
		} catch (IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
		
		Timer start_delay = new Timer();
		StartPan.btn.setVisible(false); 
		Zombiefrm.combo_lab.setVisible(true);
		
		start_delay.schedule(new TimerTask() {
			public void run() {
				if(StartListener.time != -1){
					try {
						Zombiefrm.music.getFightMusic();
					} catch (IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					try {
						Zombiefrm.music.getBGMMusic();
					} catch (IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					reset();
					Zombiefrm.mainpan.repaint();
					start_flag = true;
					KeyListener.keylisten_flag = true;
					for(int i=0;i<MainPan.key_lab.length;i++){
						MainPan.key_lab[i].setEnabled(true);
					}
					Zombie.frm.requestFocus();//將frm設為焦點
					StartPan.timelab.setText(Integer.toString(time));
					countdown(); //開始倒數
	             }
			}
		}, 3000);
	}
	
	void countdown(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if(!Zombiefrm.mainpan.forzen)
					time--;
				if (time <= -1) //當遊戲被關掉的時候
					timer.cancel();
				
				if(time < 10 && time>=0)
					StartPan.timelab.setForeground(Color.red);
				else 
					StartPan.timelab.setForeground(Color.BLACK);
				
				if(Zombiefrm.mainpan.forzen)
					StartPan.timelab.setForeground(Color.BLUE);
				StartPan.timelab.setText(Integer.toString(time));
				if(time == 0){
					gameover();
					Zombiefrm.combo_lab.setVisible(false);
					timer.cancel();
				}
				
             }
           }, 0,1000);
	}
	void reset(){
		StartPan.countlab.setText("0");
		
		Zombiefrm.count=0;
		Zombiefrm.combo=0;
		Zombiefrm.score=0;
		Zombiefrm.maxcombo=0;
		Zombiefrm.mainpan.repaint();
		StartPan.countlab.setText(Integer.toString(Zombiefrm.score));
		for(int i=0;i<20;i++){ //歸零
			Zombiefrm.lab_status[i] = 0;
		}
		for(int i=0;i<20;i+=4){ //每col隨機產生位置
			if(Zombiefrm.gamemode == 1){
				Zombiefrm.lab_status[i] = 1;
			} else {
				int n = (int)(Math.random() * 4) + i;
				Zombiefrm.lab_status[n] = 1;
			}
		}
		for(int i=0;i<20;i++){ 
			if(Zombiefrm.lab_status[i] == 1)
				MainPan.lab[i].setIcon(Zombiefrm.img);
			else
				MainPan.lab[i].setIcon(null);
		}
	}
	void gameover(){
		Zombiefrm.music.BGMstop();
		try {
			Zombiefrm.music.getOverMusic();;
		} catch (IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String name="";
		int option = JOptionPane.showConfirmDialog(null, "Time's Up.\n是否登記分數?\n就算分數太低也不會出現在排行榜上!!", "紀錄", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			name = JOptionPane.showInputDialog("請輸入你的名字");
			dataprocess d = new dataprocess();
			d.GameResult(name, Zombiefrm.score, Zombiefrm.maxcombo);
		}
		
		StartPan.btn.setVisible(true);
		start_flag = false;
		KeyListener.keylisten_flag = false;
		Zombiefrm.mainpan.setY(0);
		Zombiefrm.mainpan.repaint();
		for(int i=0;i<20;i++)
			MainPan.lab[i].setIcon(null);
	}
	
	
}
class KeyListener extends KeyAdapter {
	static boolean keylisten_flag = false; //遊戲開始可按按鍵旗標
	//private Icon time_icon = new ImageIcon("time.png");
	private Icon time_icon = new ImageIcon(getClass().getResource("images/time.png"));
	public void keyReleased(KeyEvent e){
		if(keylisten_flag && StartListener.start_flag) { //當遊戲開始與可按按鍵時
			if(e.getKeyCode() == KeyEvent.VK_D) {
				if(Zombiefrm.lab_status[16] == 1)
					move();
				else if(Zombiefrm.lab_status[16] == 2)
					addtime();
				else
					punish();
			} else if(e.getKeyCode() == KeyEvent.VK_F) {
				if(Zombiefrm.lab_status[17] == 1)
					move();
				else if(Zombiefrm.lab_status[17] == 2)
					addtime();
				else
					punish();
			} else if(e.getKeyCode() == KeyEvent.VK_J) {
				if(Zombiefrm.lab_status[18] == 1)
					move();
				else if(Zombiefrm.lab_status[18] == 2)
					addtime();
				else
					punish();
			} else if(e.getKeyCode() == KeyEvent.VK_K){
				if(Zombiefrm.lab_status[19] == 1)
					move();
				else if(Zombiefrm.lab_status[19] == 2)
					addtime();
				else
					punish();
			}
			Zombiefrm.mainpan.repaint();
			Zombiefrm.combo_lab.setText("Combo：" + Integer.toString(Zombiefrm.combo));
		}
	}
	void move(){
		try {
			Zombiefrm.music.getAttatckMusic();
		} catch (IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(Zombiefrm.maxcombo < Zombiefrm.combo)//取得最大combo數
			Zombiefrm.maxcombo = Zombiefrm.combo;
		
		for(int i=19;i>=0;i--){ //將第4排的移到第5排 ， 3-4 ，2-3，1-2 
			if(i<4){
				Zombiefrm.lab_status[i] = 0;
				continue;
			}
			Zombiefrm.lab_status[i] = Zombiefrm.lab_status[i-4];
		}
		if(Zombiefrm.gamemode == 1){
			Zombiefrm.lab_status[0] = 1;
		} else {
			int time = (int)(Math.random() * 60); //隨機亂數
			int n = (int)(Math.random() * 4); //在第一排產生隨機的殭屍
			if(time == 20 || time == 43) //當隨機亂數等於20跟43就出現時鐘
				Zombiefrm.lab_status[n] = 2;  //1為殭屍，2為時鐘
			else
				Zombiefrm.lab_status[n] = 1;
		}
		for(int i=0;i<20;i++){ //設置殭屍圖片
			if(Zombiefrm.lab_status[i] == 1)
				MainPan.lab[i].setIcon(Zombiefrm.img); //產生殭屍圖
			else if(Zombiefrm.lab_status[i] == 2)
				MainPan.lab[i].setIcon(time_icon); //產生時間圖
			else
				MainPan.lab[i].setIcon(null);
		}
		Zombiefrm.count++;
		Zombiefrm.combo++;
		getscore();//計算成績
	}
	
	void punish(){ //按錯的時候將延遲0.5秒不能按
		try {
			Zombiefrm.music.getPunishMusic();
		} catch (IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
		Zombiefrm.mainpan.punishf = true; 
		Timer timer = new Timer();
		Zombiefrm.combo = 0;
		Zombiefrm.count = 0;
		for(int i=0;i<MainPan.key_lab.length;i++){ //將4個按鍵設為disenable
			MainPan.key_lab[i].setEnabled(false);
		}
		keylisten_flag = false;
		timer.schedule(new TimerTask() {
			public void run() {
				for(int i=0;i<MainPan.key_lab.length;i++){
					MainPan.key_lab[i].setEnabled(true);
				}
				keylisten_flag = true;
				Zombiefrm.mainpan.punishf = false;
				Zombiefrm.mainpan.repaint();
             }
        }, 500);
	}
	void addtime(){
		StartPan.timelab.setText(Integer.toString(StartListener.time) + "+ 2");
		StartListener.time+=2;
		
		for(int i=19;i>=0;i--){ //將第4排的移到第5排 ， 3-4 ，2-3，1-2 
			if(i<4){
				Zombiefrm.lab_status[i] = 0;
				continue;
			}
			Zombiefrm.lab_status[i] = Zombiefrm.lab_status[i-4];
		}
		
		int n = (int)(Math.random() * 4); //在第一排產生隨機的殭屍
		Zombiefrm.lab_status[n] = 1;
		for(int i=0;i<20;i++){ //設置殭屍圖片
			if(Zombiefrm.lab_status[i] == 1)
				MainPan.lab[i].setIcon(Zombiefrm.img); //產生殭屍圖
			else if(Zombiefrm.lab_status[i] == 2)
				MainPan.lab[i].setIcon(time_icon); //產生時間圖
			else
				MainPan.lab[i].setIcon(null);
		}
	}
	void getscore(){
		Zombiefrm.score += 100 + Zombiefrm.combo * 20;
		StartPan.countlab.setText(Integer.toString(Zombiefrm.score));
	}
}
