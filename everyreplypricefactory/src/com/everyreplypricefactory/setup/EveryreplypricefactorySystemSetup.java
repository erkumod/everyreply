/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.everyreplypricefactory.setup;

import static com.everyreplypricefactory.constants.EveryreplypricefactoryConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.everyreplypricefactory.constants.EveryreplypricefactoryConstants;
import com.everyreplypricefactory.service.EveryreplypricefactoryService;


@SystemSetup(extension = EveryreplypricefactoryConstants.EXTENSIONNAME)
public class EveryreplypricefactorySystemSetup
{
	private final EveryreplypricefactoryService everyreplypricefactoryService;

	public EveryreplypricefactorySystemSetup(final EveryreplypricefactoryService everyreplypricefactoryService)
	{
		this.everyreplypricefactoryService = everyreplypricefactoryService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		everyreplypricefactoryService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return EveryreplypricefactorySystemSetup.class.getResourceAsStream("/everyreplypricefactory/sap-hybris-platform.png");
	}
}
