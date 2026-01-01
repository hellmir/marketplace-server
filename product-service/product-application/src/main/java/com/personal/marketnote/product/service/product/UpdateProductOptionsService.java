package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import com.personal.marketnote.product.exception.NotProductOwnerException;
import com.personal.marketnote.product.exception.OptionsNoValueException;
import com.personal.marketnote.product.mapper.ProductCommandToDomainMapper;
import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;
import com.personal.marketnote.product.port.in.command.UpdateProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.UpsertProductOptionsResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.in.usecase.product.UpdateProductOptionsUseCase;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.DeleteProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.productoption.SaveProductOptionsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.FIRST_ERROR_CODE;
import static com.personal.marketnote.common.domain.exception.ExceptionCode.SECOND_ERROR_CODE;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED)
public class UpdateProductOptionsService implements UpdateProductOptionsUseCase {
    private final GetProductUseCase getProductUseCase;
    private final FindProductPort findProductPort;
    private final SaveProductOptionsPort saveProductOptionsPort;
    private final DeleteProductOptionCategoryPort deleteProductOptionCategoryPort;

    @Override
    public UpsertProductOptionsResult updateProductOptions(
            Long userId, boolean isAdmin, UpdateProductOptionsCommand command
    ) {
        Long productId = command.productId();
        if (!isAdmin && !findProductPort.existsByIdAndSellerId(productId, userId)) {
            throw new NotProductOwnerException(FIRST_ERROR_CODE, productId);
        }
        Product product = getProductUseCase.getProduct(productId);

        List<RegisterProductOptionsCommand.OptionItem> optionItems = command.options();
        if (!FormatValidator.hasValue(optionItems)) {
            throw new OptionsNoValueException(SECOND_ERROR_CODE);
        }

        // 기존 카테고리 삭제
        deleteProductOptionCategoryPort.deleteById(command.optionCategoryId());

        // 새 카테고리/옵션 저장
        ProductOptionCategory savedCategory = saveProductOptionsPort.save(
                ProductCommandToDomainMapper.mapToDomain(
                        product,
                        com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand.of(
                                productId,
                                command.categoryName(),
                                optionItems
                        )
                )
        );

        return UpsertProductOptionsResult.from(savedCategory);
    }
}


