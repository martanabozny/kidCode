package com.example.progressBar2;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.webkit.WebView;

public class InProgress extends Activity {

    private Handler handler;
    private ProgressDialog progress;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                progress.dismiss();
                Intent mainIntent = new Intent(context, Web.class);
                startActivity(mainIntent);
                super.handleMessage(msg);
            }

        };
        progress.show();

        new Thread()
        {
            public void run()
            {
                WebView web = (WebView)findViewById(R.id.webView);
                web.getSettings().setJavaScriptEnabled(true);
                web.loadUrl("http://www.google.com");

                handler.sendEmptyMessage(0);
            }

        }.start();

    }

}