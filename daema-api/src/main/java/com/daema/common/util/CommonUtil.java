package com.daema.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        }catch (Exception e){
            e.printStackTrace();
        }

        return dtoList;
    }

    public static <E> boolean isNotEmptyList(Collection<E> itemList){
        return itemList != null && !itemList.isEmpty();
    }
}
