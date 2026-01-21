package com.personal.marketnote.user.adapter.in.web.identification.response;

public record WhoAmIResponse(
        String publicIp
) {
    public static WhoAmIResponse of(String publicIp) {
        return new WhoAmIResponse(publicIp);
    }
}
