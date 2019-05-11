package com.alexis.mytestdemo;

import android.util.Log;

import com.alexis.mytestdemo.service.CookieService;

import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ProjectName: MyTestDemo
 * @Package: com.alexis.mytestdemo
 * @ClassName: TaoBao
 * @Description: java类作用描述
 * @Author: Alexis.zou
 * @CreateDate: 2019/5/10 16:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/10 16:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TaoBao {
    private static final String TAG = "zhz_TAOBAO";
    // 替换成自己的cookie
    private static String cookie_str = "t=30144d0b8811189c9cfd4a8b11eb1967; cookie2=1d75d6d6faf36773631034a5c4af33db; v=0; _tb_token_=e95bfee7be5e3; cna=M+VaFanHyh4CAXQYQSszd9Rh; _w_app_lg=0; ntm=1; thw=cn; _cc_=UIHiLt3xSw%3D%3D; l=AkZGLuEEzqVguHD1/3O-jCLHlifIpYph; isg=BFNThnckC6_HBMd7oWKXsSnF6dV3TBJUv9y0UgVwr3KphHMmjdh3GrHGuLJPPz_C; ockeqeudmj=rBQqazQ%3D; munb=3680834505; WAPFDFDTGFG=%2B4cMKKP%2B8PI%2BJA%2FDWrDi9cgAk2J8aw%3D%3D; unb=3680834505; sg=55b; _l_g_=Ug%3D%3D; skt=bd86b43c790c0a89; uc1=cookie21=URm48syIZx9a&cookie15=UtASsssmOIJ0bQ%3D%3D&cookie14=UoTZ48D7ij2wZw%3D%3D; cookie1=VFIdoT9sEH9VVIHo8SamhD3IveDtSaGHzMeCRNVgWaU%3D; csg=70340dbc; uc3=vt3=F8dBy3qKVefld8Hse5w%3D&id2=UNaPAEqQCPkJpw%3D%3D&nk2=G4mgLDVwqmjRZho%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; tracknick=xiaofan0895; lgc=xiaofan0895; dnk=xiaofan0895; _nk_=xiaofan0895; cookie17=UNaPAEqQCPkJpw%3D%3D; _m_h5_tk=faa962f357d937f6f34a5b84b6281e47_1557554650268; _m_h5_tk_enc=5caa0225de815763c17a48626caa3b15";

    static class MyCookie implements CookieJar{

        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            List<Cookie> cookieList = new ArrayList<>();

            List<Cookie> parse = parse();
            for (Cookie cookie : parse){
                for (Cookie coo : cookies){
                    if (coo.name().equals(cookie.name())){
                        cookieList.add(coo);
                    }else{
                        cookieList.add(cookie);
                    }
                }
            }
            System.out.println("cookieList:"+cookieList.toString());
            cookieStore.put(url.host(), cookieList);

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }

    }

    private static List<Cookie> parse(){
        List<Cookie> cookies = new ArrayList<>();
        String[] strings = cookie_str.split(";");
        for (String str : strings) {
            String[] split = str.split("=");
            Cookie.Builder builder = new Cookie.Builder();
            builder.name(split[0].trim());
            builder.value(split[1].trim());
            builder.domain("taobao.com");
            builder.path("/");
            cookies.add(builder.build());
        }

        return cookies;
    }


    public static void main(String[] args) {


        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .cookieJar(new MyCookie()).build();
        while (true){

            final Request request = new Request.Builder().url("https://shoucang.taobao.com/item_collect_n.htm")
                    .addHeader("cookie", cookie_str).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //System.out.println(response.body().string());
                    String result = response.body().string();
                    if(!result.contains("xiaofan0895的收藏夹")){
                        System.out.println(result);
                        System.out.println("cookie failed at:"+new Date());
                    }else{
                        System.out.println("cookie is successed:"+new Date());
                    }
                }

            });
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("===================================");
        }
    }
}
