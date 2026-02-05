package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record UpdateFasstoWarehousingItemCommand(
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
        List<UpdateFasstoWarehousingGoodsCommand> godCds
) {
    public static UpdateFasstoWarehousingItemCommand of(
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
            List<UpdateFasstoWarehousingGoodsCommand> godCds
    ) {
        return new UpdateFasstoWarehousingItemCommand(
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
