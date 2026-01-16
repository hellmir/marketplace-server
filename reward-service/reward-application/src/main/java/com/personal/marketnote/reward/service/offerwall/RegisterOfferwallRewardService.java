package com.personal.marketnote.reward.service.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import com.personal.marketnote.reward.port.out.offerwall.UpdateOfferwallMapperResponsePort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterOfferwallRewardService implements RegisterOfferwallRewardUseCase {
    private final SaveOfferwallMapperPort saveOfferwallMapperPort;
    private final UpdateOfferwallMapperResponsePort updateOfferwallMapperResponsePort;
    private final ObjectMapper objectMapper;

    @Override
    public String register(OfferwallCallbackCommand command) {
        OfferwallMapper saved = saveOfferwallMapperPort.save(
                OfferwallMapper.from(RewardCommandToStateMapper.mapToOfferwallMapperCreateState(command))
        );

        JsonNode responsePayloadJson = buildSuccessResponsePayloadJson();
        String responsePayload = responsePayloadJson.toString();

        updateOfferwallMapperResponsePort.updateResponse(saved.getId(), responsePayload, responsePayloadJson);
        return responsePayload;
    }

    private JsonNode buildSuccessResponsePayloadJson() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("Result", true);
        node.put("ResultCode", 1);
        node.put("ResultMsg", "success");
        return node;
    }
}
