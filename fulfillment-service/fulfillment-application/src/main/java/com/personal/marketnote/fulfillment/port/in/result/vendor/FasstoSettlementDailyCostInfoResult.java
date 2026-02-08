package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record FasstoSettlementDailyCostInfoResult(
        String cloDt,
        String whCd,
        String cstCd,
        String inpAmt,
        String outAmt,
        String outcarAmt,
        String outairAmt,
        String keepAmt,
        String retAmt,
        String cashAmt,
        String founAmt,
        String othAmt,
        String totAmt
) {
    public static FasstoSettlementDailyCostInfoResult of(
            String cloDt,
            String whCd,
            String cstCd,
            String inpAmt,
            String outAmt,
            String outcarAmt,
            String outairAmt,
            String keepAmt,
            String retAmt,
            String cashAmt,
            String founAmt,
            String othAmt,
            String totAmt
    ) {
        return new FasstoSettlementDailyCostInfoResult(
                cloDt,
                whCd,
                cstCd,
                inpAmt,
                outAmt,
                outcarAmt,
                outairAmt,
                keepAmt,
                retAmt,
                cashAmt,
                founAmt,
                othAmt,
                totAmt
        );
    }
}
