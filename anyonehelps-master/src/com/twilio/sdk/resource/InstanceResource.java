package com.twilio.sdk.resource;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.http.NameValuePair;
import org.json.simple.JSONObject;

import com.twilio.sdk.TwilioClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

/**
 * The Class InstanceResource.
 */
public abstract class InstanceResource<C extends TwilioClient> extends Resource<C> {

	protected static final String DATE_CREATED_PROPERTY = "date_created";

	protected static final String DATE_UPDATED_PROPERTY = "date_updated";

    /** The Constant SID_PROPERTY. */
	protected static final String SID_PROPERTY = "sid";

	/** The Constant ACCOUNT_SID. */
	protected static final String ACCOUNT_SID_PROPERTY = "account_sid";

	/** The Constant for friendly name */
	protected static final String FRIENDLY_NAME_PROPERTY = "friendly_name";

	/** The properties. */
	private Map<String, Object> properties;

	/**
	 * Instantiates a new instance resource.
	 *
	 * @param client the client
	 */
	public InstanceResource(final C client) {
		this(client, null, null);
	}

	/**
	 * Instantiates a new instance resource.
	 *
	 * @param client the client
	 * @param properties the properties
	 */
	public InstanceResource(final C client, final Map<String, Object> properties) {
		this(client, properties, null);
	}

	/**
	 * Instantiates a new instance resource.
	 *
	 * @param client the client
	 * @param properties the properties
	 * @param filters the filters
	 */
	public InstanceResource(final C client, final Map<String, Object> properties, final Map<String, String> filters) {
		super(client);

		this.properties = properties == null ? new HashMap<String, Object>() : new HashMap<String, Object>(properties);
		this.filters = filters == null ? new HashMap<String, String>() : new HashMap<String, String>(filters);
		setLoaded(!this.properties.isEmpty());
	}

	private Object getAndLoadIfNecessary(final String name) {
		Object prop = properties.get(name);

		if (prop == null && !isLoaded()) {
			try {
				load(filters);
				return properties.get(name);
			} catch (TwilioRestException e) {
				throw new RuntimeException(e);
			}
		}
		return prop;
	}

	/**
	 * Gets the property.
	 *
	 * @param name the name
	 * @return the property,
	 * or null if it doesn't exist or is NULL in the response
	 */
	public String getProperty(String name) {
		Object prop = getAndLoadIfNecessary(name);

		if (prop == null) {
			return null;
		}

		if (prop instanceof String) {
			return (String) prop;
		}

		throw new IllegalArgumentException("Property " + name
				+ " is an object.  Use getObject() instead.");
	}

	/**
	 * Gets the property as an Object.
	 *
	 * @param name the name of the property
	 * @return the property as an Object
	 */
	public Object getObject(String name) {
		Object prop = getAndLoadIfNecessary(name);

		if (prop == null) {
			throw new IllegalArgumentException("Property " + name
					+ " does not exist");
		}

		return prop;
	}

	/**
	 * Gets property of Instance and attempts to cast to
	 * desired result
	 *
	 * @param name name of property
	 * @return casted property
	 */
	public <T> T getCastedObject(String name) {
		return (T) getAndLoadIfNecessary(name);
	}

	/**
	 * Gets the property as an Integer value.
	 *
	 * @param name property name
	 * @return Integer value
	 */
	public Integer getPropertyAsInteger(String name) {
		return (Integer) getObject(name);
	}

	/**
	 * Gets the property as a Date.
	 * @param name
	 * @return
	 */
	protected Date getDateProperty(String name) {
		return parseDate(getProperty(name));
	}


	/**
	 * Sets the property as an Object
	 *
	 * @param name the name
	 * @param value the value
	 */
	protected void setProperty(String name, Object value) {
		properties.put(name, value);
	}

	/**
	 * Update.
	 *
	 * @param params the params
	 * @throws TwilioRestException the twilio rest exception
	 */
	public void update(Map<String, String> params) throws TwilioRestException {
		this.getClient().safeRequest(this.getResourceLocation(), "POST", params);
	}

	/**
	 * Update.
	 *
	 * @param params the params list
	 * @throws TwilioRestException the twilio rest exception
	 */
	public void update(List<NameValuePair> params) throws TwilioRestException {
		this.getClient().safeRequest(this.getResourceLocation(), "POST", params);
	}

	/* (non-Javadoc)
	 * @see com.twilio.sdk.resource.Resource#parseResponse(com.twilio.sdk.TwilioRestResponse)
	 */
	@Override
	protected void parseResponse(TwilioRestResponse response) {
		Map<String, Object> properties = response.toMap();
		this.properties = new HashMap<String, Object>(properties);
	}
	
	/**
	 * Return a JSON representation of the properties of the object that are currently loaded
	 * 
	 * @return json of the properties of the object
	 */
	public String toJSON() {
		return JSONObject.toJSONString(properties);
	}

	/**
	 * return a date from the property string
	 *
	 * @return the date value of the input string
	 */
	protected Date parseDate(final String inDate) {
		return parseFormattedDate(DateFormatUtils.SMTP_DATETIME_FORMAT, inDate);
	}

	/**
	 * return a date from the property string (yyyy-MM-dd'T'HH:mm:ssZZ)
	 *
	 * @return the date value of the input string
	 */
	protected Date parseIsoDate(final String inDate) {
		return parseFormattedDate(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT, inDate);
	}

	/**
	 * return a date from the property string using the input date format
	 *
	 * @return the date value of the input string
	 */
	protected Date parseFormattedDate(final FastDateFormat inDateFormat, final String inDate) {
		if (inDateFormat == null || inDate == null) {
			return null;
		}
		try {
			return inDateFormat.parse(inDate);
		} catch (ParseException e) {
			return null;
		}
	}
}
