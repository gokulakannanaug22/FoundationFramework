/*******************************************************************************
 *   BSD License
 *    
 *   Copyright (c) 2017, AT&T Intellectual Property.  All other rights reserved.
 *    
 *   Redistribution and use in source and binary forms, with or without modification, are permitted
 *   provided that the following conditions are met:
 *    
 *   1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *      and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *      conditions and the following disclaimer in the documentation and/or other materials provided
 *      with the distribution.
 *   3. All advertising materials mentioning features or use of this software must display the
 *      following acknowledgement:  This product includes software developed by the AT&T.
 *   4. Neither the name of AT&T nor the names of its contributors may be used to endorse or
 *      promote products derived from this software without specific prior written permission.
 *    
 *   THIS SOFTWARE IS PROVIDED BY AT&T INTELLECTUAL PROPERTY ''AS IS'' AND ANY EXPRESS OR
 *   IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *   MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 *   SHALL AT&T INTELLECTUAL PROPERTY BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *   SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  LOSS OF USE, DATA, OR PROFITS;
 *   OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *   ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 *   DAMAGE.
 *******************************************************************************/
package com.att.idp.foundation.cosc.impl;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Map;
import java.util.Set;

import com.att.ajsc.logging.AjscEelfManager;
import com.att.detsplatform.cosc.api.COSCInterface;
import com.att.detsplatform.cosc.api.impl.ServiceFactory;
import com.att.detsplatform.cosc.base.COSCEntry;
import com.att.detsplatform.cosc.base.COSCKey;
import com.att.detsplatform.cosc.base.exceptions.COSCException;
import com.att.detsplatform.cosc.base.exceptions.InvalidEntryException;
import com.att.detsplatform.cosc.base.exceptions.TypeNotFoundException;
import com.att.eelf.configuration.EELFLogger;
import com.att.idp.foundation.cosc.COSCService;
import com.att.idp.foundation.service.JsonService;

// TODO: Auto-generated Javadoc
/**
 * COSC connect implementation class.
 *
 * @author sb527f
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class COSCServiceImpl implements COSCService {

	/** EELFLogger instance. */
	private static EELFLogger log = AjscEelfManager.getInstance().getLogger(COSCServiceImpl.class);

	/** COSC cache instance declaration. */
	private COSCInterface<COSCKey> cache;

	/**
	 * cache is set from ServiceFactory.
	 *
	 * @return COSCInterface
	 */
	@Override
	public COSCInterface<COSCKey> getCacheService() {
		if (cache == null) {
			cache = ServiceFactory.getInstance().getCacheService();
		}
		return cache;
	}

	/**
	 * Creates the COSCKey Object.
	 *
	 * @param pkey
	 *            the pkey
	 * @param keyName
	 *            the key name
	 * @return coscKey
	 */
	@Override
	public COSCKey createCOSCKey(final String pkey, final Enum<?> keyName) {
		return new COSCKey(pkey, keyName.toString(), APPLICATION_ID);

	}

	/**
	 * Create COSC Entry object for put() operation. It converts Domain object
	 * to JSON String.
	 *
	 * @param keyName
	 *            the key name
	 * @param obj
	 *            the obj
	 * @return - COSCEntry Object
	 */
	@Override
	public COSCEntry createCOSCEntry(final Enum<?> keyName, final Object obj) {

		return createCOSCEntry(keyName, null, obj);
	}

	/**
	 * Create COSC Entry object for put() operation. It converts Domain object
	 * to JSON String.
	 *
	 * @param keyName
	 *            the key name
	 * @param version
	 *            the version
	 * @param obj
	 *            the obj
	 * @return - COSCEntry Object
	 */
	@Override
	public COSCEntry createCOSCEntry(final Enum<?> keyName,final String version, final Object obj) {

		final Object domainJSONValue = JsonService.getJsonFromObject(obj);
		final COSCEntry coscEntry = new COSCEntry();
		coscEntry.setName(keyName.toString());
		if (isEmpty(version)) {
			coscEntry.setVersion(VERSION);
		} else {
			coscEntry.setVersion(version);
		}
		coscEntry.setValue(domainJSONValue);

		return coscEntry;
	}

	/**
	 * Invokes COSI API get() operation based on COSCKey.
	 *
	 * @param key
	 *            the key
	 * @return the COSC entry
	 * @throws COSCException
	 *             the COSC exception
	 */
	@Override
	public COSCEntry get(final COSCKey key) throws COSCException {
		return getCacheService().get(key);
	}

	/**
	 * This method accepts COSCKey and Entry object and invokes COSC put() API.
	 *
	 * @param key
	 *            the key
	 * @param entry
	 *            the entry
	 * @throws COSCException
	 *             the COSC exception
	 */
	@Override
	public void put(final COSCKey key, final COSCEntry entry) throws COSCException {
		if (isValidCOSCKey(key) && isValidCOSCEntry(entry)) {
			getCacheService().put(key, entry);
		}
	}

	/**
	 * This method invokes COSC delete API. Based on the COSC Key.
	 *
	 * @param key
	 *            the key
	 * @throws COSCException
	 *             the COSC exception
	 */
	public void delete(final COSCKey key) throws COSCException {
		if (isValidCOSCKey(key)) {
			getCacheService().delete(key);
		}
	}

	/**
	 * Gets the all.
	 *
	 * @param paramSet
	 *            the param set
	 * @return the all
	 * @throws TypeNotFoundException
	 *             the type not found exception
	 * @throws COSCException
	 *             the COSC exception
	 */
	@Override
	public Map<COSCKey, COSCEntry> getAll(final Set<COSCKey> paramSet) throws TypeNotFoundException, COSCException {
		return getCacheService().getAll(paramSet);
	}

	/**
	 * Put all.
	 *
	 * @param paramMap
	 *            the param map
	 * @throws TypeNotFoundException
	 *             the type not found exception
	 * @throws InvalidEntryException
	 *             the invalid entry exception
	 * @throws COSCException
	 *             the COSC exception
	 */
	@Override
	public void putAll(final Map<COSCKey, COSCEntry> paramMap)
			throws TypeNotFoundException, InvalidEntryException, COSCException {
		getCacheService().putAll(paramMap);
	}

	/**
	 * Delete all.
	 *
	 * @param paramSet
	 *            the param set
	 * @throws COSCException
	 *             the COSC exception
	 */
	@Override
	public void deleteAll(final Set<COSCKey> paramSet) throws COSCException {

		getCacheService().deleteAll(paramSet);
	}

	/**
	 * Returns true if COSCKey contains valid parameters.
	 *
	 * @param key
	 *            the key
	 * @return true, if is valid COSC key
	 */
	private boolean isValidCOSCKey(final COSCKey key) {

		boolean isValidCOSCKey = false;

		if (key != null && isNotBlank(key.getObjectKey()) && isNotBlank(key.getPrimaryKey())) {

			if (log.isDebugEnabled()) {
				log.debug("COSCCacheServiceImpl: COSC Key: Okey: " + key.getObjectKey() + "-- pKey: "
						+ key.getPrimaryKey());
			}
			key.setApplicationId(APPLICATION_ID);
			isValidCOSCKey = true;
		} else {
			if (log.isDebugEnabled()) {
				if (key == null) {
					log.debug("COSC operation is invoked without valid parameters. COSC key object is NULL.");
				} else {
					log.debug("COSC operation is invoked without valid parameters. Pkey: " + key.getPrimaryKey()
							+ "-Okey:" + key.getObjectKey());
				}
			}
		}
		return isValidCOSCKey;
	}

	/**
	 * Returns true if COSCEntry contains valid parameters.
	 *
	 * @param entry
	 *            the entry
	 * @return true, if is valid COSC entry
	 */
	private boolean isValidCOSCEntry(final COSCEntry entry) {

		boolean isValidCOSCEntry = false;

		if (entry != null && isNotBlank((String) entry.getValue()) && isNotBlank(entry.getName())) {
			final int length = ((String) entry.getValue()).length();
			if (log.isDebugEnabled()) {
				log.debug("COSCCacheServiceImpl: COSC Entry: Schema Name: " + entry.getName() + "-- Value JSON Length: "
						+ length);
				log.debug("COSCCacheServiceImpl: COSC Entry Object: " + entry.getValue());
			}
			isValidCOSCEntry = true;
		} else {
			if (log.isDebugEnabled()) {
				if (entry == null) {
					log.debug("COSC operation is invoked without valid parameters. COSC Entry object is NULL.");
				} else {
					log.debug("COSC operation is invoked without valid parameters. Schema Name: " + entry.getName());
					if (entry.getValue() != null) {
						log.debug("COSC operation is invoked without valid parameters. Enry Value Length: "
								+ ((String) entry.getValue()).length());
					}
				}
			}
		}
		return isValidCOSCEntry;
	}

}