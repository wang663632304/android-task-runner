package com.aj.TaskRunnerActivity;

import com.aj.TaskRunner.Constants;
import com.aj.TaskRunner.Task;
import com.aj.TaskRunner.TaskRunner;
import com.aj.TaskRunner.TaskRunner.TaskRunnerListener;
import com.aj.projects.net.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TaskRunnerActivity extends Activity implements TaskRunnerListener {
	
	public static final String TAG = TaskRunnerActivity.class.getName();
	
	private TaskRunner taskRunner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        taskRunner = TaskRunner.getInstance(getApplicationContext());
        //taskRunner.run(new MyTask());
        Bundle params = new Bundle();
        params.putString("url", "http://www.google.com");
        taskRunner.run(new HttpGetRequest(params), this);
        taskRunner.run(new MyTask(new Bundle()), this);
        taskRunner.run(new MyTask(new Bundle()), this);
        taskRunner.run(new MyTask(new Bundle()), this);
        taskRunner.run(new MyTask(new Bundle()), this);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "On Destroy is called");
    	taskRunner.cancel();
    }

	@Override
	public void onTaskCompleted(Bundle result) {
		Task task = result.getParcelable(Constants.EXTRA_KEY_TASK);
		Bundle taskResult = task.getResultBundle();
		String myString = taskResult.getString("my_result");
		Log.d(TAG, "Result recieved: " + myString);
	}
	
	@Override
	public void onTaskProgressUpdated(int progress) {
		Log.d(TAG, "Progress updated " + progress);
		
	}
	
	public TaskRunner getTaskRunner() {
		return taskRunner;
	}
}