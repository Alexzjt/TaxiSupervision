package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import dao.GeographicGrid;
import util.Config;

public class AddLonLatForMapSheet {
	public static void main(String[] args){
		try {
			BufferedReader reader=new BufferedReader(new FileReader(Config.GEOGRID_ADMIN_PROVINCE_MATCH_OUTPUT));
			BufferedWriter writer=new BufferedWriter(new FileWriter(Config.LONLAT_GEOGRID_ADMIN_PROVINCE_MATCH_OUTPUT));
			String line;
			while((line=reader.readLine())!=null){
				String[] line_array=line.split(",");
				GeographicGrid grid=new GeographicGrid(line_array[0]);
				writer.write(grid.lonLat.toString()+","+line+"\r\n");
			}
			writer.close();
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
