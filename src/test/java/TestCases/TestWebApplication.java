package TestCases;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

public class TestWebApplication {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://52.90.29.67/index.php/customer/account/create");
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity result = response.getEntity();

        int code = response.getStatusLine().getStatusCode();
        System.out.println("---- Code: " + code);
        String data = IOUtils.toString(result.getContent());
        System.out.println("---- Data: " + data);

        System.out.println("---- Cookies: ");
        Header[] headers = response.getHeaders("Set-Cookie");
        for (Header h : headers) {
            System.out.println(h.getValue().toString());
        }

        String formKey = StringUtils.substringBetween(data, "<input type=\"hidden\" name=\"form_key\" value=\"", "\"");
        HttpEntity entity = MultipartEntityBuilder.create()
                .addTextBody("success_url", "")
                .addTextBody("error_url", "")
                .addTextBody("form_key", formKey)
                .addTextBody("firstname", "123")
                .addTextBody("middlename", "123")
                .addTextBody("lastname", "123")
                .addTextBody("email", "tien4@gmail.com")
                .addTextBody("password", "123456")
                .addTextBody("confirmation", "123456")
                .build();

        HttpPost httpPost = new HttpPost("http://52.90.29.67/index.php/customer/account/createpost/");
        httpPost.setEntity(entity);
        response = httpClient.execute(httpPost);
        result = response.getEntity();
        code = response.getStatusLine().getStatusCode();
        System.out.println("---- Code: " + code);
        data = IOUtils.toString(result.getContent());
        System.out.println("---- Data: " + data);

        System.out.println("---- Cookies: ");
        headers = response.getHeaders("Set-Cookie");
        for (Header h : headers) {
            System.out.println(h.getValue().toString());
        }

        if (code == 302) {
            httpGet = new HttpGet("http://52.90.29.67/index.php/");
            response = httpClient.execute(httpGet);
            result = response.getEntity();

            code = response.getStatusLine().getStatusCode();
            System.out.println("---- Code: " + code);
            data = IOUtils.toString(result.getContent());
            System.out.println("---- Data: " + data);

            if (data.contains("Default welcome msg!")) {
                System.out.println("Failed to register");
                String error = StringUtils.substringBetween(data, "<ul class=\"messages\"><li class=\"error-msg\"><ul><li><span>", "</span>");

                if (StringUtils.isBlank(error)) {
                    error = "Failed to register. Unknow error";
                }
            }
            else {
                System.out.println("Passed");
            }
        }
    }
}
