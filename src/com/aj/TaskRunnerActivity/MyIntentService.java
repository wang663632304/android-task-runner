package com.aj.TaskRunnerActivity;

import com.aj.TaskRunner.TaskRunnerIntentService;

public class MyIntentService extends TaskRunnerIntentService {
	
	public MyIntentService() {
		super(MyIntentService.class.getName());
	}
}
