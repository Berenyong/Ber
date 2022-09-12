package bssm.major.club.ber.domain.school.time_table.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TimeTableResponseDto {

    private final List<String> timeTable;

    @Builder
    public TimeTableResponseDto(List<String> timeTable) {
        ArrayList<String> result = new ArrayList<>();

        int cnt = 1;
        for (String s : timeTable) {
            StringBuilder sb = new StringBuilder();
            sb.append(cnt).append("교시 : ");

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '"') sb.append(s.charAt(i));
            }

            result.add(sb.toString());
            cnt++;
        }

        this.timeTable = result;
    }
}