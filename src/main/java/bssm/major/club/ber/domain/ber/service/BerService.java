package bssm.major.club.ber.domain.ber.service;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.repository.BerRepository;
import bssm.major.club.ber.domain.ber.web.dto.request.BerConfirmRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.*;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.facade.UserFacade;
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
    private final UserFacade userFacade;
    private final BerRepository berRepository;

    @Transactional
    public BerReservationResponseDto createReservation(BerReservationRequestDto request) {
        User user = userFacade.getCurrentUser();
        Ber ber = request.toEntity();
        ber.setUser(user);
        ber.addStatusWaiting();

        switch (request.getBerNo()) {
            case "1":
            case "2":
                ber.setMax(3);
                break;
            case "3":
                ber.setMax(5);
                break;
            case "4":
            case "6":
            case "5":
            case "9":
                ber.setMax(6);
                break;
            case "7":
                ber.setMax(7);
                break;
            case "8":
                ber.setMax(8);
                break;
        }

        if (user.getDisciplinePeriod() != null && LocalDate.now().isBefore(user.getDisciplinePeriod())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_BER);
        } else {
            user.initDisciplinePeriod();
        }

        berRepository.save(ber);
        return new BerReservationResponseDto(ber);
    }

    public List<BerReservationResponseDto> inquery() {
        return berRepository.findAll().stream()
                .filter(b -> b.getStatus().name().equals("WAITING"))
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
        User user = userFacade.getCurrentUser();

        return berRepository.findAll().stream()
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .map(BerReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<BerConfirmReservationResponseDto> myReservationStatus() {
        User user = userFacade.getCurrentUser();

        return berRepository.findAll().stream()
                .filter(b -> b.getUser().getId().equals(user.getId()))
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
        List<Ber> current = berRepository.findAll().stream()
                .filter(b -> b.getBerNo().equals(Ber_NO))
                .filter(b -> b.getStatus().name().equals("APPROVAL"))
                .collect(Collectors.toList());

        long waiting = berRepository.findAll().stream()
                .filter(b -> b.getBerNo().equals(Ber_NO))
                .filter(b -> b.getStatus().name().equals("WAITING"))
                .count();

        return new CurrentStatusBerResponseDto(
                Ber_NO,
                current.size(),
                waiting
        );
    }
}
