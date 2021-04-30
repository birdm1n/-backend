package com.daema.rest.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

public class CommonUtil {

    public static <E> List<E> entityToDto(Class<?> clazz, List<E> dataList, String methodName) {

        List<E> dtoList = new ArrayList<>();

        try {
            Object obj = clazz.newInstance();

            dtoList = (List<E>) Optional.ofNullable(dataList)
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(items -> {
                        try {
                            return clazz.getMethod(methodName, items.getClass()).invoke(obj, items);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new ArrayList<E>();
                        }
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtoList;
    }

    public static <E> boolean isNotEmptyList(Collection<E> itemList) {
        return itemList != null && !itemList.isEmpty();
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static String getCmnBarcode(String inBarcode) {
        int barcodeSize = inBarcode.length();
        String outBarcode = "";

        // 1. 바코드의 뒷자리 8자리를 구한다.
        String digit8 = inBarcode.substring(barcodeSize - 8, barcodeSize);
        // 2. 마지막 문자제거 ( s20은 바코드를 스캔하면 마지막에 문자하나가 추가된다. )
        String digitFirst7 = digit8.substring(0, digit8.length() - 1);

        // 3. 7자리가 숫자인지 확인

        if (!isNumeric(digitFirst7)) {
            // 8자리중 뒤에서 7자리 구한다. ( s10은 s20과 다르게 바코드를 찍으면 문자가 추가되지 않는다. )
            String digitLast7 = digit8.substring(1, digit8.length());
            if (!isNumeric(digitLast7)) {
                // 애플 (문자숫자조합) // 전체 바코드 중 뒤에서 4자리가 공통
                outBarcode = inBarcode.substring(barcodeSize - 4, barcodeSize);
            } else {
                // S10을 포함한 삼성  //
                outBarcode = inBarcode.substring(0, 8);
            }
        } else {
            // 바코드 전체가 숫자면 LG ( 현재는 velvet-KT 만 확인함 )
            if (isNumeric(inBarcode)) {
                outBarcode = inBarcode.substring(0, 7);
            } else {
                // S20 //
                String uniqBarcode = inBarcode.substring(
                        barcodeSize - 8,
                        barcodeSize
                        );
                outBarcode = inBarcode.replace(uniqBarcode, "");
            }
        }
        return outBarcode;
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
