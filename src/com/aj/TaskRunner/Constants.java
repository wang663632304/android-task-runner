package com.aj.TaskRunner;

public abstract interface Constants {

	/**
	 * Extras key for receiver object
	 */
	public static final String EXTRA_KEY_RECEIVER             = Constants.class.getName() + "receiver";
	
	/**
	 * Extras key for task completion
	 */
	public static final String EXTRA_KEY_TASK_COMPLETED       = Constants.class.getName() + "task_completed";
	
	/**
	 * Extras key for task progress
	 */
	public static final String EXTRA_KEY_PROGRESS_UPDATED     = Constants.class.getName() + "progress_updated";
	
	/**
	 * Task completed result code
	 */
	public static final int RESULT_CODE_TASK_COMPLETED        = "task_completed".hashCode();
	
	/**
	 * Task progress update result code
	 */
	public static final int RESULT_CODE_TASK_PROGRESS_UPDATED = "task_progress_updated".hashCode();
}
