package com.personal.marketnote.community.adapter.in.client.post.response;

import com.personal.marketnote.community.port.in.result.post.GetPostCategoriesResult;
import com.personal.marketnote.community.port.in.result.post.PostCategoryItemResult;

import java.util.List;

public record GetPostCategoriesResponse(
        List<PostCategoryItemResult> categories
) {
    public static GetPostCategoriesResponse from(GetPostCategoriesResult getPostCategoriesResult) {
        return new GetPostCategoriesResponse(getPostCategoriesResult.categories());
    }
}
