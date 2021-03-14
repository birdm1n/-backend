package com.daema.repository;

import com.daema.domain.SocialData;
import org.springframework.data.repository.CrudRepository;

public interface SocialDataRepository extends CrudRepository<SocialData,Long> {

    SocialData findByIdAndType(String username, String type);

}
