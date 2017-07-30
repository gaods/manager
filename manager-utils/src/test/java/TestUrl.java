import com.hesheng.utils.HttpClientUtil;

/**
 * Created by gaods on 2017/7/30.
 */
public class TestUrl {




    public static void  main(String[] args){

        String url1="http://www.xingchenma.com:9180/service.asmx/GetYzmStr?token=5D126AC6FCDB1D40BDA713B22AC4BAF1&hm=15290479471&xmid=287";
       // String url="http://www.xingchenma.com:9180/service.asmx/UserLoginStr?name=893710847&psw=hs20170628";
        String ss=HttpClientUtil.httpGetRequest(url1);
        System.out.print(ss);

    }
}
