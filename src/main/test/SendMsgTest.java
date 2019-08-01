import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.viverselftest.util.JsonMapperUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Congwz on 2019/6/29.
 */
public class SendMsgTest {

    private static String url = "http://gw.api.taobao.com/router/rest";

    private static String appkey = "23607276";

    private static String secret = "5556e41449e00cf3d1ce8592a0693c78";

    public static String sendMsg(String code,String mobile) throws ApiException {

        String freeSignName = "瀚川智能科技";
        String templateCode = "SMS_44090037";

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("number", code);
        String sms_param = "";

        JsonMapperUtils jsonMapper = new JsonMapperUtils();
        sms_param = jsonMapper.toJson(param);
        System.out.println("param to json: " + sms_param);  //param to json: {"number":"768117"}

        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName(freeSignName);
        req.setSmsParamString(sms_param);
        req.setRecNum(mobile);
        req.setSmsTemplateCode(templateCode);

        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        Map<String, Object> response = jsonMapper.fromJson(rsp.getBody(), Map.class);
        System.out.println("response map: " + response);
        //response map: {alibaba_aliqin_fc_sms_num_send_response={result={err_code=0, model=499117261799255137^0, msg=OK, success=true}, request_id=5zd1dvjvg0pr}}


        if(response.get("error_response") == null){
            return "success";
        }else{
            Map<String, Object> result = (Map<String, Object>) response.get("error_response");
            String errMsg = (String) result.get("sub_msg");
            return errMsg;
        }

    }

    public static void main(String[] args) {

        /*int verificationcode = (int) (Math.random() * (1000000 - 100000) + 100000);
        System.out.println("verificationcode: 【" + verificationcode + "】");  //verificationcode: 【768117】
        String mobile = "15251305260";
        try {
            String returnMsg = sendMsg(String.valueOf(verificationcode), mobile);
            if(!"success".equals(returnMsg)){
                if("触发业务流控".equals(returnMsg)){
                    returnMsg = "获取验证码错误,允许每分钟获取1条,累计每小时获取7条,每天获取50条";
                }
                //throw new ErrorException("100007",returnMsg);
                System.out.println(returnMsg);
            }

        } catch (ApiException e) {
            e.printStackTrace();
            throw new ErrorException("100001",e.getMessage());
        }*/
    }
}
