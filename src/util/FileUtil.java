package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {
	
	/**
	 * 工具类的方法，传入文件夹的目录，递归的从文件夹中获取所有文件的绝对路径，包括子目录的所有文件，以List的形式返回
	 * @param dir_path
	 * @return list
	 */
	public static List<String> getAbsolutePathFromDIR(String dir_path){
		File dir=new File(dir_path);
		if(!dir.exists())
			return null;
		File[] files=dir.listFiles();
		List<String> paths=new ArrayList<String>();
		for(File file : files){
			if(file.isDirectory()){
				paths.addAll(getAbsolutePathFromDIR(file.getAbsolutePath()));
			}
			else{
				paths.add(file.getAbsolutePath());
			}
		}
		return paths;
	}
	
	
	/**
	 * 测试用
	 * @param args
	 */
	public static void main(String[] args){
		for(String str : getAbsolutePathFromDIR("C:\\Users\\lirun\\Desktop\\svn")){
			System.out.println(str);
		}
	}
}
