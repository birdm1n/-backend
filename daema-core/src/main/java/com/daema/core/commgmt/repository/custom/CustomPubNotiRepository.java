package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.PubNoti;

import java.util.HashMap;
import java.util.List;

public interface CustomPubNotiRepository {

	HashMap<String, List> getMappingList(Long telecom, Long network);
	List<PubNoti> getHistoryList(PubNoti pubNoti);
}
