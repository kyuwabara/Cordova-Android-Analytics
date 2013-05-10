/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2006-2011 Worklight, Ltd. 
 * Upgraded by Doers' Guild  
 */

package com.phonegap.plugins.analytics;

import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

import android.util.Log;

public class GoogleAnalyticsTracker extends CordovaPlugin {
	public static final String START = "start";
	public static final String STOP = "stop";
	public static final String TRACK_PAGE_VIEW = "trackPageView";
	public static final String TRACK_EVENT = "trackEvent";
	public static final String SET_CUSTOM_DIMENSION = "setCustomDimension";
	public static final String TRACK_TIMING = "trackTiming";

	private Tracker tracker;
	private com.google.analytics.tracking.android.EasyTracker instance;

	public GoogleAnalyticsTracker() {
		instance = com.google.analytics.tracking.android.EasyTracker
				.getInstance();
	}

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		if (START.equals(action)) {
			start(callbackContext);

		} else if (TRACK_PAGE_VIEW.equals(action)) {
			trackPageView(data, callbackContext);

		} else if (TRACK_EVENT.equals(action)) {
			trackEvent(data, callbackContext);

		} else if (STOP.equals(action)) {
			stop(callbackContext);

		} else if (SET_CUSTOM_DIMENSION.equals(action)) {
			setCustomDimension(data, callbackContext);

		} else if (TRACK_TIMING.equals(action)) {
			trackTiming(data, callbackContext);

		} else {
			// invalid action
			return false;
		}

		return true;
	}

	private void start(CallbackContext callbackContext) {
		instance.activityStart(this.cordova.getActivity());
		tracker = EasyTracker.getTracker();
		callbackContext.success();
	}

	private void stop(CallbackContext callbackContext) {
		instance.activityStop(this.cordova.getActivity());
		callbackContext.success();
	}

	private void trackPageView(JSONArray data, CallbackContext callbackContext) throws JSONException {
		try {
			tracker.sendView(data.getString(0));
			callbackContext.success();
		} catch (JSONException e) {
			callbackContext.error(e.getMessage());
		}
	}

	private void trackEvent(JSONArray data, CallbackContext callbackContext) throws JSONException {
		try {
			tracker.sendEvent(data.getString(0), data.getString(1),
					data.getString(2), data.getLong(3));
			callbackContext.success();
		} catch (JSONException e) {
			callbackContext.error(e.getMessage());
		}
	}

	private void trackTiming(JSONArray data, CallbackContext callbackContext) throws JSONException {
		try {
			tracker.sendTiming(data.getString(0), data.getLong(1),
					data.getString(2), data.getString(3));
			callbackContext.success();
		} catch (JSONException e) {
			callbackContext.error(e.getMessage());
		}
	}

	private void setCustomDimension(JSONArray data, CallbackContext callbackContext) throws JSONException {
		try {
			tracker.setCustomDimension(data.getInt(0), data.getString(1));
			callbackContext.success();
		} catch (JSONException e) {
			callbackContext.error(e.getMessage());
		}
	}

}
