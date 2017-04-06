package com.twilio.sdk.resource.instance.taskrouter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.twilio.sdk.TwilioTaskRouterClient;
import com.twilio.sdk.resource.NextGenInstanceResource;
import com.twilio.sdk.taskrouter.WorkflowConfiguration;

/**
 * Workflows control how tasks will be prioritized and routed into Queues, and how Tasks should escalate in priority or
 * move across queues over time. Workflows are described in a simple JSON format.
 *
 * See <a href="https://www.twilio.com/docs/taskrouter/workflows">the TaskRouter documentation</a>.
 */
public class Workflow extends NextGenInstanceResource<TwilioTaskRouterClient> {

	private static final String WORKSPACE_SID_PROPERTY = "workspace_sid";

	/**
	 * Instantiates a workflow.
	 *
	 * @param client the client
	 */
	public Workflow(final TwilioTaskRouterClient client) {
		super(client);
	}

	/**
	 * Instantiates a workflow.
	 *
	 * @param client the client
	 * @param properties the properties
	 */
	public Workflow(final TwilioTaskRouterClient client, final Map<String, Object> properties) {
		super(client, properties);
	}

	/**
	 * Instantiates a workflow.
	 *
	 * @param client the client
	 * @param workspaceSid the workspace sid
	 * @param workflowSid the workflow sid
	 */
	public Workflow(final TwilioTaskRouterClient client, final String workspaceSid, final String workflowSid) {
		super(client);
		if (StringUtils.isBlank(workspaceSid)) {
			throw new IllegalArgumentException("The workspaceSid for an Workflow cannot be null");
		}
		if (StringUtils.isBlank(workflowSid)) {
			throw new IllegalArgumentException("The workflowSid for an Workflow cannot be null");
		}
		setProperty(WORKSPACE_SID_PROPERTY, workspaceSid);
		setProperty(SID_PROPERTY, workflowSid);
	}

	/**
	 * The URL that will be called whenever a task managed by this Workflow is assigned to a Worker.
	 *
	 * @return the assignment callback url
	 */
	public String getAssignmentCallbackUrl() {
		return getProperty("assignment_callback_url");
	}

	/**
	 * JSON document configuring the rules for this Workflow.
	 *
	 * @return the configuration
	 */
	public String getConfiguration() {
		return getProperty("configuration");
	}
	
	/**
	 * WorkflowConfiguration object representing this Workflow
	 *
	 * @return the configuration
	 * @throws IOException 
	 */
	public WorkflowConfiguration parseConfiguration() throws IOException {
		String configurationJSON = getProperty("configuration");
		return WorkflowConfiguration.parse(configurationJSON);
	}

	/**
	 * The date and time this Workflow was created.
	 *
	 * @return the date created
	 */
	public Date getDateCreated() {
		return parseIsoDate(getProperty(DATE_CREATED_PROPERTY));
	}

	/**
	 * The date and time this Workflow was last updated.
	 *
	 * @return the date updated
	 */
	public Date getDateUpdated() {
		return parseIsoDate(getProperty(DATE_UPDATED_PROPERTY));
	}

	/**
	 * If the request to the AssignmentCallbackUrl fails, the assignment callback will be made to this URL.
	 *
	 * @return the fallback assignment callback url
	 */
	public String getFallbackAssignmentCallbackUrl() {
		return getProperty("fallback_assignment_callback_url");
	}

	/**
	 * A human-readable description of this Workflow.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
		return getProperty("friendly_name");
	}

	/**
	 * This Workflow's unique ID.
	 *
	 * @return the sid
	 */
	public String getSid() {
		return getProperty(SID_PROPERTY);
	}

	/**
	 * Determines how long TaskRouter will wait for a confirmation response from your application after assigning a Task
	 * to a worker. Defaults to 120 seconds.
	 *
	 * @return the task reservation timeout
	 */
	public Integer getTaskReservationTimeout() {
		return (Integer) getObject("task_reservation_timeout");
	}

	/**
	 * The unique ID of the {@link com.twilio.sdk.resource.instance.taskrouter.Workspace} containing this Workflow.
	 *
	 * @return the workspace sid
	 */
	public String getWorkspaceSid() {
		return getProperty(WORKSPACE_SID_PROPERTY);
	}
	
	/**
	 * Get workflow statistics.
	 * @return workflow statistics
	 */
	public WorkflowStatistics getStatistics() {
		return getStatistics(new HashMap<String, String>());
	}
	
	/**
	 * Get workflow statistics.
	 *
	 * @param queryBuilder query builder which contains all parameters for the stats query request
	 * @return workflow statistics
	 */
	public WorkflowStatistics getStatistics(final StatisticsQueryBuilder queryBuilder) {
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
	 * Get workflow statistics.
	 *
	 * @param filters the filters
	 * @return workflow statistics
	 */
	public WorkflowStatistics getStatistics(final Map<String, String> filters) {
		final String startDate = filters.get("StartDate");
		final String endDate = filters.get("EndDate");
		final String minutes = filters.get("Minutes");
		if((startDate != null || endDate != null) && minutes != null) {
			throw new IllegalArgumentException("Cannot provide Minutes in combination with StartDate or EndDate");
		}
		WorkflowStatistics statistics = new WorkflowStatistics(this.getClient(), this.getWorkspaceSid(), this.getSid(), filters);
		return statistics;
	}

	@Override
	protected String getResourceLocation() {
		return "/" + TwilioTaskRouterClient.DEFAULT_VERSION + "/Workspaces/" + getWorkspaceSid() + "/Workflows/" +
		       getSid();
	}
}
