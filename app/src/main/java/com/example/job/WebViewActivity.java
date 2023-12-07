package com.example.job;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private JSONArray jsonArray;
    private boolean hasPerformedSearch = false;
    private boolean hasGetInfo = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        System.out.println(webView.getSettings().getUserAgentString());

        // 启用 JavaScript 执行
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        // 设置 WebViewClient 以处理页面加载和在 WebView 内部打开链接
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 在页面加载完成后获取整个 HTML 内容

                System.out.println("666");
                if (!hasPerformedSearch) {
                    getWebViewHtmlContent();
                    String key_word = getIntent().getExtras().get("search_key").toString();
                    System.out.println(key_word);
                    // Now that the page has finished loading for the first time, perform the search
                    hasPerformedSearch = true;
                    performSearch(key_word);
                    // Update the flag to ensure the search is not performed again
                }
//                }else {
//                    getWebViewHtmlContent();
//                    // Now that the page has finished loading for the first time, perform the search
//                    hasGetInfo = true;
//                    extractAndProcessData();
//
//                }
            }
        });

        // 加载网页
        webView.loadUrl("https://www.zhipin.com/beijing/");
    }

    private void getWebViewHtmlContent() {
        // 使用 evaluateJavascript 方法获取整个 HTML 内容
        webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String htmlContent) {
                        // 处理获取到的 HTML 内容
                        if (htmlContent != null) {
                            System.out.println("HTML Content: " + htmlContent);
                        }
                    }
                });
    }
    private void performSearch(String searchKey) {
        // Inject JavaScript to fill in the search box
        String fillSearchBoxScript = "document.querySelector('.ipt-search').value = \'"+searchKey+"\';";
        webView.evaluateJavascript(fillSearchBoxScript, null);

        // Inject JavaScript to click the search button
        String clickSearchButtonScript = "document.querySelector('.btn.btn-search').click();";
        webView.evaluateJavascript(clickSearchButtonScript, null);
    }
    private void extractAndProcessData() {
        // 获取所有 job-name 元素的文本内容
        StringBuilder javaCode = new StringBuilder();

        javaCode.append("(function{List<Map<String, String>> jobDataArray = new ArrayList<>();\n");
        javaCode.append("Elements liElements = document.select(\"li\");\n");
        javaCode.append("for (Element liElement : liElements) {\n");
        javaCode.append("    // 获取职位信息\n");
        javaCode.append("    Element nameElement = liElement.selectFirst(\".name\");\n");
        javaCode.append("    String jobTitle = nameElement != null ? nameElement.text().trim() : \"N/A\";\n");
        javaCode.append("\n");
        javaCode.append("    // 获取薪水信息\n");
        javaCode.append("    Element redElement = liElement.selectFirst(\".red\");\n");
        javaCode.append("    String salary = redElement != null ? redElement.text().trim() : \"N/A\";\n");
        javaCode.append("\n");
        javaCode.append("    // 获取岗位职责信息\n");
        javaCode.append("    Element jobDemandElement = liElement.selectFirst(\".job-demand\");\n");
        javaCode.append("    String jobDescription = jobDemandElement != null ? jobDemandElement.text().trim() : \"N/A\";\n");
        javaCode.append("\n");
        javaCode.append("    // 获取公司名称\n");
        javaCode.append("    Element companyNameElement = liElement.selectFirst(\".info-company .name\");\n");
        javaCode.append("    String companyName = companyNameElement != null ? companyNameElement.text().trim() : \"N/A\";\n");
        javaCode.append("\n");
        javaCode.append("    // 检查是否有 \"N/A\" 的信息，如果没有，则添加到列表\n");
        javaCode.append("    if (!\"N/A\".equals(jobTitle) && !\"N/A\".equals(salary) && !\"N/A\".equals(jobDescription) && !\"N/A\".equals(companyName)) {\n");
        javaCode.append("        Map<String, String> jobData = new HashMap<>();\n");
        javaCode.append("        jobData.put(\"jobTitle\", jobTitle);\n");
        javaCode.append("        jobData.put(\"salary\", salary);\n");
        javaCode.append("        jobData.put(\"jobDescription\", jobDescription);\n");
        javaCode.append("        jobData.put(\"companyName\", companyName);\n");
        javaCode.append("        jobDataArray.add(jobData);\n");
        javaCode.append("    }\n");
        javaCode.append("}\n");
        javaCode.append("return JSON.stringify(jobDataArray);})();");

        webView.evaluateJavascript(javaCode.toString(), value -> {
            // 处理 JavaScript 返回的值
            // 在这里，value 将包含 JavaScript 代码的返回值

            System.out.println(value);

            // 如果返回的是 JSON 字符串，您可以使用 JSON 解析库进行解析
//            try {
//                jsonArray = new JSONArray(value);
//                // 现在 jsonArray 中包含了 JavaScript 代码返回的信息
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        });
    }
    }
