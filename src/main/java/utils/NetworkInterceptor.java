package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v144.network.Network;
import reports.ExtentFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NetworkInterceptor {
    private static ThreadLocal<DevTools> devTools = new ThreadLocal<>();
    public static void startCapture(WebDriver driver) {
        DevTools tools = ((ChromeDriver) driver).getDevTools();
        tools.createSession();
        devTools.set(tools);
        tools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty(),Optional.empty(),Optional.empty()));
        tools.addListener(Network.responseReceived(), response -> {
            String url = response.getResponse().getUrl();
            try {
                String requestId = response.getRequestId().toString();
                Network.GetResponseBodyResponse body =
                        tools.send(Network.getResponseBody(response.getRequestId()));
                String responseBody = body.getBody();
                logApi(url, responseBody);
            } catch (Exception ignored) {}
        });
    }

    private static void logApi(String url, String body) {
        ExtentFactory.getInstance()
                .getExtent()
                .info("API URL: " + url);
        ExtentFactory.getInstance()
                .getExtent()
                .info("<pre>" + body + "</pre>");
    }

    private static ThreadLocal<Map<String,String>> responses =
            ThreadLocal.withInitial(HashMap::new);
    public static void start(WebDriver driver) {
        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),Optional.empty(),Optional.empty()
        ));
        devTools.addListener(Network.responseReceived(), response -> {
            String url = response.getResponse().getUrl();
            try {
                String requestId = response.getRequestId().toString();
                Network.GetResponseBodyResponse body =
                        devTools.send(Network.getResponseBody(response.getRequestId()));
                responses.get().put(url, body.getBody());
            } catch (Exception ignored) {}
        });
    }
    public static String getResponse(String endpoint) {

        return responses.get()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().contains(endpoint))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}
