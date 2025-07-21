package com.emr.slgi.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static final String MEMBER_STATUS_REGEX = "^R\\d{3}$";
    public static final String MEMBER_UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String RRN_REGEX = "^\\d{6}-\\d{7}$";

    public static List<Boolean> regexValidate(Map<String, String> list) {

        List<Boolean> res = new ArrayList<>();

        list.forEach((regex, value) -> {

            if(regex == null || value == null) {
                res.add(false);
            }else{
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                res.add(matcher.matches());
            }

        });

        return res;

    }

    public static List<Boolean> regexValidation(Map<String, Collection<?>> regexToValues) {

        List<Boolean> result = new ArrayList<>();

        try {

            regexToValues.forEach((regex, values) -> {

                if (regex == null || values == null) {
                    values.forEach(v -> result.add(false));
                } else {
                    Pattern pattern = Pattern.compile(regex);
                    for (Object value : values) {
                        if (value == null) {
                            result.add(false);
                        } else {
                            Matcher matcher = pattern.matcher(String.valueOf(value));
                            result.add(matcher.matches());
                        }
                    }
                }

            });

        } catch(Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.RETRY);

        }

        return result;

    }



}
