package com.emr.slgi.common.constants;

public class RegexPatterns {
    public static final String UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String RRN = "^\\d{6}-\\d{7}$";
    public static final String USERID = "^[A-Za-z0-9_]+$";
    public static final String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
    public static final String JWT_TOKEN = "^[A-Za-z0-9\\-_=]+\\.[A-Za-z0-9\\-_=]+\\.[A-Za-z0-9\\-_=]+$";
    public static final String PHONE = "^(?:0\\d{1,2}-)?\\d{3,4}-\\d{4}$";
}
