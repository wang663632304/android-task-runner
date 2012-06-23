package com.aj.TaskRunner;

import java.util.HashSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class TaskRunner {
	
	public static final String TAG = TaskRunner.class.getName();
	
	private static TaskRunner instance;
	
	private Context context;
	private HashSet<TaskRunnerListener> listeners;
	
	private Intent intent;
	
	public static TaskRunner getInstance(Context context) {
		if(instance == null) {
			instance = new TaskRunner(context);
		}
		return instance;
	}

	private TaskRunner(Context context) {
		this.context = context;
		this.listeners = new HashSet<TaskRunner.TaskRunnerListener>();
		this.intent = new Intent(context, TaskRunnerIntent.class);
	}
	
	public void run(Task task, TaskRunnerListener listener) {
		this.listeners.add(listener);
		intent.putExtra(Constants.EXTRA_KEY_RECEIVER, new ResultHandler(null));
		intent.putExtra(Constants.EXTRA_KEY_TASK, task);
		context.startService(intent);
	}
	
	public void cancel() {
		context.stopService(intent);
	}
	
	public interface TaskRunnerListener {
		public void onTaskCompleted(Bundle result);
		public void onTaskProgressUpdated(int progress);
	}
	
	private class ResultHandler extends ResultReceiver {

		public ResultHandler(Handler handler) {
			super(handler);
		}

		@Override
		protected void onReceiveResult (int resultCode, Bundle resultData) {
			
			for(TaskRunnerListener trl : listeners) {
				if(resultCode == Constants.TASK_COMPLETED) {
					trl.onTaskCompleted(resultData);
				} 
				if(resultCode == Constants.TASK_PROGRESS_UPDATED) {
					trl.onTaskProgressUpdated(resultData.getInt(Constants.EXTRA_KEY_PROGRESS_UPDATED));
				}
			}
		}
	}
}
