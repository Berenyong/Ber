package bssm.major.club.ber.domain.school.time_table.web.api;

import bssm.major.club.ber.domain.school.time_table.service.TimetableService;
import bssm.major.club.ber.domain.school.time_table.web.api.dto.TimeTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimetableApiController {

    private final TimetableService timetableService;

//    @GetMapping("/{grade}/{classNo}/{day}")
//    public void getTimetable(
//            @PathVariable int grade,
//            @PathVariable int classNo,
//            @PathVariable int day
//    ) throws IOException {
//        timetableService.getTimetable(grade, classNo, day);
//    }

    @PostMapping
    public void updateTimeTable() throws IOException {
        timetableService.getTimetable();
    }

}