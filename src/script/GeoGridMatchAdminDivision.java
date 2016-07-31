package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;








import dao.GeographicGrid;
import dao.LonLat;
import util.Config;
import util.FileUtil;
import util.LonLatUtil;


public class GeoGridMatchAdminDivision {
	public static void main(String[] args){
		try {
			BufferedReader grid6_file=new BufferedReader(new FileReader(Config.PROVINCE_GRID));
			String line;
			List<GeographicGrid> gridList=new ArrayList<GeographicGrid>(22000);
			while((line=grid6_file.readLine())!=null){
				String[] array=line.split(",");
				gridList.addAll(GeographicGrid.getGridListFrom6Code(array[0]));
			}
			grid6_file.close();
			
			List<String> boundary_file_list=FileUtil.getAbsolutePathFromDIR(Config.PROVINCE_BOUNDARY_DIR);
			//boundary_file_list.sort();
			BufferedReader adminDivision_file=new BufferedReader(new FileReader(Config.ADMINISTRARIVE_DIVISION_FILE_OUTPUT));
			List<String> adminDivision_list=new ArrayList<String>(3000);
			while((line=adminDivision_file.readLine())!=null){
				adminDivision_list.add(line);
			}
			adminDivision_file.close();
			HashMap<String ,String> path_adminDivision_hash=matchPathAdminDivisionBatch(boundary_file_list, adminDivision_list);
			HashMap<String, List<LonLat>> name_LonLatList_hash=new HashMap<String, List<LonLat>>();
			for(String path : boundary_file_list){
				name_LonLatList_hash.put(path,LonLatUtil.getLonLatListFromFile(path,0,1));
			}
			BufferedWriter writer=new BufferedWriter(new FileWriter(Config.PROVINCE_MATCH));
			for(GeographicGrid grid : gridList){
				boolean judge=false;
				for(Iterator<String> iterator=name_LonLatList_hash.keySet().iterator();iterator.hasNext();){
					String path=iterator.next();
					//�˴��������Ҫ�²�һ�£����������д���ã���������ķ���
					//Ϊʲôһ��Ҫ�����������ʽ��̫������
					//��Ҫ����һ���ö��ŷָ������ַ����������һ���ֶΣ���������ţ����������ֶ��м䲻�Ӷ���
					//�������ͦ���ģ����ܲ����ˡ�
					//һ����ִ���ˣ�����˵�������ġ���˵һ��֪������ľ��Σ�֪�����µ����꣬��֪������������������꣬Ϊʲô��Ҫ�����ǰ������������ꣿ����������
					//����ɶ�á����������Ҹ�����ô�á��������������ĵĸĸ�ʽ
					if(LonLatUtil.contains(grid.lonLat, name_LonLatList_hash.get(path))){
						//System.out.println(path);
						String string=path_adminDivision_hash.get(path);
						String[] array=string.split(",");
						writer.write(grid.geographicCode+","+array[0]+","+string.substring(string.indexOf(",")+1).replace(",", "")+","+grid.toStringLonLat1LonLat2()+"\r\n");
						judge=true;
						break;
					}
				}
//				if(judge==false){
//					//System.out.println(grid.geographicCode);
//					writer.write(grid.toStringLonLatGeographicCode()+"\r\n");
//				}
			}
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static boolean matchPathAdminDivision(String path,String adminDivision){
		String[] array=adminDivision.split(",");
		for(int i=1;i<array.length;i++){
			if(array[i].equals("")){
				continue;
			}
			if(!path.contains(array[i]))
				return false;
		}
		return true;
	}
	
	public static HashMap<String,String> matchPathAdminDivisionBatch(List<String> pathList,List<String> adList){
		HashMap<String,String> result=new HashMap<String, String>(3500);
		for(String path : pathList){
			for(String ad : adList){
				if(matchPathAdminDivision(path, ad)){
					result.put(path,ad);
					break;
				}
			}
		}
		return result;
	}
}
