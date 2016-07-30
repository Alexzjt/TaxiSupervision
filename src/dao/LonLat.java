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

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * ��������ľ�γ�ȣ���������֮��ľ���
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2.0 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0), 2.0)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2.0), 2.0)));
		s = s * Config.EARTH_RADIUS;
		s = Math.round(s * 10000.0) / 10000.0;
		return s;
	}

	/**
	 * ��������ľ�γ�ȣ���������֮��ľ���
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static double GetDistance(LonLat obj1, LonLat obj2) {
		return GetDistance(obj1.latitude, obj1.longitude, obj2.latitude,
				obj2.longitude);
	}

	@Override
	public String toString() {
		StringBuilder return_str = new StringBuilder(String.valueOf(longitude));
		return_str.append(",").append(String.valueOf(latitude));
		return return_str.toString();
	}

	/**
	 * �ٶȵ�ͼ����תgcj���ұ�׼����
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

	/**
	 * �õ�һ��List�е����ľ���
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
	 * �õ�һ��List�е���С�ľ���
	 * 
	 * @param list
	 * @return
	 */
	public static double getMinLongitude(List<LonLat> list) {
		double number = 500;  //�Ȱ�ֵ���һ��
		for (LonLat lonLat : list) {
			if (lonLat.longitude < number)
				number = lonLat.longitude;
		}
		return number;
	}

	/**
	 * �õ�һ��List�е�����γ��
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
	 * �õ�һ��List�е���С��γ��
	 * 
	 * @param list
	 * @return
	 */
	public static double getMinLatitude(List<LonLat> list) {
		double number = 500;  //�Ȱ�ֵ���һ��
		for (LonLat lonLat : list) {
			if (lonLat.latitude < number)
				number = lonLat.latitude;
		}
		return number;
	}

	/**
	 * ���ߺ��߶εĹ�ϵ :�ཻ����1�����ཻ����0������������߶��Ϸ���-1
	 * 
	 * @param x
	 * @param y
	 * @param X1
	 * @param Y1
	 * @param X2
	 * @param Y2
	 * @return
	 */
	private static int IsIntersectAnt(double x, double y, double X1, double Y1,
			double X2, double Y2) {
		// �����߶ε���С���������ֵ
		double minX, maxX, minY, maxY;
		minX = X1;
		maxX = X2;
		if (minX > maxX) {
			minX = X2;
			maxX = X1;
		}
		minY = Y1;
		maxY = Y2;
		if (minY > maxY) {
			minY = Y2;
			maxY = Y1;
		}

		// ��������޽���Ŀ����ж�
		if (y < minY || y > maxY || x < minX) {
			return 0;
		}

		// �����ˮƽ�߶Σ����߶��Ϸ���-1�����򷵻�0
		if (Math.abs(maxY - minY) < Math.ulp(maxY - minY)) {
			return (x >= minX && x <= maxX) ? (-1) : 0;
		}

		// ���������������ֱ�ߵĽ���ĺ�����
		double x0 = X1 + (double) (y - Y1) * (X2 - X1) / (Y2 - Y1);

		// �����������Ҳ࣬���ཻ
		if (x0 > x) {
			return 0;
		}
		// ��������������ͬ
		if (Math.abs(x - x0) < Math.ulp(x - x0)) {
			return -1;
		}
		// �����¶˵�Ҳ������
		if (Math.abs(y - minY) < Math.ulp(y - minY)) {
			return 0;
		}
		return 1;

	}

	/**
	 * ��γ�ȵ�;�γ�����㹹�ɵ���֮��Ĺ�ϵ���ཻ����1�����ཻ����0����������Ϊ-1�� ���ߺ��߶εĹ�ϵ
	 * :�ཻ����1�����ཻ����0������������߶��Ϸ���-1
	 * 
	 * @param lonLat
	 * @param lonLat1
	 * @param lonLat2
	 * @return
	 */
	public static int IsIntersectAntLonLat(LonLat lonLat, LonLat lonLat1,
			LonLat lonLat2) {
		
		int result=IsIntersectAnt(lonLat.longitude, lonLat.latitude,
				lonLat1.longitude, lonLat1.latitude, lonLat2.longitude,
				lonLat2.latitude);
		System.out.println(lonLat.longitude+","+lonLat.latitude+","+
				lonLat1.longitude+","+lonLat1.latitude+","+ lonLat2.longitude+","+
				lonLat2.latitude+","+result);
		return result;
	}

	/**
	 * �жϾ�γ�ȵ��Ƿ�һ����γ�ȼ��ϵķ�Χ�ڡ���ʵ�ʾ����жϵ��Ƿ��ڶ�����ڲ�
	 * 
	 * @param lonLat
	 * @param list
	 * @return
	 */
	public static boolean PointInPolygon(LonLat lonLat, List<LonLat> list) {
		double max_longitude = getMaxLongitude(list);
		double min_longitude = getMinLongitude(list);
		double max_latitude = getMaxLatitude(list);
		double min_latitude = getMinLatitude(list);
		if (lonLat.latitude < min_latitude || lonLat.latitude > max_latitude
				|| lonLat.longitude > max_longitude
				|| lonLat.longitude < min_longitude) {
			return false;
		}
		int nCount = 0, nFlag = 0;
		for (int i = 0; i < list.size() - 2; i++) {
			nFlag = IsIntersectAntLonLat(lonLat, list.get(i), list.get(i + 1));
			if (nCount < 0) {
				return true; // ���ڱ���
			}
			nCount += nFlag;
		}
		nCount += IsIntersectAntLonLat(lonLat, list.get(list.size() - 1),
				list.get(0));
		if (nCount % 2 == 1) // ���ڶ������
		{
			return true;
		} else
			return false;
	}

	/**
	 * �����ļ�·��,���������ֶ�,γ�������ֶΣ��ֶα�Ŵ�0��ʼ�����ظ��ļ��еľ�γ��List
	 * 
	 * @param path
	 * @return List
	 * @throws Exception
	 */
	public static List<LonLat> getLonLatListFromFile(String path,
			int lon_location, int lat_location) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		List<LonLat> result = new ArrayList<LonLat>(50); // ����List��С�������ģ�����΢��һ����չ������Щ
		while ((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			result.add(new LonLat(array[lon_location], array[lat_location]));
		}
		reader.close();
		return result;
	}
	
	public static boolean contains( LonLat p,List<LonLat> points) 
	{
	   boolean result = false;
	   for( int i = 0; i < points.size() - 1; i++ )
	   {
	      if( ( ( ( points.get(i+1).latitude <= p.latitude ) && ( p.latitude < points.get(i).latitude ) ) || ( ( points.get(i).latitude <= p.latitude ) && ( p.latitude < points.get(i+1).latitude ) ) ) && ( p.longitude < ( points.get(i).longitude - points.get(i+1).longitude ) * ( p.latitude - points.get(i+1).latitude ) / ( points.get(i).latitude - points.get(i+1).latitude ) + points.get(i+1).longitude ) )
	      {
	         result = !result;
	      }
	   }
	   return result;
	}
}
