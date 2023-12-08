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

public class Web3Service extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WebView webView = new WebView(this);
        System.out.println(webView.getSettings().getUserAgentString());

        // 启用 JavaScript 执行
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        String search_key = intent.getStringExtra("search_key");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // 在页面加载完成后获取整个 HTML 内容
                System.out.println("in 3");
                getWeb3(webView, "https://msou.zhaopin.com/?keyword=" + search_key + "&provinceCode=489&city=489&cityName=%E5%85%A8%E5%9B%BD");

            }
        });

        webView.loadUrl("https://msou.zhaopin.com/?keyword=" + search_key + "&provinceCode=489&city=489&cityName=%E5%85%A8%E5%9B%BD");

        return START_NOT_STICKY;
    }

    private void getWeb3(WebView webView, String url) {
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
                        String theString = "<html>\n" + htmlContent.substring(htmlContent.indexOf("\\u003Cbody>"));
                        theString = theString.replace("\\n", "\n");
                        theString = theString.replace("\\u003C", "<");
                        theString = theString.replace("\\\"", "\"");
                        theString = theString.substring(0, theString.length()-1);
                        Document document = Jsoup.parse(theString);
                        Elements elements = document.getElementsByClass("position-card");
                        int size = elements.size();
                        String[] titles = new String[size];
                        String[] salaries = new String[size];
                        String[] comps = new String[size];

                        Elements tmp = elements.select(".position-card__job-title");
                        gothrough(tmp, titles);
                        tmp = elements.select(".position-card__salary");
                        gothrough(tmp, salaries);
                        int j = 0;
                        for (Element element: elements) {
                            Elements fresh = element.select(".position-card__block-gap");
                            comps[j] = fresh.text();
                            j++;
                        }

                        JobAdapter jobAdapter = SearchFragment.getJobAdapter();
                        for (int i = 0; i < size; i++) {
                            JobItem p = new JobItem(titles[i], comps[i], "面试官",
                                    salaries[i], url, "智联招聘");
                            jobAdapter.add(p);
                        }
                        //jobAdapter.notifyDataSetChanged();
                        stopSelf();
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}