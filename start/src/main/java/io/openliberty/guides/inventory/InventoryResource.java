// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.inventory;

// CDI
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

// JSON-P
import javax.json.JsonObject;

// JAX-RS
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.openliberty.guides.inventory.InventoryConfig;

@RequestScoped
@Path("hosts")
public class InventoryResource {
  @Inject
  InventoryManager manager;

  // tag::config[]
  @Inject
  InventoryConfig config;
  // end::config[]

  // tag::getPropertiesForHost[]
  @GET
  @Path("{hostname}")
  @Produces(MediaType.APPLICATION_JSON)
  public JsonObject getPropertiesForHost(
      @PathParam("hostname") String hostname) {
    return manager.get(hostname, config.getPortNumber());
  }
  // end::getPropertiesForHost[]

  // tag::listContents[]
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JsonObject listContents() {
    return manager.list();
  }
  // end::listContents[]
}
