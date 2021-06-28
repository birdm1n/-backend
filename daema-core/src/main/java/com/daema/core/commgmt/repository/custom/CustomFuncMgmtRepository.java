package com.daema.core.commgmt.repository.custom;

import java.util.List;

public interface CustomFuncMgmtRepository {

	List<String> getMemberEnableUrlPathList(long memberSeq, long storeId);
}
