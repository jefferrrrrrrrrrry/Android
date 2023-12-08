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

public class Web4Service extends Service {
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
            private boolean finished = false;
            @Override
            public void onPageFinished(WebView view, String url) {
                // 在页面加载完成后获取整个 HTML 内容
                if (!finished) {
                    getWeb4(webView, "https://www.lagou.com/wn/jobs?cl=false&fromSearch=true&labelWords=sug&suginput="+search_key+"&kd="+search_key);
                    finished = true;
                    System.out.println("in 4");
                }
            }
        });

        webView.loadUrl("https://www.lagou.com/wn/jobs?cl=false&fromSearch=true&labelWords=sug&suginput="+search_key+"&kd="+search_key);

        return START_NOT_STICKY;
    }

    private void getWeb4(WebView webView, String url) {
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
                        Elements elements = document.getElementsByClass("item-top__1Z3Zo");
                        int size = elements.size();
                        String[] titles = new String[size];
                        String[] salaries = new String[size];
                        String[] comps = new String[size];

                        Elements tmp = elements.select(".p-top__1F7CL");
                        gothrough(tmp, titles);
                        tmp = elements.select(".money__3Lkgq");
                        gothrough(tmp, salaries);
                        int j = 0;
                        for (Element element: elements) {
                            Elements fresh = element.select(".company-name__2-SjF");
                            comps[j] = fresh.text();
                            j++;
                        }

                        JobAdapter jobAdapter = SearchFragment.getJobAdapter();
                        for (int i = 0; i < size; i++) {
                            JobItem p = new JobItem(titles[i], comps[i], "面试官",
                                    salaries[i], url, "拉勾网");
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