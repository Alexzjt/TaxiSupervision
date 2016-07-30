package serviceImpl;

import dao.LonLat;
import service.LonLatTransform;
/**
 * 各地图API坐标系统比较与转换;
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系; BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。 chenhua
 */
public class LonLatTranformImpl implements LonLatTransform{
	public static double a = 6378245.0;
	public static double ee = 0.00669342162296594323;
	/**
	 * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static LonLat lonLat84_To_Gcj02(double lon, double lat) {
		if (outOfChina(lon, lat)) {
			return null;
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		//System.out.println(mgLat+","+mgLon);
		return new LonLat(mgLon, mgLat);
	}

	/**
	 * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
	 * */
	public static LonLat gcj_To_LonLat84(double lon, double lat) {
		LonLat lonLat = transform(lon, lat);
		double lontitude = lon * 2 - lonLat.longitude;
		double latitude = lat * 2 - lonLat.latitude;
		return new LonLat(lontitude,latitude);
	}

	/**
	 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
	 * 
	 * @param gg_lat
	 * @param gg_lon
	 */
	public static LonLat gcj02_To_Bd09(double gg_lon, double gg_lat) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new LonLat(bd_lon, bd_lat);
	}

	/**
	 * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
	 * bd_lat * @param bd_lon * @return
	 */
	public static LonLat bd09_To_Gcj02(double bd_lon, double bd_lat) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return new LonLat(gg_lon, gg_lat);
	}

	/**
	 * (BD-09)-->84
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	public static LonLat bd09_To_LonLat84(double bd_lon, double bd_lat) {

		LonLat gcj02 = bd09_To_Gcj02(bd_lon, bd_lat);
		LonLat map84 = gcj_To_LonLat84(gcj02.longitude,
				gcj02.latitude);
		return map84;

	}

	public static boolean outOfChina(double lon, double lat) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	public static LonLat transform(double lon, double lat) {
		if (outOfChina(lon, lat)) {
			return new LonLat(lon, lat);
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new LonLat(mgLon, mgLat);
	}

	public static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0
				* Math.PI)) * 2.0 / 3.0;
		return ret;
	}
	
	/**
	 * 环回测试，测试可逆性
	 * @param args
	 */
	public static void main(String[] args){
		LonLat gcj=new LonLat(116.350638,39.981035);
		LonLat wgs84=gcj_To_LonLat84(gcj.longitude,gcj.latitude);
		LonLat gcj2=lonLat84_To_Gcj02(wgs84.longitude, wgs84.latitude);
		
		System.out.println("gcj "+gcj);
		System.out.println("wgs84 "+wgs84);
		System.out.println("gcj2 "+gcj2);
		
		LonLat bd=gcj02_To_Bd09(gcj.longitude, gcj.latitude);
		LonLat gcj3=bd09_To_Gcj02(bd.longitude, bd.latitude);
		
		System.out.println("bd "+bd);
		System.out.println("gcj3 "+gcj3);
	}
}
