package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

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
    public static RegisterFasstoWarehousingItemCommand of(
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
        return new RegisterFasstoWarehousingItemCommand(
                ordDt,
                ordNo,
                inWay,
                slipNo,
                parcelComp,
                parcelInvoiceNo,
                remark,
                cstSupCd,
                distTermDt,
                makeDt,
                preArv,
                godCds
        );
    }
}
