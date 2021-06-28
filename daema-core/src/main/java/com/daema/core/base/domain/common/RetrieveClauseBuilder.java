package com.daema.core.base.domain.common;

import com.daema.core.base.dto.PagingDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RetrieveClauseBuilder<T> implements Serializable {

    private static final long serialVersionUID = 4255525101821105338L;

    public static PageRequest setOffsetLimit(JPQLQuery<?> query, PagingDto requestDto){
        PageRequest pageable = PageRequest.of(requestDto.getPageNo() - 1, requestDto.getPerPageCnt());

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return pageable;
    }

    public static LocalDateTime stringToLocalDateTime(String date){
        return formattingDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime stringToLocalDateTime(String date, String type){
        if("s".equals(type)){
            date = date.concat(" 00:00:00");
        }else if("e".equals(type)){
            date = date.concat(" 23:59:59");
        }

        return formattingDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    private static LocalDateTime formattingDate(String date, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, formatter);
    }
}