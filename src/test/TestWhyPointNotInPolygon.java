package test;

import java.util.List;

import dao.LonLat;

public class TestWhyPointNotInPolygon {
	public static void main(String[] args) throws Exception{
		LonLat lonLat=new LonLat(116.125,40.158333);
		LonLat lonLat1=new LonLat(116.125,40.15);
		LonLat lonLat2=new LonLat(115.853713291465,40.149226458442996);
		LonLat lonLat3=new LonLat(115.86240791140402,40.15138214969127);
		LonLat.IsIntersectAntLonLat(lonLat, lonLat2, lonLat3);
		LonLat.IsIntersectAntLonLat(lonLat1, lonLat2, lonLat3);
	}
}
