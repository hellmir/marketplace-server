package com.personal.marketnote.product.adapter.in.client.option.response;

import com.personal.marketnote.product.port.in.result.UpsertProductOptionsResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UpsertProductOptionsResponse {
    private Long id;
    private List<Long> optionIds;

    public static UpsertProductOptionsResponse from(UpsertProductOptionsResult result) {
        return UpsertProductOptionsResponse.builder()
                .id(result.id())
                .optionIds(result.optionIds())
                .build();
    }
}


