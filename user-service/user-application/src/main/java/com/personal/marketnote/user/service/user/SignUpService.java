package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.exception.illegalargument.novalue.PasswordNoValueException;
import com.personal.marketnote.common.utility.RandomCodeGenerator;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.InvalidVerificationCodeException;
import com.personal.marketnote.user.exception.UserExistsException;
import com.personal.marketnote.user.exception.UserNotActiveException;
import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.result.SignUpResult;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.SignUpUseCase;
import com.personal.marketnote.user.port.out.authentication.VerifyEmailVerificationCodePort;
import com.personal.marketnote.user.port.out.user.FindTermsPort;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.port.out.user.SaveUserPort;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.*;
import static com.personal.marketnote.user.exception.ExceptionMessage.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_COMMITTED, timeout = 180)
public class SignUpService implements SignUpUseCase {
    private final GetUserUseCase getUserUseCase;
    private final SaveUserPort saveUserPort;
    private final FindUserPort findUserPort;
    private final FindTermsPort findTermsPort;
    private final PasswordEncoder passwordEncoder;
    private final UpdateUserPort updateUserPort;
    private final VerifyEmailVerificationCodePort verifyEmailVerificationCodePort;

    @Override
    public SignUpResult signUp(SignUpCommand signUpCommand, AuthVendor authVendor, String oidcId) {
        if (authVendor.isNative() && !signUpCommand.hasPassword()) {
            throw new PasswordNoValueException(FIRST_ERROR_CODE);
        }

        if (!authVendor.isNative() && findUserPort.existsByAuthVendorAndOidcId(authVendor, oidcId)) {
            throw new UserExistsException(
                    String.format(OIDC_ID_ALREADY_EXISTS_EXCEPTION_MESSAGE, SECOND_ERROR_CODE, oidcId));
        }

        String nickname = signUpCommand.getNickname();
        if (findUserPort.existsByNickname(nickname)) {
            throw new UserExistsException(
                    String.format(NICKNAME_ALREADY_EXISTS_EXCEPTION_MESSAGE, THIRD_ERROR_CODE, nickname));
        }

        String phoneNumber = signUpCommand.getPhoneNumber();
        if (signUpCommand.hasPhoneNumber() && findUserPort.existsByPhoneNumber(phoneNumber)) {
            throw new UserExistsException(
                    String.format(PHONE_NUMBER_ALREADY_EXISTS_EXCEPTION_MESSAGE, FOURTH_ERROR_CODE, phoneNumber));
        }

        String email = signUpCommand.getEmail();

        // 이메일 인증 코드 검증
        if (!verifyEmailVerificationCodePort.verifyAndConsume(email, signUpCommand.getVerificationCode())) {
            throw new InvalidVerificationCodeException(FIFTH_ERROR_CODE, email);
        }

        // 이메일 가입 정보가 있는 경우 기존 회원 엔티티에 통합
        if (findUserPort.existsByEmail(email)) {
            User signedUpUser = getUserUseCase.getAllStatusUser(email);

            // 계정 활성화 여부 검증
            if (!signedUpUser.isActive()) {
                throw new UserNotActiveException(SIXTH_ERROR_CODE, email);
            }

            signedUpUser.addLoginAccountInfo(authVendor, oidcId, signUpCommand.getPassword(), passwordEncoder);
            updateUserPort.update(signedUpUser);

            return SignUpResult.from(signedUpUser, false);
        }

        List<Terms> terms = findTermsPort.findAll();
        String referenceCode = RandomCodeGenerator.generateReferenceCode();

        return SignUpResult.from(
                saveUserPort.save(
                        User.from(
                                authVendor,
                                oidcId,
                                signUpCommand.getNickname(),
                                signUpCommand.getEmail(),
                                signUpCommand.getPassword(),
                                passwordEncoder,
                                signUpCommand.getFullName(),
                                signUpCommand.getPhoneNumber(),
                                terms,
                                referenceCode)),
                true);
    }
}
