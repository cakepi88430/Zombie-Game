package music;
import javax.sound.sampled.*; 
import java.io.*;
public class music {
	private Clip clip;
	private Clip forzen_clip;
	private Clip BGM_clip;
	
	public void getStartMusic() throws IOException, LineUnavailableException {
		getMusic("start");
	}
	public void getAttatckMusic() throws IOException, LineUnavailableException {
		getMusic("KEY");
	}
	public void getOverMusic() throws IOException, LineUnavailableException {
		getMusic("over");
	}
	public void getFightMusic() throws IOException, LineUnavailableException {
		getMusic("fight");
	}
	public void getPunishMusic() throws IOException, LineUnavailableException {
		getMusic("punish");
	}
	public void getBGMMusic() throws IOException, LineUnavailableException {
		File file = new File("sound/BGM.wav");
		if(file.exists()){
			AudioInputStream sound = null;
			try {
				sound = AudioSystem.getAudioInputStream(file);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			BGM_clip = (Clip)AudioSystem.getLine(info);
			BGM_clip.open(sound);
			BGM_clip.start();
			BGM_clip.loop(10);
		} else {
			System.out.println("檔案不存在");
		}
	}
	public void getforzenMusic() throws IOException, LineUnavailableException {
		File file = new File("sound/forzen.wav");  
		if(file.exists()){
			AudioInputStream sound = null;
			try {
				sound = AudioSystem.getAudioInputStream(file);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			forzen_clip = (Clip)AudioSystem.getLine(info);
			forzen_clip.open(sound);
			forzen_clip.start();
		} else {
			System.out.println("檔案不存在");
		}
	}
	public void forzenstop(){
		forzen_clip.stop();
	}
	public void BGMstop(){
		BGM_clip.stop();
	}
	public void MusicClose(){
		if(BGM_clip != null)
			BGM_clip.stop();
		if(forzen_clip != null)
			forzen_clip.stop();
		if(clip != null)
			clip.stop();
	}
	public void BGMstart(){
		BGM_clip.start();
	}
	public void getMusic(String name) throws IOException, LineUnavailableException {
		File file = new File("sound/" + name + ".wav"); 
		//java.net.URL imgURL1 = getClass().getResource("./sound/");
		//File file = new File(getClass().getResource("./sound/" + name + ".wav")); 
		if(file.exists()){
			AudioInputStream sound = null;
			try {
				sound = AudioSystem.getAudioInputStream(file);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(sound);
			clip.start();
		} else {
			System.out.println("檔案不存在");
		}
	}
}
