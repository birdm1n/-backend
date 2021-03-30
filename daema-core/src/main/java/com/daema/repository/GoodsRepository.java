package com.daema.repository;

import com.daema.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Number> , CustomGoodsRepository {
}
