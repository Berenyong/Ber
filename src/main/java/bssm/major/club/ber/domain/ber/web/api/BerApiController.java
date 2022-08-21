package bssm.major.club.ber.domain.ber.web.api;

import bssm.major.club.ber.domain.ber.service.BerService;
import bssm.major.club.ber.domain.ber.web.dto.request.BerReservationRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
