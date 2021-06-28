package com.daema.rest.base.service;

import com.daema.core.base.enums.ShellEnum;
import com.daema.rest.common.util.ShellUtil;
import com.daema.core.wms.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShellService {
    private final DeviceRepository deviceRepository;

    // 비즈니스 로직 처리 후 shell call
    public void callShell(ShellEnum shellType) {
        // 7. shell 콜 하여 구전산 반영 - total_barcode, model_name, cmn_barcode
//        deviceRepository.find
        ShellUtil.execute(shellType);
    }

}