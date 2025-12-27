package com.personal.marketnote.user.adapter.in.client.user.mapper;

import com.personal.marketnote.user.adapter.in.client.user.request.AcceptTermsRequest;
import com.personal.marketnote.user.port.in.command.AcceptTermsCommand;

public class TermsRequestToCommandMapper {
    public static AcceptTermsCommand mapToCommand(AcceptTermsRequest acceptTermsRequest) {
        return AcceptTermsCommand.of(acceptTermsRequest.getIds());
    }
}
