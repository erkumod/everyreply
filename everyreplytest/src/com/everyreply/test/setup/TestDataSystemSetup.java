/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.everyreply.test.setup;

import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import de.hybris.platform.util.Config;
import com.everyreply.test.constants.EveryreplyTestConstants;
import com.everyreply.test.orders.AcceleratorTestOrderData;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * This class provides hooks into the system's initialization and update processes.
 */
@SystemSetup(extension = EveryreplyTestConstants.EXTENSIONNAME)
public class TestDataSystemSetup extends AbstractSystemSetup
{
	private static final Logger LOG = Logger.getLogger(TestDataSystemSetup.class);

	private static final String CREATE_TEST_DATA = "createTestData";

	private AcceleratorTestOrderData acceleratorTestOrderData;

	protected AcceleratorTestOrderData getAcceleratorTestOrderData()
	{
		return acceleratorTestOrderData;
	}

	@Required
	public void setAcceleratorTestOrderData(final AcceleratorTestOrderData acceleratorTestOrderData)
	{
		this.acceleratorTestOrderData = acceleratorTestOrderData;
	}

	/**
	 * Generates the Dropdown and Multi-select boxes for the projectdata import
	 */
	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();
		params.add(createBooleanSystemSetupParameter(CREATE_TEST_DATA, "Create Test Data", true));
		return params;
	}

	/**
	 * Implement this method to create initial objects. This method will be called by system creator during
	 * initialization and system update. Be sure that this method can be called repeatedly.
	 *
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.ESSENTIAL, process = Process.ALL)
	public void createEssentialData(final SystemSetupContext context)
	{
		// No essential data to import for the sample data extension
	}

	/**
	 * Implement this method to create data that is used in your project. This method will be called during the system
	 * initialization.
	 *
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.PROJECT, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		if (getBooleanSystemSetupParameter(context, CREATE_TEST_DATA))
		{
			LOG.info("Creating Test Users...");
			importImpexFile(context, "/everyreplytest/import/qa-test-users.impex");

			LOG.info("Creating Reviews...");
			importImpexFile(context, "/everyreplytest/import/reviews.impex");

			LOG.info("Creating Test Payment Subscriptions...");
			getAcceleratorTestOrderData().createPaymentInfos();

			LOG.info("Creating CS Tickets...");
			importImpexFile(context, "/everyreplytest/import/cstickets.impex");

			LOG.info("Creating Test Orders...");
			getAcceleratorTestOrderData().createSampleOrders();
			getAcceleratorTestOrderData().createSampleBOPiSOrders();

			LOG.info("Creating Test Quotes...");
			importImpexFile(context, "/everyreplytest/import/csquotes.impex");
		}
	}
}
