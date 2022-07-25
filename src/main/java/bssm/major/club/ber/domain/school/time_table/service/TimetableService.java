package bssm.major.club.ber.domain.school.time_table.service;


import bssm.major.club.ber.domain.school.time_table.entity.TimeTable;
import bssm.major.club.ber.domain.school.time_table.entity.repository.TimeTableRepository;
import bssm.major.club.ber.domain.school.time_table.web.api.dto.TimeTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final TimeTableRepository timeTableRepository;

    private final String basic = "https://open.neis.go.kr/hub/hisTimetable?";

    private final String key = "KEY=3271c51fc69742b9bfe89a908d5497a2&";

    private final String type = "Type=json&";

    private final String cityCode = "ATPT_OFCDC_SC_CODE=C10&";

    private final String schoolCode = "SD_SCHUL_CODE=7150658&";

    private final String basicUrl = basic + key + type + cityCode + schoolCode;

    public List<TimeTableResponseDto> getTimetable(int grade, int classNo) {
        return timeTableRepository.getTimeTable(grade, classNo)
                .stream()
                .map(TimeTableResponseDto::new)
                .collect(Collectors.toList());
    }


//    @Scheduled(cron = "0 4 * * * *") // 매일 새벽 4시 실행
    public void updateTimetable() throws IOException {
        timeTableRepository.deleteAll();

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        System.out.println("year = " + year); // 2022
        System.out.println("month = " + month); // 09
        System.out.println("day = " + day); // 19
        System.out.println("dayOfWeek = " + dayOfWeek); // 1

        StringBuilder date = new StringBuilder();
        date.append(year).append(0).append(month).append(day);
        String todayString = date.toString();
        int today = Integer.parseInt(todayString);

        switch (dayOfWeek) {
            case 2: // 화요일
                today -= 1;
            case 3: // 수요일
                today -= 2;
            case 4: // 목요일
                today -= 3;
            case 5: // 금요일
                today -= 4;
        }

        for (int grade = 1; grade <= 3; grade++) {
            for (int classNo = 1; classNo <= 4; classNo++) {
                for (int d = today; d <= today + 4; d++) {
                    String apiUrl = basicUrl + "ALL_TI_YMD=" + d + "&" + "GRADE=" + grade + "&" + "CLASS_NM=" + classNo;

                    URL url = new URL(apiUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8));

                    StringBuilder sb = new StringBuilder();
                    String s;

                    ArrayList<String> list = new ArrayList<>();
                    while ((s = br.readLine()) != null) {
                        sb.append(s);
                    }

                    StringTokenizer st = new StringTokenizer(sb.toString(), ",");
                    int tokens = st.countTokens();
                    for (int i = 0; i < tokens; i++) {
                        String word = st.nextToken();
                        if (word.contains("ITRT_CNTNT")) list.add(word);
                    }

                    ArrayList<String> subjects = new ArrayList<>();
                    for (String subject : list) {
                        StringTokenizer stt = new StringTokenizer(subject, ":");
                        stt.nextToken();
                        subjects.add(stt.nextToken());
                    }

                    System.out.println("subjects = " + subjects);
                    int cnt = 1;
                    for (String classname : subjects) {
                        TimeTable timeTable = new TimeTable().builder()
                                .className(classname)
                                .classNo((long) classNo)
                                .day((long) d)
                                .grade((long) grade)
                                .idx((long) cnt)
                                .build();
                        timeTableRepository.save(timeTable);
                        cnt++;
                    }
                }
            }
        }
    }
}