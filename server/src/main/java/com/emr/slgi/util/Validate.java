package com.emr.slgi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Validate {

    public static final String MEMBER_STATUS_REGEX = "^R\\d{3}$";
    public static final String MEMBER_UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String RRN_REGEX = "^\\d{6}-\\d{7}$";
    // 좀 더 복잡한 주민번호 정규식 -> ^(?:[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])-[1-4][0-9]{6}$
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

        log.info(res.toString());

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
