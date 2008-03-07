package com.thoughtworks.selenium.grid.regressiontests;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Experimenting on educated guess for which address a remote control should listen to by default when
 * it is not explicitely provided.
 */
public class LocalHostResolverTest {

    @Test
    public void listAllNetworkInterfaces() {
        for (InetAddress address : computerInternetAddresses()) {
            System.out.println("Discovered internet address " + address);
        }
    }

    @Test
    public void firstNonLocalAddress() {
        System.out.println("Discovered non local internet address "
                + firstNonLocalAddress(computerInternetAddresses()));
    }

    public List<InetAddress> computerInternetAddresses() {
        final ArrayList<NetworkInterface> interfaceList;
        final Enumeration<NetworkInterface> interfaces;
        final LinkedList<InetAddress> addressList;

        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces == null) {
                return new LinkedList<InetAddress>();
            }
        } catch (SocketException e) {
            return new LinkedList<InetAddress>();
        }

        interfaceList = Collections.list(interfaces);
        addressList = new LinkedList<InetAddress>();
        for (NetworkInterface networkCard : interfaceList) {
            final Enumeration<InetAddress> cardAddresses;

            cardAddresses = networkCard.getInetAddresses();
            if (null == cardAddresses) {
                continue;
            }
            addressList.addAll(Collections.list(cardAddresses));
        }

        return addressList;
    }

    public InetAddress firstNonLocalAddress(List<InetAddress> addresses) {
        for (InetAddress address : addresses) {
            if (address.isSiteLocalAddress() || address.isLoopbackAddress() || address.isLinkLocalAddress()) {
                System.out.println("Ignoring " + address);
                continue;
            }
            return address;
        }

        return null;
    }

}
