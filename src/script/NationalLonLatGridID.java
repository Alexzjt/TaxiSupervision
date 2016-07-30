package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.GeographicGrid;
import dao.LonLat;
import util.Config;
import util.FileUtil;
import util.LonLatUtil;

public class NationalLonLatGridID {
	public static void main(String[] args) throws Exception {
		String line;
//		BufferedReader adminDivision_file=new BufferedReader(new FileReader(Config.ADMINISTRARIVE_DIVISION_FILE_OUTPUT));
//		List<String> adminDivision_list=new ArrayList<String>(3000);
//		while((line=adminDivision_file.readLine())!=null){
//			adminDivision_list.add(line);
//		}
//		adminDivision_file.close();
		List<String> paths = FileUtil
				.getAbsolutePathFromDIR(Config.NATIONAL_BOUNDARY_DIR);
//		HashMap<String ,String> path_adminDivision_hash=GeoGridMatchAdminDivision.matchPathAdminDivisionBatch(paths, adminDivision_list);
//		BufferedWriter avgwriter=new BufferedWriter(new FileWriter(Config.COUNTY_AVERAGE_LONLAT));
		for (String path : paths) {
//			List<LonLat> lonLats=new ArrayList<LonLat>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String writer_file_path = path.replace(
						Config.NATIONAL_BOUNDARY_DIR,
						Config.NATIONAL_BOUNDARY_OUTPUT);
				File reader_file = new File(path);
				File writer_file = new File(writer_file_path.replace(
						reader_file.getName(), ""));
				if (!writer_file.exists())
					writer_file.mkdirs();
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						writer_file_path));
				while ((line = reader.readLine()) != null) {
					String[] line_array = line.split(";"); // 此处需要注意，具体的分割符是什么
					for (String lonlat_str : line_array) {
						String[] lonlat_str_array = lonlat_str.split(", ");
						if (lonlat_str_array.length == 2) {
							LonLat lonLat = new LonLat(lonlat_str_array); // 此处需要注意，具体的分割符是什么
							lonLat.baiduTOgcj();
//							lonLats.add(lonLat);
							GeographicGrid grid = new GeographicGrid(lonLat);
							writer.write(grid.toStringLonLatGeographicCode()
									+ "\r\n");
						} else {
							String[] lonlat_str_array2 = lonlat_str.split(",");
							for (int i = 0; i < lonlat_str_array2.length; i += 2) {
								LonLat lonLat = new LonLat(
										lonlat_str_array2[i],
										lonlat_str_array2[i + 1]); // 此处需要注意，具体的分割符是什么
								lonLat.baiduTOgcj();
//								lonLats.add(lonLat);
								GeographicGrid grid = new GeographicGrid(lonLat);
								writer.write(grid
										.toStringLonLatGeographicCode()
										+ "\r\n");
							}
						}
					}
				}
				reader.close();
				writer.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(path);
				// e.printStackTrace();

			}
//			avgwriter.write(path_adminDivision_hash.get(path)+","+LonLatUtil.getAverageLonLatFromList(lonLats)+"\r\n");
		}
//		avgwriter.close();
	}
}
