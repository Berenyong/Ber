package bssm.major.club.ber.global.generic;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Result<T> {
    private int count;
    private T data;
}
