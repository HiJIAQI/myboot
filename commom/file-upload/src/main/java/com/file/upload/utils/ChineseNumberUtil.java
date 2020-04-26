package com.file.upload.utils;

/**
 * 功能描述：中文数字与阿拉伯数字转换工具
 *
 * @author JIAQI
 * @date 2020/4/24 - 10:59
 */
public class ChineseNumberUtil {
    private static final String[] chnNumberChar = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] chnUnitChar = new String[]{"", "十", "百", "千"};
    private static final String[] chnUnitSection = new String[]{"", "万", "亿", "万亿"};

    private static final Unit[] chnValuePair = new Unit[]{
            new Unit("十", 10, false),
            new Unit("百", 100, false),
            new Unit("千", 1000, false),
            new Unit("万", 10000, true),
            new Unit("亿", 100000000, true)
    };

    public static String numberToChinese(long num) {
        if (num == 0) {
            return chnNumberChar[0];
        }
        StringBuilder sb = new StringBuilder();
        boolean needZero = false;
        int unit = 0;
        while (num > 0) {//重复这个过程，直到 num 等于 0为止
            //首先要对阿拉伯数字分节，并确定节权位名称
            // num 对 10000 取模可得到一个section ，
            int section = (int) (num % 10000);
            // 将这个 section 转成中文数字，然后根据节的位置补上节权位，即可完成一个节的中文数字转换
            String sectionStr = sectionToChinese(section);
            if (needZero) {
                sb.insert(0, chnNumberChar[0]);
            }
            if (!"".equals(sectionStr)) {
                sb.insert(0, sectionStr + chnUnitSection[unit]);
            }
            num = num / 10000;
            unit++;
            //如果这个section小于1000， 则下个section前要补零
            needZero = section < 1000 && section > 0;
        }
        return sb.toString();
    }

    private static String sectionToChinese(int num) {
        StringBuilder sb = new StringBuilder();
        int unit = 0;
        boolean needZero = false; //是否需要补零，默认false，因为末尾0不需要补零
        while (num > 0) {
            int c = num % 10;
            if (c == 0) {//该位数字为0
                if (needZero) {
                    sb.insert(0, chnNumberChar[c]);
                }
                //已有一个零，再出现连续的0也不需要补零
                needZero = false;

            } else {
                sb.insert(0, chnNumberChar[c] + chnUnitChar[unit]);
                //数字不为0时，两个不为0的数字间弱出现0需要补零
                needZero = true;
            }

            unit++;
            num = num / 10;
        }
        return sb.toString();
    }

    /**
     * 中文数字“四万二千五百一十三”，对每个权位依次转换成倍数并求和：4×10000 + 2×1000 +
     * 5×100 +1×10+3×1，就可以得到对应的阿拉伯数字 42513
     * <p>
     * 除此之外，节权位也需要考虑，因为它常和其他权位连在一起使用，
     * 比如“二十万”中的“十”是数字权位，“万”是节权位
     * <p>
     * 节权位比较特殊，它不是与
     * 之相邻的数字的倍数，而是整个小节的倍数，因此转换过程中，需要临时保存每个节权位出现之前的小节的值
     *
     * @param ch
     */
    public static long chineseToNumber(String ch) {
        long result = 0;
        char[] chars = ch.toCharArray();

        int number = 0;
        long sectionVal = 0;//临时保存每个节权位出现之前的小节的值
        for (int i = 0; i < chars.length; i++) {
            String c = chars[i] + "";
            if (chnNumberChar[0].equals(c)) {
                continue;
            }
            int num = chineseToValue(c);
            if (num > 0) { //是数值
                number = num;
                if (i == chars.length - 1) {//已经是最后一个字符了
                    sectionVal += number;
                    result += sectionVal;
                }
            } else {//是单位
                Unit unit = chineseToUnit(c);
                if (unit == null) {
                    continue;
                }
                if (unit.secUnit) {//是节权位说明一个节已经结束
                    result += (sectionVal + number) * unit.value;
                    sectionVal = 0;
                } else {
                    sectionVal += (number * unit.value);
                }
                number = 0;
                if (i == chars.length - 1) {//已经是最后一个字符了
                    result += sectionVal;
                }
            }
        }
        return result;
    }

    /**
     * 负责查表完成中文数字到英文数字的转换，如果返回-1，则表示这是一个权位字符
     *
     * @param c
     * @return
     */
    private static int chineseToValue(String c) {
        for (int i = 0; i < chnNumberChar.length; i++) {
            if (c.equals(chnNumberChar[i])) {
                return i;
            }
        }
        return -1;

    }

    private static Unit chineseToUnit(String c) {
        for (Unit unit : chnValuePair) {
            if (unit.name.equals(c)) {
                return unit;
            }
        }
        return null;
    }

    static class Unit {
        String name;
        int value; //10的倍数值
        boolean secUnit;//是否是节权位

        Unit(String name, int value, boolean secUnit) {
            this.name = name;
            this.value = value;
            this.secUnit = secUnit;
        }
    }
}

