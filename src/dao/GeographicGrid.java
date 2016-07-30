package dao;

import java.util.ArrayList;
import java.util.List;


public class GeographicGrid {
	public LonLat lonLat;
	public int lonCode,latCode,x1,y1,x2,y2;
	public String geographicCode;
	
	/**
	 * �޲ι��캯��
	 */
	public GeographicGrid(){
		
	}
	
	/**
	 * ����GCJ��γ�ȵĹ��캯��
	 */
	public GeographicGrid(LonLat lonLatGCJ){
		lonLat=lonLatGCJ;
		double lonCode_Double=lonLat.longitude-60;
		double latCode_Double=lonLat.latitude*1.5;
		lonCode=(int)lonCode_Double;
		latCode=(int)latCode_Double;
		double x1_Double=(lonCode_Double-lonCode)*8;
		double y1_Double=(latCode_Double-latCode)*8;
		x1=(int)x1_Double;
		y1=(int)y1_Double;
		x2=(int)((x1_Double-x1)*10);
		y2=(int)((y1_Double-y1)*10);
		StringBuilder stringBuilder=new StringBuilder(String.valueOf(latCode));
		stringBuilder.append(String.valueOf(lonCode)).append(String.valueOf(y1)).append(String.valueOf(x1)).append(String.valueOf(y2)).append(String.valueOf(x2));
		geographicCode=stringBuilder.toString();
	}
	
	/**
	 * �������������룬������γ�ȵȡ��˴������ľ�γ�����������Ͻǵġ�
	 * @param code
	 */
	public GeographicGrid(String code){
		geographicCode=code;
		if(code.length()==6){
			x2=0;
			y2=0;
		}else{
			x2=code.charAt(7)-'0';
			y2=code.charAt(6)-'0';
		}
		x1=code.charAt(5)-'0';
		y1=code.charAt(4)-'0';
		lonCode=Integer.valueOf(code.substring(2,4));
		latCode=Integer.valueOf(code.substring(0,2));
		double lon=(double)lonCode+60.0+0.125*x1+0.0125*x2;
		double lat=((double)latCode+0.125*y1+0.0125*y2)*2.0/3.0;
		lonLat=new LonLat(lon,lat);
	}
	
	/**
	 * �������������룬�Լ������x2��y2����
	 * ������γ�ȵȡ��˴������ľ�γ�����������Ͻǵġ�
	 * @param code
	 */
	public GeographicGrid(String code,int x2,int y2){
		geographicCode=code+String.valueOf(y2)+String.valueOf(x2);
		this.x2=x2;
		this.y2=y2;
		x1=code.charAt(5)-'0';
		y1=code.charAt(4)-'0';
		lonCode=Integer.valueOf(code.substring(2,4));
		latCode=Integer.valueOf(code.substring(0,2));
		double lon=(double)lonCode+60.0+0.125*x1+0.0125*x2;
		double lat=((double)latCode+0.125*y1+0.0125*y2)*2.0/3.0;
		lonLat=new LonLat(lon,lat);
	}
	
	/**
	 * ���Ǵ������дtoString��������Ϊֻ����˾�γ�Ⱥ������
	 * @return ����,γ��,�����
	 */
	public String toStringLonLatGeographicCode(){
		StringBuilder strBuilder=new StringBuilder(lonLat.toString());
		strBuilder.append(",").append(geographicCode);
		return strBuilder.toString();
	}
	
	/**
	 * ����6λ���룬���ش�00��99��100�������list
	 * @param code
	 * @return List
	 */
	public static List<GeographicGrid> getGridListFrom6Code(String code){
		List<GeographicGrid> result=new ArrayList<GeographicGrid>(101);
		for(int i=0;i<=9;i++){
			for(int j=0;j<=9;j++){
				result.add(new GeographicGrid(code, i, j));
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		GeographicGrid test=new GeographicGrid("60561264");
		System.out.println(test.toStringLonLatGeographicCode());
		GeographicGrid test2=new GeographicGrid(test.lonLat);
		System.out.println(test2.toStringLonLatGeographicCode());
	}
}
