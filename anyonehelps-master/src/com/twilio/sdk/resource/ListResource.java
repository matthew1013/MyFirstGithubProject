package com.twilio.sdk.resource;

import com.twilio.sdk.TwilioClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;
import com.twilio.sdk.parser.ResponseParser.PagingProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
public abstract class ListResource<T extends Resource, C extends TwilioClient> extends Resource<C> implements Iterable<T> {

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return new ListIterator(getPageData().iterator());
	}

	/**
	 * Instantiates a new list resource.
	 *
	 * @param client the client
	 */
	public ListResource(final C client) {
		this(client, new HashMap<String, String>());
	}

	/**
	 * Instantiates a new list resource.
	 *
	 * @param client the client
	 * @param filters the filters
	 */
	public ListResource(final C client, Map<String, String> filters) {
		super(client);
		this.filters = filters;
	}

	/** The page data. */
	protected List<T> pageData;

	/** The next uri. */
	private String nextUri = null;

	/** The start. */
	private int start = 0;

	/** The end. */
	private int end = 0;

	/** The page. */
	private int page = 0;

	/**
	 * Gets the next uri.
	 *
	 * @return the next uri
	 */
	public String getNextUri() {
		return nextUri;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Checks for next page.
	 *
	 * @return true, if successful
	 */
	protected boolean hasNextPage() {
		return nextUri != null && this.nextUri.length() > 0;
	}

	/**
	 * Fetch next page.
	 *
	 * @throws TwilioRestException the twilio rest exception
	 */
	protected void fetchNextPage() throws TwilioRestException {
		// Fetch the next page and reset accounts
		TwilioRestResponse response = this.getClient().get(nextUri);
		this.parseResponse(response);
	}

	/**
	 * Gets the page data.
	 *
	 * @return the page data
	 */
	public List<T> getPageData() {
		if (!this.isLoaded()) {
			try {
				this.load(this.filters);
			} catch (TwilioRestException e) {
				throw new RuntimeException(e);
			}
		}

		return Collections.unmodifiableList(this.pageData);
	}

	/* (non-Javadoc)
	 * @see com.twilio.sdk.resource.Resource#parseResponse(com.twilio.sdk.TwilioRestResponse)
	 */
	@Override
	protected void parseResponse(TwilioRestResponse response) {
		this.nextUri = null;
		// Setup paging
		Map<String, Object> data = response.toMap();
		this.nextUri = (String) data.get(response.getParser()
				.getPagingPropertyKey(PagingProperty.NEXT_PAGE_URI_KEY));

		this.start = this.getIntValue(data.get(response.getParser()
				.getPagingPropertyKey(PagingProperty.START_KEY)));
		this.end = this.getIntValue(data.get(response.getParser()
				.getPagingPropertyKey(PagingProperty.END_KEY)));
		this.page = this.getIntValue(data.get(response.getParser()
				.getPagingPropertyKey(PagingProperty.PAGE_KEY)));

		// Setup data
		this.pageData = this.toList(response);
	}

	/**
	 * Gets the int value.
	 *
	 * @param data the data
	 * @return the int value
	 */
	private int getIntValue(Object data) {
		if (data instanceof Integer) {
			return (Integer) data;
		}
		if (data instanceof String) {
			return Integer.parseInt((String) data);
		}

		return -1;
	}

	/**
	 * Create a new object of type T. Since we cannot construct new T() on a
	 * generic T we need to create a correctly typed object at runtime via this
	 * method call.
	 *
	 * @param client the client
	 * @param params the params
	 * @return a fully constructed object of type T
	 */
	protected abstract T makeNew(C client,
			Map<String, Object> params);

	/**
	 * Returns the string key for finding this list of objects in the response.
	 * For example:
	 *
	 * <TwilioResponse> <Accounts> <Account> </Account> <Account> </Account>
	 * </Accounts> </TwilioResponse>
	 *
	 * this should return "Accounts"
	 *
	 * @return the string key for finding this list objects in the response
	 */
	protected abstract String getListKey();

	/**
	 * To list.
	 *
	 * @param response the response
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	protected List<T> toList(TwilioRestResponse response) {
		List<T> returnList = new ArrayList<T>();

		// Right now only json responses are used
		Map<String, Object> list = response.toMap();
        Object content = list.get(this.getListKey());
		if (content instanceof List) {
			List<Object> objs = (List<Object>) list.get(this.getListKey());

			for (Object o : objs) {
                extract_object(returnList, o);
            }
		}
        else if (content instanceof Map) { /* Some filters on lists returns only one element, this makes the response consistent */
            extract_object(returnList, ((Map) content).values().iterator().next());
        }

		return returnList;
	}

    private void extract_object(List<T> returnList, Object o) {
        if (o instanceof Map) {
            T instance = this.makeNew(this.getClient(), (Map<String, Object>) o);
            if(instance.getRequestAccountSid() == null){
              //Only set RequestAccountSid if the makeNew instance didn't already set it.
              instance.setRequestAccountSid(this.getRequestAccountSid());
            }
            returnList.add(instance);
        }
    }

	private class ListIterator implements Iterator<T> {
		private Iterator<T> iterator;

		public ListIterator(Iterator<T> iterator) {
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public T next() {
			T nextElement = iterator.next();

			if (!iterator.hasNext() && hasNextPage()) {
				try {
					fetchNextPage();
				} catch (TwilioRestException e) {
					throw new RuntimeException(e);
				}

				iterator = pageData.iterator();
			}

			return nextElement;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
