package bssm.major.club.ber.domain.school.time_table.web.api;

import bssm.major.club.ber.domain.school.time_table.service.TimetableService;
import bssm.major.club.ber.domain.school.time_table.web.api.dto.TimeTableResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimetableApiController {

    private final TimetableService timetableService;

    @GetMapping("/{grade}/{classNo}")
    public Result getTimetable(
            @PathVariable int grade,
            @PathVariable int classNo
    ) throws IOException {
        List<TimeTableResponseDto> timetable = timetableService.getTimetable(grade, classNo);

        return new Result(timetable.size(), timetable);
    }

    @PostMapping
    public void updateTimeTable() throws IOException {
        timetableService.updateTimetable();
    }

}