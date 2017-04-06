package com.twilio.sdk.resource.instance.taskrouter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioTaskRouterClient;
import com.twilio.sdk.resource.NextGenInstanceResource;
import com.twilio.sdk.resource.list.taskrouter.ActivityList;
import com.twilio.sdk.resource.list.taskrouter.EventList;
import com.twilio.sdk.resource.list.taskrouter.TaskList;
import com.twilio.sdk.resource.list.taskrouter.TaskQueueList;
import com.twilio.sdk.resource.list.taskrouter.WorkerList;
import com.twilio.sdk.resource.list.taskrouter.WorkflowList;

/**
 * A Workspace is a container for your Tasks, Workers, TaskQueues, Workflows and Activities. Each of these items exists
 * within a single Workspace and will not be shared across Workspaces.
 */
public class Workspace extends NextGenInstanceResource<TwilioTaskRouterClient> {

	/**
	 * Instantiates a workspace.
	 *
	 * @param client the client
	 */
	public Workspace(final TwilioTaskRouterClient client) {
		super(client);
	}

	/**
	 * Instantiates a workspace.
	 *
	 * @param client the client
	 * @param properties the properties
	 */
	public Workspace(final TwilioTaskRouterClient client, final Map<String, Object> properties) {
		super(client, properties);
	}

	/**
	 * Instantiates a workspace.
	 *
	 * @param client the client
	 * @param sid the sid
	 */
	public Workspace(final TwilioTaskRouterClient client, final String sid) {
		super(client);
		if (StringUtils.isBlank(sid)) {
			throw new IllegalArgumentException("The Sid for a Workspace cannot be null");
		}
		setProperty(SID_PROPERTY, sid);
	}

	/**
	 * The ID of the {@link com.twilio.sdk.resource.instance.Account} that owns this Workspace.
	 *
	 * @return the account sid
	 */
	public String getAccountSid() {
		return getProperty("account_sid");
	}

	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.ActivityList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.ActivityList}
	 */
	public ActivityList getActivities() {
		ActivityList activities = new ActivityList(getClient(), getSid());
		return activities;
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.ActivityList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.ActivityList}
	 */
	public ActivityList getActivities(final Map<String, String> filters) {
		ActivityList activities = new ActivityList(getClient(), getSid(), filters);
		return activities;
	}

	/**
	 * Retrieves an {@link com.twilio.sdk.resource.instance.taskrouter.Activity} from this Workspace by ID.
	 */
	public Activity getActivity(final String activitySid) {
		Activity activity = new Activity(getClient(), getSid(), activitySid);
		return activity;
	}

	/**
	 * Creates an {@link com.twilio.sdk.resource.instance.taskrouter.Activity}.
	 *
	 * @param params the params list
	 * @return a Activity
	 * @throws com.twilio.sdk.TwilioRestException
	 */
	public Activity createActivity(final Map<String, String> params) throws TwilioRestException {
		ActivityList activities = new ActivityList(getClient(), getSid());
		return activities.create(params);
	}

	/**
	 * Creates an {@link com.twilio.sdk.resource.instance.taskrouter.Activity}.
	 *
	 * @param params the params list
	 * @return a Activity
	 * @throws TwilioRestException
	 */
	public Activity createActivity(final List<NameValuePair> params) throws TwilioRestException {
		ActivityList activities = new ActivityList(getClient(), getSid());
		return activities.create(params);
	}

	/**
	 * The date and time this Workspace was created.
	 *
	 * @return the date created
	 */
	public Date getDateCreated() {
		return parseIsoDate(getProperty(DATE_CREATED_PROPERTY));
	}

	/**
	 * The date and time this Workspace was last updated.
	 *
	 * @return the date updated
	 */
	public Date getDateUpdated() {
		return parseIsoDate(getProperty(DATE_UPDATED_PROPERTY));
	}

	/**
	 * The human-readable name of the default activity. Read only.
	 *
	 * @return the default activity name
	 */
	public String getDefaultActivityName() {
		return getProperty("default_activity_name");
	}

	/**
	 * The ID of the Activity that will be used when new Workers are created in this Workspace.
	 *
	 * @return the default activity sid
	 */
	public String getDefaultActivitySid() {
		return getProperty("default_activity_sid");
	}

	/**
	 * Retrieves an {@link com.twilio.sdk.resource.instance.taskrouter.Event} from this Workspace
	 */
	public Event getEvent(final String eventSid) {
		Event event = new Event(getClient(), getSid(), eventSid);
		return event;
	}

	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.EventList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.EventList}
	 */
	public EventList getEvents() {
		EventList events = new EventList(getClient(), getSid());
		return events;
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.EventList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.EventList}
	 */
	public EventList getEvents(final Calendar startDate, final Calendar endDate, final Integer minutes, Map<String, String> filters) {
		if(filters == null) {
			filters = new HashMap<String, String>();
		}
		if(startDate != null) {
			filters.put("StartDate", formatCalendar(startDate));
		}
		if(endDate != null) {
			filters.put("EndDate", formatCalendar(endDate));
		}
		if(minutes != null) {
			filters.put("Minutes", minutes.toString());
		}
		return getEvents(filters);
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.EventList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.EventList}
	 */
	public EventList getEvents(final Map<String, String> filters) {
		EventList events = new EventList(getClient(), getSid(), filters);
		return events;
	}

	/**
	 * An optional URL where the Workspace will publish events. You can use this to gather data for reporting.
	 *
	 * @return the event callback URL
	 */
	public String getEventCallbackUrl() {
		return getProperty("event_callback_url");
	}

	/**
	 * A human-readable description of this Workspace.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
		return getProperty("friendly_name");
	}

	/**
	 * The unique ID for this Workspace.
	 *
	 * @return the sid
	 */
	public String getSid() {
		return getProperty(SID_PROPERTY);
	}

	/**
	 * Retrieves a {@link com.twilio.sdk.resource.instance.taskrouter.Task} from this Workspace.
	 */
	public Task getTask(final String taskSid) {
		Task task = new Task(getClient(), getSid(), taskSid);
		return task;
	}

	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.TaskList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}.
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.TaskList}
	 */
	public TaskList getTasks() {
		TaskList tasks = new TaskList(getClient(), getSid());
		return tasks;
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.TaskList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}.
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.TaskList}
	 */
	public TaskList getTasks(final Map<String, String> filters) {
		TaskList tasks = new TaskList(getClient(), getSid(), filters);
		return tasks;
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Task}.
	 *
	 * @param params the params list
	 * @return a Worker
	 * @throws com.twilio.sdk.TwilioRestException
	 */
	public Task createTask(final Map<String, String> params) throws TwilioRestException {
		TaskList tasks = new TaskList(getClient(), getSid());
		return tasks.create(params);
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Task}.
	 *
	 * @param params the params list
	 * @return a Workflow
	 * @throws TwilioRestException
	 */
	public Task createTask(final List<NameValuePair> params) throws TwilioRestException {
		TaskList tasks = new TaskList(getClient(), getSid());
		return tasks.create(params);
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Task}.
	 * @param workflowSid workflow sid
	 * @param attributes attributes
	 * @param priority priority for the task
	 * @param timeout timeout for the task
	 * @return a Task
	 * @throws TwilioRestException
	 */
	public Task createTask(final String workflowSid, final Map<String, String> attributes, final Integer priority, final Integer timeout) throws TwilioRestException {
		TaskList tasks = new TaskList(getClient(), getSid());
		return tasks.create(workflowSid, attributes, priority, timeout);
	}

	/**
	 * The human-readable name of the timeout activity. Read only.
	 *
	 * @return the timeout activity name
	 */
	public String getTimeoutActivityName() {
		return getProperty("timeout_activity_name");
	}

	/**
	 * The ID of the {@link com.twilio.sdk.resource.instance.taskrouter.Activity} that will be assigned to a
	 * {@link com.twilio.sdk.resource.instance.taskrouter.Worker} when a
	 * {@link com.twilio.sdk.resource.instance.taskrouter.Task} reservation times out without a response.
	 *
	 * @return the timeout activity sid
	 */
	public String getTimeoutActivitySid() {
		return getProperty("timeout_activity_sid");
	}

	/**
	 * Retrieves a {@link com.twilio.sdk.resource.instance.taskrouter.Worker} from a Workspace
	 */
	public Worker getWorker(final String workerSid) {
		Worker worker = new Worker(getClient(), getSid(), workerSid);
		return worker;
	}

	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.WorkerList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.WorkerList}
	 */
	public WorkerList getWorkers() {
		WorkerList workers = new WorkerList(getClient(), getSid());
		return workers;
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.WorkerList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.WorkerList}
	 */
	public WorkerList getWorkers(final Map<String, String> filters) {
		WorkerList workers = new WorkerList(getClient(), getSid(), filters);
		return workers;
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Worker}.
	 *
	 * @param params the params list
	 * @return a Worker
	 * @throws com.twilio.sdk.TwilioRestException
	 */
	public Worker createWorker(final Map<String, String> params) throws TwilioRestException {
		WorkerList workers = new WorkerList(getClient(), getSid());
		return workers.create(params);
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Worker}.
	 *
	 * @param params the params list
	 * @return a Worker
	 * @throws TwilioRestException
	 */
	public Worker createWorker(final List<NameValuePair> params) throws TwilioRestException {
		WorkerList workers = new WorkerList(getClient(), getSid());
		return workers.create(params);
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Worker}.
	 *
	 * @param friendlyName the friendly name of the worker
	 * @param attributes the attributes of the worker
	 * @param activitySid the default activity for the worker
	 * @return a Worker
	 * @throws TwilioRestException
	 */
	public Worker createWorker(final String friendlyName, final Map<String, String> attributes, final String activitySid) throws TwilioRestException {
		WorkerList workers = new WorkerList(getClient(), getSid());
		return workers.create(friendlyName, attributes, activitySid);
	}

	/**
	 * Retrieves a {@link com.twilio.sdk.resource.instance.taskrouter.Workflow} from a Workspace
	 */
	public Workflow getWorkflow(final String workflowSid) {
		Workflow workflow = new Workflow(getClient(), getSid(), workflowSid);
		return workflow;
	}

	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.WorkflowList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.WorkflowList}
	 */
	public WorkflowList getWorkflows() {
		WorkflowList workflows = new WorkflowList(getClient(), getSid());
		return workflows;
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.WorkflowList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.WorkflowList}
	 */
	public WorkflowList getWorkflows(final Map<String, String> filters) {
		WorkflowList workflows = new WorkflowList(getClient(), getSid(), filters);
		return workflows;
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Workflow}.
	 *
	 * @param params the params list
	 * @return a Workflow
	 * @throws com.twilio.sdk.TwilioRestException
	 */
	public Workflow createWorkflow(final Map<String, String> params) throws TwilioRestException {
		WorkflowList workflows = new WorkflowList(getClient(), getSid());
		return workflows.create(params);
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.Workflow}.
	 *
	 * @param params the params list
	 * @return a Workflow
	 * @throws TwilioRestException
	 */
	public Workflow createWorkflow(final List<NameValuePair> params) throws TwilioRestException {
		WorkflowList workflows = new WorkflowList(getClient(), getSid());
		return workflows.create(params);
	}

	/**
	 * Retrieves a {@link com.twilio.sdk.resource.instance.taskrouter.TaskQueue} from a Workspace
	 */
	public TaskQueue getTaskQueue(final String taskQueueSid) {
		TaskQueue taskQueue = new TaskQueue(getClient(), getSid(), taskQueueSid);
		return taskQueue;
	}

	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.TaskQueueList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.TaskQueueList}
	 */
	public TaskQueueList getTaskQueues() {
		TaskQueueList taskQueues = new TaskQueueList(getClient(), getSid());
		return taskQueues;
	}
	
	/**
	 * Retrieves the {@link com.twilio.sdk.resource.list.taskrouter.TaskQueueList} for this {@link
	 * com.twilio.sdk.resource.instance.taskrouter.Workspace}
	 *
	 * @return the {@link com.twilio.sdk.resource.list.taskrouter.TaskQueueList}
	 */
	public TaskQueueList getTaskQueues(final Map<String, String> filters) {
		TaskQueueList taskQueues = new TaskQueueList(getClient(), getSid(), filters);
		return taskQueues;
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.TaskQueue}.
	 *
	 * @param params the params list
	 * @return a TaskQueue
	 * @throws com.twilio.sdk.TwilioRestException
	 */
	public TaskQueue createTaskQueue(final Map<String, String> params) throws TwilioRestException {
		TaskQueueList taskQueues = new TaskQueueList(getClient(), getSid());
		return taskQueues.create(params);
	}

	/**
	 * Creates a {@link com.twilio.sdk.resource.instance.taskrouter.TaskQueue}.
	 *
	 * @param params the params list
	 * @return a TaskQueue
	 * @throws TwilioRestException
	 */
	public TaskQueue createTaskQueue(final List<NameValuePair> params) throws TwilioRestException {
		TaskQueueList taskQueues = new TaskQueueList(getClient(), getSid());
		return taskQueues.create(params);
	}
	
	/**
	 * Get workspace statistics.
	 * @return workspace statistics
	 */
	public WorkspaceStatistics getStatistics() {
		return getStatistics(new HashMap<String, String>());
	}
	
	/**
	 * Get workspace statistics.
	 *
	 * @param queryBuilder query builder which contains all parameters for the stats query request
	 * @return workspace statistics
	 */
	public WorkspaceStatistics getStatistics(final StatisticsQueryBuilder queryBuilder) {
		Map<String, String> filters = new HashMap<String, String>();
		Calendar startDate = queryBuilder.getStartDate();
		Calendar endDate = queryBuilder.getEndDate();
		Integer minutes = queryBuilder.getMinutes();
		if(startDate != null) {
			filters.put("StartDate", formatCalendar(startDate));
		}
		if(endDate != null) {
			filters.put("EndDate", formatCalendar(endDate));
		}
		if(minutes != null) {
			filters.put("Minutes", minutes.toString());
		}
		return getStatistics(filters);
	}

	/**
	 * Get workspace statistics.
	 *
	 * @param filters the filters
	 * @return workspace statistics
	 */
	public WorkspaceStatistics getStatistics(final Map<String, String> filters) {
		final String startDate = filters.get("StartDate");
		final String endDate = filters.get("EndDate");
		final String minutes = filters.get("Minutes");
		if((startDate != null || endDate != null) && minutes != null) {
			throw new IllegalArgumentException("Cannot provide Minutes in combination with StartDate or EndDate");
		}
		WorkspaceStatistics statistics = new WorkspaceStatistics(this.getClient(), this.getSid(), filters);
		return statistics;
	}

	@Override
	protected String getResourceLocation() {
		return "/" + TwilioTaskRouterClient.DEFAULT_VERSION + "/Workspaces/" + getSid();
	}
}
