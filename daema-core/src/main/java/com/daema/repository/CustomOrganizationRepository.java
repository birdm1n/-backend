package com.daema.repository;

import java.util.HashMap;
import java.util.List;

public interface CustomOrganizationRepository {

	HashMap<String, List> getOrgnztAndMemberList(long storeId);
}
