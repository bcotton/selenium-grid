package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


public class RemoteControlProvisionerTest {

    @Test
    public void availableRemoteControlsReturnsAnEmptyArrayWhenNoneHaveBeenAdded() {
      assertTrue(new RemoteControlProvisioner().availableRemoteControls().isEmpty());
    }

    @Test
    public void availableRemoteControlsReturnsARemoteControlThanHasBeenAdded() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        provisioner.add(remoteControl);
        assertEquals(1, provisioner.availableRemoteControls().size());
        assertTrue(provisioner.availableRemoteControls().contains(remoteControl));
    }

    @Test
    public void multipleRemoteControlsCanBeAdded() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);

        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);
        assertEquals(2, provisioner.availableRemoteControls().size());
        assertTrue(provisioner.availableRemoteControls().contains(firstRemoteControl));
        assertTrue(provisioner.availableRemoteControls().contains(secondRemoteControl));
    }

    @Test
    public void onceRemovedARemoteControlIsNotPartOfTheAvailableList() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.remove(remoteControl);
        assertTrue(provisioner.availableRemoteControls().isEmpty());
    }

    @Test
    public void removeOnlyRemovesASpecificRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        provisioner.remove(firstRemoteControl);
        assertFalse(provisioner.availableRemoteControls().contains(firstRemoteControl));
        assertTrue(provisioner.availableRemoteControls().contains(secondRemoteControl));
    }

    @Test
    public void removeReturnsFalseForARemoteControlThanHasNeverBeenAdded() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        assertFalse(provisioner.remove(new RemoteControlProxy("", 0, "", null)));
    }

    @Test
    public void removeReturnsTrueForAnAvailableRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        assertTrue(provisioner.remove(remoteControl));
    }

    @Test
    public void removeReturnsTrueForAReservedRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);
        provisioner.reserve();

        assertTrue(provisioner.remove(remoteControl));
    }

    public void reserveReturnsNullWhenThereIsNoAvailableRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        assertEquals(null, provisioner.reserve());
    }

    @Test
    public void reserveReturnsTheFirstAvailableRemoteControl() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        assertEquals(firstRemoteControl, provisioner.reserve());
        assertFalse(provisioner.availableRemoteControls().contains(firstRemoteControl));
        assertTrue(provisioner.availableRemoteControls().contains(secondRemoteControl));
    }

    @Test
    public void onceReservedARemoteControlIsNotAvailableAnymore() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.reserve();
        assertTrue(provisioner.availableRemoteControls().isEmpty());
    }

    @Test
    public void reservedOnlyARemoveTheReservedRemoteControlFromTheAvailableList() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("a", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("b", 0, "", null);
        provisioner.add(firstRemoteControl);
        provisioner.add(secondRemoteControl);

        assertEquals(firstRemoteControl, provisioner.reserve());
        assertFalse(provisioner.availableRemoteControls().contains(firstRemoteControl));
        assertTrue(provisioner.availableRemoteControls().contains(secondRemoteControl));
    }

    @Test
    public void releaseMakesAReservedRemoteControlAvailable() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.reserve();
        provisioner.release(remoteControl);
        assertTrue(provisioner.availableRemoteControls().contains(remoteControl));
    }

    @Test
    public void availableRemoteControlListHasNotDuplicatesWhenReleasingARemoteControlThatIsNotReserved() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        provisioner.add(remoteControl);

        provisioner.release(remoteControl);
        assertEquals(1, provisioner.availableRemoteControls().size());
        assertTrue(provisioner.availableRemoteControls().contains(remoteControl));
    }

    @Test
    public void availableRemoteControlIsUnchangedWhenReleasingARemoteControlThatHasNeverBeenAdded() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();

        provisioner.release(new RemoteControlProxy("", 0, "", null));
        assertTrue(provisioner.availableRemoteControls().isEmpty());
    }

    @Test
    public void reservedRemoteControlsReturnsAnEmptyArrayWhenNoneHaveBeenAdded() {
      assertTrue(new RemoteControlProvisioner().reservedRemoteControls().isEmpty());
    }

    @Test
    public void reservedRemoteControlsReturnsAnEmptyArrayWhenRemoteControlHasBeenAddedButNotReserved() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        provisioner.add(remoteControl);
        assertTrue(provisioner.reservedRemoteControls().isEmpty());
    }

    @Test
    public void reservedRemoteControlsReturnsARemoteControlThatHaveBeenReserved() {
        final RemoteControlProvisioner provisioner = new RemoteControlProvisioner();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        provisioner.add(remoteControl);
        provisioner.reserve();
        assertEquals(1, provisioner.reservedRemoteControls().size());
        assertTrue(provisioner.reservedRemoteControls().contains(remoteControl));
    }

}
