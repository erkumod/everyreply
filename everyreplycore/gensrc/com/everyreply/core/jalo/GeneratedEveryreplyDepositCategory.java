/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Sep 1, 2019 10:39:16 AM                     ---
 * ----------------------------------------------------------------
 */
package com.everyreply.core.jalo;

import com.everyreply.core.constants.EveryreplyCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem EveryreplyDepositCategory}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedEveryreplyDepositCategory extends GenericItem
{
	/** Qualifier of the <code>EveryreplyDepositCategory.country</code> attribute **/
	public static final String COUNTRY = "country";
	/** Qualifier of the <code>EveryreplyDepositCategory.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>EveryreplyDepositCategory.priceValue</code> attribute **/
	public static final String PRICEVALUE = "priceValue";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(COUNTRY, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(PRICEVALUE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.country</code> attribute.
	 * @return the country - country
	 */
	public String getCountry(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COUNTRY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.country</code> attribute.
	 * @return the country - country
	 */
	public String getCountry()
	{
		return getCountry( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.country</code> attribute. 
	 * @param value the country - country
	 */
	public void setCountry(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COUNTRY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.country</code> attribute. 
	 * @param value the country - country
	 */
	public void setCountry(final String value)
	{
		setCountry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.description</code> attribute.
	 * @return the description - description
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedEveryreplyDepositCategory.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.description</code> attribute.
	 * @return the description - description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.description</code> attribute. 
	 * @return the localized description - description
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.description</code> attribute. 
	 * @return the localized description - description
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.description</code> attribute. 
	 * @param value the description - description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedEveryreplyDepositCategory.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.description</code> attribute. 
	 * @param value the description - description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.description</code> attribute. 
	 * @param value the description - description
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.description</code> attribute. 
	 * @param value the description - description
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.priceValue</code> attribute.
	 * @return the priceValue - priceValue
	 */
	public BigDecimal getPriceValue(final SessionContext ctx)
	{
		return (BigDecimal)getProperty( ctx, PRICEVALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EveryreplyDepositCategory.priceValue</code> attribute.
	 * @return the priceValue - priceValue
	 */
	public BigDecimal getPriceValue()
	{
		return getPriceValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.priceValue</code> attribute. 
	 * @param value the priceValue - priceValue
	 */
	public void setPriceValue(final SessionContext ctx, final BigDecimal value)
	{
		setProperty(ctx, PRICEVALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EveryreplyDepositCategory.priceValue</code> attribute. 
	 * @param value the priceValue - priceValue
	 */
	public void setPriceValue(final BigDecimal value)
	{
		setPriceValue( getSession().getSessionContext(), value );
	}
	
}
