package com.personal.marketnote.community.port.in.result.post;

public record PostCategoryItemResult(
        String name,
        String description
) {
    public static PostCategoryItemResult of(String name, String description) {
        return new PostCategoryItemResult(name, description);
    }
}
