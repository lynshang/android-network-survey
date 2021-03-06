package com.craxiom.networksurvey;

import com.craxiom.networksurvey.mqtt.MqttBrokerConnectionInfo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link com.craxiom.networksurvey.mqtt.MqttBrokerConnectionInfo} class.
 *
 * @since 0.1.3
 */
public class MqttConnectionInfoTest
{
    @Test
    public void validateTlsEnableMqttConnectionUri()
    {
        final String host = "mqtt.example.com";
        final int port = 8883;
        final boolean tlsEnabled = true;
        final String clientId = "Pixel3a";
        final String username = "bob";
        final String password = "bob's password";

        final MqttBrokerConnectionInfo mqttBrokerConnectionInfo = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, password, true, true, true);

        assertEquals("ssl://mqtt.example.com:8883", mqttBrokerConnectionInfo.getMqttServerUri());
        assertEquals(clientId, mqttBrokerConnectionInfo.getMqttClientId());
        assertEquals(username, mqttBrokerConnectionInfo.getMqttUsername());
        assertEquals(password, mqttBrokerConnectionInfo.getMqttPassword());
        assertTrue(mqttBrokerConnectionInfo.isCellularStreamEnabled());
        assertTrue(mqttBrokerConnectionInfo.isWifiStreamEnabled());
        assertTrue(mqttBrokerConnectionInfo.isGnssStreamEnabled());
    }

    @Test
    public void validatePlaintextMqttConnectionUri()
    {
        final String host = "mqtt.example.com";
        final int port = 1883;
        final boolean tlsEnabled = false;
        final String clientId = "Pixel3a";
        final String username = "bob";
        final String password = "bob's password";

        final MqttBrokerConnectionInfo mqttBrokerConnectionInfo = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, password, true, true, true);

        assertEquals("tcp://mqtt.example.com:1883", mqttBrokerConnectionInfo.getMqttServerUri());
        assertEquals(clientId, mqttBrokerConnectionInfo.getMqttClientId());
        assertEquals(username, mqttBrokerConnectionInfo.getMqttUsername());
        assertEquals(password, mqttBrokerConnectionInfo.getMqttPassword());
        assertTrue(mqttBrokerConnectionInfo.isCellularStreamEnabled());
        assertTrue(mqttBrokerConnectionInfo.isWifiStreamEnabled());
        assertTrue(mqttBrokerConnectionInfo.isGnssStreamEnabled());
    }

    @Test
    public void validateMqttConnectionInfoEquals_correct()
    {
        final String host = "mqtt.example.com";
        final int port = 1883;
        final boolean tlsEnabled = false;
        final String clientId = "iPhone";
        final String username = "bob";
        final String password = "bob's password";

        final MqttBrokerConnectionInfo mqttBrokerConnectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, password, true, true, true);
        final MqttBrokerConnectionInfo mqttBrokerConnectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, password, true, true, true);

        assertEquals(mqttBrokerConnectionInfo1, mqttBrokerConnectionInfo2);
    }

    @Test
    public void validateMqttConnectionInfoEquals_invalid()
    {
        final String host = "mqtt.example.com";
        final int port = 1883;
        final boolean tlsEnabled = false;
        final String clientId = "iPhone";
        final String username = "bob";
        final String password = "bob's password";

        MqttBrokerConnectionInfo connectionInfo1 = new MqttBrokerConnectionInfo("mqtt.example.com", port, tlsEnabled, clientId, username, password, true, true, true);
        MqttBrokerConnectionInfo connectionInfo2 = new MqttBrokerConnectionInfo("craxiom.com", port, tlsEnabled, clientId, username, password, true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        connectionInfo1 = new MqttBrokerConnectionInfo(host, 123, tlsEnabled, clientId, username, password, true, true, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, 1234, tlsEnabled, clientId, username, password, true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, true, clientId, username, password, true, true, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, false, clientId, username, password, true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, "Pixel4", username, password, true, true, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, "S20", username, password, true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, "john", password, true, true, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, "steve", password, true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's password", true, true, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's burgers", true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        // Test cellularStreamEnabled inequality
        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's password", false, true, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's burgers", true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        // Test wifiStreamEnabled inequality
        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's password", true, false, true);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's burgers", true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);

        // Test gnssStreamEnabled inequality
        connectionInfo1 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's password", true, true, false);
        connectionInfo2 = new MqttBrokerConnectionInfo(host, port, tlsEnabled, clientId, username, "bob's burgers", true, true, true);
        assertNotEquals(connectionInfo1, connectionInfo2);
    }
}
