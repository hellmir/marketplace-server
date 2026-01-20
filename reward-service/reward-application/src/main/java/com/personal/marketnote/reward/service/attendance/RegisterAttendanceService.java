package com.personal.marketnote.reward.service.attendance;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.attendance.AttendancePolicy;
import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;
import com.personal.marketnote.reward.exception.InvalidAttendanceTimeException;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.attendance.RegisterAttendanceCommand;
import com.personal.marketnote.reward.port.in.result.attendance.RegisterAttendanceResult;
import com.personal.marketnote.reward.port.in.usecase.attendance.RegisterAttendanceUseCase;
import com.personal.marketnote.reward.port.out.attendance.FindAttendancePolicyPort;
import com.personal.marketnote.reward.port.out.attendance.FindUserAttendanceHistoryPort;
import com.personal.marketnote.reward.port.out.attendance.SaveUserAttendanceHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterAttendanceService implements RegisterAttendanceUseCase {
    private static final long ALLOWED_DELAY_MINUTES = 1L;
    private static final short DEFAULT_POLICY_ID = 10_000;

    private final SaveUserAttendanceHistoryPort saveUserAttendanceHistoryPort;
    private final FindUserAttendanceHistoryPort findUserAttendanceHistoryPort;
    private final FindAttendancePolicyPort findAttendancePolicyPort;

    @Override
    public RegisterAttendanceResult register(RegisterAttendanceCommand command) {
        validateAttendedAt(command.attendedAt());

        short continuousPeriod = calculateContinuousPeriod(command.userId(), command.attendedAt());
        AttendancePolicy attendancePolicy = selectAttendancePolicy(continuousPeriod, command.attendedAt().toLocalDate());
        UserAttendanceHistory savedHistory = saveUserAttendanceHistoryPort.save(
                UserAttendanceHistory.from(
                        RewardCommandToStateMapper.mapToUserAttendanceHistoryCreateState(
                                command,
                                attendancePolicy,
                                continuousPeriod
                        )
                )
        );

        return RegisterAttendanceResult.builder()
                .id(savedHistory.getId())
                .build();
    }

    private void validateAttendedAt(LocalDateTime attendedAt) {
        LocalDateTime now = LocalDateTime.now();
        if (
                FormatValidator.hasNoValue(attendedAt)
                        || attendedAt.isAfter(now)
                        || attendedAt.isBefore(now.minusMinutes(ALLOWED_DELAY_MINUTES))
        ) {
            throw new InvalidAttendanceTimeException("전송된 출석일시가 올바르지 않습니다.");
        }
    }

    private short calculateContinuousPeriod(Long userId, LocalDateTime attendedAt) {
        LocalDate attendedDate = attendedAt.toLocalDate();

        return findUserAttendanceHistoryPort.findLatestByUserId(userId)
                .filter(last -> last.getAttendedAt().toLocalDate().equals(attendedDate.minusDays(1)))
                .map(last -> (short) (last.getContinuousPeriod() + 1))
                .orElse((short) 1);
    }

    private AttendancePolicy selectAttendancePolicy(short continuousPeriod, LocalDate attendedDate) {
        return findAttendancePolicyPort.findByContinuousPeriodAndAttendenceDate(continuousPeriod, attendedDate)
                .or(() -> findAttendancePolicyPort.findByContinuousPeriodAndAttendenceDateIsNull(continuousPeriod))
                .or(() -> findAttendancePolicyPort.findById(DEFAULT_POLICY_ID))
                .orElseThrow(() -> new IllegalStateException("기본 출석 정책을 찾을 수 없습니다. 서버 담당자에게 문의 바랍니다."));
    }
}
