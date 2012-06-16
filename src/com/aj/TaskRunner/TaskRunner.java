package com.aj.TaskRunner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class TaskRunner {
	
	public static final String TAG = TaskRunner.class.getName();
	
	private Context context;
	private TaskRunnerListener resultListener;

	public TaskRunner(Context context, TaskRunnerListener resultListener) {
		this.context = context;
		this.resultListener = resultListener;
	}
	
	public void run(Task task) {
		Intent intent = new Intent(context, TaskRunnerIntent.class);
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
			if(resultListener != null) {
				if(resultCode == Constants.TASK_COMPLETED) {
					resultListener.onTaskCompleted(resultData);
				} 
				if(resultCode == Constants.TASK_PROGRESS_UPDATED) {
					resultListener.onTaskProgressUpdated(resultData.getInt(Constants.EXTRA_KEY_PROGRESS_UPDATED));
				}
			}
		}
	}
}
