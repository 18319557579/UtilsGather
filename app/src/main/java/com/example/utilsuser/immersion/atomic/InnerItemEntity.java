/*
 * Copyright (C)  Justson(https://github.com/Justson/AgentWeb)
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

package com.example.utilsuser.immersion.atomic;

import java.io.Serializable;

/**
 * @author cenxiaozhong
 * @date 2018/7/15
 * @since 1.0.0
 */
public class InnerItemEntity implements Serializable {

	private String guideTitle;
	private DoAction doAction;

	public InnerItemEntity(String guideTitle, DoAction doAction) {
		this.guideTitle = guideTitle;
		this.doAction = doAction;
	}

	public String getGuideTitle() {
		return guideTitle;
	}

	public void setGuideTitle(String guideTitle) {
		this.guideTitle = guideTitle;
	}

	public DoAction getDoAction() {
		return doAction;
	}

	public void setDoAction(DoAction doAction) {
		this.doAction = doAction;
	}
}
