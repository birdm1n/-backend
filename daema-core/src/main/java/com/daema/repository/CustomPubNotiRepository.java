package com.daema.repository;

import com.daema.domain.PubNoti;

import java.util.HashMap;
import java.util.List;

public interface CustomPubNotiRepository {

	HashMap<String, List> getMappingList(int telecom, int network);
	List<PubNoti> getHistoryList(PubNoti pubNoti);
}
