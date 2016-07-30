package navinfo;

import java.io.*;
import java.util.*;

import dao.LonLat;

import util.Config;

public class GetTopology {

	
	/**
	 * 根据四维地图的mid文件，得到该文件内的所有图幅号，用Set形式表达
	 * @param mid_file_path
	 * @return
	 */
	public static Set<String> getMapSheetNumberSet(String mid_file_path){
		try {
			Set<String> MapSheetNumberSet=new HashSet<String>();
			BufferedReader reader=new BufferedReader(new FileReader(mid_file_path));
			String line;
			while((line=reader.readLine())!=null){
				String[] line_array = line.split("\",\"|\"");
				MapSheetNumberSet.add(line_array[1]);
			}
			reader.close();
			return MapSheetNumberSet;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}