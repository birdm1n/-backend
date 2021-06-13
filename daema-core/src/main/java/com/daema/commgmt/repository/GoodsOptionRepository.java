package com.daema.commgmt.repository;

import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsOptionRepository extends JpaRepository<GoodsOption, Number> {
    List<GoodsOption> findByGoodsGoodsIdInAndDelYn(List<Number> goodsId, String delYn);
    List<GoodsOption> findByGoodsIsInAndDelYn(List<Goods> goods, String delYn);
    GoodsOption findTopByGoodsAndCapacityAndColorNameAndUnLockYnAndDelYn(Goods goods, String capacity, String colorName, String unLockYn, String delYn);
}
