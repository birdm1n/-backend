package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.dto.response.PubNotiRawDataListDto;

import java.util.List;

public interface CustomPubNotiRawDataRepository {
	void migrationSmartChoiceData(long memberSeq);
	List<PubNotiRawDataListDto> searchPubNotiRawData();
}
