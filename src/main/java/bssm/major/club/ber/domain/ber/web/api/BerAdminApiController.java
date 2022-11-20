package bssm.major.club.ber.domain.ber.web.api;

import bssm.major.club.ber.domain.ber.service.BerService;
import bssm.major.club.ber.domain.ber.web.dto.request.BerAnswerRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.request.BerConfirmRequestDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerConfirmResponseDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerReservationResponseDto;
import bssm.major.club.ber.domain.ber.web.dto.response.BerWarningResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class BerAdminApiController {

    private final BerService berService;

    @GetMapping
    public Result<List<BerReservationResponseDto>> inQuery() {
        List<BerReservationResponseDto> inquiry = berService.inquery();
        return new Result<>(inquiry.size(), inquiry);
    }

    @PutMapping("/{id}")
    public BerConfirmResponseDto confirm(@PathVariable Long id, @RequestBody @Valid BerConfirmRequestDto request) {
        return berService.confirm(id, request);
    }

    @PutMapping("/answer/{id}")
    public BerConfirmResponseDto updateAnswer(@PathVariable Long id, @RequestBody BerAnswerRequestDto request) {
        return berService.updateAnswer(id, request.getAnswer());
    }

    @PutMapping("/warning/{userId}")
    public BerWarningResponseDto warning(@PathVariable Long userId) {
        return berService.addWarning(userId);
    }

}
