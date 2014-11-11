package com.hp.score.lang.compiler.utils;
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
import com.hp.score.lang.compiler.domain.SlangFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;

@Component
public class YamlParser {

    @Autowired
    private Yaml yaml;

    public SlangFile loadSlangFile(File source) {
        SlangFile slangFile;
        try (FileInputStream is = new FileInputStream(source)) {
            slangFile = yaml.loadAs(is, SlangFile.class);
        } catch (java.io.IOException e) {
            throw new RuntimeException("There was a problem parsing the yaml file syntax for some reason", e);
        }
        return slangFile;
    }

}