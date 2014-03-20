/*
 * Copyright (C) 2014 Read It Later Inc. (Pocket)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pocket.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.preview.support.wearable.notifications.WearableNotifications.Builder;

import com.pocket.wear.util.R;

/**
 * Helper methods for adding a "Add to Pocket" option to your Android Wear notifications.
 * <p>
 * Simply invoke {@link #addPocketAction(Context, android.preview.support.wearable.notifications.WearableNotifications.Builder, String, String)}.
 */
public class PocketWearableNotificationUtil {
	
	/**
	 * If the Pocket app is installed on this device, this will add a "Add to Pocket" action to the {@link Builder}.
	 * <p>
	 * If Pocket isn't installed, this will simply do nothing.
	 * 
	 * @param builder The builder to add the action to.
	 * @param notificationId The notification id that will later be used to dismiss the notification if they tap this action.
	 * @param url The url (starting with http:// or https://) that should be added to Pocket when the user taps the action.
	 * @param tweetStatusId Optional. If the url is saved from a tweet, pass its status id to attribute the save to that tweet. Otherwise pass null.
	 * @param context
	 */
	public static void addPocketAction(WearableNotifications.Builder builder, int notificationId, String url, String tweetStatusId, Context context) {
		if (!PocketUtil.isPocketInstalled(context)) {
			return; // Pocket isn't available so don't add as an option.
		}
		
		Intent intent = new Intent(PocketIntentReceiver.ACTION)
		 	.setClass(context, PocketIntentReceiver.class);
		intent.putExtra(PocketIntentReceiver.EXTRA_ADD_INTENT, PocketUtil.newAddToPocketIntent(url, tweetStatusId, context));
		intent.putExtra(PocketIntentReceiver.EXTRA_NOTIFICATION_ID, notificationId);
 
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		WearableNotifications.Action action =  new WearableNotifications.Action.Builder(
	             R.drawable.ic_pocket,
	             context.getString(R.string.add_to_pocket),
	             pendingIntent)
				.build();
		
		builder.addAction(action);
	}
	
}
