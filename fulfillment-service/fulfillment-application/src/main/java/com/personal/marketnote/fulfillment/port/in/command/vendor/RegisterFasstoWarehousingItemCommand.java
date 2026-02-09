package com.personal.marketnote.fulfillment.port.in.command.vendor;

import lombok.Builder;

import java.util.List;

@Builder
public record RegisterFasstoWarehousingItemCommand(
        String ordDt,
        String ordNo,
        String inWay,
        String slipNo,
        String parcelComp,
        String parcelInvoiceNo,
        String remark,
        String cstSupCd,
        String distTermDt,
        String makeDt,
        String preArv,
        List<RegisterFasstoWarehousingGoodsCommand> godCds
) {
}
