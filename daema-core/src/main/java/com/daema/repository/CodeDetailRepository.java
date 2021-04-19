package com.daema.repository;

import com.daema.domain.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, Integer> {
    List<CodeDetail> findAllByCodeIdInAndUseYnOrderByCodeIdAscOrderNumAsc(List<String> codeIds, String useYn);
}
