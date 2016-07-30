package util;

public class StringUtil {
	
	
	/**
	 * ȫ�ǰ��ת��
	 * @param fullWidthStr
	 * @return
	 */
	public static String fullWidth2halfWidth(String fullWidthStr) {
		if (null == fullWidthStr || fullWidthStr.length() <= 0) {
			return "";
		}
		char[] charArray = fullWidthStr.toCharArray();
		// ��ȫ���ַ�ת����char�������
		for (int i = 0; i < charArray.length; ++i) {
			int charIntValue = (int) charArray[i];
			// �������ת����ϵ,����Ӧ�±�֮�����ƫ����65248;����ǿո�Ļ�,ֱ����ת��
			if (charIntValue >= 65281 && charIntValue <= 65374) {
				charArray[i] = (char) (charIntValue - 65248);
			} else if (charIntValue == 12288) {
				charArray[i] = (char) 32;
			}
		}
		return new String(charArray);
	}
}
