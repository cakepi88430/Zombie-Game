package rank;

import java.io.IOException;

public class dataprocess {
	public void GameResult(String name,int score,int maxcombo){
		FileIO io = new FileIO();
		String filedata[][] = io.getFileData();
		/*for(int i=0;i<10;i++){
			for(int j=0;j<3;j++){
				System.out.println(filedata[i][j]);
			}
			
		}*/
		int max=-1;
		//if(Integer.parseInt(filedata[9][1]) < score || filedata[9][1] == null){
			for(int i=0;i<filedata.length;i++){
				if(filedata[i][1] == null || max >= 0){
					if(max < 0)
						max = i;
					break;
				}
					
				if(Integer.parseInt(filedata[i][1]) < score) {
					max=i;
				}
				if(Integer.parseInt(filedata[i][1]) == score) {
					max=i+1;
				}
			}
		//}
		if(max >= 0 && max < filedata.length){
			for(int i=filedata.length-1;i>max;i--){
				if(filedata[i-1][1] == null)
					continue;
				/*if(max == 0)
					break;*/
				filedata[i][0] = filedata[i-1][0];
				filedata[i][1] = filedata[i-1][1];
				filedata[i][2] = filedata[i-1][2];
			}
			filedata[max][0] = name;
			filedata[max][1] = Integer.toString(score);
			filedata[max][2] = Integer.toString(maxcombo);
		}
		try {
			io.FileOut(filedata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
