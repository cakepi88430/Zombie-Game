package zombie;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import zombie.*;
import rank.*;

public class main extends JFrame {
	private Icon TOPimg = new ImageIcon(getClass().getResource("images/TOP.png"));
	private Icon LOGINimg = new ImageIcon(getClass().getResource("images/login.png"));
	static JButton btn = new JButton("");
	static JButton rank_btn = new JButton("");
	public static main frm; 
	
	main (){
		getContentPane().setBackground(Color.PINK);
		setTitle ("殭屍打打樂");
		setSize (250,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(this); //設定視窗初始位置在螢幕中心
		setResizable(false);
		setVisible(true);
		setLayout(null);
		btn.setIcon(LOGINimg);
		btn.setToolTipText("進入遊戲");
		rank_btn.setIcon(TOPimg);
		rank_btn.setToolTipText("排行榜");
		btn.setBounds(50, 50, 140, 140);
		rank_btn.setBounds(50, 200, 140, 100);
		add(btn);
		add(rank_btn);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Zombie.run();
				main.frm.setVisible(false);
			}
		});
		rank_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				rank.run();
				main.frm.setVisible(false);
			}
		});
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frm = new main(); 
		
	}

}
