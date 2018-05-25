/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.springboot.support.fat;

import java.net.SocketException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import componenttest.annotation.AllowedFFDC;
import componenttest.custom.junit.runner.FATRunner;

@RunWith(FATRunner.class)
public class SSLMutualAuthTests15 extends SSLCommonTests {

    @Override
    public Set<String> getFeatures() {
        return new HashSet<>(Arrays.asList("springBoot-1.5", "servlet-3.1", "transportSecurity-1.0"));
    }

    @Override
    public String getApplication() {
        return SPRING_BOOT_15_APP_BASE;
    }

    @After
    public void stopTestServer() throws Exception {
        super.stopServer(true);
    }

    /* Passes when application property server.ssl.client-auth=NEED and if client side keystore and truststore are provided for authentication. */
    @Test
    public void testClientAuthNeedWithClientSideKeyStoreFor15() throws Exception {
        testSSLApplication();
    }

    /*
     * Fails when application property server.ssl.client-auth=NEED and if client side keystore and truststore are not provided for authentication .
     * This scenario throws a Socket Exception
     */
    @Test
    @AllowedFFDC({ "java.net.SocketException" })
    public void testClientAuthNeedWithoutClientSideKeyStoreFor15() throws Exception {
        try {
            testSSLApplication();
            Assert.fail("The connection should not succeed");
        } catch (SocketException e) {

        }
    }

    /* Passes when application property server.ssl.client-auth=WANT and even if client side keystore and truststore are not provided for authentication. */
    @Test
    public void testClientAuthWantWithoutClientSideKeyStoreFor15() throws Exception {
        testSSLApplication();
    }
}
