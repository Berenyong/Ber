package bssm.major.club.ber.domain.ber.web.api;

import bssm.major.club.ber.domain.ber.service.BerService;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerConfirmReservationResponseDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerReservationResponseDto;
import bssm.major.club.ber.domain.ber.web.dto.response.CurrentStatusBerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/ber")
@RestController
public class BerApiController {

    private final BerService berService;

    @PostMapping
    public BerReservationResponseDto createReservation(@RequestBody BerReservationRequestDto request) {
        return berService.createReservation(request);
    }

    @GetMapping
    public CurrentStatusBerResponseDto currentStatusBer(@RequestParam("berNo") String berNum) {
        return berService.currentStatusBer(berNum);
    }

    @GetMapping("/reservation")
    public List<BerReservationResponseDto> myReservation() {
        return berService.myReservation();
    }

    @GetMapping("/reservation/status")
    public List<BerConfirmReservationResponseDto> myReservationStatus() {
        return berService.myReservationStatus();
    }

    @PutMapping("{id}")
    public BerReservationResponseDto updateMyReservation(@PathVariable Long id, @RequestBody BerReservationRequestDto request) {
        return berService.updateMyReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        berService.cancelReservation(id);
    }

}