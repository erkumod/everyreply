/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Aug 18, 2019 11:05:08 PM                    ---
 * ----------------------------------------------------------------
 */
package com.everyreplypricefactory.jalo;

import com.everyreplypricefactory.constants.EveryreplypricefactoryConstants;
import de.hybris.platform.europe1.jalo.Europe1PriceFactory;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>EveryreplypricefactoryManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedEveryreplypricefactoryManager extends Europe1PriceFactory
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	@Override
	public String getName()
	{
		return EveryreplypricefactoryConstants.EXTENSIONNAME;
	}
	
}
