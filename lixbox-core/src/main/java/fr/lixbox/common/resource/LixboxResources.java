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
