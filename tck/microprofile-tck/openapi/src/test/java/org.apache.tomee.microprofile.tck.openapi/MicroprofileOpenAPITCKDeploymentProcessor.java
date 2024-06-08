/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomee.microprofile.tck.openapi;

import java.util.Map;

import org.apache.ziplock.JarLocation;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.event.container.BeforeDeploy;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class MicroprofileOpenAPITCKDeploymentProcessor {

    public void observeDeployment(@Observes final BeforeDeploy beforeDeploy) {
        DeploymentDescription deployment = beforeDeploy.getDeployment();
        Archive<?> testableArchive = deployment.getTestableArchive();
        if (testableArchive != null) {
            process(testableArchive);
        } else {
            process(deployment.getArchive());
        }
    }

    private void process(Archive<?> archive) {
        if (archive instanceof WebArchive webapp) {
            webapp.addAsLibrary(JarLocation.jarLocation(Matchers.class)) // required for ModelConstructionTest
                    .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        }
    }

}