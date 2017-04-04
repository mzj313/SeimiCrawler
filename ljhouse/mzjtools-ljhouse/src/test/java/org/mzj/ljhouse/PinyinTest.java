package org.mzj.ljhouse;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinTest {

	@Test
	public void test全拼() {
		String src = "中国china重量重复";
		
		char[] ch  = src.toCharArray();
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuffer resultBuf = new StringBuffer();
        try {
            for (int i = 0; i < ch.length; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(ch[i]).matches("[\\u4E00-\\u9FA5]+")) {
                	String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch[i], outputFormat);
                    resultBuf.append(arr[0]);
                } else {
                    resultBuf.append(java.lang.Character.toString(ch[i]));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        String result = resultBuf.toString();
        
        System.out.println(result);
	}
	
	@Test
	public void test首字母() {
		String src = "中国china重量重复";
		
		StringBuffer resultBuf = new StringBuffer();
        for (int j = 0; j < src.length(); j++) {
            char word = src.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
            	resultBuf.append(pinyinArray[0].charAt(0));
            } else {
            	resultBuf.append(word);
            }
        }
        String result = resultBuf.toString();
        
		System.out.println(result);
	}
	
	//JPinyin是一个汉字转拼音的Java开源类库 https://github.com/stuxuhai/jpinyin
	@Test
	public void testJPinYin() {
		try {
			String str = "你好世界重量重复";
			String result;
			result = com.github.stuxuhai.jpinyin.PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK); // nǐ,hǎo,shì,jiè
			System.out.println(result);
			result = com.github.stuxuhai.jpinyin.PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER); // ni3,hao3,shi4,jie4
			System.out.println(result);
			result = com.github.stuxuhai.jpinyin.PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
			System.out.println(result);
			result = com.github.stuxuhai.jpinyin.PinyinHelper.getShortPinyin(str); // nhsj
			System.out.println(result);
//			com.github.stuxuhai.jpinyin.PinyinHelper.addPinyinDict("user.dict");  // 添加用户自定义字典
			boolean 是否多音字 = com.github.stuxuhai.jpinyin.PinyinHelper.hasMultiPinyin('重');
			System.out.println(是否多音字);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
