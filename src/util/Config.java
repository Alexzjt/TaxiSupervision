package util;
import java.nio.channels.ScatteringByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {
	public static SimpleDateFormat DF=new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat DF_TOLL=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static int SPEED_UPBOUND=250;   //最高限速约定
	public static int SPEED_LOWBOUND=0;    //最低限速约定
	public static String MAP_MIF="D:\\zjt\\重庆地图\\Rchongqing.mif";	   //.mif文件的存储路径
	public static String MAP_MID="D:\\zjt\\重庆地图\\Rchongqing.txt";	   //.mid文件的存储路径
	public static String STATION="D:\\zjt\\station.csv";	   //收费站文件的存储路径
	public static String TACHYMETER="D:\\zjt\\CSY.txt";	   //测速仪文件的存储路径
	public static String ROADLINK_OUTPUT="D:\\zjt\\路网拓扑zjt\\roadlink"; //路链的输出情况
	public static String OLD_TOPOLOGY="D:\\zjt\\路网拓扑0629.csv";  //常兵做的老拓扑，但是其中的测速仪数据是能用的
	public static int HIGHWAY_COUNT=14000;    //从之前的数据了解到，高速公路路链总共是14000条左右
	public static double EARTH_RADIUS=6371393;    //地球半径 米
	public static double TOLERANCE=500; //米。预设的误差值，用于计算收费站、测速仪经纬度与路链、路段经纬度序列之间的距离，小于此值说明在一个路上
	public static String START_ROADLINK_ID="16777580";//预设的起始路链号
	/////////////////////////////////////////////////
	public static String ROADLINK_MIDDLE_FILE="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\路链拓扑program.csv";  //生成的中间文件
	public static String STATION_SHORTEST_PATH_LENGTH="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\收费站最短距离"+DF.format(new Date())+".csv";  //生成的中间文件
	public static String SHORTEST_PATH_DIR="D:\\zjt\\OD寻径结果\\";
	public static int water=0;
	////////////////////////////////////////////////////////////
	public static String CYSNEARESTROADLINK="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\测速仪与路链相距最短距离.csv";  //给金玉辉算的
	////////////////////////////////////////////////////////////
	public static String HIGHWAY_START_END="E:\\百度云同步盘\\诸老师这边的事\\路网拓扑划分201604\\高速编号所在起止路链.csv";
	public static String ROADLINK_HIGHWAY_ID="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\路链拓扑"+DF.format(new Date())+".csv";
	public static String HIGHWAY_ID_LONLAT="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\高速公路ID及其经纬度"+DF.format(new Date())+".csv";
	///////////////////////////////////////////////////////////////
	public static String SUB_OPTIMAL_PATH_DIR="D:\\zjt\\OD最短路径和次短路径（不同收费广场）\\";
	public static String SHORTEST_PATH_LENGTH_IN="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\收费站最短距离20160623104640.csv";
	public static String ROADLINK_MIDDLE_FILE_SUB_OPT="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\路链拓扑20160712213432.csv";
	public static double TOLERANCE_MULTIPLE=1.2;   //次优路径阈值设置为  最短路径乘以此因子。
	public static double CHONGQING_G5001=180;   //重庆绕城高速的路径长度。
	public static double TOLERANCE_SIMILARITY=0.1;   //两条路径的区别，如果小于此值，则认为相似。
	public static int COUNT_SUB_OPT_PATH=5;         //如果次优路径的数量超过此数目，只求最短的前xxx条
	public static String ROADLINK_EACH_DIR="G:\\zjt\\OD路链岔路进出口(只保存路链ID)\\";
	////////////////////////////////////////////////////////////////////////
	public static int NUMBERS_OF_ROADLINK=13771;
	public static String OD_DIJKSTRA_DIR="D:\\zjt\\OD最短路径（不同收费广场）\\";
	////////////////////////////////////////////////////////////////////////
	public static String NAVINFO_PRE="D:\\zjt\\四维图新\\level2\\level2\\";
	public static String NAVINFO_STATION_OUTPUT="D:\\zjt\\四维图新\\收费站poiID名称经纬度所在路名\\";
	/////////////////////////////////////////////////////////////////////////
	public static String OD_DIJKSTRA_DIR_SHORT="D:\\zjt\\OD最短路径按高速编号\\";
	//////////////////////////////////////////////////////////////////////////
	public static String TOLL_DATA="D:\\zjt\\2015年数据\\201512\\2015-12-01.csv";
	public static String TOLL_DATA_ANALYSIS="D:\\zjt\\2015年收费数据解析\\2015-12-01.csv";
	public static String TOLL_DATA_UNKNOWN="D:\\zjt\\2015年未知收费站\\2015-12-01.csv";
	public static String ROADLINK_TOPOLOGY_29="D:\\zjt\\四维图新\\29省路网拓扑\\";
	//////////////////////////////////////////////////////////////////////////
	public static String NATIONAL_BOUNDARY_DIR="D:\\zjt\\区县边界经纬度\\china\\";
	public static String NATIONAL_BOUNDARY_OUTPUT="D:\\zjt\\区县边界经纬度\\chinaGridCode\\";
	public static String BEIJING_GRID="D:\\zjt\\网约车实验结果\\北京6位网格.csv";
	public static String BEIJING_BOUNDARY_DIR="D:\\zjt\\区县边界经纬度\\chinaGridCode\\北京市\\";
	public static String BEIJING_MATCH="D:\\zjt\\网约车实验结果\\北京8位网格匹配"+DF.format(new Date())+".csv";
	public static String NATION_MATCH="D:\\zjt\\网约车实验结果\\全国8位网格匹配"+DF.format(new Date())+".csv";
	/////////////////////////////////////////////////////////////////////////////
	public static String ADMINISTRARIVE_DIVISION_FILE_PATH="E:\\BaiduYunDownload\\行政区划.txt"; 
	public static String ADMINISTRARIVE_DIVISION_FILE_OUTPUT="D:\\zjt\\网约车实验结果\\行政区划省市县分割.txt";
	//////////////////////////////////////////////////////////////////////////////
	public static String GEOGRID_ADMIN_PROVINCE_MATCH_OUTPUT="D:\\zjt\\网约车实验结果\\地理网格与行政区划.csv";
	public static String LONLAT_GEOGRID_ADMIN_PROVINCE_MATCH_OUTPUT="D:\\zjt\\网约车实验结果\\经纬度地理网格与行政区划.csv";
	public static String PINYIN_CODE_NAME_PATH="D:\\zjt\\网约车实验结果\\省级行政区划代码拼音.txt";
}
