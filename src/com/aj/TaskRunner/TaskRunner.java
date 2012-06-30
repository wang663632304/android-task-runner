package com.aj.TaskRunner;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class TaskRunner {
	
	public static final String TAG = TaskRunner.class.getName();
	
	private static TaskRunner instance;
	
	private Context context;
	private HashMap<String, TaskRunnerListener> listeners;
	
	public static TaskRunner getInstance(Context context) {
		if(instance == null) {
			instance = new TaskRunner(context);
		}
		return instance;
	}

	private TaskRunner(Context context) {
		this.context = context;
		this.listeners = new HashMap<String, TaskRunnerListener>();
	}
	
	public void run(Task task, Class <?> serviceClass, TaskRunnerListener listener) {
		final String taskId = task.toString();
		task.setId(taskId);
		this.listeners.put(taskId, listener);
		Intent intent = new Intent(context, serviceClass);
		intent.putExtra(Constants.EXTRA_KEY_RECEIVER, new ResultHandler(null));
		intent.putExtra(Constants.EXTRA_KEY_TASK, task);
		context.startService(intent);
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
			final Task task = resultData.getParcelable(Constants.EXTRA_KEY_TASK);
			final String taskId = task.getId();
			final TaskRunnerListener listener = listeners.get(taskId);
			if(listener != null) {
				if(resultCode == Constants.TASK_COMPLETED) {
					listener.onTaskCompleted(resultData);
					listeners.remove(taskId);
				} 
				if(resultCode == Constants.TASK_PROGRESS_UPDATED) {
					listener.onTaskProgressUpdated(resultData.getInt(Constants.EXTRA_KEY_PROGRESS_UPDATED));
				}
			}
		}
	}
}
