package com.daema.dto.common;

import com.daema.common.util.CommonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> extends PagingDto {

    private List<T> resultList;

    public ResponseDto (Class<?> clazz, Page<T> dataList) {

        if(dataList != null) {
            this.setPageNo(dataList.getPageable().getPageNumber() + 1);
            this.setPerPageCnt(dataList.getPageable().getPageSize());
            this.setTotalCnt(dataList.getTotalElements());
            this.setNumberOfElements(dataList.getNumberOfElements());

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
    public ResponseDto (Function<T, T> fn, Page<T> dataList){
        if(dataList != null) {
            this.setResultList(dataList.stream().map(fn).collect(Collectors.toList()));
        }
    }
}
































