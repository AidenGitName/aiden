package com.wonders.hms.util;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.util.vo.ApiVO;
import com.wonders.hms.util.vo.KmsVO;
import com.wonders.hms.util.vo.MessageVO;
import com.wonders.hms.util.vo.api.AgentVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@ConfigurationProperties(prefix = "kms")
@Getter
@Setter
public class KMSProperties {
    private String xApiKeyHeader; // local 용 해더정보
    private String xApiKey; // local 용 해더정보

    private String endPoint; // KMS EndPoint URL
    private String secretName; // KMS 파라미터 값
    private KmsVO result; // 응답 데이터
    private MessageVO message; // 성공시 받는 message 정보

    @PostConstruct
    public void init(){
        result = procKMS();
        message = result.getMessage();
    }

    /**
     * KMS API 연결
     * @return
     */
    @SuppressWarnings("unchecked")
    private KmsVO procKMS() {
        try {
            String param = "{\"secret_name\" : \"" + this.secretName + "\"}"; //JSON 형태 데이터로 요청

            // 응답 데이터 JSON으로 변환
            String result = getHttpConnection(this.endPoint, param);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(result, KmsVO.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * httpConnect
     * @param address
     * @param sendData
     * @return
     */
    private String getHttpConnection(String address, String sendData) {
        HttpURLConnection con = null;

        try {
            URL url = new URL(address); // 요청을 보낸 URL
            con = (HttpURLConnection)url.openConnection();
            if(xApiKeyHeader != null) con.setRequestProperty(xApiKeyHeader, xApiKey); // local 환경에서만 API Header 속성 추가
            con.setRequestProperty("Content-type", "application/json");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();

            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            dos.write(sendData.getBytes());
            dos.flush();

            int resCode = con.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                return read(con.getInputStream());
            } else {
                return read(con.getErrorStream());
            }
        } catch (IOException e) {
            System.out.println(" HttpUrlConnect Exception :"+ e);
            e.printStackTrace();
        } finally {
            if( con != null) con.disconnect();
        }

        return "";
    }

    /**
     * InputSteam 변환
     * @param in
     * @return
     * @throws IOException
     */
    private String read(InputStream in) throws IOException {
        StringBuffer outStr = new StringBuffer();

        try(BufferedReader buf = new BufferedReader(new InputStreamReader(in, "utf-8"))){
            String inputLine = "";
            while ((inputLine = buf.readLine()) != null) {
                outStr.append(inputLine).append("\n");
            }

            return outStr.toString();
        }

    }
    public Optional<AgentVO> getAgent(){
        Optional<MessageVO> optMessageVO = Optional.ofNullable(message);
        Optional<ApiVO> optApiVO = Optional.ofNullable(optMessageVO.map(MessageVO::getApi).orElseThrow(IllegalStateException::new));
        Optional<AgentVO> optAgentVO = Optional.ofNullable(optApiVO.map(ApiVO::getAgent).orElseThrow(IllegalStateException::new));

        return optAgentVO;
    }

}
