package cn.wolfcode.common.utils;

public class ValidateUtils {
    public static final String PHONE_PATTERN = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    public static final String LICENSE_PLATE_PATTERN = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

    public static boolean validPhone(String phone){
        return phone.matches(PHONE_PATTERN);
    }

    public static boolean validLicensePlate(String phone){
        return phone.matches(LICENSE_PLATE_PATTERN);
    }

}
