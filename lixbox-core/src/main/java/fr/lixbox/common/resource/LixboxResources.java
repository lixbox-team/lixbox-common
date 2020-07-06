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
package fr.lixbox.common.resource;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Cette classe externalise les strings des classes.
 * 
 * @author ludovic.terral
 */
public class LixboxResources
{
    // ----------- Attribut -----------
    private static final String BUNDLE_NAME = "fr.lixbox.common.resource.LixboxResources";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);



    // ----------- Methode -----------
    private LixboxResources()
    {
    }



    /**
     * Cette methode renvoie le texte associe au resource bundle.
     * 
     * @param key de texte.
     * 
     * @return message le texte.
     */
    public static String getString(final String key)
    {
        String result = null;
        try
        {
            result = RESOURCE_BUNDLE.getString(key);
        }
        catch (final MissingResourceException e)
        {
            result = '!' + key + '!';
        }
        return result;
    }



    /**
     * Cette methode renvoie le texte associe au resource bundle en utilisant des masques de
     * formatage.
     * 
     * @param key de texte.
     * @param parameters de format
     * 
     * @return message le texte.
     */
    public static String getString(final String key, final Object[] parameters)
    {
        String baseMsg;
        try
        {
            baseMsg = RESOURCE_BUNDLE.getString(key);
        }
        catch (final MissingResourceException e)
        {
            baseMsg = '!' + key + '!';
        }
        return MessageFormat.format(baseMsg, parameters);
    }



    /**
     * Cette methode renvoie le texte associe au resource bundle en utilisant des masques de
     * formatage.
     * 
     * @param key de texte.
     * @param parameter de format
     * 
     * @return message le texte.
     */
    public static String getString(final String key, final Object parameter)
    {
        return getString(key, new Object[] { parameter });
    }
}
