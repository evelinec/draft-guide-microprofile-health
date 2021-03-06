// tag::comment[]
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
// end::comment[]
// tag::HealthTest[]
package it.io.openliberty.guides.health;

import static org.junit.Assert.*;
import java.util.HashMap;
import javax.json.JsonArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HealthTest {

  private JsonArray servicesStates;
  private HashMap<String, String> dataWhenServicesUP, dataWhenInventoryDown;
  public static final String INV_MAINTENANCE = "io_openliberty_guides_inventory_inMaintenance";

  @Before
  public void setup() {
    dataWhenServicesUP = new HashMap<String, String>();
    dataWhenInventoryDown = new HashMap<String, String>();

    dataWhenServicesUP.put("PropertiesResource", "UP");
    dataWhenServicesUP.put("InventoryResource", "UP");

    dataWhenInventoryDown.put("PropertiesResource", "UP");
    dataWhenInventoryDown.put("InventoryResource", "DOWN");
  }

  @Test
  public void testIfServicesStatesUp() {
    servicesStates = HealthTestUtil.connectToHealthEnpoint(200);
    checkServicesStates(dataWhenServicesUP, servicesStates);
  }

  @Test
  public void testIfInventoryTemporarilyDown() {
    servicesStates = HealthTestUtil.connectToHealthEnpoint(200);
    checkServicesStates(dataWhenServicesUP, servicesStates);
    HealthTestUtil.changeInventoryProperty(INV_MAINTENANCE + "\": false",
                                           INV_MAINTENANCE + "\": true");
    servicesStates = HealthTestUtil.connectToHealthEnpoint(503);
    checkServicesStates(dataWhenInventoryDown, servicesStates);
  }

  private void checkServicesStates(HashMap<String, String> testData,
      JsonArray servicesStates) {
    testData.forEach((service, expectedState) -> {
      assertEquals("The state of " + service + " service is not matching the ",
                   expectedState,
                   HealthTestUtil.getActualState(service, servicesStates));
    });

  }

  @After
  public void teardown() {
    HealthTestUtil.cleanUp();
  }

}
// end::HealthTest[]