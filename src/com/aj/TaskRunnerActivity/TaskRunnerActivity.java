package com.aj.TaskRunnerActivity;

import com.aj.TaskRunner.Task;
import com.aj.TaskRunner.TaskRunner;
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
        Bundle params = new Bundle();
        MyTask myTask1 = new MyTask(params);
        MyTask myTask2 = new MyTask(params);
        MyTask myTask3 = new MyTask(params);
        MyTask myTask4 = new MyTask(params);
        
        //Bundle params = new Bundle();
        //params.putString("url", "http://www.google.com");
        //HttpGetRequest getRequest = new HttpGetRequest(params);
        //taskRunner.runTask(getRequest, MyIntentService.class, new MyListener1());
        
        taskRunner.runTaskByService(myTask1, MyIntentService.class, new MyListener1());
        taskRunner.runTaskByService(myTask2, MyIntentService.class, new MyListener2());
        taskRunner.runTaskByService(myTask3, MyIntentService.class, new MyListener1());
        taskRunner.runTaskByService(myTask4, MyIntentService.class, new MyListener2());
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "On Destroy is called");
    	taskRunner.stopService(MyIntentService.class);
    }
    
    private class MyListener1 implements TaskRunnerListener {
    	@Override
    	public void onTaskCompleted(Bundle result) {
    		Task task = result.getParcelable(Task.EXTRA_KEY_TASK);
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
    		Task task = result.getParcelable(Task.EXTRA_KEY_TASK);
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