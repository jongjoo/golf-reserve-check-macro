package com.example.macro.service;

import com.example.macro.golf.service.SlackService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootTest
class AsecovalleyServiceTest {

    @Test
    void call() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TEST");
            }
        };
        Timer timer = new Timer("true");
        timer.schedule(timerTask, 1000, 5000);
    }

    @Test
    void callTest() {

        String URI = "https://www.asecovalley.co.kr/GolfRes/onepage/real_reservation.asp#pointdate=20220613&courseid=0&openyn=1&dategbn=2&choice_time=0&settype=T&prevDate=202205&nowDate=202206&nextDate=202207";
        String URI_7 = "https://www.asecovalley.co.kr/GolfRes/onepage/real_calendar_ajax_view.asp";
        Document doc = null;
        try {
            doc = Jsoup.connect(URI_7)
                    .data("golfrestype", "real")
                    .data("schDate", "202207")
                    .data("usrmemcd", "70")
                    .data("toDay", "20220613")
                    .data("calnum", "2")
                    .post();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements elementList = doc.select("td.cm_day");
        Element element_7_1 = elementList.get(4);

        String result = String.valueOf(element_7_1);
        if (result.contains("오픈전입니다.")) {
            System.out.println("오픈전");
        } else {
            System.out.println("!!!!!!!!!!!!!!오픈!!!!!!!!!!!!!");
        }

    }

    @Autowired
    SlackService service;
    @Test
    void 슬랙콜_테스트(){
        service.postMsgSlack();
    }

}