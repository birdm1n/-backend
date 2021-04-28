package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.dto.response.PubNotiRawDataListDto;

import java.util.List;

public interface CustomPubNotiRawDataRepository {
	void migrationSmartChoiceData(long memberSeq);
	List<PubNotiRawDataListDto> searchPubNotiRawData();
}
