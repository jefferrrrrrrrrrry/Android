package com.example.job;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.job.Job.JobItem;
import com.example.job.Job.JobsAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        webView.setVisibility(View.GONE);
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
                    String key_word = getIntent().getExtras().get("search_key").toString();
                    System.out.println(key_word);
                    // Now that the page has finished loading for the first time, perform the search
                    hasPerformedSearch = true;
                    performSearch(key_word);
                    // Update the flag to ensure the search is not performed again

                }else {
                    hasGetInfo = true;
                    extractAndProcessData();

                }
            }
        });
        // 加载网页
        webView.loadUrl("https://www.zhipin.com/beijing/");
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

        webView.evaluateJavascript("(function() { \n"+
                "const jobElements = document.querySelectorAll('.item');\n"+
                "const jobsArray = [];\n" +
                        "\n" +
                        "// 遍历所有元素\n" +
                        "jobElements.forEach((jobElement, index) => {\n" +
                        "    const title = jobElement.querySelector('.title-text').textContent.trim();\n" +
                        "    const salary = jobElement.querySelector('.salary').textContent.trim();\n" +
                        "    const company = jobElement.querySelector('.company').textContent.trim();\n" +
                        "    const hrname = jobElement.querySelector('.user-wrap').querySelector('.name').textContent.trim();\n" +
                        "    const href = jobElement.querySelector('a').getAttribute('href');\n"+
                        "    // Extracting labels\n" +
                        "    const jobInfo = {\n" +
                        "        title,\n" +
                        "        salary,\n" +
                        "        company,\n" +
                        "        hrname,\n" +
                        "        href,\n" +
                        "    };\n" +
                        "    jobsArray.push(jobInfo);\n" +
                        "});" +
                "return JSON.stringify(jobsArray);})();", value -> {
            try {
                value=value.substring(1,value.length()-1).replaceAll("\\\\","");
                JSONArray jsonArray=new JSONArray(value);
                JobsAll.getAll().clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // 获取 JSON 对象中的属性值
                    String title = jsonObject.getString("title");
                    String salary = jsonObject.getString("salary");
                    String company = jsonObject.getString("company");
                    String hrname = jsonObject.getString("hrname");
                    String href = jsonObject.getString("href");
                    JobsAll.getAll().add(new JobItem(title,company,hrname,salary,webView.getUrl()+href));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
    }
   // "[{\"title\":\"兼职初中作业班老师\",\"salary\":\"3500-4000元/月\",\"company\":\"思睿\",\"workplace\":"北京\"}"
   // "[{\"title\":\"兼职初中作业班老师\",\"salary\":\"3500-4000元/月\",\"company\":\"思睿\",\"workplace\":\"北京\"},{\"title\":\"小学生作业托管老师\",\"salary\":\"5-8K\",\"company\":\"为文教育\",\"workplace\":\"北京\"}]"