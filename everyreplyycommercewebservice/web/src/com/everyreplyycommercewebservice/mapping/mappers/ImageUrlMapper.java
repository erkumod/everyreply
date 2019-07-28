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
package com.everyreplyycommercewebservice.mapping.mappers;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercewebservicescommons.dto.product.ImageWsDTO;
import de.hybris.platform.webservicescommons.mapping.mappers.AbstractCustomMapper;
import com.everyreplyycommercewebservice.constants.YcommercewebservicesConstants;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang3.StringUtils;

public class ImageUrlMapper extends AbstractCustomMapper<ImageData, ImageWsDTO>
{
    @Override
    public void mapAtoB(final ImageData a, final ImageWsDTO b, final MappingContext context)
    {
        // other fields are mapped automatically

        context.beginMappingField("url", getAType(), a, "url", getBType(), b);
        try
        {
            if (shouldMap(a, b, context))
            {
                StringBuilder url = new StringBuilder(YcommercewebservicesConstants.V2_ROOT_CONTEXT)
                        .append(a.getUrl());
                b.setUrl(url.toString());
            }
        }
        finally
        {
            context.endMappingField();
        }
    }
}
