package bssm.major.club.ber.domain.ber.service;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.repository.BerRepository;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerReservationResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BerService {

    private final UserRepository userRepository;
    private final BerRepository berRepository;

    @Transactional
    public BerReservationResponseDto createReservation(BerReservationRequestDto request) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        Ber ber = berRepository.save(request.toEntity());
        ber.confirmUser(user);
        ber.addStatus();

        return new BerReservationResponseDto(ber);
    }

    public List<BerReservationResponseDto> inquiry() {
        return berRepository.findAll().stream()
                .map(BerReservationResponseDto::new)
                .collect(Collectors.toList());
    }
}
