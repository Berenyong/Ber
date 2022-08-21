package bssm.major.club.ber.domain.ber.service;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.repository.BerRepository;
import bssm.major.club.ber.domain.ber.web.dto.request.BerAnswerRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.request.BerConfirmRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerConfirmReservationResponseDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerConfirmResponseDto;
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
        ber.addStatusWaiting();

        return new BerReservationResponseDto(ber);
    }

    public List<BerReservationResponseDto> inquiry() {
        return berRepository.findAll().stream()
                .map(BerReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BerConfirmResponseDto confirm(Long id, BerConfirmRequestDto request) {
        Ber ber = berRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        switch (request.getStatus()) {
            case "WAITING":
                ber.addStatusWaiting();
                break;
            case "APPROVAL":
                ber.addStatusAccept();
                break;
            case "REFUSAL":
                ber.addStatusRefusal();
                break;
        }

        ber.updateAnswer(request.getAnswer());
        return new BerConfirmResponseDto(ber);
    }

    public List<BerReservationResponseDto> myReservation() {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        return berRepository.findAll().stream()
//                .filter(b -> b.getStatus().name().equals("WAITING"))
                .filter(b -> b.getUser().equals(user))
                .map(BerReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<BerConfirmReservationResponseDto> myReservationStatus() {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        return berRepository.findAll().stream()
//                .filter(b -> b.getStatus().name().equals("WAITING"))
                .filter(b -> b.getUser().equals(user))
                .map(BerConfirmReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BerConfirmResponseDto updateAnswer(Long id, String answer) {
        Ber ber = berRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        ber.updateAnswer(answer);

        return new BerConfirmResponseDto(ber);
    }

    @Transactional
    public BerReservationResponseDto updateMyReservation(Long id, BerReservationRequestDto request) {
        Ber ber = berRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        ber.updateNumber(request.getNumber());
        ber.updateTitle(request.getTitle());
        ber.updateContent(request.getContent());

        return new BerReservationResponseDto(ber);
    }
}
