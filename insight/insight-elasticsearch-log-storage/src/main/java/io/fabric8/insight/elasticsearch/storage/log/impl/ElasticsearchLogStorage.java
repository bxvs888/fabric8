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
package io.fabric8.insight.elasticsearch.storage.log.impl;

import io.fabric8.api.scr.ValidatingReference;
import io.fabric8.insight.elasticsearch.AbstractElasticsearchStorage;
import io.fabric8.insight.storage.StorageService;
import org.apache.felix.scr.annotations.*;
import org.elasticsearch.node.Node;

@Component(immediate = true, name = "io.fabric8.insight.log.elasticsearch")
@Service({StorageService.class})
public class ElasticsearchLogStorage extends AbstractElasticsearchStorage {

    @Reference(name = "node", referenceInterface = org.elasticsearch.node.Node.class, target = "(cluster.name=insight)")
    private final ValidatingReference<Node> node = new ValidatingReference<>();

    @Activate
    public void activate() {
        running = true;
        thread = new Thread(this, "ElasticStorage");
        thread.start();
    }

    @Deactivate
    public void deactivate() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    private void bindNode(Node node) {
        this.node.bind(node);
    }

    private void unbindNode(Node node) {
        this.node.unbind(node);
    }

    @Override
    public Node getNode() {
        return node.get();
    }
}
