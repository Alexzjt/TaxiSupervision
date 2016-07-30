package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import dao.LonLat;

/**
 * 经纬度工具类
 * @author 张景涛
 *
 */
public class LonLatUtil {

	/**
	 * 得到一个List中的最大的经度
	 * 
	 * @param list
	 * @return
	 */
	public static double getMaxLongitude(List<LonLat> list) {
		double number = 0;
		for (LonLat lonLat : list) {
			if (lonLat.longitude > number)
				number = lonLat.longitude;
		}
		return number;
	}

	/**
	 * 得到一个List中的最小的经度
	 * 
	 * @param list
	 * @return
	 */
	public static double getMinLongitude(List<LonLat> list) {
		double number = 500; // 先把值设大一点
		for (LonLat lonLat : list) {
			if (lonLat.longitude < number)
				number = lonLat.longitude;
		}
		return number;
	}

	/**
	 * 得到一个List中的最大的纬度
	 * 
	 * @param list
	 * @return
	 */
	public static double getMaxLatitude(List<LonLat> list) {
		double number = 0;
		for (LonLat lonLat : list) {
			if (lonLat.latitude > number)
				number = lonLat.latitude;
		}
		return number;
	}

	/**
	 * 得到一个List中的最小的纬度
	 * 
	 * @param list
	 * @return
	 */
	public static double getMinLatitude(List<LonLat> list) {
		double number = 500; // 先把值设大一点
		for (LonLat lonLat : list) {
			if (lonLat.latitude < number)
				number = lonLat.latitude;
		}
		return number;
	}

	/**
	 * 传入文件路径,经度所在字段,纬度所在字段，字段编号从0开始。返回该文件中的经纬度List
	 * 
	 * @param path
	 * @return List
	 * @throws Exception
	 */
	public static List<LonLat> getLonLatListFromFile(String path, int lon_location, int lat_location) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		List<LonLat> result = new ArrayList<LonLat>(50); // 这块的List大小是随便设的，设稍微大一点扩展起来快些
		while ((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			result.add(new LonLat(array[lon_location], array[lat_location]));
		}
		reader.close();
		return result;
	}

	/**
	 * 判断点是否在区域内
	 * @param p
	 * @param points
	 * @return
	 */
	public static boolean contains(LonLat p, List<LonLat> points) {
		double max_longitude = getMaxLongitude(points);
		double min_longitude = getMinLongitude(points);
		double max_latitude = getMaxLatitude(points);
		double min_latitude = getMinLatitude(points);
		if (p.latitude < min_latitude || p.latitude > max_latitude || p.longitude > max_longitude
				|| p.longitude < min_longitude) {
			return false;
		}
		
		boolean result = false;
		for (int i = 0; i < points.size() - 1; i++) {
			if ((((points.get(i + 1).latitude <= p.latitude) && (p.latitude < points.get(i).latitude))
					|| ((points.get(i).latitude <= p.latitude) && (p.latitude < points.get(i + 1).latitude)))
					&& (p.longitude < (points.get(i).longitude - points.get(i + 1).longitude)
							* (p.latitude - points.get(i + 1).latitude)
							/ (points.get(i).latitude - points.get(i + 1).latitude) + points.get(i + 1).longitude)) {
				result = !result;
			}
		}
		return result;
	}

	/**
	 * 根据两点的经纬度，计算两点之间的距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double a = radLat1 - radLat2;
		double b = Math.toRadians(lng1) - Math.toRadians(lng2);

		double s = 2.0 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0), 2.0)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0), 2.0)));
		s = s * Config.EARTH_RADIUS;
		s = Math.round(s * 10000.0) / 10000.0;
		return s;
	}

	/**
	 * 根据两点的经纬度，计算两点之间的距离
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static double getDistance(LonLat obj1, LonLat obj2) {
		return getDistance(obj1.latitude, obj1.longitude, obj2.latitude, obj2.longitude);
	}
	
	/**
	 * 求一个经纬度list的平均经纬度
	 * @param list
	 * @return
	 */
	public static LonLat getAverageLonLatFromList(List<LonLat> list){
		double lon=0,lat=0,size=list.size();;
		for(LonLat lonLat : list){
			lon+=lonLat.longitude;
			lat+=lonLat.latitude;
		}
		return new LonLat(lon/size,lat/size);
	}
}
