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

import de.hybris.platform.core.Registry;
import de.hybris.platform.europe1.channel.strategies.RetrieveChannelStrategy;
import de.hybris.platform.europe1.enums.PriceRowChannel;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.util.localization.Localization;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everyreplypricefactory.constants.EveryreplypricefactoryConstants;



/**
 * This is the extension manager of the Everyreplypricefactory extension.
 */
public class EveryreplypricefactoryManager extends GeneratedEveryreplypricefactoryManager
{
	/** Edit the local|project.properties to change logging behavior (properties 'log4j.*'). */
	private static final Logger LOG = LoggerFactory.getLogger(EveryreplypricefactoryManager.class);
	@Resource(name = "retrieveChannelStrategy")
	RetrieveChannelStrategy aRetrieveChannelStrategy;
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
		LOG.debug("constructor of EveryreplypricefactoryManager called.");
	}

	/**
	 * Use this method to do some basic work only ONCE in the lifetime of a tenant resp. "deployment". This method is called
	 * after manager creation (for example within startup of a tenant). Note that if you have more than one tenant you have
	 * a manager instance for each tenant.
	 */
	@Override
	public void init()
	{
		LOG.debug("init() of EveryreplypricefactoryManager called, current tenant: {}", getTenant().getTenantID());
	}

	/**
	 * Use this method as a callback when the manager instance is being destroyed (this happens before system
	 * initialization, at redeployment or if you shutdown your VM). Note that if you have more than one tenant you have a
	 * manager instance for each tenant.
	 */
	@Override
	public void destroy()
	{
		LOG.debug("destroy() of EveryreplypricefactoryManager called, current tenant: {}", getTenant().getTenantID());
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
	public PriceRow matchPriceRowForPrice(final SessionContext ctx, final Product product, final EnumerationValue productGroup,
			final User user, final EnumerationValue userGroup, final long qtd, final Unit unit, final Currency currency,
			final Date date, final boolean net, final boolean giveAwayMode) throws JaloPriceFactoryException
	{
		if (product == null && productGroup == null)
		{

			throw new JaloPriceFactoryException(
					"cannot match price without product and product group - at least one must be present", 0);


		}
		else if (user == null && userGroup == null)
		{

			throw new JaloPriceFactoryException("cannot match price without user and user group - at least one must be present", 0);

		}
		else if (currency == null)
		{

			throw new JaloPriceFactoryException("cannot match price without currency", 0);

		}
		else if (date == null)
		{

			throw new JaloPriceFactoryException("cannot match price without date", 0);

		}
		else if (unit == null)
		{

			throw new JaloPriceFactoryException("cannot match price without unit", 0);

		}
		else
		{
			final Collection rows = this.queryPriceRows4Price(ctx, product, productGroup, user, userGroup, date, currency,
					giveAwayMode);
			if (!rows.isEmpty())
			{

				final PriceRowChannel channel = aRetrieveChannelStrategy.getChannel(ctx);
				final List list = this.filterPriceRows4Price(rows, qtd, unit, currency, date, giveAwayMode, channel);


				if (list.isEmpty())
				{

					return null;

				}
				else if (list.size() == 1)
				{

					return (PriceRow) list.get(0);


				}
				else
				{
					//kumud: need to implement
					//list.sort(new PriceRowMatchComparator(this, currency, net, unit));
					return (PriceRow) list.get(0);


				}
			}
			else
			{
				return null;
			}
		}
	}


	/*
	 * protected Collection<PriceRow> queryPriceRows4Price(SessionContext ctx, Product product, EnumerationValue
	 * productGroup, User user, EnumerationValue userGroup, Date date, Currency currency, boolean giveAwayMode) {
	 * PDTRowsQueryBuilder builder = this.getQueryPriceRowBasicData4PriceBuilder(ctx, product, productGroup, user,
	 * userGroup); QueryWithParams queryAndParams = builder.build();
	 *
	 * SearchResult search = FlexibleSearch.getInstance().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(),
	 * queryAndParams.getResultClassList(), true, true, 0, -1);
	 *
	 *
	 * Currency base = currency.isBase().booleanValue()?null:C2LManager.getInstance().getBaseCurrency(); return
	 * this.priceRowResultListToBasicData(o); }).filter((priceRowBasicData) -> { return
	 * priceRowBasicData.isCurrencyValid(currency, base); }).filter((priceRowBasicData) -> { return
	 * priceRowBasicData.isDateInRange(date); }).filter((priceRowBasicData) -> { return
	 * priceRowBasicData.isGiveAwayModeValid(giveAwayMode); }).map((priceRowBasicData) -> { return
	 * priceRowBasicData.getPK(); }).collect(Collectors.toList()); List pks = (List)search.getResult().stream().map((o) -> {
	 *
	 *
	 * return new LazyLoadItemList(WrapperFactory.getPrefetchLanguages(ctx), pks, 100); }
	 *
	 * private PDTRowsQueryBuilder getQueryPriceRowBasicData4PriceBuilder(SessionContext ctx, Product product,
	 * EnumerationValue productGroup, User user, EnumerationValue userGroup) { PK productPk = product ==
	 * null?null:product.getPK(); PK productGroupPk = productGroup == null?null:productGroup.getPK(); PK userPk = user ==
	 * null?null:user.getPK(); PK userGroupPk = userGroup == null?null:userGroup.getPK(); String productId =
	 * this.extractProductId(ctx, product);
	 *
	 * LinkedHashMap columns = new LinkedHashMap();
	 *
	 * columns.put(PriceRow.PK, PK.class); columns.put("startTime", Date.class); columns.put("endTime", Date.class);
	 * columns.put("currency", Currency.class); columns.put("giveAwayPrice", Boolean.class);
	 *
	 * DefaultPDTRowsQueryBuilder builder = new DefaultPDTRowsQueryBuilder(TC.PRICEROW, columns); return
	 * builder.withAnyProduct().withAnyUser().withProduct(productPk).withProductId(productId).withProductGroup(
	 * productGroupPk).withUser(userPk).withUserGroup(userGroupPk); }
	 */
}
