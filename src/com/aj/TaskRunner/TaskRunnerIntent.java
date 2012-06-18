package com.aj.TaskRunner;

import com.aj.TaskRunner.Task.TaskProgressListener;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public final class TaskRunnerIntent extends IntentService implements TaskProgressListener {
	
	public static final String TAG = TaskRunnerIntent.class.getName();
	
	private ResultReceiver receiver;
	
	public TaskRunnerIntent() {
		this(TaskRunnerIntent.class.getName());
	}

	public TaskRunnerIntent(String name) {
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
		task.execute(this);
		b.putBundle(Constants.EXTRA_KEY_RESULT_BUNDLE, task.getResultBundle());
		receiver.send(Constants.TASK_COMPLETED, b);
		
	}

	@Override
	public void onProgressUpdate(int progress) {
		final Bundle b = new Bundle();
		b.putInt(Constants.EXTRA_KEY_PROGRESS_UPDATED, progress);
		receiver.send(Constants.TASK_PROGRESS_UPDATED, b);
	}
}
