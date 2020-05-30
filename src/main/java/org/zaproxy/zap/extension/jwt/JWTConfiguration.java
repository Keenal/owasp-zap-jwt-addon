/*
 * Zed Attack Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Copyright 2019 The ZAP Development Team
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
package org.zaproxy.zap.extension.jwt;

import org.apache.log4j.Logger;
import org.parosproxy.paros.common.AbstractParam;

/**
 * This class holds configuration related to JWT scanner.
 *
 * @author KSASAN preetkaran20@gmail.com
 * @since TODO add version
 */
public class JWTConfiguration extends AbstractParam {

    protected static final Logger LOGGER = Logger.getLogger(JWTExtension.class);

    /** The base configuration key for all JWT configurations. */
    private static final String PARAM_BASE_KEY = "jwt";

    private static final String PARAM_TRUST_STORE_PATH = PARAM_BASE_KEY + ".trustStorePath";
    private static final String PARAM_TRUST_STORE_PASSWORD = PARAM_BASE_KEY + ".trustStorePassword";
    private static final String PARAM_ENABLE_CLIENT_CONFIGURATION_SCAN =
            PARAM_BASE_KEY + ".enableClientConfigurationScan";

    private String trustStorePath;
    private String trustStorePassword;

    /** Fuzzer settings. Not storing private keys because of security concerns. */
    private String jwtRsaPrivateKeyFileChooserPath;

    private char[] jwtHMacSignatureKey;

    private boolean enableClientConfigurationScan;
    private static volatile JWTConfiguration jwtConfiguration;

    private JWTConfiguration() {}

    public static JWTConfiguration getInstance() {
        if (jwtConfiguration == null) {
            synchronized (JWTConfiguration.class) {
                if (jwtConfiguration == null) {
                    jwtConfiguration = new JWTConfiguration();
                }
            }
        }
        return jwtConfiguration;
    }

    public String getTrustStorePath() {
        return trustStorePath;
    }

    public void setTrustStorePath(String trustStorePath) {
        this.trustStorePath = trustStorePath;
        this.getConfig().setProperty(PARAM_TRUST_STORE_PATH, trustStorePath);
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
        this.getConfig().setProperty(PARAM_TRUST_STORE_PASSWORD, trustStorePassword);
    }

    public String getJwtRsaPrivateKeyFileChooserPath() {
        return jwtRsaPrivateKeyFileChooserPath;
    }

    public void setJwtRsaPrivateKeyFileChooserPath(String jwtRsaPrivateKeyFileChooserPath) {
        this.jwtRsaPrivateKeyFileChooserPath = jwtRsaPrivateKeyFileChooserPath;
    }

    public void setJwtHMacSignatureKey(char[] jwtHMacSignatureKey) {
        this.jwtHMacSignatureKey = jwtHMacSignatureKey;
    }

    public char[] getJwtHMacSignatureKey() {
        return jwtHMacSignatureKey;
    }

    public boolean isEnableClientConfigurationScan() {
        return enableClientConfigurationScan;
    }

    public void setEnableClientConfigurationScan(boolean enableClientSideConfiguration) {
        this.enableClientConfigurationScan = enableClientSideConfiguration;
        this.getConfig()
                .setProperty(PARAM_ENABLE_CLIENT_CONFIGURATION_SCAN, enableClientSideConfiguration);
    }

    @Override
    protected void parse() {
        this.setTrustStorePath(getConfig().getString(PARAM_TRUST_STORE_PATH));
        this.setTrustStorePassword(getConfig().getString(PARAM_TRUST_STORE_PASSWORD));
        this.setEnableClientConfigurationScan(
                getBoolean(PARAM_ENABLE_CLIENT_CONFIGURATION_SCAN, false));
    }
}
