package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingItemCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingItemResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.out.scheduler.ScheduleFasstoWarehousingPollingCommand;
import com.personal.marketnote.fulfillment.port.out.scheduler.ScheduleFasstoWarehousingPollingPort;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoWarehousingPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterFasstoWarehousingServiceTest {
    @Mock
    private RegisterFasstoWarehousingPort registerFasstoWarehousingPort;
    @Mock
    private ScheduleFasstoWarehousingPollingPort scheduleFasstoWarehousingPollingPort;

    @InjectMocks
    private RegisterFasstoWarehousingService registerFasstoWarehousingService;

    @Test
    @DisplayName("입고 요청 성공 후 ordNo별 polling 스케줄을 등록한다")
    void registerWarehousing_schedulesPollingPerOrdNo() {
        RegisterFasstoWarehousingCommand command = RegisterFasstoWarehousingCommand.of(
                "CUST",
                "token",
                List.of(
                        buildItem("20260116", "ORD-1"),
                        buildItem("20260116", "ORD-2")
                )
        );

        RegisterFasstoWarehousingResult result = RegisterFasstoWarehousingResult.of(
                2,
                List.of(
                        RegisterFasstoWarehousingItemResult.of("OK", "200", "SLIP-1", "ORD-1"),
                        RegisterFasstoWarehousingItemResult.of("OK", "200", "SLIP-2", "ORD-2")
                )
        );
        when(registerFasstoWarehousingPort.registerWarehousing(any())).thenReturn(result);

        registerFasstoWarehousingService.registerWarehousing(command);

        ArgumentCaptor<ScheduleFasstoWarehousingPollingCommand> captor
                = ArgumentCaptor.forClass(ScheduleFasstoWarehousingPollingCommand.class);
        verify(scheduleFasstoWarehousingPollingPort, org.mockito.Mockito.times(2))
                .schedule(captor.capture());

        assertThat(captor.getAllValues())
                .extracting(ScheduleFasstoWarehousingPollingCommand::ordNo)
                .containsExactlyInAnyOrder("ORD-1", "ORD-2");
    }

    private RegisterFasstoWarehousingItemCommand buildItem(String ordDt, String ordNo) {
        return RegisterFasstoWarehousingItemCommand.builder()
                .ordDt(ordDt)
                .ordNo(ordNo)
                .inWay("01")
                .godCds(List.of(RegisterFasstoWarehousingGoodsCommand.of("P1", null, 1)))
                .build();
    }
}
