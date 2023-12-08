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

import com.example.job.Job.JobAdapter;
import com.example.job.Job.JobItem;
import com.example.job.ui.search.SearchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                } else {
                    hasGetInfo = true;
                    getWebViewHtmlContent();
                    // Now that the page has finished loading for the first time, perform the search


                }
            }
        });

        // 加载网页
        webView.loadUrl("https://www.zhipin.com/beijing/");
    }

    private void getWebViewHtmlContent() {
        // 使用 evaluateJavascript 方法获取整个 HTML 内容
        webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                new ValueCallback<String>() {
                    private void gothrough(Elements e, String[] input) {
                        int i = 0;
                        for (Element ele : e) {
                            input[i] = ele.text();
                            i++;
                        }
                    }
                    @Override
                    public void onReceiveValue(String htmlContent) {
                        // 处理获取到的 HTML 内容
                        if (htmlContent != null && hasGetInfo) {
                            String theString = htmlContent;
                            theString = theString.replace("\\n", "\n");
                            theString = theString.replace("\\u003C", "<");
                            theString = theString.replace("\\\"", "\"");
                            theString = theString.substring(1, theString.length()-1);
                            Document document = Jsoup.parse(theString);
                            Elements elements = document.getElementsByClass("item").select("a");
                            int size = elements.size();
                            String[] titles = new String[size];
                            String[] salaries = new String[size];
                            String[] comps = new String[size];
                            String[] workplaces = new String[size];
                            String[] hr = new String[size];
                            Elements tmp = elements.select(".title-text");
                            gothrough(tmp, titles);
                            tmp = elements.select(".salary");
                            gothrough(tmp, salaries);
                            tmp = elements.select(".company");
                            gothrough(tmp, comps);
                            tmp = elements.select(".workplace");
                            gothrough(tmp, workplaces);
                            tmp = elements.select(".user-wrap").select(".name");
                            gothrough(tmp, hr);
                            ArrayList<String> href = new ArrayList<>();
                            elements.forEach((e) -> {
                                href.add(e.attr("href"));
                            });
                            String[] hreF = href.toArray(new String[0]);
//                            System.out.println(elements.first().attr("href"));
//                            System.out.println(elements.select(".title-text").text());
//                            System.out.println(elements.select(".salary").text());
//                            System.out.println(elements.select(".company").text());
//                            System.out.println(elements.select(".workplace").text());

                            JobAdapter jobAdapter = SearchFragment.getJobAdapter();
                            for (int i = 0; i < size; i++) {
                                JobItem p = new JobItem(titles[i], workplaces[i] + " " + comps[i], hr[i],
                                        salaries[i], "https://www.zhipin.com/beijing/" + hreF[i]);
                                jobAdapter.add(p);
                            }
                            jobAdapter.notifyDataSetChanged();
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

    }
