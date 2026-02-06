package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.product.domain.option.ProductOption;
import com.personal.marketnote.product.domain.option.ProductOptionCategory;
import com.personal.marketnote.product.domain.option.ProductOptionCategorySnapshotState;
import com.personal.marketnote.product.domain.option.ProductOptionSnapshotState;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicySnapshotState;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSnapshotState;
import com.personal.marketnote.product.exception.PricePolicyNotFoundException;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.result.product.GetProductInfoWithOptionsResult;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.GetPricePoliciesUseCase;
import com.personal.marketnote.product.port.in.usecase.product.GetProductInventoryUseCase;
import com.personal.marketnote.product.port.out.file.FindProductImagesPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.review.FindProductReviewAggregatesPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductUseCaseTest {
    @Mock
    private GetPricePoliciesUseCase getPricePoliciesUseCase;
    @Mock
    private GetProductInventoryUseCase getProductInventoryUseCase;
    @Mock
    private FindProductPort findProductPort;
    @Mock
    private FindProductOptionCategoryPort findProductOptionCategoryPort;
    @Mock
    private FindPricePoliciesPort findPricePoliciesPort;
    @Mock
    private FindProductImagesPort findProductImagesPort;
    @Mock
    private FindProductReviewAggregatesPort findProductReviewAggregatesPort;
    @Mock
    private Executor productImageExecutor;

    @InjectMocks
    private GetProductService getProductService;

    @Test
    @DisplayName("상품 ID로 상품을 조회한다")
    void getProduct_success() {
        Product product = buildProduct(1L, false);

        when(findProductPort.findById(1L)).thenReturn(Optional.of(product));

        Product result = getProductService.getProduct(1L);

        assertThat(result).isEqualTo(product);
        verify(findProductPort).findById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 상품 조회 시 예외를 던진다")
    void getProduct_notFound() {
        when(findProductPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getProductService.getProduct(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("가격 정책 ID로 상품 상세 정보를 조회할 때 가격 정책이 없으면 예외를 던진다")
    void getProductInfo_byPricePolicyId_notFound() {
        when(getPricePoliciesUseCase.getPricePoliciesAndOptions(List.of(10L))).thenReturn(List.of());

        assertThatThrownBy(() -> getProductService.getProductInfo(10L))
                .isInstanceOf(PricePolicyNotFoundException.class)
                .hasMessageContaining("가격 정책을 찾을 수 없습니다");

        verifyNoInteractions(
                findProductPort,
                findProductOptionCategoryPort,
                findPricePoliciesPort,
                findProductImagesPort,
                getProductInventoryUseCase,
                findProductReviewAggregatesPort
        );
    }

    @Test
    @DisplayName("가격 정책 ID로 상품 상세 정보 조회 시 옵션 가격 정책을 선택한다")
    void getProductInfo_byPricePolicyId_selectsOptionPricePolicy() {
        stubProductImageExecutor();
        Product product = buildProduct(1L, true);
        ProductOption option1 = buildOption(101L, "옵션1");
        ProductOption option2 = buildOption(102L, "옵션2");
        List<ProductOption> optionList = List.of(option1, option2);

        PricePolicy policyForLookup = buildPricePolicy(500L, product, null, optionList);
        when(getPricePoliciesUseCase.getPricePoliciesAndOptions(List.of(500L)))
                .thenReturn(List.of(policyForLookup));

        when(findProductPort.findById(1L)).thenReturn(Optional.of(product));
        ProductOptionCategory category = buildOptionCategory(11L, product, optionList);
        when(findProductOptionCategoryPort.findActiveWithOptionsByProductId(1L))
                .thenReturn(List.of(category));

        PricePolicy defaultPolicy = buildPricePolicy(400L, product, null, List.of());
        PricePolicy selectedPolicy = buildPricePolicy(500L, product, List.of(101L, 102L), List.of());
        when(findPricePoliciesPort.findByProductId(1L)).thenReturn(List.of(defaultPolicy, selectedPolicy));

        when(getProductInventoryUseCase.getProductStocks(List.of(500L)))
                .thenReturn(Map.of(500L, 12));

        GetFilesResult representativeImages = buildFilesResult(1L, "rep.jpg");
        GetFilesResult contentImages = buildFilesResult(2L, "content.jpg");
        when(findProductImagesPort.findImagesByProductIdAndSort(1L, FileSort.PRODUCT_REPRESENTATIVE_IMAGE))
                .thenReturn(Optional.of(representativeImages));
        when(findProductImagesPort.findImagesByProductIdAndSort(1L, FileSort.PRODUCT_CONTENT_IMAGE))
                .thenReturn(Optional.of(contentImages));

        GetProductInfoWithOptionsResult result = getProductService.getProductInfo(500L);

        assertThat(result.productInfo().selectedPricePolicy().id()).isEqualTo(500L);
        assertThat(result.productInfo().stock()).isEqualTo(12);
        assertThat(result.categories()).hasSize(1);
        assertThat(result.categories().getFirst().options())
                .extracting(option -> option.isSelected())
                .containsOnly(true);
        assertThat(result.representativeImages()).isEqualTo(representativeImages);
        assertThat(result.contentImages()).isEqualTo(contentImages);
        assertThat(result.pricePolicies())
                .extracting(pricePolicy -> pricePolicy.id())
                .containsExactlyInAnyOrder(400L, 500L);
    }

    @Test
    @DisplayName("상품 상세 정보 조회 시 기본 가격 정책이 없으면 예외를 던진다")
    void getProductInfo_missingDefaultPolicy_throws() {
        stubProductImageExecutor();
        Product product = buildProduct(2L, false);
        PricePolicy optionPolicy = buildPricePolicy(900L, product, List.of(1L), List.of());

        when(findProductPort.findById(2L)).thenReturn(Optional.of(product));
        when(findProductOptionCategoryPort.findActiveWithOptionsByProductId(2L))
                .thenReturn(List.of());
        when(findPricePoliciesPort.findByProductId(2L)).thenReturn(List.of(optionPolicy));
        when(findProductImagesPort.findImagesByProductIdAndSort(2L, FileSort.PRODUCT_REPRESENTATIVE_IMAGE))
                .thenReturn(Optional.empty());
        when(findProductImagesPort.findImagesByProductIdAndSort(2L, FileSort.PRODUCT_CONTENT_IMAGE))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getProductService.getProductInfo(2L, List.of()))
                .isInstanceOf(PricePolicyNotFoundException.class)
                .hasMessageContaining("가격 정책을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("상품 상세 정보 조회 시 상품이 없으면 예외를 던진다")
    void getProductInfo_productNotFound_throws() {
        stubProductImageExecutor();
        stubProductImagesEmpty(77L);
        when(findProductPort.findById(77L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getProductService.getProductInfo(77L, List.of()))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다");

        verify(findProductPort).findById(77L);
        verifyNoInteractions(findProductOptionCategoryPort, findPricePoliciesPort, getProductInventoryUseCase);
    }

    @Test
    @DisplayName("상품 상세 정보 조회 시 선택된 옵션이 가격 정책과 불일치하면 기본 가격 정책을 사용한다")
    void getProductInfo_optionMismatch_usesDefaultPolicy() {
        stubProductImageExecutor();
        Product product = buildProduct(3L, true);
        ProductOption option1 = buildOption(201L, "옵션-A");
        ProductOptionCategory category = buildOptionCategory(21L, product, List.of(option1));
        PricePolicy defaultPolicy = buildPricePolicy(700L, product, null, List.of());
        PricePolicy optionPolicy = buildPricePolicy(701L, product, List.of(202L), List.of());

        when(findProductPort.findById(3L)).thenReturn(Optional.of(product));
        when(findProductOptionCategoryPort.findActiveWithOptionsByProductId(3L))
                .thenReturn(List.of(category));
        when(findPricePoliciesPort.findByProductId(3L)).thenReturn(List.of(defaultPolicy, optionPolicy));
        when(getProductInventoryUseCase.getProductStocks(List.of(700L)))
                .thenReturn(Map.of(700L, 5));
        when(findProductImagesPort.findImagesByProductIdAndSort(3L, FileSort.PRODUCT_REPRESENTATIVE_IMAGE))
                .thenReturn(Optional.empty());
        when(findProductImagesPort.findImagesByProductIdAndSort(3L, FileSort.PRODUCT_CONTENT_IMAGE))
                .thenReturn(Optional.empty());

        GetProductInfoWithOptionsResult result = getProductService.getProductInfo(3L, List.of(201L));

        assertThat(result.productInfo().selectedPricePolicy().id()).isEqualTo(700L);
        assertThat(result.representativeImages()).isNull();
        assertThat(result.contentImages()).isNull();
    }

    @Test
    @DisplayName("상품 상세 정보 조회 시 옵션 개별 노출이 꺼진 상품은 기본 가격 정책을 사용한다")
    void getProductInfo_findAllOptionsDisabled_usesDefaultPolicy() {
        stubProductImageExecutor();
        Product product = buildProduct(4L, false);
        ProductOption option = buildOption(301L, "옵션");
        ProductOptionCategory category = buildOptionCategory(41L, product, List.of(option));
        List<Long> selectedOptionIds = List.of(301L);
        PricePolicy defaultPolicy = buildPricePolicy(800L, product, null, List.of());
        PricePolicy optionPolicy = buildPricePolicy(801L, product, selectedOptionIds, List.of());

        when(findProductPort.findById(4L)).thenReturn(Optional.of(product));
        when(findProductOptionCategoryPort.findActiveWithOptionsByProductId(4L))
                .thenReturn(List.of(category));
        when(findPricePoliciesPort.findByProductId(4L)).thenReturn(List.of(defaultPolicy, optionPolicy));
        when(getProductInventoryUseCase.getProductStocks(List.of(800L)))
                .thenReturn(Map.of(800L, 9));
        stubProductImagesEmpty(4L);

        GetProductInfoWithOptionsResult result = getProductService.getProductInfo(4L, selectedOptionIds);

        assertThat(result.productInfo().selectedPricePolicy().id()).isEqualTo(800L);
        assertThat(result.productInfo().stock()).isEqualTo(9);
    }

    @Test
    @DisplayName("상품 상세 정보 조회 시 선택된 옵션이 없으면 기본 가격 정책을 사용한다")
    void getProductInfo_emptySelectedOptions_usesDefaultPolicy() {
        stubProductImageExecutor();
        Product product = buildProduct(5L, true);
        ProductOption option = buildOption(401L, "옵션");
        ProductOptionCategory category = buildOptionCategory(51L, product, List.of(option));
        PricePolicy defaultPolicy = buildPricePolicy(810L, product, null, List.of());
        PricePolicy optionPolicy = buildPricePolicy(811L, product, List.of(401L), List.of());

        when(findProductPort.findById(5L)).thenReturn(Optional.of(product));
        when(findProductOptionCategoryPort.findActiveWithOptionsByProductId(5L))
                .thenReturn(List.of(category));
        when(findPricePoliciesPort.findByProductId(5L)).thenReturn(List.of(defaultPolicy, optionPolicy));
        when(getProductInventoryUseCase.getProductStocks(List.of(810L)))
                .thenReturn(Map.of(810L, 2));
        stubProductImagesEmpty(5L);

        GetProductInfoWithOptionsResult result = getProductService.getProductInfo(5L, List.of());

        assertThat(result.productInfo().selectedPricePolicy().id()).isEqualTo(810L);
        assertThat(result.categories().getFirst().options())
                .extracting(optionItem -> optionItem.isSelected())
                .containsOnly(false);
    }

    @Test
    @DisplayName("상품 상세 정보 조회 시 옵션 카테고리가 없으면 기본 가격 정책을 사용한다")
    void getProductInfo_emptyCategories_usesDefaultPolicy() {
        stubProductImageExecutor();
        Product product = buildProduct(6L, true);
        List<Long> selectedOptionIds = List.of(501L);
        PricePolicy defaultPolicy = buildPricePolicy(820L, product, null, List.of());
        PricePolicy optionPolicy = buildPricePolicy(821L, product, selectedOptionIds, List.of());

        when(findProductPort.findById(6L)).thenReturn(Optional.of(product));
        when(findProductOptionCategoryPort.findActiveWithOptionsByProductId(6L))
                .thenReturn(List.of());
        when(findPricePoliciesPort.findByProductId(6L)).thenReturn(List.of(defaultPolicy, optionPolicy));
        when(getProductInventoryUseCase.getProductStocks(List.of(820L)))
                .thenReturn(Map.of(820L, 1));
        stubProductImagesEmpty(6L);

        GetProductInfoWithOptionsResult result = getProductService.getProductInfo(6L, selectedOptionIds);

        assertThat(result.productInfo().selectedPricePolicy().id()).isEqualTo(820L);
        assertThat(result.categories()).isEmpty();
    }

    private Product buildProduct(Long id, boolean findAllOptions) {
        return Product.from(
                ProductSnapshotState.builder()
                        .id(id)
                        .sellerId(1L)
                        .name("상품-" + id)
                        .brandName("브랜드-" + id)
                        .detail("설명-" + id)
                        .findAllOptionsYn(findAllOptions)
                        .productTags(List.of())
                        .status(EntityStatus.ACTIVE)
                        .build()
        );
    }

    private ProductOption buildOption(Long id, String content) {
        return ProductOption.from(
                ProductOptionSnapshotState.builder()
                        .id(id)
                        .content(content)
                        .status(EntityStatus.ACTIVE)
                        .build()
        );
    }

    private ProductOptionCategory buildOptionCategory(Long id, Product product, List<ProductOption> options) {
        return ProductOptionCategory.from(
                ProductOptionCategorySnapshotState.builder()
                        .id(id)
                        .product(product)
                        .name("카테고리-" + id)
                        .options(options)
                        .status(EntityStatus.ACTIVE)
                        .build()
        );
    }

    private PricePolicy buildPricePolicy(
            Long id,
            Product product,
            List<Long> optionIds,
            List<ProductOption> productOptions
    ) {
        return PricePolicy.from(
                PricePolicySnapshotState.builder()
                        .id(id)
                        .product(product)
                        .price(10000L)
                        .discountPrice(9000L)
                        .discountRate(BigDecimal.valueOf(10))
                        .accumulatedPoint(100L)
                        .accumulationRate(BigDecimal.valueOf(1))
                        .status(EntityStatus.ACTIVE)
                        .optionIds(optionIds)
                        .productOptions(productOptions)
                        .build()
        );
    }

    private GetFilesResult buildFilesResult(Long id, String name) {
        return new GetFilesResult(
                List.of(
                        new GetFileResult(
                                id,
                                FileSort.PRODUCT_CATALOG_IMAGE.name(),
                                "jpg",
                                name,
                                "https://example.com/" + name,
                                List.of(),
                                1L
                        )
                )
        );
    }

    private void stubProductImageExecutor() {
        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
            task.run();
            return null;
        }).when(productImageExecutor).execute(any(Runnable.class));
    }

    private void stubProductImagesEmpty(Long productId) {
        when(findProductImagesPort.findImagesByProductIdAndSort(productId, FileSort.PRODUCT_REPRESENTATIVE_IMAGE))
                .thenReturn(Optional.empty());
        when(findProductImagesPort.findImagesByProductIdAndSort(productId, FileSort.PRODUCT_CONTENT_IMAGE))
                .thenReturn(Optional.empty());
    }
}
