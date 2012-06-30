package com.aj.TaskRunnerActivity;

import com.aj.TaskRunner.Constants;
import com.aj.TaskRunner.Task;
import com.aj.TaskRunner.TaskRunner;
import com.aj.TaskRunner.TaskRunnerIntentService;
import com.aj.TaskRunner.TaskRunner.TaskRunnerListener;
import com.aj.projects.net.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TaskRunnerActivity extends Activity {
	
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
        taskRunner.run(new HttpGetRequest(params), MyIntentService.class, new MyListener1());
        taskRunner.run(new MyTask(new Bundle()), MyIntentService.class, new MyListener1());
        taskRunner.run(new MyTask(new Bundle()), MyIntentService.class, new MyListener2());
        taskRunner.run(new MyTask(new Bundle()), TaskRunnerIntentService.class, new MyListener1());
        taskRunner.run(new MyTask(new Bundle()), TaskRunnerIntentService.class, new MyListener2());
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "On Destroy is called");
    }
    
    private class MyListener1 implements TaskRunnerListener {
    	@Override
    	public void onTaskCompleted(Bundle result) {
    		Task task = result.getParcelable(Constants.EXTRA_KEY_TASK);
    		Bundle taskResult = task.getResultBundle();
    		String myString = taskResult.getString("my_result");
    		Log.d(TAG, "Result recieved by Listener 1: " + myString);
    	}
    	
    	@Override
    	public void onTaskProgressUpdated(int progress) {
    		Log.d(TAG, "Progress updated by Listener 1: " + progress);
    	}
    }
    
    private class MyListener2 implements TaskRunnerListener {
    	@Override
    	public void onTaskCompleted(Bundle result) {
    		Task task = result.getParcelable(Constants.EXTRA_KEY_TASK);
    		Bundle taskResult = task.getResultBundle();
    		String myString = taskResult.getString("my_result");
    		Log.d(TAG, "Result recieved by Listener 2: " + myString);
    	}
    	
    	@Override
    	public void onTaskProgressUpdated(int progress) {
    		Log.d(TAG, "Progress updated by Listener 2: " + progress);
    	}
    }
}