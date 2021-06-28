package com.daema.core.base.repository;

import com.daema.core.base.domain.SocialData;
import org.springframework.data.repository.CrudRepository;

public interface SocialDataRepository extends CrudRepository<SocialData,Long> {

    SocialData findBySocialDataIdAndSocialType(String username, String type);

}
