/*
 * Copyright (C) 2015 The Nevolution Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oasisfeng.nevo.decorators;

import android.graphics.drawable.Icon;

import com.oasisfeng.nevo.sdk.MutableNotification;
import com.oasisfeng.nevo.sdk.MutableStatusBarNotification;
import com.oasisfeng.nevo.sdk.NevoDecoratorService;
import com.oasisfeng.nevo.util.IconCache;

/**
 * Make notification icon transparency
 *
 * @author TimothyZhang023
 */
public class TransparentIconDecorator extends NevoDecoratorService {

	@Override public boolean apply(final MutableStatusBarNotification evolved) {
		final MutableNotification n = evolved.getNotification();
		String packageName = evolved.getPackageName();

		Icon iconCache = IconCache.getInstance().getIconCache(this, packageName);
		if (iconCache != null) {
			n.setSmallIcon(iconCache);
		}

		return true;
	}


}
