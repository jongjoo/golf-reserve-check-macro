package com.example.macro.golf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@SpringBootTest
class PlazaServiceTest {

    @Autowired
    PlazaService plazaService;


    @Test
    void doPost() throws JsonProcessingException {
        plazaService.doPost();
    }

    @Test
    void dopost_기능확인() throws IOException {
        String URI = "https://booking.hanwharesort.co.kr/pzc/pnr/0010/doExecute.mvc?BRCH_CD=0400&RSRV_DATE=20220714&CUST_NO=0002673371&MEMB_NO=&DATA_NAME_I=ds_search&INTF_ID=TFO00HBSGOLREM9086&RECV_SVC_CD=HBSGOLREM9086";
        Document doc = Jsoup.connect(URI)
                .header(HttpHeaders.COOKIE, "JSESSIONID=PyZRK0E0iurD0BftSIhoY0rm2B33UimUBARbcA9dMXaG3jAf7X001PC7VaLT2SFl.cHJvZEVYQVBEb21haW4vQml6UmVzZXJ2RVhBUDMwMQ==; WMONID=QpWLnEZmw_3")
                .ignoreContentType(true)
                .post();

        System.out.println(doc);

    }
}