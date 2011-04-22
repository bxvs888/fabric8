/**
 * Copyright (C) 2011, FuseSource Corp.  All rights reserved.
 * http://fusesource.com
 *
 * The software in this package is published under the terms of the
 * CDDL license a copy of which has been included with this distribution
 * in the license.txt file.
 */
package org.fusesource.fabric.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.fusesource.fabric.api.AgentService;
import org.fusesource.fabric.api.Profile;
import org.fusesource.fabric.api.ProfileService;

public abstract class FabricCommand extends OsgiCommandSupport {

    protected AgentService agentService;
    protected ProfileService profileService;

    public AgentService getAgentService() {
        return agentService;
    }

    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    protected String toString(Profile[] profiles) {
        if (profiles == null) {
            return "";
        }
        int iMax = profiles.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(profiles[i].getId());
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }

    protected Profile[] getProfiles(String version, List<String> names) {
        Profile[] allProfiles = profileService.getProfiles(version);
        List<Profile> profiles = new ArrayList<Profile>();
        if (names == null) {
            return new Profile[0];
        }
        for (String name : names) {
            Profile profile = null;
            for (Profile p : allProfiles) {
                if (name.equals(p.getId())) {
                    profile = p;
                    break;
                }
            }
            if (profile == null) {
                throw new IllegalArgumentException("Profile not found: " + name);
            }
            profiles.add(profile);
        }
        return profiles.toArray(new Profile[profiles.size()]);
    }

}
