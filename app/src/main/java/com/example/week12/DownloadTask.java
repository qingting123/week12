package com.example.week12;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
    ProgressBar pb;
    TextView tvProgress;
    int progress = 0;
    protected Context context;

    public DownloadTask(Activity activity) {
        context = activity;
        pb = (ProgressBar) activity.findViewById(R.id.progress_bar);
        tvProgress = (TextView) activity.findViewById(R.id.tv_progress);
    }

    //子线程中运行，耗时操作。将执行结束的结果返回onPostExecute（）参数中
    @Override
    protected Boolean doInBackground(Void... params) {
        while (true) {
            //更新进度信息，回调onProgressUpdate函数
            publishProgress(progress);
            if (progress != 100) {
                progress += 10;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return true;
            }
        }
    }

    //运行在UI线程中,任务执行前的准备，比如弹出进度条
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
    }

    //运行在UI线程中,执行处理返回的结果
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            Toast.makeText(context, "下载完毕", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
        }
    }

    //运行在UI线程中，更新当前进度条信息。被publishProgress调用
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pb.setProgress(progress);
        tvProgress.setText(progress + "%");
    }
}
