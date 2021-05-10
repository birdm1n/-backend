package com.daema.commgmt.repository;

import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.repository.custom.CustomGoodsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Number> , CustomGoodsRepository {

}
