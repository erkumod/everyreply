/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Oct 20, 2019 10:53:40 AM                    ---
 * ----------------------------------------------------------------
 */
package com.everyreply.core.jalo;

import com.everyreply.core.constants.EveryreplyCoreConstants;
import com.everyreply.core.jalo.KumudApparalProduct;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Utilities;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generated class for type {@link com.everyreply.core.jalo.KumudProduct KumudProduct}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedKumudProduct extends GenericItem
{
	/** Qualifier of the <code>KumudProduct.color</code> attribute **/
	public static final String COLOR = "color";
	/** Qualifier of the <code>KumudProduct.kumudLangProduct</code> attribute **/
	public static final String KUMUDLANGPRODUCT = "kumudLangProduct";
	/** Relation ordering override parameter constants for KumudLanguages from ((everyreplycore))*/
	protected static String KUMUDLANGUAGES_SRC_ORDERED = "relation.KumudLanguages.source.ordered";
	protected static String KUMUDLANGUAGES_TGT_ORDERED = "relation.KumudLanguages.target.ordered";
	/** Relation disable markmodifed parameter constants for KumudLanguages from ((everyreplycore))*/
	protected static String KUMUDLANGUAGES_MARKMODIFIED = "relation.KumudLanguages.markmodified";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(COLOR, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudProduct.color</code> attribute.
	 * @return the color - Color of the product.
	 */
	public String getColor(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedKumudProduct.getColor requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, COLOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudProduct.color</code> attribute.
	 * @return the color - Color of the product.
	 */
	public String getColor()
	{
		return getColor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudProduct.color</code> attribute. 
	 * @return the localized color - Color of the product.
	 */
	public Map<Language,String> getAllColor(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,COLOR,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudProduct.color</code> attribute. 
	 * @return the localized color - Color of the product.
	 */
	public Map<Language,String> getAllColor()
	{
		return getAllColor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudProduct.color</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedKumudProduct.setColor requires a session language", 0 );
		}
		setLocalizedProperty(ctx, COLOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setColor(final String value)
	{
		setColor( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setAllColor(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,COLOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudProduct.color</code> attribute. 
	 * @param value the color - Color of the product.
	 */
	public void setAllColor(final Map<Language,String> value)
	{
		setAllColor( getSession().getSessionContext(), value );
	}
	
	@Override
	public boolean isMarkModifiedDisabled(final Item referencedItem)
	{
		ComposedType relationSecondEnd0 = TypeManager.getInstance().getComposedType("KumudApparalProduct");
		if(relationSecondEnd0.isAssignableFrom(referencedItem.getComposedType()))
		{
			return Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED);
		}
		return true;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudProduct.kumudLangProduct</code> attribute.
	 * @return the kumudLangProduct
	 */
	public Set<KumudApparalProduct> getKumudLangProduct(final SessionContext ctx)
	{
		final List<KumudApparalProduct> items = getLinkedItems( 
			ctx,
			true,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			"KumudApparalProduct",
			null,
			false,
			false
		);
		return new LinkedHashSet<KumudApparalProduct>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>KumudProduct.kumudLangProduct</code> attribute.
	 * @return the kumudLangProduct
	 */
	public Set<KumudApparalProduct> getKumudLangProduct()
	{
		return getKumudLangProduct( getSession().getSessionContext() );
	}
	
	public long getKumudLangProductCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			"KumudApparalProduct",
			null
		);
	}
	
	public long getKumudLangProductCount()
	{
		return getKumudLangProductCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudProduct.kumudLangProduct</code> attribute. 
	 * @param value the kumudLangProduct
	 */
	public void setKumudLangProduct(final SessionContext ctx, final Set<KumudApparalProduct> value)
	{
		setLinkedItems( 
			ctx,
			true,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>KumudProduct.kumudLangProduct</code> attribute. 
	 * @param value the kumudLangProduct
	 */
	public void setKumudLangProduct(final Set<KumudApparalProduct> value)
	{
		setKumudLangProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to kumudLangProduct. 
	 * @param value the item to add to kumudLangProduct
	 */
	public void addToKumudLangProduct(final SessionContext ctx, final KumudApparalProduct value)
	{
		addLinkedItems( 
			ctx,
			true,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to kumudLangProduct. 
	 * @param value the item to add to kumudLangProduct
	 */
	public void addToKumudLangProduct(final KumudApparalProduct value)
	{
		addToKumudLangProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from kumudLangProduct. 
	 * @param value the item to remove from kumudLangProduct
	 */
	public void removeFromKumudLangProduct(final SessionContext ctx, final KumudApparalProduct value)
	{
		removeLinkedItems( 
			ctx,
			true,
			EveryreplyCoreConstants.Relations.KUMUDLANGUAGES,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(KUMUDLANGUAGES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from kumudLangProduct. 
	 * @param value the item to remove from kumudLangProduct
	 */
	public void removeFromKumudLangProduct(final KumudApparalProduct value)
	{
		removeFromKumudLangProduct( getSession().getSessionContext(), value );
	}
	
}
