package com.personal.marketnote.community.port.in.result.post;

import com.personal.marketnote.community.domain.post.PostCategory;

import java.util.Arrays;
import java.util.List;

public record GetPostCategoriesResult(
        List<PostCategoryItemResult> categories
) {
    public static GetPostCategoriesResult of(
            List<PostCategoryItemResult> categories
    ) {
        return new GetPostCategoriesResult(categories);
    }

    public static GetPostCategoriesResult from(PostCategory[] postCategories) {
        return new GetPostCategoriesResult(
                Arrays.stream(postCategories)
                        .map(
                                category -> PostCategoryItemResult.of(
                                        category.getCode(), category.getDescription()
                                )
                        )
                        .toList()
        );
    }
}
