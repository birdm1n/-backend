package com.daema.core.base.repository;

import com.daema.core.base.domain.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, Long> {
    List<CodeDetail> findAllByCodeIdInAndUseYnOrderByCodeIdAscOrderNumAsc(List<String> codeIds, String useYn);
}
