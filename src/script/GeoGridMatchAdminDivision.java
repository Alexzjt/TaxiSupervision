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
					//此处我真的是要吐槽一下，不是我这块写的烂，而是我真的服了
					//为什么一定要用这种输出格式，太无聊了
					//我要处理一个用逗号分隔开的字符串，输出第一个字段，再输出逗号，再输出别的字段中间不加逗号
					//这个做法挺蠢的，我受不了了。
					//一个固执的人，你是说不过他的。你说一个知道长宽的矩形，知道左下的坐标，还知道相邻网格的左下坐标，为什么还要输出当前网格的右上坐标？？？？？？
					//这有啥用。。还害的我改了这么久。。而且是最无聊的改格式
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
