package com.hp.score.lang.compiler;
/*
 * Licensed to Hewlett-Packard Development Company, L.P. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/

/*
 * Created by orius123 on 05/11/14.
 */

import com.hp.score.api.ExecutionPlan;
import com.hp.score.api.ExecutionStep;
import com.hp.score.lang.compiler.configuration.SpringConfiguration;
import com.hp.score.lang.entities.CompilationArtifact;
import com.hp.score.lang.entities.ScoreLangConstants;
import com.hp.score.lang.entities.bindings.Input;
import com.hp.score.lang.entities.bindings.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class CompileBasicFlowTest {

    @Autowired
    private SlangCompiler compiler;

    @Test
    public void testCompileFlowBasic() throws Exception {
        URI flow = getClass().getResource("/flow.yaml").toURI();
        URI operation = getClass().getResource("/operation.yaml").toURI();

        List<File> path = new ArrayList<>();
        path.add(new File(operation));

        CompilationArtifact compilationArtifact = compiler.compileFlow(new File(flow), path);
        ExecutionPlan executionPlan = compilationArtifact.getExecutionPlan();
        Assert.assertNotNull("execution plan is null", executionPlan);
        Assert.assertEquals("there is a different number of steps than expected", 4, executionPlan.getSteps().size());
        Assert.assertEquals("execution plan name is different than expected", "basic_flow", executionPlan.getName());
        Assert.assertEquals("the dependencies size is not as expected", 2, compilationArtifact.getDependencies().size());
    }

    @Test
    public void testCompileFlowWithData() throws Exception {
        URI flow = getClass().getResource("/flow_with_data.yaml").toURI();
        URI operation = getClass().getResource("/operation.yaml").toURI();

        List<File> path = new ArrayList<>();
        path.add(new File(operation));

        CompilationArtifact compilationArtifact = compiler.compileFlow(new File(flow), path);
        ExecutionPlan executionPlan = compilationArtifact.getExecutionPlan();
        Assert.assertNotNull("execution plan is null", executionPlan);
        Assert.assertEquals("there is a different number of steps than expected", 4, executionPlan.getSteps().size());
        Assert.assertEquals("execution plan name is different than expected", "SimpleFlow", executionPlan.getName());
        Assert.assertEquals("the dependencies size is not as expected", 2, compilationArtifact.getDependencies().size());

        ExecutionStep startStep = executionPlan.getStep(1L);
        @SuppressWarnings("unchecked") List<Input> inputs = (List<Input>) startStep.getActionData().get(ScoreLangConstants.OPERATION_INPUTS_KEY);
        Assert.assertNotNull("inputs doesn't exist", inputs);
        Assert.assertEquals("there is a different number of inputs than expected", 1, inputs.size());

        ExecutionStep beginTaskStep = executionPlan.getStep(2L);
        @SuppressWarnings("unchecked") List<Input> taskArguments = (List<Input>) beginTaskStep.getActionData().get(ScoreLangConstants.TASK_INPUTS_KEY);
        Assert.assertNotNull("arguments doesn't exist", taskArguments);
        Assert.assertEquals("there is a different number of arguments than expected", 1, taskArguments.size());

        ExecutionStep FinishTaskSteps = executionPlan.getStep(3L);
        Object publish = FinishTaskSteps.getActionData().get(ScoreLangConstants.TASK_PUBLISH_KEY); //todo test
        @SuppressWarnings("unchecked") Map<String, String> navigate = (Map<String, String>) FinishTaskSteps.getActionData().get(ScoreLangConstants.TASK_NAVIGATION_KEY);

        Assert.assertNotNull("publish don't exist", publish);
        Assert.assertNotNull("navigate don't exist", navigate);
        Assert.assertEquals("there is a different number of navigation values than expected", 2, navigate.size());


        ExecutionStep endStep = executionPlan.getStep(0L);
        Object outputs = endStep.getActionData().get(ScoreLangConstants.EXECUTABLE_OUTPUTS_KEY); //todo test
        @SuppressWarnings("unchecked") List<Result> results = (List<Result>) endStep.getActionData().get(ScoreLangConstants.EXECUTABLE_RESULTS_KEY);

        Assert.assertNotNull("outputs don't exist", outputs);
        Assert.assertEquals("there is a different number of results values than expected", 1, results.size());
    }

}