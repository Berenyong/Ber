package bssm.major.club.ber.domain.school.time_table.service;


import bssm.major.club.ber.domain.school.time_table.web.api.dto.TimeTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final String basic = "https://open.neis.go.kr/hub/hisTimetable?";

    private final String key = "KEY=3271c51fc69742b9bfe89a908d5497a2&";

    private final String type = "Type=json&";

    private final String cityCode = "ATPT_OFCDC_SC_CODE=C10&";

    private final String schoolCode = "SD_SCHUL_CODE=7150658&";

    private final String basicUrl = basic + key + type + cityCode + schoolCode;
    
    public TimeTableResponseDto getTimetable(int grade, int classNo, int day) throws IOException {
        String apiUrl = basicUrl + "ALL_TI_YMD=" + day + "&" + "GRADE=" + grade + "&" + "CLASS_NM=" + classNo;

        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String s;
        
        ArrayList<String> list = new ArrayList<>();
        while((s = br.readLine()) != null) {
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

        return new TimeTableResponseDto(subjects);
    }
}