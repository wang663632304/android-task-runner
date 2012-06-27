package com.aj.TaskRunner;

import com.aj.TaskRunner.Task.TaskProgressListener;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public final class TaskRunnerIntentService extends IntentService implements TaskProgressListener {
	
	public static final String TAG = TaskRunnerIntentService.class.getName();
	
	private ResultReceiver receiver;
	
	public TaskRunnerIntentService() {
		this(TaskRunnerIntentService.class.getName());
	}

	public TaskRunnerIntentService(String name) {
		super(name);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "Destroying IntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		receiver = intent.getParcelableExtra(Constants.EXTRA_KEY_RECEIVER);
		final Bundle b = new Bundle();
		final Task task = intent.getParcelableExtra(Constants.EXTRA_KEY_TASK);
		task.execute(this, task);
		b.putParcelable(Constants.EXTRA_KEY_TASK, task);
		receiver.send(Constants.TASK_COMPLETED, b);
		
	}

	@Override
	public void onProgressUpdate(int progress, Task task) {
		final Bundle b = new Bundle();
		b.putParcelable(Constants.EXTRA_KEY_TASK, task);
		b.putInt(Constants.EXTRA_KEY_PROGRESS_UPDATED, progress);
		receiver.send(Constants.TASK_PROGRESS_UPDATED, b);
	}
}
