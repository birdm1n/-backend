package com.daema.domain.common;

import com.daema.domain.dto.common.SearchParamDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RetrieveClauseBuilder<T> implements Serializable {

    private static final long serialVersionUID = 4255525101821105338L;

    public static PageRequest setOffsetLimit(JPQLQuery<?> query, SearchParamDto requestDto){
        PageRequest pageable = PageRequest.of(requestDto.getPageNo(), requestDto.getPerPageCnt());

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return pageable;
    }

    public static LocalDateTime stringToLocalDateTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        return dateTime;
    }
}