package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.LineListener;

import dao.GeographicGrid;
import dao.LonLat;
import util.Config;
import util.FileUtil;
import util.LonLatUtil;

/**
 * 从四维图新给的行政区划号及其边界中读取信息，并且与地理网格匹配
 * @author 张景涛
 *
 */
public class GeoGridMatchAdminDivisionFromNavinfo {
	public static void main(String[] args){
		try {
			String line;
			BufferedReader mid_file=new BufferedReader(new FileReader(Config.ADMINDIVISION_MID));
			BufferedReader mif_file=new BufferedReader(new FileReader(Config.ADMINDIVISION_MIF));
			List<String> mid_list=new ArrayList<String>(3000);
			List<LonLat> center_list=new ArrayList<LonLat>(3000);
			List<List<List<LonLat>>> mif_list_list_list=new ArrayList<List<List<LonLat>>>(3000);
			while((line=mid_file.readLine())!=null){
				String[] array=line.split("\",\"|\"");
				mid_list.add(array[9]+","+array[1]+array[4]+array[7]);
			}
			mid_file.close();
			
			boolean block_begin=false;
			List<List<LonLat>> mif_list_list=null;
			List<LonLat> mif_list=null;
			while((line=mif_file.readLine())!=null){
				if(line.contains("Region")){
					String[] array=line.split("  ");
					block_begin=true;
					mif_list_list=new ArrayList<List<LonLat>>(Integer.valueOf(array[1]));
				}
				else if(line.contains("Pen")){
					mif_list_list.add(mif_list);
					mif_list_list_list.add(mif_list_list);
					mif_list=null;
					mif_list_list=null;
				}
				else if(line.contains("Center")){
					String[] array=line.trim().split(" ");
					center_list.add(new LonLat(array[1],array[2]));
				}
				else if(line.contains(".")){
					mif_list.add(new LonLat(line));
				}
				else if(line.trim().matches("[0-9]+")){
					if(mif_list!=null){
						mif_list_list.add(mif_list);
					}
					mif_list=new ArrayList<LonLat>(100);
				}
				else{
					continue;
				}
			}
			mif_file.close();
			
			List<String> grid6_file_list=FileUtil.getAbsolutePathFromDIR(Config.GEOGRID_ADMIN_PROVINCE_MATCH_DIR);
			for(String path : grid6_file_list){
				String province=null;
				BufferedReader grid6_file=new BufferedReader(new FileReader(path));
				List<GeographicGrid> gridList=new ArrayList<GeographicGrid>(22000);
				while((line=grid6_file.readLine())!=null){
					String[] array=line.split(",");
					province=array[2];
					gridList.addAll(GeographicGrid.getGridListFrom6Code(array[0]));
				}
				grid6_file.close();
				
				BufferedWriter writer=new BufferedWriter(new FileWriter(Config.PROVINCE_MATCH_DIR+province+".csv"));
				int last_index=0;
				for(GeographicGrid grid : gridList){
					boolean judge=false;
					for(List<LonLat> list : mif_list_list_list.get(last_index)){
						if(LonLatUtil.contains(grid.lonLat, list)){
							writer.write(grid.geographicCode+","+mid_list.get(last_index)+","+grid.toStringLonLat1LonLat2()+"\r\n");
							judge=true;
							break;
						}
					}
					if(judge)
						continue;
					for(int index=0;index<mid_list.size();index++){
						if(mid_list.get(index).contains(province)){
							for(List<LonLat> list : mif_list_list_list.get(index)){
								if(LonLatUtil.contains(grid.lonLat, list)){
									writer.write(grid.geographicCode+","+mid_list.get(index)+","+grid.toStringLonLat1LonLat2()+"\r\n");
									judge=true;
									last_index=index;
									break;
								}
							}
						}
						if(judge){
							//System.out.println(grid.geographicCode);
							break;
						}
					}
				}
				writer.close();
				System.out.println(province);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
