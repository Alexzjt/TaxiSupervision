package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;

import java.io.FileWriter;
import java.util.HashMap;

import util.Config;

public class AdministrativeDivision {
	public static HashMap<String, String> getPinyinCodeNameHash(String path){
		HashMap<String, String> pinyin_ADcode_Name=new HashMap<String, String>();
		try {
			BufferedReader reader =new BufferedReader(new FileReader(path));
			String line;
			while((line=reader.readLine())!=null){
				String[] line_array=line.split(",");
				pinyin_ADcode_Name.put(line_array[0], line.replace(line_array[0], "").trim());
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pinyin_ADcode_Name;
	}
	
	
	
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					Config.ADMINISTRARIVE_DIVISION_FILE_PATH));
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					Config.ADMINISTRARIVE_DIVISION_FILE_OUTPUT));
			String line = null, province = null, city = null;
			String before="",now="";
			while ((line = reader.readLine()) != null) {
				String[] line_array = line.split("\\s+");
				before=now;
				now=line_array[0];
				if(!before.endsWith("0000")&&before.endsWith("00")&&now.endsWith("00"))
					System.out.println(before);
				if (line_array[1].equals("市辖区") || line_array[1].equals("县")
						|| line_array[1].equals("省直辖县级行政区划")
						|| line_array[1].equals("自治区直辖县级行政区划")){
					if(line_array[0].endsWith("00"))
						city=null;
					continue;
				}
				if (line_array[0].endsWith("0000")) {
					province = line_array[1];
					city=null;
				} else if (line_array[0].endsWith("00")) {
					city = line_array[1];
				} else {
					StringBuilder stringBuilder = new StringBuilder(
							line_array[0]);
					stringBuilder.append(",").append(province==null?"":province).append(",").append(city==null?"":city)
							.append(",").append(line_array[1]).append("\r\n");
					writer.write(stringBuilder.toString());
				}
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
