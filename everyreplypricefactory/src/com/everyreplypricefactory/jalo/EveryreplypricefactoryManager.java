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
package com.everyreplypricefactory.jalo;



import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.ItemSyncTimestamp;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.core.Constants.USER;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.Registry;
import de.hybris.platform.cronjob.jalo.ChangeDescriptor;
import de.hybris.platform.cronjob.jalo.JobLog;
import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.europe1.constants.Europe1Tools;
import de.hybris.platform.europe1.constants.GeneratedEurope1Constants.TC;
import de.hybris.platform.europe1.jalo.AbstractDiscountRow;
import de.hybris.platform.europe1.jalo.DiscountRow;
import de.hybris.platform.europe1.jalo.Europe1DiscountInformation;
import de.hybris.platform.europe1.jalo.GlobalDiscountRow;
import de.hybris.platform.europe1.jalo.PDTRow;
import de.hybris.platform.europe1.jalo.PDTRowsQueryBuilder;
import de.hybris.platform.europe1.jalo.PDTRowsQueryBuilder.QueryWithParams;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.europe1.jalo.TaxRow;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.ExtensibleItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.Item.ItemAttributeMap;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.price.Discount;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.order.price.ProductPriceInformations;
import de.hybris.platform.jalo.order.price.Tax;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloAbstractTypeException;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.util.DateRange;
import de.hybris.platform.util.JspContext;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.util.StandardDateRange;
import de.hybris.platform.util.TaxValue;
import de.hybris.platform.util.localization.Localization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.everyreplypricefactory.constants.EveryreplypricefactoryConstants;
import com.google.common.base.Preconditions;


/**
 * This is the extension manager of the Everyreplypricefactory extension.
 */
public class EveryreplypricefactoryManager extends GeneratedEveryreplypricefactoryManager
{
	/** Edit the local|project.properties to change logging behavior (properties 'log4j.*'). */
	//	private static final Logger LOG = LoggerFactory.getLogger(EveryreplypricefactoryManager.class);

	/*
	 * Some important tips for development: Do NEVER use the default constructor of manager's or items. => If you want to do
	 * something whenever the manger is created use the init() or destroy() methods described below Do NEVER use STATIC
	 * fields in your manager or items! => If you want to cache anything in a "static" way, use an instance variable in your
	 * manager, the manager is created only once in the lifetime of a "deployment" or tenant.
	 */


	/**
	 * Get the valid instance of this manager.
	 *
	 * @return the current instance of this manager
	 */
	public static EveryreplypricefactoryManager getInstance()
	{
		return (EveryreplypricefactoryManager) Registry.getCurrentTenant().getJaloConnection().getExtensionManager()
				.getExtension(EveryreplypricefactoryConstants.EXTENSIONNAME);
	}


	/**
	 * Never call the constructor of any manager directly, call getInstance() You can place your business logic here - like
	 * registering a jalo session listener. Each manager is created once for each tenant.
	 */
	public EveryreplypricefactoryManager() // NOPMD
	{
		//LOG.debug("constructor of EveryreplypricefactoryManager called.");
	}

	/**
	 * Use this method to do some basic work only ONCE in the lifetime of a tenant resp. "deployment". This method is called
	 * after manager creation (for example within startup of a tenant). Note that if you have more than one tenant you have
	 * a manager instance for each tenant.
	 */
	@Override
	public void init()
	{
		//LOG.debug("init() of EveryreplypricefactoryManager called, current tenant: {}", getTenant().getTenantID());
	}

	/**
	 * Use this method as a callback when the manager instance is being destroyed (this happens before system
	 * initialization, at redeployment or if you shutdown your VM). Note that if you have more than one tenant you have a
	 * manager instance for each tenant.
	 */
	@Override
	public void destroy()
	{
		//LOG.debug("destroy() of EveryreplypricefactoryManager called, current tenant: {}", getTenant().getTenantID());
	}

	/**
	 * Implement this method to create initial objects. This method will be called by system creator during initialization
	 * and system update. Be sure that this method can be called repeatedly. An example usage of this method is to create
	 * required cronjobs or modifying the type system (setting e.g some default values)
	 *
	 * @param params
	 *           the parameters provided by user for creation of objects for the extension
	 * @param jspc
	 *           the jsp context; you can use it to write progress information to the jsp page during creation
	 */

	/*
	 * public void createEssentialData(final Map<String, String> params, final JspContext jspc) { // implement here code
	 * creating essential data }
	 */
	/**
	 * Implement this method to create data that is used in your project. This method will be called during the system
	 * initialization. An example use is to import initial data like currencies or languages for your project from an csv
	 * file.
	 *
	 * @param params
	 *           the parameters provided by user for creation of objects for the extension
	 * @param jspc
	 *           the jsp context; you can use it to write progress information to the jsp page during creation
	 */
	/*
	 * @Override public void createProjectData(final Map<String, String> params, final JspContext jspc) { // implement here
	 * code creating project data }
	 */


	//public static final long MATCH_ANY;
	//public static final long MATCH_BY_PRODUCT_ID;
	//private RetrieveChannelStrategy retrieveChannelStrategy;
	//private static final long[] ANY_COLLECTION;
	public static final String USE_FAST_ALGORITHMS = "use.fast.algorithms";
	private volatile Boolean cachesTaxes = null;

	//	private static final Set EUROPE1_ENUMS;
	//private static final Comparator<PriceRow> PR_COMP;
	//private static final Comparator<TaxRow> TR_COMP;
	//private static final Comparator<DiscountRow> DR_COMP;






	@Override
	protected synchronized void invalidateTaxCache()
	{
		this.cachesTaxes = null;
	}

	@Override
	protected void checkBeforeItemRemoval(final SessionContext ctx, final Item item) throws ConsistencyCheckException
	{
	}



	@Override
	protected void removeRowsFor(final SessionContext ctx, final Product product) throws ConsistencyCheckException
	{
		final List rows = FlexibleSearch.getInstance()
				.search("SELECT tbl.PK FROM ({{ SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(PriceRow.class).getCode() + "*} " + "WHERE {" + "product"
						+ "}=?item }} " + " UNION ALL " + "{{ SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(TaxRow.class).getCode() + "*} " + "WHERE {" + "product"
						+ "}=?item }} " + " UNION ALL " + "{{ SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(DiscountRow.class).getCode() + "*} " + "WHERE {" + "product"
						+ "}=?item }} " + ") tbl", Collections.singletonMap("item", product), PDTRow.class)
				.getResult();
		final Iterator arg4 = rows.iterator();

		while (arg4.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg4.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	protected void removeRowsFor(final SessionContext ctx, final User user) throws ConsistencyCheckException
	{
		final List rows = FlexibleSearch.getInstance()
				.search("SELECT tbl.PK FROM ({{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(PriceRow.class).getCode() + "*} " + "WHERE {" + "user"
						+ "}=?item }} " + "UNION ALL " + "{{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(TaxRow.class).getCode() + "*} " + "WHERE {" + "user" + "}=?item }} "
						+ "UNION ALL " + "{{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(DiscountRow.class).getCode() + "*} " + "WHERE {" + "user"
						+ "}=?item }} " + "UNION ALL " + "{{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(GlobalDiscountRow.class).getCode() + "*} " + "WHERE {" + "user"
						+ "}=?item }} " + ") tbl", Collections.singletonMap("item", user), PDTRow.class)
				.getResult();
		final Iterator arg4 = rows.iterator();

		while (arg4.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg4.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	protected void removeRowsFor(final SessionContext ctx, final Currency currency) throws ConsistencyCheckException
	{
		final List rows = FlexibleSearch.getInstance()
				.search("SELECT tbl.PK FROM ({{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(PriceRow.class).getCode() + "*} " + "WHERE {" + "currency"
						+ "}=?item }} " + "UNION ALL " + "{{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(DiscountRow.class).getCode() + "*} " + "WHERE {" + "currency"
						+ "}=?item }} " + "UNION ALL " + "{{SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(GlobalDiscountRow.class).getCode() + "*} " + "WHERE {" + "currency"
						+ "}=?item }} " + ") tbl", Collections.singletonMap("item", currency), PDTRow.class)
				.getResult();
		final Iterator arg4 = rows.iterator();

		while (arg4.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg4.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	protected void removeRowsFor(final SessionContext ctx, final Unit unit) throws ConsistencyCheckException
	{
		final List rows = FlexibleSearch.getInstance()
				.search("SELECT {" + Item.PK + "} FROM {" + TypeManager.getInstance().getComposedType(PriceRow.class).getCode()
						+ "*} " + "WHERE {" + "unit" + "}=?item", Collections.singletonMap("item", unit), PDTRow.class)
				.getResult();
		final Iterator arg4 = rows.iterator();

		while (arg4.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg4.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	protected void removeRowsFor(final SessionContext ctx, final Tax tax) throws ConsistencyCheckException
	{
		final List rows = FlexibleSearch.getInstance()
				.search("SELECT {" + Item.PK + "} FROM {" + TypeManager.getInstance().getComposedType(TaxRow.class).getCode() + "*} "
						+ "WHERE {" + "tax" + "}=?item", Collections.singletonMap("item", tax), PDTRow.class)
				.getResult();
		final Iterator arg4 = rows.iterator();

		while (arg4.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg4.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	protected void removeRowsFor(final SessionContext ctx, final Discount discount) throws ConsistencyCheckException
	{
		final List rows = FlexibleSearch.getInstance()
				.search("SELECT tbl.PK FROM ({{ SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(DiscountRow.class).getCode() + "*} " + "WHERE {" + "discount"
						+ "}=?item }} " + " UNION ALL " + "{{ SELECT {" + Item.PK + "} as PK FROM {"
						+ TypeManager.getInstance().getComposedType(GlobalDiscountRow.class).getCode() + "*} " + "WHERE {" + "discount"
						+ "}=?item }} " + ") tbl", Collections.singletonMap("item", discount), PDTRow.class)
				.getResult();
		final Iterator arg4 = rows.iterator();

		while (arg4.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg4.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	protected void removeRowsFor(final SessionContext ctx, final EnumerationValue enumerationValue)
			throws ConsistencyCheckException
	{
		final String typeCode = enumerationValue.getComposedType().getCode();
		String query = null;
		if ("ProductPriceGroup".equalsIgnoreCase(typeCode))
		{
			query = "SELECT {" + Item.PK + "} FROM {" + TypeManager.getInstance().getComposedType(PriceRow.class).getCode() + "*} "
					+ "WHERE {" + "pg" + "}=?item";
		}

		if ("UserPriceGroup".equalsIgnoreCase(typeCode))
		{
			query = "SELECT {" + Item.PK + "} FROM {" + TypeManager.getInstance().getComposedType(PriceRow.class).getCode() + "*} "
					+ "WHERE {" + "ug" + "}=?item";
		}

		if ("ProductTaxGroup".equalsIgnoreCase(typeCode))
		{
			query = "SELECT {" + Item.PK + "} FROM {" + TypeManager.getInstance().getComposedType(TaxRow.class).getCode() + "*} "
					+ "WHERE {" + "pg" + "}=?item";
		}

		if ("UserTaxGroup".equalsIgnoreCase(typeCode))
		{
			query = "SELECT {" + Item.PK + "} FROM {" + TypeManager.getInstance().getComposedType(TaxRow.class).getCode() + "*} "
					+ "WHERE {" + "ug" + "}=?item";
		}

		if ("ProductDiscountGroup".equalsIgnoreCase(typeCode))
		{
			query = "SELECT tbl.PK FROM ({{ SELECT {" + Item.PK + "} as PK FROM {"
					+ TypeManager.getInstance().getComposedType(DiscountRow.class).getCode() + "*} " + "WHERE {" + "pg" + "}=?item }} "
					+ " UNION ALL " + "{{ SELECT {" + Item.PK + "} as PK FROM {"
					+ TypeManager.getInstance().getComposedType(GlobalDiscountRow.class).getCode() + "*} " + "WHERE {" + "pg"
					+ "}=?item }} " + ") tbl";
		}

		if ("UserDiscountGroup".equalsIgnoreCase(typeCode))
		{
			query = "SELECT tbl.PK FROM ({{ SELECT {" + Item.PK + "} as PK FROM {"
					+ TypeManager.getInstance().getComposedType(DiscountRow.class).getCode() + "*} " + "WHERE {" + "ug" + "}=?item }} "
					+ " UNION ALL " + "{{ SELECT {" + Item.PK + "} as PK FROM {"
					+ TypeManager.getInstance().getComposedType(GlobalDiscountRow.class).getCode() + "*} " + "WHERE {" + "ug"
					+ "}=?item }} " + ") tbl";
		}

		final List rows = FlexibleSearch.getInstance()
				.search(query, Collections.singletonMap("item", enumerationValue), PDTRow.class).getResult();
		final Iterator arg6 = rows.iterator();

		while (arg6.hasNext())
		{
			final PDTRow toRemove = (PDTRow) arg6.next();
			toRemove.remove(ctx);
		}

	}

	@Override
	public PriceRow createPriceRow(final Product product, final EnumerationValue productPriceGroup, final User user,
			final EnumerationValue userPriceGroup, final long minQuantity, final Currency currency, final Unit unit,
			final int unitFactor, final boolean net, final DateRange dateRange, final double price) throws JaloPriceFactoryException
	{
		return this.createPriceRow(this.getSession().getSessionContext(), product, productPriceGroup, user, userPriceGroup,
				minQuantity, currency, unit, unitFactor, net, dateRange, price);
	}

	@Override
	public PriceRow createPriceRow(final Product product, final EnumerationValue productPriceGroup, final User user,
			final EnumerationValue userPriceGroup, final long minQuantity, final Currency currency, final Unit unit,
			final int unitFactor, final boolean net, final DateRange dateRange, final double price, final EnumerationValue channel)
			throws JaloPriceFactoryException
	{
		return this.createPriceRow(this.getSession().getSessionContext(), product, productPriceGroup, user, userPriceGroup,
				minQuantity, currency, unit, unitFactor, net, dateRange, price, channel);
	}

	@Override
	public PriceRow createPriceRow(final SessionContext ctx, final Product product, final EnumerationValue productPriceGroup,
			final User user, final EnumerationValue userPriceGroup, final long minQuantity, final Currency currency, final Unit unit,
			final int unitFactor, final boolean net, final DateRange dateRange, final double price, final EnumerationValue channel)
			throws JaloPriceFactoryException
	{
		PriceRow result = null;

		try
		{
			result = (PriceRow) ComposedType.newInstance(this.getSession().getSessionContext(), PriceRow.class, new Object[]
			{ "product", product, "pg", productPriceGroup, "user", user, "ug", userPriceGroup, "minqtd", Long.valueOf(minQuantity),
					"currency", currency, "unit", unit, "unitFactor", Integer.valueOf(unitFactor), "net", Boolean.valueOf(net),
					"dateRange", dateRange, "price", Double.valueOf(price), "channel", channel });
			return result;
		}
		catch (final JaloGenericCreationException arg18)
		{
			Object cause = arg18.getCause();
			if (cause == null)
			{
				cause = arg18;
			}

			if (cause instanceof RuntimeException)
			{
				throw (RuntimeException) cause;
			}
			else if (cause instanceof JaloPriceFactoryException)
			{
				throw (JaloPriceFactoryException) cause;
			}
			else
			{
				throw new JaloSystemException((Throwable) cause);
			}
		}
		catch (final JaloAbstractTypeException arg19)
		{
			throw new JaloSystemException(arg19);
		}
		catch (final JaloItemNotFoundException arg20)
		{
			throw new JaloSystemException(arg20);
		}
	}

	@Override
	public PriceRow createPriceRow(final SessionContext ctx, final Product product, final EnumerationValue productPriceGroup,
			final User user, final EnumerationValue userPriceGroup, final long minQuantity, final Currency currency, final Unit unit,
			final int unitFactor, final boolean net, final DateRange dateRange, final double price) throws JaloPriceFactoryException
	{
		try
		{
			return (PriceRow) ComposedType.newInstance(this.getSession().getSessionContext(), PriceRow.class, new Object[]
			{ "product", product, "pg", productPriceGroup, "user", user, "ug", userPriceGroup, "minqtd", Long.valueOf(minQuantity),
					"currency", currency, "unit", unit, "unitFactor", Integer.valueOf(unitFactor), "net", Boolean.valueOf(net),
					"dateRange", dateRange, "price", Double.valueOf(price) });
		}
		catch (final JaloGenericCreationException arg16)
		{
			Object cause = arg16.getCause();
			if (cause == null)
			{
				cause = arg16;
			}

			if (cause instanceof RuntimeException)
			{
				throw (RuntimeException) cause;
			}
			else if (cause instanceof JaloPriceFactoryException)
			{
				throw (JaloPriceFactoryException) cause;
			}
			else
			{
				throw new JaloSystemException((Throwable) cause);
			}
		}
		catch (final JaloAbstractTypeException arg17)
		{
			throw new JaloSystemException(arg17);
		}
		catch (final JaloItemNotFoundException arg18)
		{
			throw new JaloSystemException(arg18);
		}
	}

	@Override
	@Deprecated
	public TaxRow createTaxRow(final Product product, final EnumerationValue productPriceGroup, final User user,
			final EnumerationValue userPriceGroup, final Tax tax) throws JaloPriceFactoryException
	{
		return this.createTaxRow(this.getSession().getSessionContext(), product, productPriceGroup, user, userPriceGroup, tax,
				(DateRange) null, (Double) null);
	}

	@Override
	public TaxRow createTaxRow(final Product product, final EnumerationValue productPriceGroup, final User user,
			final EnumerationValue userPriceGroup, final Tax tax, final DateRange dateRange, final Double value)
			throws JaloPriceFactoryException
	{
		return this.createTaxRow(this.getSession().getSessionContext(), product, productPriceGroup, user, userPriceGroup, tax,
				dateRange, value);
	}

	@Override
	public TaxRow createTaxRow(final SessionContext ctx, final Product product, final EnumerationValue productPriceGroup,
			final User user, final EnumerationValue userPriceGroup, final Tax tax, final DateRange dateRange, final Double value)
			throws JaloPriceFactoryException
	{
		try
		{
			return (TaxRow) ComposedType.newInstance(this.getSession().getSessionContext(), TaxRow.class, new Object[]
			{ "product", product, "pg", productPriceGroup, "user", user, "ug", userPriceGroup, "tax", tax, "dateRange", dateRange,
					"value", value });
		}
		catch (final JaloGenericCreationException arg10)
		{
			Object cause = arg10.getCause();
			if (cause == null)
			{
				cause = arg10;
			}

			if (cause instanceof RuntimeException)
			{
				throw (RuntimeException) cause;
			}
			else if (cause instanceof JaloPriceFactoryException)
			{
				throw (JaloPriceFactoryException) cause;
			}
			else
			{
				throw new JaloSystemException((Throwable) cause);
			}
		}
		catch (final JaloAbstractTypeException arg11)
		{
			throw new JaloSystemException(arg11);
		}
		catch (final JaloItemNotFoundException arg12)
		{
			throw new JaloSystemException(arg12);
		}
	}

	@Override
	public DiscountRow createDiscountRow(final Product product, final EnumerationValue productPriceGroup, final User user,
			final EnumerationValue userPriceGroup, final Currency currency, final Double value, final DateRange dateRange,
			final Discount discount) throws JaloPriceFactoryException
	{
		return this.createDiscountRow(this.getSession().getSessionContext(), product, productPriceGroup, user, userPriceGroup,
				currency, value, dateRange, discount);
	}

	@Override
	public DiscountRow createDiscountRow(final SessionContext ctx, final Product product, final EnumerationValue productPriceGroup,
			final User user, final EnumerationValue userPriceGroup, final Currency currency, final Double value,
			final DateRange dateRange, final Discount discount) throws JaloPriceFactoryException
	{
		try
		{
			return (DiscountRow) ComposedType.newInstance(this.getSession().getSessionContext(), DiscountRow.class, new Object[]
			{ "product", product, "pg", productPriceGroup, "user", user, "ug", userPriceGroup, "currency", currency, "value", value,
					"dateRange", dateRange, "discount", discount });
		}
		catch (final JaloGenericCreationException arg11)
		{
			Object cause = arg11.getCause();
			if (cause == null)
			{
				cause = arg11;
			}

			if (cause instanceof RuntimeException)
			{
				throw (RuntimeException) cause;
			}
			else if (cause instanceof JaloPriceFactoryException)
			{
				throw (JaloPriceFactoryException) cause;
			}
			else
			{
				throw new JaloSystemException((Throwable) cause);
			}
		}
		catch (final JaloAbstractTypeException arg12)
		{
			throw new JaloSystemException(arg12);
		}
		catch (final JaloItemNotFoundException arg13)
		{
			throw new JaloSystemException(arg13);
		}
	}

	@Override
	public GlobalDiscountRow createGlobalDiscountRow(final User user, final EnumerationValue userPriceGroup,
			final Currency currency, final Double value, final DateRange dateRange, final Discount discount)
			throws JaloPriceFactoryException
	{
		return this.createGlobalDiscountRow(this.getSession().getSessionContext(), user, userPriceGroup, currency, value, dateRange,
				discount);
	}

	@Override
	public GlobalDiscountRow createGlobalDiscountRow(final SessionContext ctx, final User user,
			final EnumerationValue userPriceGroup, final Currency currency, final Double value, final DateRange dateRange,
			final Discount discount) throws JaloPriceFactoryException
	{
		try
		{
			return (GlobalDiscountRow) ComposedType.newInstance(this.getSession().getSessionContext(), GlobalDiscountRow.class,
					new Object[]
					{ "user", user, "ug", userPriceGroup, "currency", currency, "value", value, "dateRange", dateRange, "discount",
							discount });
		}
		catch (final JaloGenericCreationException arg9)
		{
			Object cause = arg9.getCause();
			if (cause == null)
			{
				cause = arg9;
			}

			if (cause instanceof RuntimeException)
			{
				throw (RuntimeException) cause;
			}
			else if (cause instanceof JaloPriceFactoryException)
			{
				throw (JaloPriceFactoryException) cause;
			}
			else
			{
				throw new JaloSystemException((Throwable) cause);
			}
		}
		catch (final JaloAbstractTypeException arg10)
		{
			throw new JaloSystemException(arg10);
		}
		catch (final JaloItemNotFoundException arg11)
		{
			throw new JaloSystemException(arg11);
		}
	}


	@Override
	@SLDSafe(portingClass = "ProductEurope1PricesAttributeHandler", portingMethod = "get(final ProductModel model)")
	public Collection<PriceRow> getEurope1Prices(final SessionContext ctx, final Product product)
	{
		CatalogManager.getInstance();
		return CatalogManager.isSyncInProgressAsPrimitive(ctx)
				? this.getRealPartOfPriceRows(ctx, product, this.getPPG(ctx, product))
				: this.getProductPriceRows(ctx, product, this.getPPG(ctx, product));
	}

	@Override
	@SLDSafe(portingClass = "ProductEurope1PricesAttributeHandler", portingMethod = "get(final ProductModel model)")
	public Collection<PriceRow> getEurope1Prices(final Product item)
	{
		return this.getEurope1Prices(this.getSession().getSessionContext(), item);
	}



	@Override
	@SLDSafe(portingClass = "ProductEurope1PricesAttributeHandler", portingMethod = "set(final ProductModel model, final Collection<PriceRowModel> value)")
	public void setEurope1Prices(final SessionContext ctx, final Product item, final Collection prices)
	{
		final HashSet toRemove = new HashSet(this.getEurope1Prices(ctx, item));
		if (prices != null)
		{
			toRemove.removeAll(prices);
		}

		try
		{
			final Iterator e = toRemove.iterator();

			while (e.hasNext())
			{
				final PriceRow priceRow = (PriceRow) e.next();
				if (item.equals(priceRow.getProduct(ctx)))
				{
					priceRow.remove(ctx);
				}
			}

		}
		catch (final ConsistencyCheckException arg6)
		{
			throw new JaloSystemException(arg6);
		}
	}







	@Override
	@SLDSafe(portingClass = "ProductEurope1TaxesAttributeHandler", portingMethod = "set(final ProductModel model, final Collection<TaxRowModel> value)")
	public void setEurope1Taxes(final SessionContext ctx, final Product item, final Collection taxes)
	{
		final HashSet toRemove = new HashSet(this.getEurope1Taxes(ctx, item));
		if (taxes != null)
		{
			toRemove.removeAll(taxes);
		}

		try
		{
			final Iterator e = toRemove.iterator();

			while (e.hasNext())
			{
				final TaxRow taxRow = (TaxRow) e.next();
				if (taxRow.getProduct(ctx) != null)
				{
					taxRow.remove(ctx);
				}
			}

		}
		catch (final ConsistencyCheckException arg6)
		{
			throw new JaloSystemException(arg6);
		}
	}

	@Override
	@SLDSafe(portingClass = "ProductEurope1TaxesAttributeHandler", portingMethod = "set(final ProductModel model, final Collection<TaxRowModel> value)")
	public void setEurope1Taxes(final Product item, final Collection<TaxRow> value)
	{
		this.setEurope1Taxes(this.getSession().getSessionContext(), item, value);
	}



	@Override
	@SLDSafe(portingClass = "ProductEurope1DiscountsAttributeHandler", portingMethod = "get(final ProductModel model)")
	public Collection<DiscountRow> getEurope1Discounts(final Product item)
	{
		return this.getEurope1Discounts(this.getSession().getSessionContext(), item);
	}



	@Override
	@SLDSafe(portingClass = "ProductEurope1DiscountsAttributeHandler", portingMethod = "set(final ProductModel model, final Collection<DiscountRowModel> value)")
	public void setEurope1Discounts(final SessionContext ctx, final Product item, final Collection discounts)
	{
		final HashSet toRemove = new HashSet(this.getEurope1Discounts(ctx, item));
		if (discounts != null)
		{
			toRemove.removeAll(discounts);
		}

		try
		{
			final Iterator e = toRemove.iterator();

			while (e.hasNext())
			{
				final DiscountRow discountRow = (DiscountRow) e.next();
				if (discountRow.getProduct(ctx) != null)
				{
					discountRow.remove(ctx);
				}
			}

		}
		catch (final ConsistencyCheckException arg6)
		{
			throw new JaloSystemException(arg6);
		}
	}

	@Override
	@SLDSafe(portingClass = "UserEurope1DiscountsAttributeHandler", portingMethod = "get(final UserModel model)")
	public Collection<GlobalDiscountRow> getEurope1Discounts(final User user)
	{
		return this.getEurope1Discounts(this.getSession().getSessionContext(), user);
	}

	@Override
	@SLDSafe(portingClass = "ProductEurope1DiscountsAttributeHandler", portingMethod = "set(final ProductModel model, final Collection<DiscountRowModel> value)")
	public void setEurope1Discounts(final Product product, final Collection discounts)
	{
		this.setEurope1Discounts(this.getSession().getSessionContext(), product, discounts);
	}

	@Override
	@SLDSafe(portingClass = "UserEurope1DiscountsAttributeHandler", portingMethod = "get(final UserModel model)")
	public Collection<GlobalDiscountRow> getEurope1Discounts(final SessionContext ctx, final User user)
	{
		return this.getUserGlobalDiscountRows(user, (EnumerationValue) user.getProperty(ctx, "Europe1PriceFactory_UDG"));
	}

	@Override
	@SLDSafe(portingClass = "UserEurope1DiscountsAttributeHandler", portingMethod = "set(final UserModel model, final Collection<GlobalDiscountRowModel> globalDiscountRowModels)")
	public void setEurope1Discounts(final User user, final Collection discounts)
	{
		this.setEurope1Discounts(this.getSession().getSessionContext(), user, discounts);
	}

	@Override
	@SLDSafe(portingClass = "UserEurope1DiscountsAttributeHandler", portingMethod = "set(final UserModel model, final Collection<GlobalDiscountRowModel> globalDiscountRowModels)")
	public void setEurope1Discounts(final SessionContext ctx, final User user, final Collection discounts)
	{
		final HashSet toRemove = new HashSet(this.getEurope1Discounts(ctx, user));
		if (discounts != null)
		{
			toRemove.removeAll(discounts);
		}

		try
		{
			final Iterator e = toRemove.iterator();

			while (e.hasNext())
			{
				final GlobalDiscountRow gdr = (GlobalDiscountRow) e.next();
				if (gdr.getUser(ctx) != null)
				{
					gdr.remove(ctx);
				}
			}

		}
		catch (final ConsistencyCheckException arg6)
		{
			throw new JaloSystemException(arg6);
		}
	}

	@Override
	public Collection getTaxValues(final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		SessionContext ctx;
		AbstractOrder order;
		ArrayList ret;
		Currency reqCurr;
		String reqIsoCode;
		Iterator arg8;
		boolean hasValue;
		if (this.isCachingTaxes())
		{
			ctx = this.getSession().getSessionContext();
			order = entry.getOrder(ctx);
			final Collection rows1 = this.getCachedTaxes(entry.getProduct(ctx), this.getPTG(ctx, entry), order.getUser(ctx),
					this.getUTG(ctx, entry), order.getDate(ctx));
			if (rows1.isEmpty())
			{
				return Collections.EMPTY_LIST;
			}
			else
			{
				ret = new ArrayList(rows1.size());
				reqCurr = order.getCurrency(ctx);
				reqIsoCode = reqCurr.getIsoCode();

				TaxValue tax1;
				for (arg8 = rows1.iterator(); arg8.hasNext(); ret.add(tax1))
				{
					final TaxValue tr1 = (TaxValue) arg8.next();
					tax1 = tr1;
					hasValue = tr1.isAbsolute();
					if (hasValue)
					{
						final String abs1 = tr1.getCurrencyIsoCode();
						if (abs1 != null && !abs1.equals(reqIsoCode))
						{
							tax1 = new TaxValue(tr1.getCode(),
									C2LManager.getInstance().getCurrencyByIsoCode(abs1).convertAndRound(reqCurr, tr1.getValue()), true,
									reqIsoCode);
						}
					}
				}

				return ret;
			}
		}
		else
		{
			ctx = this.getSession().getSessionContext();
			order = entry.getOrder(ctx);
			final List rows = this.matchTaxRows(entry.getProduct(ctx), this.getPTG(ctx, entry), order.getUser(ctx),
					this.getUTG(ctx, entry), order.getDate(ctx), -1);
			if (rows.isEmpty())
			{
				return Collections.EMPTY_LIST;
			}
			else
			{
				ret = new ArrayList(rows.size());
				reqCurr = order.getCurrency(ctx);
				reqIsoCode = reqCurr.getIsoCode();

				Tax tax;
				boolean abs;
				double value;
				String isoCode;
				for (arg8 = rows.iterator(); arg8.hasNext(); ret.add(new TaxValue(tax.getCode(), value, abs, isoCode)))
				{
					final TaxRow tr = (TaxRow) arg8.next();
					tax = tr.getTax(ctx);
					hasValue = tr.hasValue(ctx);
					abs = hasValue ? tr.isAbsoluteAsPrimitive() : tax.isAbsolute().booleanValue();
					if (abs)
					{
						final Currency rowCurr = abs ? (hasValue ? tr.getCurrency(ctx) : tax.getCurrency(ctx)) : null;
						if (rowCurr != null && !rowCurr.equals(reqCurr))
						{
							final double taxDoubleValue = hasValue ? tr.getValue(ctx).doubleValue() : tax.getValue().doubleValue();
							value = rowCurr.convertAndRound(reqCurr, taxDoubleValue);
						}
						else
						{
							value = hasValue ? tr.getValue(ctx).doubleValue() : tax.getValue().doubleValue();
						}

						isoCode = reqIsoCode;
					}
					else
					{
						value = hasValue ? tr.getValue(ctx).doubleValue() : tax.getValue().doubleValue();
						isoCode = null;
					}
				}

				return ret;
			}
		}
	}

	@Override
	public PriceValue getBasePrice(final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		final SessionContext ctx = this.getSession().getSessionContext();
		final AbstractOrder order = entry.getOrder(ctx);
		Currency currency = null;
		EnumerationValue productGroup = null;
		User user = null;
		EnumerationValue userGroup = null;
		Unit unit = null;
		long quantity = 0L;
		boolean net = false;
		Date date = null;
		final Product product = entry.getProduct();
		final boolean giveAwayMode = entry.isGiveAway(ctx).booleanValue();
		final boolean entryIsRejected = entry.isRejected(ctx).booleanValue();
		PriceRow row;
		if (giveAwayMode && entryIsRejected)
		{
			row = null;
		}
		else
		{
			row = this.matchPriceRowForPrice(ctx, product, productGroup = this.getPPG(ctx, product), user = order.getUser(),
					userGroup = this.getUPG(ctx, user), quantity = entry.getQuantity(ctx).longValue(), unit = entry.getUnit(ctx),
					currency = order.getCurrency(ctx), date = order.getDate(ctx), net = order.isNet().booleanValue(), giveAwayMode);
		}

		if (row != null)
		{
			final Currency msg1 = row.getCurrency();
			double price;
			if (currency.equals(msg1))
			{
				price = row.getPriceAsPrimitive() / row.getUnitFactorAsPrimitive();
			}
			else
			{
				price = msg1.convert(currency, row.getPriceAsPrimitive() / row.getUnitFactorAsPrimitive());
			}

			final Unit priceUnit = row.getUnit();
			final Unit entryUnit = entry.getUnit();
			final double convertedPrice = priceUnit.convertExact(entryUnit, price);
			return new PriceValue(currency.getIsoCode(), convertedPrice, row.isNetAsPrimitive());
		}
		else if (giveAwayMode)
		{
			return new PriceValue(order.getCurrency(ctx).getIsoCode(), 0.0D, order.isNet().booleanValue());
		}
		else
		{
			final String msg = Localization
					.getLocalizedString("exception.europe1pricefactory.getbaseprice.jalopricefactoryexception1", new Object[]
			{ product, productGroup, user, userGroup, Long.toString(quantity), unit, currency, date, Boolean.toString(net) });
			throw new JaloPriceFactoryException(msg, 0);
		}
	}

	@Override
	public List getDiscountValues(final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		final SessionContext ctx = this.getSession().getSessionContext();
		final AbstractOrder order = entry.getOrder(ctx);
		return Europe1Tools.createDiscountValueList(this.matchDiscountRows(entry.getProduct(ctx), this.getPDG(ctx, entry),
				order.getUser(ctx), this.getUDG(ctx, entry), order.getCurrency(ctx), order.getDate(ctx), -1));
	}

	@Override
	public List getDiscountValues(final AbstractOrder order) throws JaloPriceFactoryException
	{
		final SessionContext ctx = this.getSession().getSessionContext();
		return Europe1Tools.createDiscountValueList(this.matchDiscountRows((Product) null, (EnumerationValue) null,
				order.getUser(ctx), this.getUDG(ctx, order), order.getCurrency(ctx), order.getDate(ctx), -1));
	}

	@Override
	public ProductPriceInformations getAllPriceInformations(final SessionContext ctx, final Product product, final Date date,
			final boolean net) throws JaloPriceFactoryException
	{
		final User user = ctx.getUser();
		final Currency curr = ctx.getCurrency();
		final List taxes = this.getTaxInformations(product, this.getPTG(ctx, product), user, this.getUTG(ctx, user), date);
		final List prices = this.getPriceInformations(ctx, product, this.getPPG(ctx, product), user, this.getUPG(ctx, user), curr,
				net, date, Europe1Tools.getTaxValues(taxes));
		final List discounts = this.getDiscountInformations(product, this.getPDG(ctx, product), user, this.getUDG(ctx, user), curr,
				date);
		return new ProductPriceInformations(prices, taxes, discounts);
	}

	@Override
	public List getProductDiscountInformations(final SessionContext ctx, final Product product, final Date date, final boolean net)
			throws JaloPriceFactoryException
	{
		return this.getDiscountInformations(product, this.getPDG(ctx, product), ctx.getUser(), this.getUDG(ctx, ctx.getUser()),
				ctx.getCurrency(), date);
	}

	@Override
	public List getProductPriceInformations(final SessionContext ctx, final Product product, final Date date, final boolean net)
			throws JaloPriceFactoryException
	{
		return this.getPriceInformations(ctx, product, this.getPPG(ctx, product), ctx.getUser(), this.getUPG(ctx, ctx.getUser()),
				ctx.getCurrency(), net, date, (Collection) null);
	}

	@Override
	public List getProductTaxInformations(final SessionContext ctx, final Product product, final Date date)
			throws JaloPriceFactoryException
	{
		return this.getTaxInformations(product, this.getPTG(ctx, product), ctx.getUser(), this.getUTG(ctx, ctx.getUser()), date);
	}

	@Override
	protected List getDiscountInformations(final Product product, final EnumerationValue productGroup, final User user,
			final EnumerationValue userGroup, final Currency curr, final Date date) throws JaloPriceFactoryException
	{
		final LinkedList discountInfos = new LinkedList();
		final Iterator it = this.matchDiscountRows(product, productGroup, user, userGroup, curr, date, -1).iterator();

		while (it.hasNext())
		{
			discountInfos.add(new Europe1DiscountInformation((DiscountRow) it.next()));
		}

		return discountInfos;
	}



	@Override
	protected List<PriceRow> filterPriceRows(final List<PriceRow> priceRows)
	{
		if (priceRows.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		else
		{
			Unit lastUnit = null;
			long lastMin = -1L;
			final ArrayList ret = new ArrayList(priceRows);
			final ListIterator it = ret.listIterator();

			while (true)
			{
				while (it.hasNext())
				{
					final PriceRow row = (PriceRow) it.next();
					final long min = row.getMinQuantity();
					final Unit unit = row.getUnit();
					if (lastUnit != null && lastUnit.equals(unit) && lastMin == min)
					{
						it.remove();
					}
					else
					{
						lastUnit = unit;
						lastMin = min;
					}
				}

				return ret;
			}
		}
	}


	@Override
	protected List<? extends AbstractDiscountRow> filterDiscountRows4Price(final Collection<? extends AbstractDiscountRow> rows,
			final Date date)
	{
		if (rows.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		else
		{
			final ArrayList ret = new ArrayList(rows);
			final ListIterator it = ret.listIterator();

			while (it.hasNext())
			{
				final AbstractDiscountRow abstractDiscountRow = (AbstractDiscountRow) it.next();
				final StandardDateRange dataRange = abstractDiscountRow.getDateRange();
				if (dataRange != null && !dataRange.encloses(date))
				{
					it.remove();
				}
			}

			return ret;
		}
	}

	@Override
	protected Collection<? extends AbstractDiscountRow> queryDiscounts4Price(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup, final User user, final EnumerationValue userGroup)
	{
		final boolean global = product == null && productGroup == null;
		final String discountRowTypeCode = global ? TC.GLOBALDISCOUNTROW : TC.DISCOUNTROW;
		final PK productPk = product == null ? null : product.getPK();
		final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
		final PK userPk = user == null ? null : user.getPK();
		final PK userGroupPk = userGroup == null ? null : userGroup.getPK();
		final String productId = this.extractProductId(ctx, product);
		final PDTRowsQueryBuilder builder = this.getPDTRowsQueryBuilderFor(discountRowTypeCode);
		final QueryWithParams queryAndParams = builder.withAnyProduct().withAnyUser().withProduct(productPk)
				.withProductId(productId).withProductGroup(productGroupPk).withUser(userPk).withUserGroup(userGroupPk).build();
		return FlexibleSearch.getInstance().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(), DiscountRow.class)
				.getResult();
	}










	@Override
	protected PDTRowsQueryBuilder getPDTRowsQueryBuilderFor(final String type)
	{
		return PDTRowsQueryBuilder.defaultBuilder(type);
	}





	private boolean useFastAlg()
	{
		return this.useFastAlg(this.getSession().getSessionContext());
	}

	private boolean useFastAlg(final SessionContext ctx)
	{
		return ctx == null || !Boolean.FALSE.equals(ctx.getAttribute("use.fast.algorithms"));
	}

	@Override
	public Collection getProductPriceRows(final Product product, final EnumerationValue productGroup)
	{
		return this.getProductPriceRows(this.getSession().getSessionContext(), product, productGroup);
	}


	@Override
	public Collection getProductPriceRowsFast(final SessionContext ctx, final Product product, final EnumerationValue productGroup)
	{
		if (product == null)
		{
			throw new JaloInvalidParameterException("cannot find price rows without product ", 0);
		}
		else
		{
			final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
			final String productId = this.extractProductId(ctx, product);
			final PDTRowsQueryBuilder builder = this.getPDTRowsQueryBuilderFor(TC.PRICEROW);
			final QueryWithParams queryAndParams = builder.withAnyProduct().withProduct(product.getPK()).withProductId(productId)
					.withProductGroup(productGroupPk).build();
			return this.getSession().getFlexibleSearch().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(),
					Collections.singletonList(PriceRow.class), true, true, 0, -1).getResult();
		}
	}

	@Override
	public Collection getProductTaxRows(final Product product, final EnumerationValue productGroup)
	{
		return this.getProductTaxRows(this.getSession().getSessionContext(), product, productGroup);
	}



	@Override
	public Collection getProductTaxRowsFast(final SessionContext ctx, final Product product, final EnumerationValue productGroup)
	{
		if (product == null)
		{
			throw new JaloInvalidParameterException("cannot find tax rows without product ", 0);
		}
		else
		{
			final String typeCode = TypeManager.getInstance().getComposedType(TaxRow.class).getCode();
			final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
			final String productId = this.extractProductId(ctx, product);
			final PDTRowsQueryBuilder builder = this.getPDTRowsQueryBuilderFor(typeCode);
			final QueryWithParams queryAndParams = builder.withAnyProduct().withProduct(product.getPK()).withProductId(productId)
					.withProductGroup(productGroupPk).build();
			return this.getSession().getFlexibleSearch().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(),
					Collections.singletonList(TaxRow.class), true, true, 0, -1).getResult();
		}
	}

	@Override
	public Collection getProductDiscountRows(final Product product, final EnumerationValue productGroup)
	{
		return this.getProductDiscountRows(this.getSession().getSessionContext(), product, productGroup);
	}


	@Override
	public Collection getProductDiscountRowsFast(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup)
	{
		if (product == null)
		{
			throw new JaloInvalidParameterException("cannot find price rows without product ", 0);
		}
		else
		{
			final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
			final String productId = this.extractProductId(ctx, product);
			final PDTRowsQueryBuilder builder = this.getPDTRowsQueryBuilderFor(TC.DISCOUNTROW);
			final QueryWithParams queryAndParams = builder.withAnyProduct().withProduct(product.getPK()).withProductId(productId)
					.withProductGroup(productGroupPk).build();
			return this.getSession().getFlexibleSearch().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(),
					Collections.singletonList(DiscountRow.class), true, true, 0, -1).getResult();
		}
	}

	@Override
	protected String extractProductId(final SessionContext ctx, final Product product)
	{
		final String idFromContext = (String) (ctx != null ? ctx.getAttribute("productId") : null);
		return idFromContext != null ? idFromContext : this.extractProductId(product);
	}

	@Override
	protected String extractProductId(final Product product)
	{
		return product == null ? null : product.getCode();
	}

	@Override
	public Collection getUserGlobalDiscountRows(final User user, final EnumerationValue userGroup)
			throws JaloInvalidParameterException
	{
		if (user == null && userGroup == null)
		{
			throw new JaloInvalidParameterException("cannot match price without user and user group - at least one must be present",
					0);
		}
		else
		{
			final StringBuilder select = new StringBuilder();
			final StringBuilder query = new StringBuilder();
			final TypeManager typeManager = TypeManager.getInstance();
			select.append("SELECT ");
			select.append("{dr:").append(Item.PK).append("}, ");
			select.append("CASE WHEN {dr:").append("startTime").append("} IS NULL THEN 2 ELSE 1 END as drOrd ");
			select.append("FROM {").append(typeManager.getComposedType(GlobalDiscountRow.class).getCode()).append(" AS dr JOIN ");
			select.append(typeManager.getComposedType(Discount.class).getCode()).append(" AS disc ON {dr:").append("discount")
					.append("}={disc:").append(Item.PK).append("}  LEFT JOIN ");
			select.append(typeManager.getComposedType(Currency.class).getCode()).append(" AS curr ON {dr:").append("currency")
					.append("}={curr:").append(Item.PK).append("} } ");
			select.append("WHERE ");
			final HashMap values = new HashMap();
			this.appendUserConditions(query, "dr", user, userGroup, values, "ug");
			query.append(" ORDER BY ");
			query.append("{disc:").append("code").append("} ASC");
			query.append(",drOrd ASC");
			query.append(", CASE WHEN {").append("currency").append("} IS NULL THEN 0 ELSE 1 END ASC");
			query.append(",{curr:").append("isocode").append("} ASC");
			return FlexibleSearch.getInstance().search(select.toString() + query.toString(), values,
					Collections.singletonList(GlobalDiscountRow.class), true, true, 0, -1).getResult();
		}
	}


	@Override
	protected List<TaxRow> filterTaxRows4Price(final Collection<TaxRow> rows, final Date date)
	{
		if (rows.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		else
		{
			final ArrayList ret = new ArrayList(rows);
			final ListIterator it = ret.listIterator();

			while (it.hasNext())
			{
				final TaxRow taxRow = (TaxRow) it.next();
				final StandardDateRange dateRange = taxRow.getDateRange();
				if (dateRange != null && !dateRange.encloses(date))
				{
					it.remove();
				}
			}

			return ret;
		}
	}

	@Override
	protected Collection<TaxRow> superQueryTax4Price(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup, final User user, final EnumerationValue userGroup)
	{
		final String taxRowTypeCode = TypeManager.getInstance().getComposedType(TaxRow.class).getCode();
		final PK productPk = product == null ? null : product.getPK();
		final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
		final PK userPk = user == null ? null : user.getPK();
		final PK userGroupPk = userGroup == null ? null : userGroup.getPK();
		final String productId = this.extractProductId(ctx, product);
		final PDTRowsQueryBuilder builder = this.getPDTRowsQueryBuilderFor(taxRowTypeCode);
		final QueryWithParams queryAndParams = builder.withAnyProduct().withAnyUser().withProduct(productPk)
				.withProductGroup(productGroupPk).withProductId(productId).withUser(userPk).withUserGroup(userGroupPk).build();
		return FlexibleSearch.getInstance().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(), TaxRow.class)
				.getResult();
	}

	@Override
	protected Collection<TaxRow> queryTax4Price(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup, final User user, final EnumerationValue userGroup)
	{
		ArrayList results = null;
		final CatalogManager catalogManager = CatalogManager.getInstance();
		final Iterator arg9 = this.superQueryTax4Price(ctx, product, productGroup, user, userGroup).iterator();

		while (true)
		{
			CatalogVersion taxRowCatalogVersion;
			TaxRow taxRow;
			do
			{
				if (!arg9.hasNext())
				{
					return results == null ? Collections.EMPTY_LIST : results;
				}

				taxRow = (TaxRow) arg9.next();
				taxRowCatalogVersion = catalogManager.getCatalogVersion(taxRow);
			}
			while (taxRowCatalogVersion != null && !taxRowCatalogVersion.equals(catalogManager.getCatalogVersion(product)));

			if (results == null)
			{
				results = new ArrayList();
			}

			results.add(taxRow);
		}
	}

	@Override
	protected void appendProductConditions(final StringBuilder query, final String alias, final Product product,
			final EnumerationValue productGroup, final Map values, final String pgConstant)
	{
		query.append("{").append(alias).append(":").append("productMatchQualifier").append("} IN ( ?anyP ");
		values.put("anyP", Long.valueOf(PK.NULL_PK.getLongValue()));
		if (product != null)
		{
			query.append(",?product ");
			values.put("product", Long.valueOf(product.getPK().getLongValue()));
		}

		if (productGroup != null)
		{
			query.append(",?pg ");
			values.put("pg", Long.valueOf(productGroup.getPK().getLongValue()));
		}

		query.append(")");
	}

	@Override
	protected void appendUserConditions(final StringBuilder query, final String alias, final User user,
			final EnumerationValue userGroup, final Map values, final String ugConst)
	{
		query.append("{").append(alias).append(":").append("userMatchQualifier").append("} IN ( ?anyU ");
		values.put("anyU", Long.valueOf(PK.NULL_PK.getLongValue()));
		if (user != null)
		{
			query.append(",?user ");
			values.put("user", Long.valueOf(user.getPK().getLongValue()));
		}

		if (userGroup != null)
		{
			query.append(",?ug ");
			values.put("ug", Long.valueOf(userGroup.getPK().getLongValue()));
		}

		query.append(")");
	}

	@Override
	protected EnumerationValue getEnumFromContextOrItem(final SessionContext ctx, final ExtensibleItem item,
			final String qualifier)
	{
		EnumerationValue enumerationValue = (EnumerationValue) (ctx != null ? ctx.getAttribute(qualifier) : null);
		if (enumerationValue == null)
		{
			enumerationValue = item != null ? (EnumerationValue) item.getProperty(ctx, qualifier) : null;
		}

		return enumerationValue;
	}

	@Override
	protected EnumerationValue getPTG(final SessionContext ctx, final AbstractOrderEntry entry)
	{
		final EnumerationValue overridePTG = this.getEurope1PriceFactory_PTG(ctx, entry);
		return overridePTG == null ? this.getPTG(ctx, entry.getProduct()) : overridePTG;
	}

	@Override
	public EnumerationValue getPTG(final SessionContext ctx, final Product product)
	{
		return this.getEnumFromContextOrItem(ctx, product, "Europe1PriceFactory_PTG");
	}

	@Override
	protected EnumerationValue getPDG(final SessionContext ctx, final AbstractOrderEntry entry)
	{
		final EnumerationValue overridePDG = this.getEurope1PriceFactory_PDG(ctx, entry);
		return overridePDG == null ? this.getPDG(ctx, entry.getProduct()) : overridePDG;
	}

	@Override
	public EnumerationValue getPDG(final SessionContext ctx, final Product product)
	{
		return this.getEnumFromContextOrItem(ctx, product, "Europe1PriceFactory_PDG");
	}

	@Override
	protected EnumerationValue getPPG(final SessionContext ctx, final AbstractOrderEntry entry)
	{
		final EnumerationValue overridePPG = this.getEurope1PriceFactory_PPG(ctx, entry);
		return overridePPG == null ? this.getPPG(ctx, entry.getProduct()) : overridePPG;
	}

	@Override
	public EnumerationValue getPPG(final SessionContext ctx, final Product product)
	{
		return this.getEnumFromContextOrItem(ctx, product, "Europe1PriceFactory_PPG");
	}

	@Override
	protected EnumerationValue getUTG(final SessionContext ctx, final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		return this.getUTG(ctx, entry.getOrder());
	}

	@Override
	protected EnumerationValue getUTG(final SessionContext ctx, final AbstractOrder order) throws JaloPriceFactoryException
	{
		final EnumerationValue overrideUTG = this.getEurope1PriceFactory_UTG(ctx, order);
		return overrideUTG == null ? this.getUTG(ctx, order.getUser()) : overrideUTG;
	}

	@Override
	public EnumerationValue getUTG(final SessionContext ctx, final User user) throws JaloPriceFactoryException
	{
		final EnumerationValue enumerationValue = this.getEnumFromContextOrItem(ctx, user, "Europe1PriceFactory_UTG");
		return enumerationValue != null ? enumerationValue : this.getEnumFromGroups(user, "userTaxGroup");
	}

	@Override
	public EnumerationValue getUDG(final SessionContext ctx, final User user) throws JaloPriceFactoryException
	{
		final EnumerationValue enumerationValue = this.getEnumFromContextOrItem(ctx, user, "Europe1PriceFactory_UDG");
		return enumerationValue != null ? enumerationValue : this.getEnumFromGroups(user, "userDiscountGroup");
	}

	@Override
	protected EnumerationValue getUDG(final SessionContext ctx, final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		return this.getUDG(ctx, entry.getOrder());
	}

	@Override
	protected EnumerationValue getUDG(final SessionContext ctx, final AbstractOrder order) throws JaloPriceFactoryException
	{
		final EnumerationValue overrideUDG = this.getEurope1PriceFactory_UDG(ctx, order);
		return overrideUDG == null ? this.getUDG(ctx, order.getUser()) : overrideUDG;
	}

	@Override
	public EnumerationValue getUPG(final SessionContext ctx, final User user) throws JaloPriceFactoryException
	{
		final EnumerationValue enumerationValue = this.getEnumFromContextOrItem(ctx, user, "Europe1PriceFactory_UPG");
		return enumerationValue != null ? enumerationValue : this.getEnumFromGroups(user, "userPriceGroup");
	}

	@Override
	protected EnumerationValue getUPG(final SessionContext ctx, final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		return this.getUPG(ctx, entry.getOrder());
	}

	@Override
	protected EnumerationValue getUPG(final SessionContext ctx, final AbstractOrder order) throws JaloPriceFactoryException
	{
		final EnumerationValue overrideUPG = this.getEurope1PriceFactory_UPG(ctx, order);
		return overrideUPG == null ? this.getUPG(ctx, order.getUser()) : overrideUPG;
	}

	@Override
	protected EnumerationValue getEnumFromGroups(final User user, final String attribute) throws JaloPriceFactoryException
	{
		EnumerationValue enumerationValue = null;
		final HashSet controlSet = new HashSet();
		Object groups = user.getGroups();

		while (enumerationValue == null && !((Collection) groups).isEmpty())
		{
			final HashSet nextGroups = new HashSet();
			final Iterator it = ((Collection) groups).iterator();

			while (it.hasNext())
			{
				final UserGroup userGroup = (UserGroup) it.next();
				controlSet.add(userGroup);
				final EnumerationValue ugValue = (EnumerationValue) userGroup.getProperty(attribute);
				if (ugValue != null)
				{
					if (enumerationValue != null && !ugValue.equals(enumerationValue))
					{
						throw new JaloPriceFactoryException("multiple " + attribute + " values found for user " + user.getUID()
								+ " from its groups " + groups + " : " + enumerationValue.getCode() + " != " + ugValue.getCode(), 0);
					}

					enumerationValue = ugValue;
				}
				else if (enumerationValue == null)
				{
					nextGroups.addAll(userGroup.getGroups());
				}
			}

			if (enumerationValue == null)
			{
				nextGroups.removeAll(controlSet);
				groups = nextGroups;
			}
		}

		return enumerationValue;
	}

	@Override
	public EnumerationValue getUserPriceGroup(final String code)
	{
		try
		{
			return this.getSession().getEnumerationManager().getEnumerationValue("UserPriceGroup", code);
		}
		catch (final JaloItemNotFoundException arg1)
		{
			return null;
		}
	}

	@Override
	public EnumerationValue createUserPriceGroup(final String code) throws ConsistencyCheckException
	{
		return this.getSession().getEnumerationManager().createEnumerationValue("UserPriceGroup", code);
	}

	@Override
	public EnumerationValue getProductTaxGroup(final String code)
	{
		try
		{
			return this.getSession().getEnumerationManager().getEnumerationValue("ProductTaxGroup", code);
		}
		catch (final JaloItemNotFoundException arg1)
		{
			return null;
		}
	}

	@Override
	public EnumerationValue createProductTaxGroup(final String code) throws ConsistencyCheckException
	{
		return this.getSession().getEnumerationManager().createEnumerationValue("ProductTaxGroup", code);
	}

	@Override
	public EnumerationValue getUserTaxGroup(final String code)
	{
		try
		{
			return this.getSession().getEnumerationManager().getEnumerationValue("UserTaxGroup", code);
		}
		catch (final JaloItemNotFoundException arg1)
		{
			return null;
		}
	}

	@Override
	public EnumerationValue createUserTaxGroup(final String code) throws ConsistencyCheckException
	{
		return this.getSession().getEnumerationManager().createEnumerationValue("UserTaxGroup", code);
	}

	@Override
	public boolean isCreatorDisabled()
	{
		return false;
	}

	@Override
	public void createProjectData(final Map params, final JspContext jspc)
	{
	}



	@Override
	protected boolean isCachingTaxes()
	{
		Boolean localCaches = this.cachesTaxes;
		if (localCaches == null)
		{
			synchronized (this)
			{
				localCaches = this.cachesTaxes;
				if (localCaches == null)
				{
					if (this.getTenant().getConfig().getBoolean("europe1.cache.taxes", true))
					{
						this.fillTaxCache();
						localCaches = Boolean.TRUE;
					}
					else
					{
						localCaches = Boolean.FALSE;
					}

					this.cachesTaxes = localCaches;
				}
			}
		}

		return localCaches.booleanValue();
	}




	private void createSearchRestrictions(final JspContext jspc)
	{
		final TypeManager typeman = this.getSession().getTypeManager();
		final UserGroup catalogViewers = this.getSession().getUserManager().getUserGroupByGroupID(USER.CUSTOMER_USERGROUP);
		if (typeman.getSearchRestriction(typeman.getComposedType(PriceRow.class), "Frontend_Group_PriceRows") == null)
		{
			typeman.createRestriction("Frontend_Group_PriceRows", catalogViewers, typeman.getComposedType(PriceRow.class),
					"(  {item:product} IS NOT NULL OR{item:"
							+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION + "} IS NULL OR "
							+ "{" + "item" + ":"
							+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.PriceRow.CATALOGVERSION
							+ "} IN (?session." + "catalogversions" + ") )");
		}

		if (typeman.getSearchRestriction(typeman.getComposedType(TaxRow.class), "Frontend_Group_TaxRows") == null)
		{
			typeman.createRestriction("Frontend_Group_TaxRows", catalogViewers, typeman.getComposedType(TaxRow.class),
					"(  {item:product} IS NOT NULL OR{item:"
							+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION + "} IS NULL OR "
							+ "{" + "item" + ":" + de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION
							+ "} IN (?session." + "catalogversions" + ") )");
		}

		if (typeman.getSearchRestriction(typeman.getComposedType(DiscountRow.class), "Frontend_Group_DiscountRows") == null)
		{
			typeman.createRestriction("Frontend_Group_DiscountRows", catalogViewers, typeman.getComposedType(DiscountRow.class),
					"(  {item:product} IS NOT NULL OR{item:"
							+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION + "} IS NULL OR "
							+ "{" + "item" + ":"
							+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.DiscountRow.CATALOGVERSION
							+ "} IN (?session." + "catalogversions" + ") )");
		}

	}

	@Override
	public void createEssentialData(final Map params, final JspContext jspc) throws Exception
	{
		if ("init".equals(params.get("initmethod")))
		{
			this.createSearchRestrictions(jspc);
		}

	}

	@Override
	protected PriceRow getCounterpartItem(final SessionContext ctx, final PriceRow pricerow, final CatalogVersion targetVersion)
	{
		Preconditions.checkArgument(pricerow.getProduct() == null);
		final EnumerationValue pricegroup = pricerow.getPg();
		final User user = pricerow.getUser();
		final EnumerationValue usergroup = pricerow.getUg();
		final HashMap params = new HashMap();
		if (pricegroup != null)
		{
			params.put("pg", pricegroup);
		}

		if (user != null)
		{
			params.put("u", user);
		}

		if (usergroup != null)
		{
			params.put("ug", usergroup);
		}

		params.put("unit", pricerow.getUnit());
		params.put("min", pricerow.getMinqtd());
		params.put("curr", pricerow.getCurrency());
		params.put("net", pricerow.isNet());
		params.put("tgt", targetVersion);
		final List rows = FlexibleSearch.getInstance()
				.search(
						"SELECT {" + Item.PK + "} FROM {" + TC.PRICEROW + "} " + "WHERE {" + "pg" + "}"
								+ (pricegroup == null ? " IS NULL" : " = ?pg") + " AND " + "{" + "user" + "}"
								+ (user == null ? " IS NULL" : " = ?u") + " AND " + "{" + "ug" + "}"
								+ (usergroup == null ? " IS NULL" : " = ?ug") + " AND " + "{" + "unit" + "} = ?unit AND " + "{" + "minqtd"
								+ "} = ?min AND " + "{" + "currency" + "} = ?curr AND " + "{" + "net" + "} = ?net AND " + "{"
								+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.PriceRow.CATALOGVERSION + "} = ?tgt ",
						params, PriceRow.class)
				.getResult();
		return rows.isEmpty() ? null : (PriceRow) rows.get(0);
	}

	@Override
	protected TaxRow getCounterpartItem(final SessionContext ctx, final TaxRow taxrow, final CatalogVersion targetVersion)
	{
		Preconditions.checkArgument(taxrow.getProduct() == null);
		final EnumerationValue pricegroup = taxrow.getPg();
		final User user = taxrow.getUser();
		final EnumerationValue usergroup = taxrow.getUg();
		final HashMap params = new HashMap();
		if (pricegroup != null)
		{
			params.put("pg", pricegroup);
		}

		if (user != null)
		{
			params.put("u", user);
		}

		if (usergroup != null)
		{
			params.put("ug", usergroup);
		}

		params.put("tax", taxrow.getTax());
		params.put("tgt", targetVersion);
		final List rows = FlexibleSearch.getInstance()
				.search(
						"SELECT {" + Item.PK + "} FROM {" + TC.TAXROW + "} " + "WHERE {" + "pg" + "}"
								+ (pricegroup == null ? " IS NULL" : " = ?pg") + " AND " + "{" + "user" + "}"
								+ (user == null ? " IS NULL" : " = ?u") + " AND " + "{" + "ug" + "}"
								+ (usergroup == null ? " IS NULL" : " = ?ug") + " AND " + "{" + "tax" + "} = ?tax AND " + "{"
								+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION + "} = ?tgt ",
						params, TaxRow.class)
				.getResult();
		return rows.isEmpty() ? null : (TaxRow) rows.get(0);
	}

	@Override
	protected DiscountRow getCounterpartItem(final SessionContext ctx, final DiscountRow discountrow,
			final CatalogVersion targetVersion)
	{
		Preconditions.checkArgument(discountrow.getProduct() == null);
		final EnumerationValue pricegroup = discountrow.getPg();
		final User user = discountrow.getUser();
		final EnumerationValue usergroup = discountrow.getUg();
		final HashMap params = new HashMap();
		if (pricegroup != null)
		{
			params.put("pg", pricegroup);
		}

		if (user != null)
		{
			params.put("u", user);
		}

		if (usergroup != null)
		{
			params.put("ug", usergroup);
		}

		params.put("discount", discountrow.getDiscount());
		params.put("tgt", targetVersion);
		final List rows = FlexibleSearch.getInstance()
				.search(
						"SELECT {" + Item.PK + "} FROM {" + TC.TAXROW + "} " + "WHERE {" + "pg" + "}"
								+ (pricegroup == null ? " IS NULL" : " = ?pg") + " AND " + "{" + "user" + "}"
								+ (user == null ? " IS NULL" : " = ?u") + " AND " + "{" + "ug" + "}"
								+ (usergroup == null ? " IS NULL" : " = ?ug") + " AND " + "{" + "tax" + "} = ?tax AND " + "{"
								+ de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION + "} = ?tgt ",
						params, DiscountRow.class)
				.getResult();
		return rows.isEmpty() ? null : (DiscountRow) rows.get(0);
	}

	@Override
	public void beforeItemCreation(final SessionContext ctx, final ComposedType type, final ItemAttributeMap attributes)
			throws JaloBusinessException
	{
		super.beforeItemCreation(ctx, type, attributes);
		final Class jaloClass = type.getJaloClass();
		if (!JobLog.class.isAssignableFrom(jaloClass) && !ItemSyncTimestamp.class.isAssignableFrom(jaloClass)
				&& !ProductFeature.class.isAssignableFrom(jaloClass) && !ChangeDescriptor.class.isAssignableFrom(jaloClass))
		{
			CatalogVersion catver;
			Product prod;
			if (PriceRow.class.isAssignableFrom(jaloClass))
			{
				catver = (CatalogVersion) attributes
						.get(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.PriceRow.CATALOGVERSION);
				if (catver == null)
				{
					prod = (Product) attributes.get("product");
					if (prod != null)
					{
						catver = CatalogManager.getInstance().getCatalogVersion((SessionContext) null, prod);
					}

					if (catver != null)
					{
						attributes.put(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.PriceRow.CATALOGVERSION,
								catver);
					}
				}

				attributes.setAttributeMode(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.PriceRow.CATALOGVERSION,
						AttributeMode.INITIAL);
			}
			else if (TaxRow.class.isAssignableFrom(jaloClass))
			{
				catver = (CatalogVersion) attributes
						.get(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION);
				if (catver == null)
				{
					prod = (Product) attributes.get("product");
					if (prod != null)
					{
						catver = CatalogManager.getInstance().getCatalogVersion((SessionContext) null, prod);
					}

					if (catver != null)
					{
						attributes.put(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION, catver);
					}
				}

				attributes.setAttributeMode(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.TaxRow.CATALOGVERSION,
						AttributeMode.INITIAL);
			}
			else if (DiscountRow.class.isAssignableFrom(jaloClass))
			{
				catver = (CatalogVersion) attributes
						.get(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.DiscountRow.CATALOGVERSION);
				if (catver == null)
				{
					prod = (Product) attributes.get("product");
					if (prod != null)
					{
						catver = CatalogManager.getInstance().getCatalogVersion((SessionContext) null, prod);
					}

					if (catver != null)
					{
						attributes.put(de.hybris.platform.europe1.constants.Europe1Constants.Attributes.DiscountRow.CATALOGVERSION,
								catver);
					}
				}

				attributes.setAttributeMode(
						de.hybris.platform.europe1.constants.Europe1Constants.Attributes.DiscountRow.CATALOGVERSION,
						AttributeMode.INITIAL);
			}

		}
	}



}
