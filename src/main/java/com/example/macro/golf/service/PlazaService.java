package com.example.macro.golf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class PlazaService implements GolfCheckService{

    Logger log = LoggerFactory.getLogger(getClass());
    private final SlackService slackService;

    public PlazaService(SlackService slackService) {
        this.slackService = slackService;
    }

    @Override
    public void call() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    doPost();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer("true");
        timer.schedule(timerTask, 1000, 10000);
    }

    public void doPost() throws JsonProcessingException {

        String URI = "https://booking.hanwharesort.co.kr/pzc/pnr/0010/doExecute.mvc?BRCH_CD=0400&RSRV_DATE=20220715&CUST_NO=0002673371&MEMB_NO=&DATA_NAME_I=ds_search&INTF_ID=TFO00HBSGOLREM9086&RECV_SVC_CD=HBSGOLREM9086";
        Document doc = null;
        try {
            doc = Jsoup.connect(URI)
                    .header(HttpHeaders.COOKIE, "JSESSIONID=PyZRK0E0iurD0BftSIhoY0rm2B33UimUBARbcA9dMXaG3jAf7X001PC7VaLT2SFl.cHJvZEVYQVBEb21haW4vQml6UmVzZXJ2RVhBUDMwMQ==; WMONID=QpWLnEZmw_3")
                    .ignoreContentType(true)
                    .post();
        } catch (IOException e) {
            return;
        }

        Element element = doc.body();
        String body = element.text();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        String openYn = jsonNode.get("ds").get("Data").get("ds_openChk").get(0).get("OPEN_YN").asText();
        log.info(openYn);

        if (openYn.contains("N")) {
            LocalDateTime nowTime = LocalDateTime.now();
            String format = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            log.info("오픈전 - {} ", format);
        } else {
            slackService.postMsgSlack();
        }

//        {"ds":{"Data":{"ds_list":[],"ds_openChk":[{"OPEN_YN":"Y"}]},"MessageHeader":{"MSG_PRCS_RSLT_CD":"0","MSG_DATA_SUB_RPTT_CNT":1,"MSG_ETC":null,"MSG_DATA_SUB":[{"MSG_INDC_CD":"1","MSG_CD":"SCMI000001","MSG_CTNS":"정상적으로 처리되었습니다."}]},"TransactionHeader":{"STN_MSG_TR_TP_CD":"O","SYSTEM_TYPE":"HABIS","SCREEN_SHORTEN_NO":"","SCREEN_ID":"","PERS_INFO_PROC_RESN":null,"CMP_NO":"","CORP_CD":"1000","BRANCH_NO":"","LOC_CD":"","WRKR_NO":"l1711019","PERS_INFO_MASK":null,"MASK_AUTH":"0","OSDE_TR_CD":"","OSDE_TR_ORG_CD":"","OSDE_TR_MSG_CD":"","OSDE_TR_JOB_CD":"","OSDE_TR_RUTN_ID":"","OSDE_TR_PRG_NO":"","FILLER":""},"SystemHeader":{"STD_TMSG_LEN":null,"TMSG_VER_DV_CD":"01","ENVR_INFO_DV_CD":"D","STN_MSG_ENCP_CD":"0","STN_MSG_COMP_CD":"0","LANG_CD":"KO","TMSG_WRTG_DT":"20220625","TMSG_CRE_SYS_NM":"HPG01937","STD_TMSG_SEQ_NO":"01656116211225","STD_TMSG_PRGR_NO":"00","STN_TMSG_IP":"172.25.251.113","STN_TMSG_MAC":"","FRS_RQST_SYS_CD":"HPG","FRS_RQST_DTM":"20220625091651225","TRMS_SYS_CD":"MCI","TRMS_ND_NO":"0202","RQST_RSPS_DV_CD":"R","TRSC_SYNC_DV_CD":"S","TMSG_RQST_DTM":"20220625091651243","RECV_SVC_CD":"HBSGOLREM9086","INTF_ID":"TFO00HBSGOLREM9086","TMSG_RSPS_DTM":"20220625091651261","PRCS_RSLT_CD":"","ERR_OCC_SYS_CD":"","STN_TMSG_ERR_CD":"","MCI_NODE_NO":"","REMT_IP":"","MCI_SSN_ID":"","FILLER":""}}}
//        {"ds":{"Data":{"ds_list":[],"ds_openChk":[{"OPEN_YN":"N"}]},"MessageHeader":{"MSG_PRCS_RSLT_CD":"0","MSG_DATA_SUB_RPTT_CNT":1,"MSG_ETC":null,"MSG_DATA_SUB":[{"MSG_INDC_CD":"1","MSG_CD":"SCMI000001","MSG_CTNS":"정상적으로 처리되었습니다."}]},"TransactionHeader":{"STN_MSG_TR_TP_CD":"O","SYSTEM_TYPE":"HABIS","SCREEN_SHORTEN_NO":"","SCREEN_ID":"","PERS_INFO_PROC_RESN":null,"CMP_NO":"","CORP_CD":"1000","BRANCH_NO":"","LOC_CD":"","WRKR_NO":"l1711019","PERS_INFO_MASK":null,"MASK_AUTH":"0","OSDE_TR_CD":"","OSDE_TR_ORG_CD":"","OSDE_TR_MSG_CD":"","OSDE_TR_JOB_CD":"","OSDE_TR_RUTN_ID":"","OSDE_TR_PRG_NO":"","FILLER":""},"SystemHeader":{"STD_TMSG_LEN":null,"TMSG_VER_DV_CD":"01","ENVR_INFO_DV_CD":"D","STN_MSG_ENCP_CD":"0","STN_MSG_COMP_CD":"0","LANG_CD":"KO","TMSG_WRTG_DT":"20220625","TMSG_CRE_SYS_NM":"HPG91394","STD_TMSG_SEQ_NO":"61656116264733","STD_TMSG_PRGR_NO":"00","STN_TMSG_IP":"172.25.251.113","STN_TMSG_MAC":"","FRS_RQST_SYS_CD":"HPG","FRS_RQST_DTM":"20220625091744733","TRMS_SYS_CD":"MCI","TRMS_ND_NO":"0202","RQST_RSPS_DV_CD":"R","TRSC_SYNC_DV_CD":"S","TMSG_RQST_DTM":"20220625091744749","RECV_SVC_CD":"HBSGOLREM9086","INTF_ID":"TFO00HBSGOLREM9086","TMSG_RSPS_DTM":"20220625091744766","PRCS_RSLT_CD":"","ERR_OCC_SYS_CD":"","STN_TMSG_ERR_CD":"","MCI_NODE_NO":"","REMT_IP":"","MCI_SSN_ID":"","FILLER":""}}}
    }

}
