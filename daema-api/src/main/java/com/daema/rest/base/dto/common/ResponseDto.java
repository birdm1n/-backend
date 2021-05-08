package com.daema.rest.base.dto.common;

import com.daema.rest.common.util.CommonUtil;
import com.daema.base.dto.PagingDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> extends PagingDto {

    private List<T> resultList;

    public ResponseDto (Class<?> clazz, Page<T> dataList) {

        if(dataList != null) {

            setResponsePaginationInfo(dataList);

            try {

                Object obj = clazz.newInstance();

                this.setResultList(
                        (List<T>)
                                dataList.getContent().stream()
                                        .map(entity -> {
                                                    try {
                                                        return clazz.getMethod("from", entity.getClass()).invoke(obj, entity);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        return null;
                                                    }
                                                }
                                        ).collect(Collectors.toList())
                );
            } catch (Exception ignore_e) {
                ignore_e.printStackTrace();
            }
        }
    }

    public ResponseDto (Class<?> clazz, Page<T> dataList, String method) {

        if(dataList != null) {

            setResponsePaginationInfo(dataList);

            try {

                Object obj = clazz.newInstance();

                this.setResultList(
                        (List<T>)
                                dataList.getContent().stream()
                                        .map(entity -> {
                                                    try {
                                                        return clazz.getMethod(method, entity.getClass()).invoke(obj, entity);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        return null;
                                                    }
                                                }
                                        ).collect(Collectors.toList())
                );
            } catch (Exception ignore_e) {
                ignore_e.printStackTrace();
            }
        }
    }

    public ResponseDto (Class<?> clazz, List<T> dataList) {
        if(dataList != null) {
            this.setResultList(CommonUtil.entityToDto(clazz, dataList, "from"));
        }
    }

    /**
     * new ResponseDto(dataList, entity -> (Dto.from((Entity) entity)));
     * @param dataList
     * @param fn
     */
    public ResponseDto (Page<T> dataList, Function<T, T> fn){
        if(dataList != null) {

            setResponsePaginationInfo(dataList);

            this.setResultList(dataList.stream().map(fn).collect(Collectors.toList()));
        }
    }
    
    // Page<DTO>인 경우 사용
    public ResponseDto (Page<T> dataList){
        if(dataList != null) {

            setResponsePaginationInfo(dataList);

            this.setResultList(dataList.stream().collect(Collectors.toList()));
        }
    }

    private void setResponsePaginationInfo(Page<T> dataList){
        setPageNo(dataList.getNumber() + 1);
        setPerPageCnt(dataList.getPageable().getPageSize());
        setTotalCnt(dataList.getTotalElements());
        setNumberOfElements(dataList.getNumberOfElements());

        setPageStartNo(((getPageNo() - 1) / getPageRange() ) * getPageRange() + 1);
        setPageEndNo(dataList.getTotalPages() == 0 ? 1 :
                Math.min((getPageStartNo() + (getPageRange() - 1)), dataList.getTotalPages()));

        setPageNumList(IntStream.rangeClosed(getPageStartNo(), getPageEndNo()).boxed().collect(Collectors.toList()));
        setPageLastNo(dataList.getTotalPages());
    }
}
































