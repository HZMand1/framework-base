package com.base.utils.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;

/**
 * TODO 字符串工具类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/12 16:19
 * @copyright XXX Copyright (c) 2019
 */
public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    public static final String US_ASCII = "US-ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_16 = "UTF-16";
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private char[] chartable = new char[]{'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'};
    private char[] alphatable = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public int[] table = new int[27];

    public StringUtil() {
        for (int i = 0; i < 27; ++i) {
            this.table[i] = this.gbValue(this.chartable[i]);
        }

    }

    public static String getEncoding(String str) {
        String[] encodeStr = new String[]{"UTF-8", "ISO-8859-1", "GBK", "GB2312", "US-ASCII", "UTF-16BE", "UTF-16LE", "UTF-16"};
        String encode = "";
        String[] var3 = encodeStr;
        int var4 = encodeStr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            encode = checkEncoding(str, string);
            if (isNotBlank(encode)) {
                return encode;
            }
        }

        return "";
    }

    public static String checkEncoding(String str, String encode) {
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var3) {
        }

        return "";
    }

    public static List<String> subGbstring(String str, int len, boolean gb) {
        List<String> list = new ArrayList();
        int nowlen;
        int start;
        if (gb) {
            nowlen = 0;
            start = 0;
            StringUtil obj = new StringUtil();

            for (int i = 0; i < str.length(); ++i) {
                int strgb = obj.gbValue(str.charAt(i));
                if (strgb < obj.table[0]) {
                    ++nowlen;
                } else {
                    nowlen += 2;
                }

                if (nowlen == len) {
                    list.add(str.substring(start, i + 1));
                    start = i + 1;
                    nowlen = 0;
                } else if (nowlen > len) {
                    list.add(str.substring(start, i));
                    start = i;
                    nowlen = 0;
                } else if (i + 1 == str.length()) {
                    list.add(str.substring(start, i + 1));
                    start = i;
                    nowlen = 0;
                }
            }
        } else {
            nowlen = len;

            for (start = 0; start < str.length(); nowlen += start) {
                if (nowlen + 1 > str.length()) {
                    nowlen = str.length();
                }

                list.add(str.substring(start, nowlen));
                start = nowlen + 1;
            }
        }

        return list;
    }

    public char charAlpha(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 97 + 65);
        } else if (ch >= 'A' && ch <= 'Z') {
            return ch;
        } else {
            int gb = this.gbValue(ch);
            if (gb < this.table[0]) {
                return '0';
            } else {
                int i;
                for (i = 0; i < 26 && !this.match(i, gb); ++i) {
                }

                return i >= 26 ? '0' : this.alphatable[i];
            }
        }
    }

    public String stringAlpha(String SourceStr) {
        StringBuffer result = new StringBuffer("");
        int StrLength = SourceStr.length();

        try {
            for (int i = 0; i < StrLength; ++i) {
                result.append(this.charAlpha(SourceStr.charAt(i)));
            }
        } catch (Exception var6) {
            result.append("");
        }

        return result.toString();
    }

    private boolean match(int i, int gb) {
        if (gb < this.table[i]) {
            return false;
        } else {
            int j;
            for (j = i + 1; j < 26 && this.table[j] == this.table[i]; ++j) {
            }

            if (j == 26) {
                return gb <= this.table[j];
            } else {
                return gb < this.table[j];
            }
        }
    }

    private int gbValue(char ch) {
        String str = "";
        str = str + ch;

        try {
            byte[] bytes = str.getBytes("GB2312");
            return bytes.length < 2 ? 0 : (bytes[0] << 8 & '\uff00') + (bytes[1] & 255);
        } catch (Exception var4) {
            return 0;
        }
    }

    public static String getMd5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            return null;
        }
    }

    public static String getRand(int n) {
        Random rnd = new Random();
        StringBuffer pass = new StringBuffer("0");

        int x;
        for (x = rnd.nextInt(9); x == 0; x = rnd.nextInt(9)) {
        }

        pass.append(String.valueOf(x));

        for (int i = 1; i < n; ++i) {
            pass.append(String.valueOf(rnd.nextInt(9)));
        }

        return pass.toString();
    }

    public static String getStrLen(String str, int num) {
        int forNum = 0;
        int alli = 0;
        int strLen = 0;
        if (num <= 0) {
            return str;
        } else if (null == str) {
            return null;
        } else {
            if (str.length() >= num) {
                strLen = num;
            } else {
                strLen = str.length();
            }

            for (int i = 0; i < strLen && (double) num != Math.floor((double) ((float) forNum / 2.0F)); ++i) {
                if (str.substring(i, i + 1).getBytes().length > 1) {
                    ++alli;
                }

                ++alli;
                if (alli >= num) {
                    return str.substring(0, i);
                }
            }

            return str.substring(0, strLen);
        }
    }

    public static boolean isLen(String str, int num) {
        int forNum = 0;
        int alli = 0;
        int strLen = 0;
        if (str.length() >= num) {
            return true;
        } else {
            strLen = str.length();

            for (int i = 0; i < strLen && (double) num != Math.floor((double) ((float) forNum / 2.0F)); ++i) {
                if (str.substring(i, i + 1).getBytes().length > 1) {
                    ++alli;
                }

                ++alli;
            }

            return alli > num;
        }
    }

    public static String fillLeft(String source, char fillChar, int len) {
        StringBuffer ret = new StringBuffer();
        if (null == source) {
            return ret.append("").toString();
        } else {
            if (source.length() > len) {
                ret.append(source);
            } else {
                int slen = source.length();

                while (ret.toString().length() + slen < len) {
                    ret.append(fillChar);
                }

                ret.append(source);
            }

            return ret.toString();
        }
    }

    public static String filRight(String source, char fillChar, int len) {
        StringBuffer ret = new StringBuffer();
        if (null == source) {
            return ret.append("").toString();
        } else {
            if (source.length() > len) {
                ret.append(source);
            } else {
                ret.append(source);

                while (ret.toString().length() < len) {
                    ret.append(fillChar);
                }
            }

            return ret.toString();
        }
    }

    public static String filterStr(String str) {
        if (null != str && !"".equals(str)) {
            str = str.replaceAll("'", "''");
            return str;
        } else {
            return str;
        }
    }

    public static boolean isDigit(char c) {
        String nums = "0123456789.";
        return nums.indexOf(String.valueOf(c)) != -1;
    }

    public static String substring(String str, int num) {
        byte[] substr = new byte[num];
        System.arraycopy(str.getBytes(), 0, substr, 0, num);
        str = new String(substr);
        return str;
    }

    public static String checkStr(String inputStr) {
        StringBuffer error = new StringBuffer("");
        if (null != inputStr && !"".equals(inputStr.trim())) {
            for (int i = 0; i < inputStr.length(); ++i) {
                char c = inputStr.charAt(i);
                if (c == '"') {
                    error.append(" 特殊字符[\"]");
                }

                if (c == '\'') {
                    error.append(" 特殊字符[']");
                }

                if (c == '<') {
                    error.append(" 特殊字符[<]");
                }

                if (c == '>') {
                    error.append(" 特殊字符[>]");
                }

                if (c == '&') {
                    error.append(" 特殊字符[&]");
                }

                if (c == '%') {
                    error.append(" 特殊字符[%]");
                }
            }
        }

        return error.toString();
    }

    public static String isBlankToMsg(String str, String msg) {
        String returnstr = "";
        if (isBlank(str)) {
            returnstr = msg + ",";
        }

        return returnstr;
    }

    public static String getFileName(String filepath) {
        return isNotBlank(filepath) ? filepath.substring(filepath.lastIndexOf("\\") + 1, filepath.length()) : "";
    }

    public static String changeCharset(String str, String oldCharset, String newCharset) {
        if (str != null) {
            byte[] bs = null;

            try {
                if (isNotBlank(oldCharset)) {
                    bs = str.getBytes(oldCharset);
                } else {
                    bs = str.getBytes();
                }
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
            }

            if (bs == null) {
                bs = str.getBytes();
            }

            try {
                String newstr = new String(bs, newCharset);
                return newstr;
            } catch (Exception var5) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static StringBuilder[] getSplit(String str, String split) {
        String[] ggStr = null;
        if (null == str) {
            return null;
        } else {
            ggStr = str.split(split);
            StringBuilder[] b = new StringBuilder[ggStr.length];

            for (int i = 0; i < ggStr.length; ++i) {
                StringBuilder sBuffer = new StringBuilder();
                char[] c = ggStr[i].toCharArray();
                int data = 0;

                for (int j = 0; j < c.length; ++j) {
                    if (isDigit(c[j])) {
                        ++data;
                        sBuffer.append(c[j]);
                    } else if (data > 0) {
                        break;
                    }
                }

                if (null == sBuffer || sBuffer.length() == 0) {
                    sBuffer.append('0');
                }

                b[i] = sBuffer;
            }

            return b;
        }
    }

    public static void setGoodsSpec(Object obj) {
        if (null != obj) {
            setGoodsSpec(obj, "goodsSpec");
        }

    }

    public static void setGoodsSpec(Object obj, String specName) {
        if (null != obj) {
            Object specObj = BeanUtil.forceGetProperty(obj, specName);
            if (null != specObj) {
                StringBuilder[] specs = getSplit(specObj.toString(), "\\*");

                for (int i = 0; i < 5; ++i) {
                    int j = i + 1;
                    Double value = 0.0D;
                    if (specs.length >= j) {
                        value = Double.valueOf(specs[i].toString());
                    }

                    try {
                        BeanUtil.forceSetProperty(obj, specName + j, value);
                    } catch (Exception var8) {
                    }
                }
            }
        }

    }

    public static String makeSign(String value) {
        String str = "";
        if (null == value) {
            return str;
        } else {
            str = value.trim();
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("\"", "&quot;");
            return str;
        }
    }

    public static String intercept(String str, int len) {
        String newstr = "";
        if (null == str) {
            return newstr;
        } else {
            if (str.length() > len) {
                newstr = str.substring(0, len) + "...";
            } else {
                newstr = str;
            }

            return newstr;
        }
    }

    public static String replaceHtml(String str) {
        if (null == str) {
            return "";
        } else {
            str = replace(str, "&", "&amp;");
            str = replace(str, "'", "&apos;");
            str = replace(str, "\"", "&quot;");
            str = replace(str, "\n", "<br>");
            str = replace(str, "\t", "&nbsp;&nbsp;");
            str = replace(str, " ", "&nbsp;");
            return str;
        }
    }

    public static String reReplaceHtml(String str) {
        if (null == str) {
            return "";
        } else {
            str = replace(str, "&amp;", "&");
            str = replace(str, "&apos;", "'");
            str = replace(str, "&quot;", "\"");
            str = replace(str, "<br>", "\n");
            str = replace(str, "&nbsp;&nbsp;", "\t");
            str = replace(str, "&nbsp;", " ");
            return str;
        }
    }

    public static Boolean isBlankAll(Object... args) {
        Boolean flag = true;

        for (int i = 0; i < args.length; ++i) {
            if (args[i] instanceof String) {
                if (!isBlank((String) args[i])) {
                    flag = false;
                }
            } else if (null != args[i]) {
                flag = false;
            }
        }

        return flag;
    }

    public static Boolean isBlankOne(Object... args) {
        Boolean flag = false;

        for (int i = 0; i < args.length; ++i) {
            if (args[i] instanceof String) {
                if (isBlank((String) args[i])) {
                    flag = true;
                }
            } else if (null == args[i]) {
                flag = true;
            }
        }

        return flag;
    }

    public static String getFirstUpper(String str) {
        String newStr = "";
        if (str.length() > 0) {
            newStr = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        }

        return newStr;
    }

    public static int indexOfAll(String tagetStr, String str) {
        int i = 0;
        if (null != tagetStr) {
            i = tagetStr.length() - tagetStr.replace(str, "").length();
        }

        return i;
    }

    public static String getNullTo(String str) {
        if (isBlank(str)) {
            str = "";
        }

        return str;
    }

    public static boolean equals(Long a, Long b) {
        boolean flag = false;
        if (null == a) {
            a = 0L;
        }

        if (null == b) {
            b = 0L;
        }

        if (a.equals(b)) {
            flag = true;
        }

        return flag;
    }

    public static boolean equals(Object a, Object b) {
        boolean flag = false;
        if (null == a) {
            a = "";
        }

        Object aStr = String.valueOf(a);
        if (null == b) {
            b = "";
        }

        Object bStr = String.valueOf(b);
        if (aStr.equals(bStr)) {
            flag = true;
        }

        return flag;
    }

    public static boolean equals(String a, String b) {
        boolean flag = false;
        if (null == a) {
            a = "";
        }

        if (null == b) {
            b = "";
        }

        if (a.equals(b)) {
            flag = true;
        }

        return flag;
    }

    public static Class[] getClass(String str, String value, List<Object> valueList) {
        if (isNotBlank(str)) {
            String[] s = str.split(",");
            if (null != s) {
                List<Class> list = new ArrayList();
                String[] valueS = null;
                if (isNotBlank(value)) {
                    valueS = value.split(";");
                }

                Class[] c = new Class[s.length];

                for (int i = 0; i < s.length; ++i) {
                    String str1 = s[i];
                    String v = null;
                    if (null != valueS && valueS.length > i) {
                        v = valueS[i];
                    }

                    if (isNotBlank(str1)) {
                        if (str1.equals("String")) {
                            c[i] = String.class;
                            list.add(String.class);
                            if (null != v) {
                                valueList.add(v);
                            }
                        } else if (str1.equals("int")) {
                            c[i] = Integer.TYPE;
                            list.add(Integer.TYPE);
                            if (null != v) {
                                valueList.add(Long.valueOf(v).intValue());
                            }
                        } else if (str1.equals("Long")) {
                            c[i] = Long.class;
                            list.add(Long.class);
                            if (null != v) {
                                valueList.add(Long.valueOf(v));
                            }
                        } else if (str1.equals("Double")) {
                            c[i] = Double.class;
                            list.add(Double.class);
                            if (null != v) {
                                valueList.add(Double.valueOf(v));
                            }
                        } else if (str1.equals("double")) {
                            c[i] = Double.TYPE;
                            list.add(Double.TYPE);
                            if (null != v) {
                                valueList.add(Double.valueOf(v));
                            }
                        } else if (str1.equals("Date")) {
                            c[i] = Date.class;
                            list.add(Date.class);
                            if (null != v) {
                                valueList.add(DateUtil.string2Date(v, "yyyy-MM-dd"));
                            }
                        } else if (str1.equals("Integer")) {
                            c[i] = Integer.class;
                            list.add(Integer.class);
                            if (null != v) {
                                valueList.add(Integer.valueOf(v));
                            }
                        } else if (!str1.equals("boolean") && !str1.equals("Boolean")) {
                            if (str1.equals("Object")) {
                                c[i] = Object.class;
                                list.add(Object.class);
                                if (null != v) {
                                    valueList.add(v);
                                }
                            } else {
                                Class clazz = null;

                                try {
                                    clazz = Class.forName(str1);
                                    c[i] = clazz;
                                    list.add(clazz);
                                    if (null != v) {
                                        Object o = clazz.newInstance();
                                        valueList.add(o);
                                    }
                                } catch (ClassNotFoundException var12) {
                                    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var12);
                                } catch (InstantiationException var13) {
                                    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var13);
                                } catch (IllegalAccessException var14) {
                                    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var14);
                                }
                            }
                        } else {
                            c[i] = Boolean.TYPE;
                            list.add(Boolean.TYPE);
                            if (null != v) {
                                if ("true".equals(v)) {
                                    valueList.add(true);
                                } else {
                                    valueList.add(false);
                                }
                            }
                        }
                    }
                }

                return c;
            }
        }

        return null;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public int getStrShowCount(String str, String ts) {
        boolean flag = true;
        int i = 0;

        while (flag) {
            int index = str.indexOf(ts);
            if (index != -1) {
                ++i;
                str = str.substring(index + ts.length());
            } else {
                flag = false;
            }
        }

        return i;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String remove(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove) ? replace(str, remove, "", -1) : str;
    }

    public static String remove(String str, char remove) {
        if (!isEmpty(str) && str.indexOf(remove) != -1) {
            char[] chars = str.toCharArray();
            int pos = 0;

            for (int i = 0; i < chars.length; ++i) {
                if (chars[i] != remove) {
                    chars[pos++] = chars[i];
                }
            }

            return new String(chars, 0, pos);
        } else {
            return str;
        }
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if (end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0 ? 0 : increase;
                increase *= max < 0 ? 16 : (max > 64 ? 64 : max);

                StringBuilder buf;
                for (buf = new StringBuilder(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if (max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }

    public static String replaceChars(String str, char searchChar, char replaceChar) {
        return str == null ? null : str.replace(searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if (!isEmpty(str) && !isEmpty(searchChars)) {
            if (replaceChars == null) {
                replaceChars = "";
            }

            boolean modified = false;
            int replaceCharsLength = replaceChars.length();
            int strLength = str.length();
            StringBuilder buf = new StringBuilder(strLength);

            for (int i = 0; i < strLength; ++i) {
                char ch = str.charAt(i);
                int index = searchChars.indexOf(ch);
                if (index >= 0) {
                    modified = true;
                    if (index < replaceCharsLength) {
                        buf.append(replaceChars.charAt(index));
                    }
                } else {
                    buf.append(ch);
                }
            }

            if (modified) {
                return buf.toString();
            } else {
                return str;
            }
        } else {
            return str;
        }
    }

    public static boolean isNumeric(String cs) {
        if (cs != null && cs.length() != 0) {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static String replaceFontSize(String strOrigin, String fontSizeStr, String fontSizeUnit, String newFontSize) {
        StringBuffer sBuffer = new StringBuffer();
        ArrayList<String> arrayList = new ArrayList();
        int fontSizeStrLen = fontSizeStr.length();
        int fontSizeUnitLen = fontSizeUnit.length();
        String strRight = new String(strOrigin);

        int fontSizeIndex;
        while ((fontSizeIndex = strRight.indexOf(fontSizeStr)) > 0) {
            String strLeft = strRight.substring(0, fontSizeIndex + fontSizeStrLen);
            int pxIndex = strRight.substring(fontSizeIndex + fontSizeStrLen).indexOf(fontSizeUnit);
            strRight = strRight.substring(fontSizeIndex + fontSizeStrLen + pxIndex + fontSizeUnitLen);
            arrayList.add(strLeft);
        }

        for (int j = 0; j < arrayList.size(); ++j) {
            sBuffer.append((String) arrayList.get(j)).append(newFontSize).append(fontSizeUnit);
        }

        sBuffer.append(strRight);
        String strModified = sBuffer.toString();
        return strModified;
    }

    private static String deleteCRLFOnce(String input) {
        return input.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");
    }

    public static String deleteCRLF(String input) {
        if (null != input && !"".equals(input)) {
            input = deleteCRLFOnce(input);
            return deleteCRLFOnce(input);
        } else {
            return input;
        }
    }

    public static boolean containsString(String strsWithSymbol, String str, String symbol) {
        String[] strArr = strsWithSymbol.split(symbol);
        List<String> strList = Arrays.asList(strArr);
        return strList.contains(str);
    }
}
