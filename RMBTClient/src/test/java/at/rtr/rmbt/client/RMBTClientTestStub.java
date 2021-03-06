/*******************************************************************************
 * Copyright 2020 Rundfunk und Telekom Regulierungs-GmbH (RTR-GmbH)
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
 ******************************************************************************/

package at.rtr.rmbt.client;

import at.rtr.rmbt.client.helper.ControlServerConnection;
import at.rtr.rmbt.client.v2.task.TaskDesc;

import java.util.List;

public class RMBTClientTestStub extends RMBTClient {

    //constructor used for unit testing
    public static RMBTClient getInstance(final List<TaskDesc> taskDescList, final String collectorUrl) {
        //used for unit testing
        final ControlServerConnection controlConnection = new ControlServerConnection();
        final RMBTTestParameter params = controlConnection.getTestParameter(null);


        RMBTClient rmbtClient = new RMBTClientTestStub(params, controlConnection);
        rmbtClient.taskDescList = taskDescList;
        return rmbtClient;
    }


    RMBTClientTestStub(RMBTTestParameter params, ControlServerConnection controlConnection) {
        super(params, controlConnection, true);
    }
}
