package bssm.major.club.ber.domain.ber.service;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.repository.BerRepository;
import bssm.major.club.ber.domain.ber.web.dto.request.BerConfirmRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.*;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

        switch (request.getBerNo()) {
            case "Ber_NO1":
            case "Ber_NO2":
                ber.updateMax(3);
                break;
            case "Ber_NO3":
                ber.updateMax(5);
                break;
            case "Ber_NO4":
            case "Ber_NO6":
            case "Ber_NO5":
            case "Ber_NO9":
                ber.updateMax(6);
                break;
            case "Ber_NO7":
                ber.updateMax(7);
                break;
            case "Ber_NO8":
                ber.updateMax(8);
                break;
        }

        if (user.getDisciplinePeriod() != null && LocalDate.now().isBefore(user.getDisciplinePeriod())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_BER);
        } else {
            user.initDisciplinePeriod();
        }

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
            case "APPROVAL":
                ber.addStatusAccept();
                break;
            case "REFUSAL":
                ber.addStatusRefusal();
                break;
        }

        if (ber.getMax() == ber.getCurrent()) {
            throw new CustomException(ErrorCode.OVER_FLOW_BER);
        }

        berRepository.findAll().stream()
                .filter(b -> b.getBerNo().equals(ber.getBerNo()))
                .forEach(Ber::increaseCurrent);

        ber.updateAnswer(request.getAnswer());
        return new BerConfirmResponseDto(ber);
    }

    public List<BerReservationResponseDto> myReservation() {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        return berRepository.findAll().stream()
                .filter(b -> b.getUser().equals(user))
                .map(BerReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<BerConfirmReservationResponseDto> myReservationStatus() {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        return berRepository.findAll().stream()
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

        ber.updateNumber(request.getBerNo());
        ber.updateTitle(request.getTitle());
        ber.updateContent(request.getContent());

        return new BerReservationResponseDto(ber);
    }

    @Transactional
    public void cancelReservation(Long id) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        Ber ber = berRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        berRepository.delete(ber);
    }

    @Transactional
    public BerWarningResponseDto addWarning(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.addWarning();

        if (user.getWarning() == 2) {
            user.initWarning();
            user.add2Days();
        }

        return new BerWarningResponseDto(user);
    }

    public CurrentStatusBerResponseDto currentStatusBer(String Ber_NO) {
        System.out.println("Ber_NO = " + Ber_NO);

        List<Ber> current = berRepository.findAll().stream()
                .filter(b -> b.getBerNo().equals(Ber_NO))
                .filter(b -> b.getStatus().name().equals("APPROVAL"))
                .collect(Collectors.toList());

        long waiting = berRepository.findAll().stream()
                .filter(b -> b.getBerNo().equals(Ber_NO))
                .filter(b -> b.getStatus().name().equals("WAITING"))
                .count();

        return new CurrentStatusBerResponseDto(
                current.get(0).getBerNo(),
                current.size(),
                waiting
        );
    }
}
