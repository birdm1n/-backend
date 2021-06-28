package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.repository.custom.CustomGoodsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Number> , CustomGoodsRepository {

}
