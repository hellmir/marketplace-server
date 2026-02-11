package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.marketnote.common.utility.FormatValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterFasstoDeliveryDataDeserializer extends JsonDeserializer<List<RegisterFasstoDeliveryItemResponse>> {
    @Override
    public List<RegisterFasstoDeliveryItemResponse> deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException {
        ObjectCodec codec = parser.getCodec();
        ObjectMapper mapper = codec instanceof ObjectMapper objectMapper ? objectMapper : new ObjectMapper();
        JsonNode node = mapper.readTree(parser);

        if (FormatValidator.hasNoValue(node) || node.isNull()) {
            return List.of();
        }

        if (node.isArray()) {
            List<RegisterFasstoDeliveryItemResponse> items = new ArrayList<>();
            for (JsonNode itemNode : node) {
                if (FormatValidator.hasNoValue(itemNode) || itemNode.isNull()) {
                    continue;
                }
                items.add(mapper.treeToValue(itemNode, RegisterFasstoDeliveryItemResponse.class));
            }
            return items;
        }

        if (node.isObject() && hasItemFields(node)) {
            RegisterFasstoDeliveryItemResponse item = mapper.treeToValue(node, RegisterFasstoDeliveryItemResponse.class);
            return List.of(item);
        }

        return List.of();
    }

    private boolean hasItemFields(JsonNode node) {
        return node.hasNonNull("code")
                || node.hasNonNull("msg")
                || node.hasNonNull("slipNo")
                || node.hasNonNull("ordNo");
    }
}
