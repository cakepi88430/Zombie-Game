package rank;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.*;
import zombie.main;

public class rank {
	static RankFrm frm;
	public static void run(){
		frm = new RankFrm();
		frm.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent event) {
		    	frm=null;
		    	//GameExit();
		    	main.frm.setVisible(true);
		}});
	}
}

class RankFrm extends JFrame{
	RankFrm(){
		setTitle ("排行榜");
		setSize (500,500);
		setLocationRelativeTo(this); //設定視窗初始位置在螢幕中心
		setResizable(false);
		setVisible(true);
		setLayout(null);
		RankPan rankpan = new RankPan();
		add(rankpan);
	}
}
class RankPan extends JPanel {
	RankPan(){
		FileIO io = new FileIO();
		JLabel rank = new JLabel("排行");
		JLabel name = new JLabel("名稱");
		JLabel score = new JLabel("分數");
		JLabel maxcombo = new JLabel("最高ComBo數");
		JLabel[][] data_lab = new JLabel[10][3];
		JLabel[] num = new JLabel[10];
		
		setLayout(new GridLayout(11,4));
		setBounds(50, 10, 450, 450);
		add(rank);
		add(name);
		add(score);
		add(maxcombo);
		String data[][] = io.getFileData();
		for(int i=0;i<10;i++){
			num[i] = new JLabel(Integer.toString(i+1));
			add(num[i]);
			for(int j=0;j<3;j++){
				data_lab[i][j] = new JLabel(data[i][j]);
				add(data_lab[i][j]);
			}
		}
	}
}