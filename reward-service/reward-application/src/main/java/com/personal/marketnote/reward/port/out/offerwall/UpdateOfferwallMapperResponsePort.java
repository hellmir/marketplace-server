package com.personal.marketnote.reward.port.out.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;

public interface UpdateOfferwallMapperResponsePort {
    OfferwallMapper updateResponse(Long id, String responsePayload, JsonNode responsePayloadJson);
}
