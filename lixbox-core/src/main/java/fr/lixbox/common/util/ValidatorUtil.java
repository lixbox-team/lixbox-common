/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 * This file is part of lixbox-common.
 *
 *    lixbox-iam is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    lixbox-iam is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *    along with lixbox-common.  If not, see <https://www.gnu.org/licenses/>
 *   
 *   @AUTHOR Lixbox-team
 *
 ******************************************************************************/
package fr.lixbox.common.util;

/**
 * Cette classe est un utilitaire qui assure la validation des elements communs
 * 
 * @author virgile.delacerda
 */
public class ValidatorUtil
{
    // ----------- Attribut -----------
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String IPV4_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final String IPV6_PATTERN = "^[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}$";
	
	
	
    // ----------- Methode -----------
	private ValidatorUtil()
	{
	    //private constructor
	}
	
	
	
	/**
	 * Retroune la validite d'une adresse mail
	 * @param value
	 * @return
	 */
	public static boolean isEmail(String value)
	{
		return StringUtil.getMatch(value, EMAIL_PATTERN);
	}
	
	
	
	/**
	 * Retroune la validite d'une adresse IPv4
	 * @param value
	 * @return
	 */
	public static boolean isIPv4(String value)
	{
		return StringUtil.getMatch(value, IPV4_PATTERN);
	}
	
	
	
	/**
	 * Retroune la validite d'une adresse IPv6
	 * @param value
	 * @return
	 */
	public static boolean isIPv6(String value)
	{
		return StringUtil.getMatch(value, IPV6_PATTERN);
	}
	
	
	
	/**
	 * Retroune la validite d'une adresse IP
	 * @param value
	 * @return
	 */
	public static boolean isIP(String value)
	{
		return (StringUtil.getMatch(value, IPV6_PATTERN) || StringUtil.getMatch(value, IPV4_PATTERN));
	}
}