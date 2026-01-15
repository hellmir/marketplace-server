package com.personal.marketnote.community.port.in.command.post;

public record UpdatePostCommand(
        Long id,
        String title,
        String content
) {
    public static UpdatePostCommand of(Long id, String title, String content) {
        return new UpdatePostCommand(id, title, content);
    }
}
