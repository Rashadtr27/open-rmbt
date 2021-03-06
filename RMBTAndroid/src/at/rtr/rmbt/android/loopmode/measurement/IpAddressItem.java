/*******************************************************************************
 * Copyright 2016 Specure GmbH
 * Copyright 2016 Rundfunk und Telekom Regulierungs-GmbH (RTR-GmbH)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package at.rtr.rmbt.android.loopmode.measurement;

import android.content.Context;
import at.alladin.rmbt.android.R;
import at.rtr.rmbt.android.loopmode.DetailsListItem;
import at.rtr.rmbt.android.loopmode.LoopModeResults;

/**
 * provides information about the client's IP address during a test
 * @author lb
 *
 */
public class IpAddressItem implements DetailsListItem {

	final LoopModeResults results;
	final Context context;
	
	public IpAddressItem(final Context context, final LoopModeResults results) {
		this.results = results;
		this.context = context;
	}
	
	@Override
	public String getTitle() {
		return context.getResources().getString(R.string.lm_measurement_ip);
	}

	@Override
	public String getCurrent() {
		if (results != null && results.getCurrentTest() != null) {
			return results.getCurrentTest().getIp();
		}
		
		return null;
	}

	@Override
	public int getStatusResource() {
		return DetailsListItem.STATUS_RESOURCE_NOT_AVAILABLE;
	}

}
