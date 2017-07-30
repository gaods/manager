import com.hesheng.utils.HttpClientUtil;

/**
 * Created by gaods on 2017/7/30.
 */
public class TestUrl {




    public static void  main(String[] args){

        String url="http://www.xingchenma.com:9180/service.asmx/UserLoginStr?name=893710847&psw=hs20170628";
        String ss=HttpClientUtil.httpGetRequest(url);
        System.out.print(ss);

    }
}
