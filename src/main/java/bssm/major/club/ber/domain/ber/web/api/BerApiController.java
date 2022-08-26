package bssm.major.club.ber.domain.ber.web.api;

import bssm.major.club.ber.domain.ber.service.BerService;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerConfirmReservationResponseDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/ber")
@RestController
public class BerApiController {

    private final BerService berService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BerReservationResponseDto createReservation(@RequestBody BerReservationRequestDto request) {
        return berService.createReservation(request);
    }

    @GetMapping("/reservation")
    @ResponseStatus(HttpStatus.OK)
    public List<BerReservationResponseDto> myReservation() {
        return berService.myReservation();
    }

    @GetMapping("/reservation/status")
    @ResponseStatus(HttpStatus.OK)
    public List<BerConfirmReservationResponseDto> myReservationStatus() {
        return berService.myReservationStatus();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BerReservationResponseDto updateMyReservation(@PathVariable Long id, @RequestBody BerReservationRequestDto request) {
        return berService.updateMyReservation(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable Long id) {
        berService.cancelReservation(id);
    }

}