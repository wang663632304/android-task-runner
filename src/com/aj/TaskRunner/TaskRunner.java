package com.aj.TaskRunner;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * <h3>TaskRunner</h3>
 * <div>Use TaskRunner.getInstance(Context) method to get singletone. </div> 
 * <div>Then use runTask method. It takes three parametrs: task, intent service and listener. </div>
 * <div>Example: </div>
 * <pre>
 * {@code
 * TaskRunner taskRunner = TaskRunner.getInstance(getApplicationContext());
 * 
 * Bundle params = new Bundle();
 * MyTask myTask = new MyTask(params);
 * 
 * MyListener myListener = new MyListener()
 * 
 * taskRunner.runTask(myTask, MyIntentService.class, myListener);
 * } 
 * </pre> 
 */

public class TaskRunner {
	
	/**
	 * Used for setting tag in Log.d() output
	 */
	public static final String TAG = TaskRunner.class.getName();
	
	/**
	 * Singletone instance
	 */
	private static TaskRunner instance;
	
	/**
	 * Context
	 */
	private Context context;
	
	/**
	 * Map of listeners
	 */
	private HashMap<String, TaskRunnerListener> listeners;
	
	/**
	 * Returns singletone object
	 * @param context
	 * @return TaskRunner
	 */
	public static TaskRunner getInstance(Context context) {
		if(instance == null) {
			instance = new TaskRunner(context);
		}
		return instance;
	}
	
	/**
	 * <i><b>public void runTaskByService(Task task, Class &lt;?&gt; serviceClass, TaskRunnerListener listener)</b></i>
	 * <div>This method is passing given task to specified service for execution.</div>
	 * <div>If service was not previously started it will be started at this point.</div> 
	 * <div>If service was started by previous call to this method, then task will be passed to running service, 
	 * and it will be executed as soon as previous task(s) are completed.</div> 
	 * <br />
	 * @param task
	 * @param serviceClass
	 * @param listener
	 * @return void Returns immediately after invocation. Use TaskRunnerListener callback to handle the progress and final result.
	 */
	public void runTaskByService(Task task, Class <?> serviceClass, TaskRunnerListener listener) {
		final String taskId = task.toString();
		task.setId(taskId);
		this.listeners.put(taskId, listener);
		Intent intent = new Intent(context, serviceClass);
		intent.putExtra(Constants.EXTRA_KEY_RECEIVER, new ResultHandler(null));
		intent.putExtra(Task.EXTRA_KEY_TASK, task);
		context.startService(intent);
	}
	
	/**
	 * <i><b>public void stopService(Class &lt;?&gt; serviceClass)</b></i>
	 * <div>Stops the service.
	 * <div> This method will not stop currently running task. It will let currently executing task to complete all the way.</div> 
	 * <div>However, all other tasks that were scheduled for execution by this service will be canceled and service will be stopped.</div>
	 * @param serviceClass 
	 */
	public void stopService(Class <?> serviceClass) {
		Intent intent = new Intent(context, serviceClass);
		context.stopService(intent);
	}
	
	/**
	 * <h3>TaskRunnerListener interface</h3>
	 *
	 */
	public interface TaskRunnerListener {
		/**
		 * <i><b>public void onTaskCompleted(Bundle result)</b></i>
		 * <div>This callback method will be invoked as soon as task is completed.</div>
		 * @param result
		 */
		public void onTaskCompleted(Bundle result);
		/**
		 * <i><b>public void onTaskProgressUpdated(int progress)</b></i>
		 * <div>This callback method will be invoked as soon as task invokes TaskProgressListener.onProgressUpdate(int progress, Task task) method.</div>
		 * @param progress
		 */
		public void onTaskProgressUpdated(int progress);
	}
	
	
	/**
	 * Private constructor
	 * @param context
	 */
	private TaskRunner(Context context) {
		this.context = context;
		this.listeners = new HashMap<String, TaskRunnerListener>();
	}
	
	/**
	 * Result handler class
	 */
	private class ResultHandler extends ResultReceiver {

		public ResultHandler(Handler handler) {
			super(handler);
		}

		@Override
		protected void onReceiveResult (int resultCode, Bundle resultData) {
			final Task task = resultData.getParcelable(Task.EXTRA_KEY_TASK);
			final String taskId = task.getId();
			final TaskRunnerListener listener = listeners.get(taskId);
			if(listener != null) {
				if(resultCode == Constants.RESULT_CODE_TASK_COMPLETED) {
					listener.onTaskCompleted(resultData);
					listeners.remove(taskId);
				} 
				if(resultCode == Constants.RESULT_CODE_TASK_PROGRESS_UPDATED) {
					listener.onTaskProgressUpdated(resultData.getInt(Constants.EXTRA_KEY_PROGRESS_UPDATED));
				}
			}
		}
	}
}
