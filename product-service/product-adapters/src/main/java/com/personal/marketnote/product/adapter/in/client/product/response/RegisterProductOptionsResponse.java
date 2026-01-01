package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.RegisterProductOptionsResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class RegisterProductOptionsResponse {
    private Long id;
    private List<Long> optionIds;

    public static RegisterProductOptionsResponse from(RegisterProductOptionsResult result) {
        return RegisterProductOptionsResponse.builder()
                .id(result.id())
                .optionIds(result.optionIds())
                .build();
    }
}


