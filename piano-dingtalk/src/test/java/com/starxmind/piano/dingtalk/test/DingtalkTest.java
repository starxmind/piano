package com.starxmind.piano.dingtalk.test;

import com.google.common.collect.Lists;
import com.starxmind.bass.http.XHttp;
import com.starxmind.bass.io.core.IOUtils;
import com.starxmind.bass.sugar.ClassLoaderUtils;
import com.starxmind.bass.sugar.ExceptionUtils;
import com.starxmind.bass.sugar.ExpressionUtils;
import com.starxmind.bass.sugar.beans.ExceptionLocation;
import com.starxmind.piano.dingtalk.DingTalkRobot;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class DingtalkTest {
    private DingTalkRobot dingTalkRobot;
    private String accessToken = "dingtalk access token";
    private String secret = "dingtalk secret";
    private String atMobile = "@mobile";

    @Before
    public void setUp() {
        XHttp XHttp = new XHttp();
        dingTalkRobot = new DingTalkRobot(XHttp, accessToken, secret);
    }

//    @Test
    public void sendTextMessage() throws IOException {
        try {
            int test = 1 / 0;
        } catch (Exception ex) {
            InputStream inputStream = ClassLoaderUtils.getResourceStream(getClass(), "errorDetail.md");
            String contentMarkdown = IOUtils.readStreamAsString(inputStream);
            ExceptionLocation exceptionLocation = ExceptionUtils.getCustomExceptionLocation(ex);
            Map<String, String> variables = new HashMap<>();
            variables.put("class", exceptionLocation.getClassName());
            variables.put("method", exceptionLocation.getMethodName());
            variables.put("line", String.valueOf(exceptionLocation.getLineNumber()));
            variables.put("log", ExceptionUtils.track(ex));
            final String
                    title = "【测试环境】xxx服务发生一个错误",
                    content = title + "\n" + ExpressionUtils.evaluateExpression(contentMarkdown, variables);
            dingTalkRobot.sendTextMessage(content);
        }
    }

//    @Test
    public void sendMarkdownMessage() throws IOException {
        try {
            int test = 1 / 0;
        } catch (Exception ex) {
            InputStream inputStream = ClassLoaderUtils.getResourceStream(getClass(), "errorDetail.md");
            String contentMarkdown = IOUtils.readStreamAsString(inputStream);
            ExceptionLocation exceptionLocation = ExceptionUtils.getCustomExceptionLocation(ex);
            Map<String, String> variables = new HashMap<>();
            variables.put("class", exceptionLocation.getClassName());
            variables.put("method", exceptionLocation.getMethodName());
            variables.put("line", String.valueOf(exceptionLocation.getLineNumber()));
            variables.put("log", ExceptionUtils.track(ex));
            final String
                    title = "【测试环境】xxx服务发生一个错误",
                    content = ExpressionUtils.evaluateExpression(contentMarkdown, variables);
            dingTalkRobot.sendMarkdownMessage(
                    title,
                    content
            );
        }
    }

    //    @Test
    public void sendMarkdownMessageWithAtSomebodies() throws IOException {
        try {
            int test = 1 / 0;
        } catch (Exception ex) {
            InputStream inputStream = ClassLoaderUtils.getResourceStream(getClass(), "errorDetail.md");
            String contentMarkdown = IOUtils.readStreamAsString(inputStream);
            ExceptionLocation exceptionLocation = ExceptionUtils.getCustomExceptionLocation(ex);
            final String title = "【测试环境】xxx服务发生一个错误";
            Map<String, String> variables = new HashMap<>();
            variables.put("title", title);
            variables.put("class", exceptionLocation.getClassName());
            variables.put("method", exceptionLocation.getMethodName());
            variables.put("line", String.valueOf(exceptionLocation.getLineNumber()));
            variables.put("log", ExceptionUtils.track(ex));
            final String content = ExpressionUtils.evaluateExpression(contentMarkdown, variables);
            dingTalkRobot.sendMarkdownMessage(
                    title,
                    content,
                    Lists.newArrayList(atMobile),
                    null
            );
        }
    }
}
