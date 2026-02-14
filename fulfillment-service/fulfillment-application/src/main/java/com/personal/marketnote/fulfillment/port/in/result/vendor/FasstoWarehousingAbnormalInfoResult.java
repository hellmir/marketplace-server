package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record FasstoWarehousingAbnormalInfoResult(
        String slipNo,
        String goodsSerialNo,
        String goodsSerialStatus,
        String whCd,
        String cstCd,
        String cstNm,
        String godCd,
        String description,
        String remark,
        String fileSeq,
        Integer lastFileSeqNo,
        String regDate,
        String regNM,
        String updDate,
        String updNm,
        String fileNo,
        List<Object> imageUrl
) {
    public static FasstoWarehousingAbnormalInfoResult of(
            String slipNo,
            String goodsSerialNo,
            String goodsSerialStatus,
            String whCd,
            String cstCd,
            String cstNm,
            String godCd,
            String description,
            String remark,
            String fileSeq,
            Integer lastFileSeqNo,
            String regDate,
            String regNM,
            String updDate,
            String updNm,
            String fileNo,
            List<Object> imageUrl
    ) {
        return new FasstoWarehousingAbnormalInfoResult(
                slipNo,
                goodsSerialNo,
                goodsSerialStatus,
                whCd,
                cstCd,
                cstNm,
                godCd,
                description,
                remark,
                fileSeq,
                lastFileSeqNo,
                regDate,
                regNM,
                updDate,
                updNm,
                fileNo,
                imageUrl
        );
    }
}
