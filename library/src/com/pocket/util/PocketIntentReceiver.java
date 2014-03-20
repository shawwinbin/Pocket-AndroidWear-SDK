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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preview.support.v4.app.NotificationManagerCompat;

/**
 * Broadcast receiver to respond to an Add to Pocket action from a Notification created with {@link PocketWearableNotificationUtil}.
 */
public class PocketIntentReceiver extends BroadcastReceiver {
    
	public static final String ACTION = "com.pocket.wear.ACTION_ADD_TO_POCKET";
	public static final String EXTRA_NOTIFICATION_ID = "com.pocket.wear.EXTRA_NOTIFICATION_ID";
	public static final String EXTRA_ADD_INTENT = "com.pocket.wear.EXTRA_ADD_INTENT";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
        	// Dismiss the notification
            int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);
            NotificationManagerCompat.from(context).cancel(notificationId);
            
            // Save to Pocket
            Intent addIntent = (Intent) intent.getParcelableExtra(EXTRA_ADD_INTENT);
            addIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(addIntent);
        }
    }
}
