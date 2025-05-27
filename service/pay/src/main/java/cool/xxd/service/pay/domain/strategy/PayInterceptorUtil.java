package cool.xxd.service.pay.domain.strategy;

import okhttp3.Request;
import okio.Buffer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public final class PayInterceptorUtil {
    public static String geUrl(Request request) {
        var requestPath = request.url().uri().getPath();
        var queryString = request.url().encodedQuery();
        var urlSb = new StringBuilder();
        urlSb.append(requestPath);
        if (StringUtils.isNotBlank(queryString)) {
            urlSb.append("?").append(queryString);
        }
        return urlSb.toString();
    }

    public static String getBody(Request request) {
        var requestBody = request.body();
        if (requestBody == null) {
            return "";
        }
        var buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buffer.readUtf8();
    }
}
