/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 *   Copyrigth - LIXTEC - Tous droits reserves.
 *   
 *   Le contenu de ce fichier est la propriete de la societe Lixtec.
 *   
 *   Toute utilisation de ce fichier et des informations, sous n'importe quelle
 *   forme necessite un accord ecrit explicite des auteurs
 *   
 *   @AUTHOR Ludovic TERRAL
 *
 ******************************************************************************/
package fr.lixbox.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import fr.lixbox.common.util.ValidatorUtil;

public class TestValidatorUtil
{
	private static final Log LOG = LogFactory.getLog(TestValidatorUtil.class);
	
	@Test
	public void testIsEmail()
	{
		try
		{
			String emailValide = "lebron.james@lixbox.fr";
			String emailNull = null;
			String emailInvalide = "lebron.james.lixbox.fr";
			
			LOG.info("Email valide : " + emailValide);
			LOG.info("Email null : " + emailNull);
			LOG.info("Email invalide : " + emailInvalide);
			
			assertTrue(ValidatorUtil.isEmail(emailValide));
			assertFalse(ValidatorUtil.isEmail(emailNull));
			assertFalse(ValidatorUtil.isEmail(emailInvalide));
		}
		catch (Exception e)
		{
			assertTrue(false);
		}
	}
	
	@Test
	public void testIsIPv4()
	{
		try
		{
			String valueValide = "127.0.0.1";
			String valueNull = null;
			String valueInvalide = "1234.25";
			
			LOG.info("IPv4 valide : " + valueValide);
			LOG.info("IPv4 null : " + valueNull);
			LOG.info("IPv4 invalide : " + valueInvalide);
			
			assertTrue(ValidatorUtil.isIPv4(valueValide));
			assertFalse(ValidatorUtil.isIPv4(valueNull));
			assertFalse(ValidatorUtil.isIPv4(valueInvalide));
		}
		catch (Exception e)
		{
			assertTrue(false);
		}
	}
	
	@Test
	public void testIsIPv6()
	{
		try
		{
			String valueValide = "2001:0db8:0000:85a3:0000:0000:ac1f:8001";
			String valueNull = null;
			String valueInvalide = "1234:25:";
			
			LOG.info("IPv6 valide : " + valueValide);
			LOG.info("IPv6 null : " + valueNull);
			LOG.info("IPv6 invalide : " + valueInvalide);
			
			assertTrue(ValidatorUtil.isIPv6(valueValide));
			assertFalse(ValidatorUtil.isIPv6(valueNull));
			assertFalse(ValidatorUtil.isIPv6(valueInvalide));
		}
		catch (Exception e)
		{
			assertTrue(false);
		}
	}
	
	@Test
	public void testIsIP()
	{
		try
		{
			String valueValide = "127.0.0.1";
			String valueNull = null;
			String valueInvalide = "1234.25";
			
			LOG.info("IPv4 valide : " + valueValide);
			LOG.info("IPv4 null : " + valueNull);
			LOG.info("IPv4 invalide : " + valueInvalide);
			
			assertTrue(ValidatorUtil.isIP(valueValide));
			assertFalse(ValidatorUtil.isIP(valueNull));
			assertFalse(ValidatorUtil.isIP(valueInvalide));
		}
		catch (Exception e)
		{
			assertTrue(false);
		}
	}
}