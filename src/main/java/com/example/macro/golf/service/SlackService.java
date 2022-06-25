package com.example.macro.golf.service;

import com.example.macro.golf.dto.Attachments;
import com.example.macro.golf.dto.Fields;
import com.example.macro.golf.dto.SlackMsgDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlackService {


    public void postMsgSlack() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://hooks.slack.com/services")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        SlackMsgDTO slackMsgDTO = slackDtoConvert();
        webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_HTML)
                .body(Mono.just(slackMsgDTO), SlackMsgDTO.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public SlackMsgDTO slackDtoConvert() {
        SlackMsgDTO slackMsgDTO = new SlackMsgDTO();
        Attachments attachments = new Attachments();
        Fields fields = new Fields();
        LocalDateTime nowTime = LocalDateTime.now();
        String format = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

        fields.setTitle("[지금이야] Notes");
        fields.setValue("오픈 완료!! 예약!!");

        fields.setShort1(false);
        List<Fields> fieldsList = new ArrayList<>();
        fieldsList.add(fields);

        attachments.setFallback("** 예약 가능 현황 **");
        attachments.setPretext("** 예약 가능 현황 ** \n ( " + format + " )");
        attachments.setColor("#87CEEB");
        attachments.setFields(fieldsList);
        List<Attachments> attachmentsList = new ArrayList<>();
        attachmentsList.add(attachments);

        slackMsgDTO.setAttachments(attachmentsList);
        return slackMsgDTO;
    }
}
