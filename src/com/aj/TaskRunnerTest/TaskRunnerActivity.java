package com.aj.TaskRunnerTest;

import com.aj.TaskRunner.Constants;
import com.aj.TaskRunner.TaskRunner;
import com.aj.TaskRunner.TaskRunner.TaskRunnerListener;
import com.aj.projects.net.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TaskRunnerActivity extends Activity implements TaskRunnerListener {
	
	public static final String TAG = TaskRunnerActivity.class.getName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TaskRunner taskRunner = new TaskRunner(getApplicationContext(), this);
        //taskRunner.run(new MyTask());
        Bundle params = new Bundle();
        params.putString("url", "http://www.google.com");
        taskRunner.run(new HttpGetRequest(params));
        
        taskRunner.run(new MyTask(new Bundle()));
    }

	@Override
	public void onTaskCompleted(Bundle result) {
		String myString = result.getBundle(Constants.EXTRA_KEY_RESULT_BUNDLE).getString("my_result");
		Log.d(TAG, "Result recieved: " + myString);
	}
	
	@Override
	public void onTaskProgressUpdated(int progress) {
		Log.d(TAG, "Progress updated " + progress);
		
	}
}