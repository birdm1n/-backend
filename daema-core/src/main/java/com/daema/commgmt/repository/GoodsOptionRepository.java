package com.daema.commgmt.repository;

import com.daema.commgmt.domain.GoodsOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsOptionRepository extends JpaRepository<GoodsOption, Number> {
    List<GoodsOption> findByGoodsIdIn(List<Number> goodsId);
    void deleteByGoodsId(Number goodsId);
}
