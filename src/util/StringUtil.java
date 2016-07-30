package util;

public class StringUtil {
	
	
	/**
	 * 全角半角转换
	 * @param fullWidthStr
	 * @return
	 */
	public static String fullWidth2halfWidth(String fullWidthStr) {
		if (null == fullWidthStr || fullWidthStr.length() <= 0) {
			return "";
		}
		char[] charArray = fullWidthStr.toCharArray();
		// 对全角字符转换的char数组遍历
		for (int i = 0; i < charArray.length; ++i) {
			int charIntValue = (int) charArray[i];
			// 如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
			if (charIntValue >= 65281 && charIntValue <= 65374) {
				charArray[i] = (char) (charIntValue - 65248);
			} else if (charIntValue == 12288) {
				charArray[i] = (char) 32;
			}
		}
		return new String(charArray);
	}
}
