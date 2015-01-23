/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.api;

import java.util.List;
import java.util.Set;

public interface VersionBuilder extends AttributableBuilder<VersionBuilder> {

    VersionBuilder from(Version version);
    
    VersionBuilder identity(String versionId);

    VersionBuilder setRevision(String revision);
	
	VersionBuilder addProfile(Profile profile);

	VersionBuilder addProfiles(List<Profile> profile);

	Set<String> getProfiles();
	
	Profile getProfile(String profileId);
	
	VersionBuilder removeProfile(String profileId);

    Version getVersion();

    final class Factory {

        public static VersionBuilder create() {
            return getProfileBuilders().profileVersionBuilder();
        }

        public static VersionBuilder create(String versionId) {
            return getProfileBuilders().profileVersionBuilder(versionId);
        }

        public static VersionBuilder createFrom(Version version) {
            return getProfileBuilders().profileVersionBuilderFrom(version);
        }

        private static ProfileBuilders getProfileBuilders() {
            return ProfileBuilders.Factory.getProfileBuilders();
        }

        // Hide ctor
        private Factory() {
        }
    }
}
