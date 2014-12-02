/*
 * Licensed to Hewlett-Packard Development Company, L.P. under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.hp.score.lang.entities;

import java.io.Serializable;

/**
 * @author moradi
 * @since 26/11/2014
 * @version $Id$
 */
public class ResultNavigation implements Serializable {

	private final long nextStepId;
	private final String presetResult;

    public ResultNavigation(long nextStepId, String presetResult) {
        this.nextStepId = nextStepId;
        this.presetResult = presetResult;
    }

    public long getNextStepId() {
		return this.nextStepId;
	}

	public String getPresetResult() {
		return this.presetResult;
	}
}