package script;

import navinfo.GetTopology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import util.Config;

public class GeoGridMatchAdminDivisionProvince {
	public static void main(String[] args) throws Exception {
		HashMap<String, String> pinyin_ADcode_Name=AdministrativeDivision.getPinyinCodeNameHash(Config.PINYIN_CODE_NAME_PATH);
		BufferedWriter writer=new BufferedWriter(new FileWriter(Config.GEOGRID_ADMIN_PROVINCE_MATCH_OUTPUT));
		File dir = new File(Config.NAVINFO_PRE);
		String[] file_name_array = dir.list();
		for (String province : file_name_array) {
			System.out.println(province);
			Set<String> numberSet=GetTopology.getMapSheetNumberSet(Config.NAVINFO_PRE + province
							+ "\\road\\R" + province + ".mid");
			for(Iterator<String> iterator=numberSet.iterator();iterator.hasNext();){
				String mapsheet=iterator.next();
				writer.write(mapsheet+pinyin_ADcode_Name.get(province)+"\r\n");
			}
		}
		writer.close();
	}
}
