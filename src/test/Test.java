package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import script.GeoGridMatchAdminDivision;
import util.Config;
import util.FileUtil;

public class Test {
	public static void main(String[] args) throws Exception{
		String line;
		List<String> boundary_file_list=FileUtil.getAbsolutePathFromDIR(Config.NATIONAL_BOUNDARY_DIR);
		
		BufferedReader adminDivision_file=new BufferedReader(new FileReader(Config.ADMINISTRARIVE_DIVISION_FILE_OUTPUT));
		List<String> adminDivision_list=new ArrayList<String>(3000);
		while((line=adminDivision_file.readLine())!=null){
			adminDivision_list.add(line);
		}
		adminDivision_file.close();
		HashMap<String ,String> path_adminDivision_hash=GeoGridMatchAdminDivision.matchPathAdminDivisionBatch(boundary_file_list, adminDivision_list);
		for(String string : adminDivision_list){
			if(!path_adminDivision_hash.containsValue(string))
				System.out.println(string);
		}
	}
}
