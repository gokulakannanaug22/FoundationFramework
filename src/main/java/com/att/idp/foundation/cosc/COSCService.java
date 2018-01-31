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

package com.att.idp.foundation.cosc;

import java.util.Map;
import java.util.Set;

import com.att.detsplatform.cosc.api.COSCInterface;
import com.att.detsplatform.cosc.base.COSCEntry;
import com.att.detsplatform.cosc.base.COSCKey;
import com.att.detsplatform.cosc.base.exceptions.COSCException;
import com.att.detsplatform.cosc.base.exceptions.InvalidEntryException;
import com.att.detsplatform.cosc.base.exceptions.TypeNotFoundException;


// TODO: Auto-generated Javadoc
/**
 * The Interface COSCService.
 * 
 * @author sb527f
 */
public interface COSCService {

	/** The application id. */
	String APPLICATION_ID = "salesApplicationId";

	/** The version. */
	String VERSION = "v1";

	/**
	 * Gets the cache service.
	 *
	 * @return the cache service
	 */
	COSCInterface<COSCKey> getCacheService();

	/**
	 * Creates the COSC key.
	 *
	 * @param pkey the pkey
	 * @param keyName the key name
	 * @return the COSC key
	 */
	COSCKey createCOSCKey(String pkey, Enum<?> keyName);

	/**
	 * Creates the COSC entry.
	 *
	 * @param keyname the keyname
	 * @param obj the obj
	 * @return the COSC entry
	 */
	COSCEntry createCOSCEntry(Enum<?> keyname, Object obj);

	/**
	 * Creates the COSC entry.
	 *
	 * @param keyname the keyname
	 * @param version the version
	 * @param obj the obj
	 * @return the COSC entry
	 */
	COSCEntry createCOSCEntry(Enum<?> keyname, String version, Object obj);

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the COSC entry
	 * @throws COSCException the COSC exception
	 */
	COSCEntry get(COSCKey key) throws COSCException;

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 * @throws COSCException the COSC exception
	 */
	void put(COSCKey key, COSCEntry value) throws COSCException;

	/**
	 * Delete.
	 *
	 * @param key the key
	 * @throws COSCException the COSC exception
	 */
	void delete(COSCKey key) throws COSCException;

	/**
	 * Gets the all.
	 *
	 * @param paramSet the param set
	 * @return the all
	 * @throws TypeNotFoundException the type not found exception
	 * @throws COSCException the COSC exception
	 */
	Map<COSCKey, COSCEntry> getAll(Set<COSCKey> paramSet) throws TypeNotFoundException, COSCException;

	/**
	 * Put all.
	 *
	 * @param paramMap the param map
	 * @throws TypeNotFoundException the type not found exception
	 * @throws InvalidEntryException the invalid entry exception
	 * @throws COSCException the COSC exception
	 */
	void putAll(Map<COSCKey, COSCEntry> paramMap) throws TypeNotFoundException, InvalidEntryException, COSCException;

	/**
	 * Delete all.
	 *
	 * @param paramSet the param set
	 * @throws COSCException the COSC exception
	 */
	void deleteAll(Set<COSCKey> paramSet) throws COSCException;

}
