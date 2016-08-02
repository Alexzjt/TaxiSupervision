package script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import dao.GeographicGrid;
import util.Config;

/**
 * 从四维图新给的行政区划号及其边界中读取信息，并且与地理网格匹配
 * @author 张景涛
 *
 */
public class GeoGridMatchAdminDivisionFromNavinfo {
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
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
