package com.personal.marketnote.reward.port.out.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;

public interface SaveOfferwallMapperPort {
    OfferwallMapper save(OfferwallMapper offerwallMapper);
}
