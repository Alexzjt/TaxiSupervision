package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


import util.Config;

public class LonLat {
	public static double x_pi = Math.PI * 3000.0 / 180.0;
	public double longitude, latitude;

	public LonLat(String str1, String str2) {
		if (str1.equals("null"))
			longitude = 0;
		else
			longitude = Double.valueOf(str1);
		if (str2.equals("null"))
			latitude = 0;
		else
			latitude = Double.valueOf(str2);
	}

	public LonLat(double num1, double num2) {
		longitude = num1;
		latitude = num2;
	}

	public LonLat(String str) {
		String[] array = str.split(" ");
		longitude = Double.valueOf(array[0]);
		latitude = Double.valueOf(array[1]);
	}

	public LonLat(String[] array) {
		longitude = Double.valueOf(array[0]);
		latitude = Double.valueOf(array[1]);
	}



	@Override
	public String toString() {
		StringBuilder return_str = new StringBuilder(String.valueOf(longitude));
		return_str.append(",").append(String.valueOf(latitude));
		return return_str.toString();
	}

	/**
	 * 百度地图坐标转gcj国家标准坐标
	 * 
	 */
	public void baiduTOgcj() {
		double x = longitude - 0.0065, y = latitude - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gcjLon = z * Math.cos(theta);
		double gcjLat = z * Math.sin(theta);
		longitude = gcjLon;
		latitude = gcjLat;
	}

}
