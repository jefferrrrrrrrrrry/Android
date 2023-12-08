package com.example.job;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.job.Job.JobAdapter;
import com.example.job.Job.JobItem;
import com.example.job.ui.search.SearchFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SearchService extends Service {
    private WebView webView;
    private boolean hasPerformedSearch = false;
    private boolean hasGetInfo = false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        webView = new WebView(this);
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
                    String key_word = intent.getExtras().get("search_key").toString();
                    System.out.println(key_word);
                    // Now that the page has finished loading for the first time, perform the search
                    hasPerformedSearch = true;
                    performSearch(key_word);
                    // Update the flag to ensure the search is not performed again
                } else {
                    hasGetInfo = true;
                    getWebViewHtmlContent();

                }
            }
        });

        // 加载网页
        webView.loadUrl("https://www.zhipin.com/beijing/");

        return START_NOT_STICKY;
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
                            ArrayList<String> ka = new ArrayList<>();
                            ArrayList<String> lid = new ArrayList<>();
                            elements.forEach((e) -> {
                                href.add(e.attr("href"));
                                ka.add(e.attr("ka"));
                                lid.add(e.attr("data-lid"));
                            });
                            String[] hreF = href.toArray(new String[0]);
                            String[] KA = ka.toArray(new String[0]);
                            String[] LID = lid.toArray(new String[0]);
//                            System.out.println(elements.first().attr("href"));
//                            System.out.println(elements.select(".title-text").text());
//                            System.out.println(elements.select(".salary").text());
//                            System.out.println(elements.select(".company").text());
//                            System.out.println(elements.select(".workplace").text());

                            JobAdapter jobAdapter = SearchFragment.getJobAdapter();
                            for (int i = 0; i < size; i++) {
                                JobItem p = new JobItem(titles[i], workplaces[i] + " " + comps[i], hr[i],
                                        salaries[i], "https://www.zhipin.com" + hreF[i] + "?ka=" + KA[i] + "_blank&lid=" + LID[i]);
                                jobAdapter.add(p);
                            }
                            jobAdapter.notifyDataSetChanged();
                            stopSelf();
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}