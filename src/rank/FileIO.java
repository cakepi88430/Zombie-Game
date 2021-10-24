package rank;
import java.io.*;

public class FileIO {
	final int rank_total=10;
	String[] FileIn() throws IOException {
		String str;
		String[] file_str = new String[rank_total];
		int i=0;
		FileExist();
		FileReader fr = new FileReader("./file/score.txt");
		BufferedReader bfr = new BufferedReader(fr);
		
		while((str=bfr.readLine())!=null){
			file_str[i] = str;
			i++;
		}
		fr.close();
		return file_str;
	}
	String[][] getFileData() {
		String file_str[] = null;
		try {
			file_str = FileIn();
		} catch (IOException e) {
			
		}
		
		String[][] str = new String[10][3];
		for(int i=0;i<rank_total;i++){
			String tmp[] = null;
			if(file_str[i] != null){
				tmp = file_str[i].split(",");
				for(int j=0;j<3;j++){
					str[i][j] = tmp[j];
					//System.out.println(str[i][j]);
				}
			}
			
		}
		return str;
	}
	void FileOut(String data[][]) throws IOException {
		FileExist();
		FileWriter fw = new FileWriter("./file/score.txt", false);
		for(int i=0;i<rank_total;i++){
			if(data[i][2] == null)
				break;
			fw.write(data[i][0] + "," + data[i][1] + "," + data[i][2] + "\r\n");
		}
		fw.close();
	}
	void FileExist(){
		File file = new File("./file", "score.txt");
		try {
			if(!file.exists())
			file.createNewFile();
		} catch(IOException e){
			
		}
	}
}
