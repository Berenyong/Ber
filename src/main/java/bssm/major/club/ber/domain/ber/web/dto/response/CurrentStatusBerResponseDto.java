package bssm.major.club.ber.domain.ber.web.dto.response;

import lombok.Getter;

@Getter
public class CurrentStatusBerResponseDto {

    private final String berNo;
    private final int current;
    private final long waiting;

    public CurrentStatusBerResponseDto(String berNo, int current, long waiting) {
        this.berNo = berNo;
        this.current = current;
        this.waiting = waiting;
    }
}
