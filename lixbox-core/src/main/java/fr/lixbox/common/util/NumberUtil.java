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
        var result = false;
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
        var firstZero = false;
        final var result = new StringBuilder(32);
        final var length = chaine.length();
        for (var i=0; i < length;i++)
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
        var result = false;
        var i=0;
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
