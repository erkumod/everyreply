/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Oct 20, 2019 10:53:40 AM                    ---
 * ----------------------------------------------------------------
 */
package com.everyreply.core.jalo;

import com.everyreply.core.constants.EveryreplyCoreConstants;
import com.everyreply.core.jalo.KumudProduct;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Utilities;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.product.Product KumudApparalProduct}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedKumudApparalProduct extends Product
{
	/** Qualifier of the <code>KumudApparalProduct.color</code> attribute **/
	public static final String COLOR = "color";
	/** Qualifier of the <code>KumudApparalProduct.kumudLang</code> attribute **/
	public static final String KUMUDLANG = "kumudLang";
	/** Relation ordering override parameter constants for KumudLanguages from ((everyreplycore))*/
	protected static String KUMUDLANGUAGES_SRC_ORDERED = "relation.KumudLanguages.source.ordered";
	protected static String KUMUDLANGUAGES_TGT_ORDERED = "relation.KumudLanguages.target.ordered";
	/** Relation disable markmodifed parameter constants for KumudLanguages from ((everyreplycore))*/
	protected static String KUMUDLANGUAGES_MARKMODIFIED = "relation.KumudLanguages.markmodified";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(Product.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(COLOR, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudApparalProduct.color</code> attribute.
	 * @return the color - Color of the product.
	 */
	public String getColor(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedKumudApparalProduct.getColor requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, COLOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudApparalProduct.color</code> attribute.
	 * @return the color - Color of the product.
	 */
	public String getColor()
	{
		return getColor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudApparalProduct.color</code> attribute. 
	 * @return the localized color - Color of the product.
	 */
	public Map<Language,String> getAllColor(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,COLOR,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudApparalProduct.color</code> attribute. 
	 * @return the localized color - Color of the product.
	 */
	public Map<Language,String> getAllColor()
	{
		return getAllColor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudApparalProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setColor(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedKumudApparalProduct.setColor requires a session language", 0 );
		}
		setLocalizedProperty(ctx, COLOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudApparalProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setColor(final String value)
	{
		setColor( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudApparalProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setAllColor(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,COLOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudApparalProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setAllColor(final Map<Language,String> value)
	{
		setAllColor( getSession().getSessionContext(), value );
	}
	
	@Override
	public boolean isMarkModifiedDisabled(final Item referencedItem)
	{
		ComposedType relationSecondEnd0 = TypeManager.getInstance().getComposedType("KumudProduct");
		if(relationSecondEnd0.isAssignableFrom(referencedItem.getComposedType()))
		{
			return Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED);
		}
		return true;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudApparalProduct.kumudLang</code> attribute.
	 * @return the kumudLang - One export cronjob can export multiple languages
	 */
	public Collection<KumudProduct> getKumudLang(final SessionContext ctx)
	{
		final List<KumudProduct> items = getLinkedItems( 
			ctx,
			false,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			"KumudProduct",
			null,
			false,
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudApparalProduct.kumudLang</code> attribute.
	 * @return the kumudLang - One export cronjob can export multiple languages
	 */
	public Collection<KumudProduct> getKumudLang()
	{
		return getKumudLang( getSession().getSessionContext() );
	}
	
	public long getKumudLangCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			"KumudProduct",
			null
		);
	}
	
	public long getKumudLangCount()
	{
		return getKumudLangCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudApparalProduct.kumudLang</code> attribute. 
	 * @param value the kumudLang - One export cronjob can export multiple languages
	 */
	public void setKumudLang(final SessionContext ctx, final Collection<KumudProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudApparalProduct.kumudLang</code> attribute. 
	 * @param value the kumudLang - One export cronjob can export multiple languages
	 */
	public void setKumudLang(final Collection<KumudProduct> value)
	{
		setKumudLang( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to kumudLang. 
	 * @param value the item to add to kumudLang - One export cronjob can export multiple languages
	 */
	public void addToKumudLang(final SessionContext ctx, final KumudProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to kumudLang. 
	 * @param value the item to add to kumudLang - One export cronjob can export multiple languages
	 */
	public void addToKumudLang(final KumudProduct value)
	{
		addToKumudLang( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from kumudLang. 
	 * @param value the item to remove from kumudLang - One export cronjob can export multiple languages
	 */
	public void removeFromKumudLang(final SessionContext ctx, final KumudProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from kumudLang. 
	 * @param value the item to remove from kumudLang - One export cronjob can export multiple languages
	 */
	public void removeFromKumudLang(final KumudProduct value)
	{
		removeFromKumudLang( getSession().getSessionContext(), value );
	}
	
}
