package com.personal.marketnote.reward.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class VendorCommunicationPayloadGenerator {
    private final ObjectMapper objectMapper;

    public JsonNode buildResponsePayloadJson(boolean isSuccess, int resultCode, String resultMessage) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("Result", isSuccess);
        node.put("ResultCode", resultCode);
        node.put("ResultMsg", resultMessage);

        return node;
    }

    public JsonNode buildPayloadJson(
            String rewardKey,
            String usn,
            String campaignKey,
            Integer campaignType,
            String campaignName,
            Long quantity,
            String signedValue,
            Integer appKey,
            String appName,
            String adid,
            String idfa,
            String timeStamp
    ) {
        ObjectNode node = objectMapper.createObjectNode();

        putIfNotBlank(node, "reward_key", rewardKey);
        putIfNotBlank(node, "usn", usn);
        putIfNotBlank(node, "campaign_key", campaignKey);
        putIfNotBlank(node, "campaign_type", campaignType);
        putIfNotBlank(node, "campaign_name", campaignName);
        putIfNotBlank(node, "quantity", quantity);
        putIfNotBlank(node, "signed_value", signedValue);
        putIfNotBlank(node, "app_key", appKey);
        putIfNotBlank(node, "app_name", appName);
        putIfNotBlank(node, "adid", adid);
        putIfNotBlank(node, "idfa", idfa);
        putIfNotBlank(node, "time_stamp", timeStamp);

        return node;
    }

    private void putIfNotBlank(ObjectNode node, String field, String value) {
        if (StringUtils.hasText(value)) {
            node.put(field, value);
        }
    }

    private void putIfNotBlank(ObjectNode node, String field, Number value) {
        if (FormatValidator.hasValue(value)) {
            node.put(field, value.toString());
        }
    }
}
