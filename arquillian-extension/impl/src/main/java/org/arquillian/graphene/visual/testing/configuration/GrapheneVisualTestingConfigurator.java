/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.graphene.visual.testing.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.arquillian.graphene.visual.testing.impl.DiffsUtils;

import org.arquillian.recorder.reporter.event.ReportingExtensionConfigured;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.rusheye.arquillian.event.FailedTestsCollection;
import org.jboss.rusheye.arquillian.event.VisuallyUnstableTestsCollection;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 *
 */
public class GrapheneVisualTestingConfigurator {

    private static final Logger logger = Logger.getLogger(GrapheneVisualTestingConfigurator.class.getSimpleName());
    
    private static final String EXTENSION_NAME = "visual-testing";

    @Inject
    @ApplicationScoped
    private InstanceProducer<GrapheneVisualTestingConfiguration> configuration;
    
    @Inject
    @ApplicationScoped
    private InstanceProducer<FailedTestsCollection> failedTestsCollection;
    
    @Inject
    @ApplicationScoped
    private InstanceProducer<VisuallyUnstableTestsCollection> visuallyUnstableTestsCollection;
    
    @Inject
    @ApplicationScoped
    private InstanceProducer<DiffsUtils> diffsUtils;

    @Inject
    private Event<GrapheneVisualTestingExtensionConfigured> extensionConfiguredEvent;

    public void configureExtension(@Observes ReportingExtensionConfigured event, ArquillianDescriptor descriptor) {
        GrapheneVisualTestingConfiguration conf = new GrapheneVisualTestingConfiguration();

        for (ExtensionDef extension : descriptor.getExtensions()) {
            if (extension.getExtensionName().equals(EXTENSION_NAME)) {
                conf.setConfiguration(extension.getExtensionProperties());
                conf.validate();
                break;
            }
        }

        this.configuration.set(conf);
        this.failedTestsCollection.set(new FailedTestsCollection());
        this.visuallyUnstableTestsCollection.set(new VisuallyUnstableTestsCollection());
        this.diffsUtils.set(new DiffsUtils(false));

        if (logger.isLoggable(Level.INFO)) {
            System.out.println("Configuration of Arquillian Graphene Visual Testing:");
            System.out.println(this.configuration.get().toString());
        }

        extensionConfiguredEvent.fire(new GrapheneVisualTestingExtensionConfigured());
    }
}