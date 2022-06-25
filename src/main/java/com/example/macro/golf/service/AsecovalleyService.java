package com.example.macro.golf.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class AsecovalleyService implements GolfCheckService {

    Logger log = LoggerFactory.getLogger(getClass());
    private final SlackService slackService;

    public AsecovalleyService(SlackService slackService) {
        this.slackService = slackService;
    }

    @Override
    public void call() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                doPost();
            }
        };
        Timer timer = new Timer("true");
        timer.schedule(timerTask, 1000, 10000);
    }

    public void doPost() {
        String URI = "https://www.asecovalley.co.kr/GolfRes/onepage/real_reservation.asp#pointdate=20220613&courseid=0&openyn=1&dategbn=2&choice_time=0&settype=T&prevDate=202205&nowDate=202206&nextDate=202207";
        String URI7 = "https://www.asecovalley.co.kr/GolfRes/onepage/real_calendar_ajax_view.asp";
        Document doc = null;
        try {
            doc = Jsoup.connect(URI7)
                    .data("golfrestype", "real")
                    .data("schDate", "202207")
                    .data("usrmemcd", "70")
                    .data("toDay", "20220613")
                    .data("calnum", "2")
                    .post();
        } catch (IOException e) {
            return;
        }

        Elements elementList = doc.select("td.cm_day");
        Element element = elementList.get(4);

        String result = String.valueOf(element);
        if (result.contains("오픈전입니다.")) {
            LocalDateTime nowTime = LocalDateTime.now();
            String format = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            log.info("오픈전 - {} ", format);
        } else {
            slackService.postMsgSlack();
        }
    }

}
