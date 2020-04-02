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

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cette classe est un utilitaire qui effectue des operations 
 * sur les nombres.
 * 
 * @author ludovic.terral
 */
public class NumberUtil extends NumberUtils
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(NumberUtil.class);
    
    
    
    // ----------- Methode -----------
    /**
     * Cette methode verifie si le nombre
     * passe en parametre est impair
     */
    public static boolean isImpair(int nombre)
    {
        return ((nombre % 2) > 0);
    }
    
    
    /**
     * Cette methode verifie si le nombre
     * passe en parametre est impair
     */
    public static boolean isPair(int nombre)
    {
        return (0 == (nombre % 2));
    }
    
    
    
    /**
     * Cette methode verifie si un nombre se situe dans l'intervalle 
     * passe en parametre
     * 
     * @param nbreaVerifier
     * @param intervalleMin
     * @param intervalleMax
     * @param inclus
     * 
     * @return true si dans l'intervalle
     *         false dans tous les autres cas.
     */
    public static boolean between(double nbreaVerifier, double intervalleMin, double intervalleMax, boolean inclus)
    {
        boolean result = false;
        if (inclus)
        {
            intervalleMin--;
            intervalleMax++;
        }
        if ((nbreaVerifier > intervalleMin) && (nbreaVerifier < intervalleMax))
        {
            result=true;
        }
        return result;
    }
    
    
    
    /**
     * Cette methode genere un nombre aleatoire compris
     * dans l'intervalle
     * 
     * @param intervalleMin
     * @param intervalleMax
     * 
     * @return nbreAleatoire
     */
    public static double random(double intervalleMin, double intervalleMax)
    {   
        final Random rdm = new SecureRandom();
        return intervalleMin + ((intervalleMax - intervalleMin) * rdm.nextDouble());        
    }
    
    
    
    /**
     * Cette methode supprime tous les caracteres qui ne sont pas 
     * des chiffres et renvoie la chaine restante
     * 
     * @param chaine
     * 
     * @return une chane de chiffre
     */
    public static String keepNumbers(final String chaine)
    {
        boolean firstZero = false;
        final StringBuilder result = new StringBuilder(32);
        final int length = chaine.length();
        for (int i=0; i < length;i++)
        {
            if (StringUtils.isNumeric(String.valueOf(chaine.charAt(i))))
            {
                if (!firstZero)
                {
                    result.append(chaine.charAt(i));
                }
                firstZero = true;
            }
        }
        return result.toString();
    }
    
    
    
    /**
     * Cette methode evalue la numericite d'une chaine.
     * Pour cela, on supprime les premiers 0
     * 
     * @param str
     * 
     * @return true si un nombre 
     *         false si autre chose
     */
    public static boolean isNumeric(String str)
    {   
        boolean result = false;
        int i=0;
        try
        {
            if (StringUtil.isNotEmpty(str))
            {
                while (('0' == str.charAt(i)) && (i < (str.length() - 1)))
                {
                    str = str.substring(i);
                    i++;
                }
                result |= StringUtils.isNumeric(str);
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getMessage());            
        }
        return result; 
    }
}
