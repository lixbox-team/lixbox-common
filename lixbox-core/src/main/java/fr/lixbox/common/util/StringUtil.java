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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import fr.lixbox.common.exceptions.ProcessusException;


/**
 * Cette classe est un utilitaire qui traite les chaines de caracteres.
 * 
 * @author ludovic.terral
 */
public class StringUtil extends StringUtils
{
    // ----------- Attribut -----------   
    /** Index du 1er caractere accentue * */
    private static final int MIN = 192;
    /** Index du dernier caractere accentue * */
    private static final int MAX = 255;
    /** Vecteur de correspondance entre accent / sans accent * */
    private static final List<String> MAP = initMap();
    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";
    public static final String BACKSLASH = "\\";
    public static final String DIGITS = "[0-9]*+";
    public static final String ARRAY_INDEX_PATTERN =
        BACKSLASH + LEFT_BRACKET + DIGITS + BACKSLASH + RIGHT_BRACKET;
    

    
    // ----------- Methode -----------
    /** // $codepro.audit.disable sourceLength
     * Cette methode permet d'initialiser le tableau de correspondance 
     * entre les caracteres accentues et leurs homologues non accentues
     */
    private static List<String> initMap()
    {
        final List<String> result = new ArrayList<>();
        var car = ("A");
        result.add(car); /* '\u00C0' a alt-0192 */
        result.add(car); /* '\u00C1' a alt-0193 */
        result.add(car); /* '\u00C2' a alt-0194 */
        result.add(car); /* '\u00C3' a alt-0195 */
        result.add(car); /* '\u00C4' a alt-0196 */
        result.add(car); /* '\u00C5' a alt-0197 */
        car = ("AE");
        result.add(car); /* '\u00C6' a alt-0198 */
        car = ("C");
        result.add(car); /* '\u00C7' a alt-0199 */
        car = ("E");
        result.add(car); /* '\u00C8' e alt-0200 */
        result.add(car); /* '\u00C9' e alt-0201 */
        result.add(car); /* '\u00CA' e alt-0202 */
        result.add(car); /* '\u00CB' a alt-0203 */
        car = ("I");
        result.add(car); /* '\u00CC' a alt-0204 */
        result.add(car); /* '\u00CD' a alt-0205 */
        result.add(car); /* '\u00CE' i alt-0206 */
        result.add(car); /* '\u00CF' a alt-0207 */
        car = ("D");
        result.add(car); /* '\u00D0' a alt-0208 */
        car = ("N");
        result.add(car); /* '\u00D1' a alt-0209 */
        car = ("O");
        result.add(car); /* '\u00D2' a alt-0210 */
        result.add(car); /* '\u00D3' a alt-0211 */
        result.add(car); /* '\u00D4' o alt-0212 */
        result.add(car); /* '\u00D5' a alt-0213 */
        result.add(car); /* '\u00D6' a alt-0214 */
        car = ("*");
        result.add(car); /* '\u00D7' a alt-0215 */
        car = ("0");
        result.add(car); /* '\u00D8' a alt-0216 */
        car = ("U");
        result.add(car); /* '\u00D9' u alt-0217 */
        result.add(car); /* '\u00DA' a alt-0218 */
        result.add(car); /* '\u00DB' a alt-0219 */
        result.add(car); /* '\u00DC' a alt-0220 */
        car = ("Y");
        result.add(car); /* '\u00DD' a alt-0221 */
        car = ("a");
        result.add(car); /* '\u00DE' a alt-0222 */
        car = ("B");
        result.add(car); /* '\u00DF' a alt-0223 */
        car = ("a");
        result.add(car); /* '\u00E0' a alt-0224 */
        result.add(car); /* '\u00E1' a alt-0225 */
        result.add(car); /* '\u00E2' a alt-0226 */
        result.add(car); /* '\u00E3' a alt-0227 */
        result.add(car); /* '\u00E4' a alt-0228 */
        result.add(car); /* '\u00E5' a alt-0229 */
        car = ("ae");
        result.add(car); /* '\u00E6' a alt-0230 */
        car = ("c");
        result.add(car); /* '\u00E7' a alt-0231 */
        car = ("e");
        result.add(car); /* '\u00E8' e alt-0232 */
        result.add(car); /* '\u00E9' e alt-0233 */
        result.add(car); /* '\u00EA' e alt-0234 */
        result.add(car); /* '\u00EB' a alt-0235 */
        car = ("i");
        result.add(car); /* '\u00EC' a alt-0236 */
        result.add(car); /* '\u00ED' a alt-0237 */
        result.add(car); /* '\u00EE' i alt-0238 */
        result.add(car); /* '\u00EF' a alt-0239 */
        car = ("d");
        result.add(car); /* '\u00F0' a alt-0240 */
        car = ("n");
        result.add(car); /* '\u00F1' a alt-0241 */
        car = ("o");
        result.add(car); /* '\u00F2' a alt-0242 */
        result.add(car); /* '\u00F3' a alt-0243 */
        result.add(car); /* '\u00F4' o alt-0244 */
        result.add(car); /* '\u00F5' a alt-0245 */
        result.add(car); /* '\u00F6' a alt-0246 */
        car = ("/");
        result.add(car); /* '\u00F7' a alt-0247 */
        car = ("0");
        result.add(car); /* '\u00F8' a alt-0248 */
        car = ("u");
        result.add(car); /* '\u00F9' u alt-0249 */
        result.add(car); /* '\u00FA' a alt-0250 */
        result.add(car); /* '\u00FB' a alt-0251 */
        result.add(car); /* '\u00FC' a alt-0252 */
        car = ("y");
        result.add(car); /* '\u00FD' a alt-0253 */
        car = ("a");
        result.add(car); /* '\u00FE' a alt-0254 */
        car = ("y");
        result.add(car); /* '\u00FF' a alt-0255 */
        result.add(car); /* '\u00FF' alt-0255 */
        return result;
    }

    

    /**
     * Cette methode transforme une chaine pouvant contenir 
     * des accents dans une version sans accent
     * 
     * @param chaine Chaine a convertir sans accent
     * 
     * @return Chaine dont les accents ont ete supprime
     */
    public static java.lang.String removeAccent(java.lang.String chaine)
    {
        var result = new StringBuilder(chaine);
        final int length = result.length();
        for (var bcl = 0; bcl < length; bcl++)
        {
            int carVal = chaine.charAt(bcl);
            if ((carVal >= MIN) && (carVal <= MAX))
            { 
                // Remplacement
                java.lang.String newVal = MAP.get(carVal - MIN);
                result = result.replace(bcl, bcl + 1, newVal);
            }
        }
        return result.toString();
    }

    

    /**
     * Cette methode transforme une chaine en majuscule et
     * sans accents
     * 
     * @param chaine
     * 
     * @return la chaine en majuscule sans accent
     */
    public static String capitalize(String chaine)
    {
        String result = chaine;
        if (null != chaine)
        {
            result = removeAccent(chaine);
            result = result.trim().toUpperCase(Locale.FRANCE);    
        }
        return result;
    }
    
        

    /**
     * Cette methode teste si une chaine 
     * est <code>null</code>, vide ou ne contient que des espaces.
     * 
     * @param str
     * 
     * @return true si null ou vide
     *         false si non vide
     */
    public static boolean isEmpty(String str)
    {
        return null == trimToNull(str);
    }

    
    

    /**
     * Cette methode teste si une chaine 
     * n'est pas <code>null</code>, vide ou ne contient que des espaces.
     * 
     * @param str
     * 
     * @return true si null ou vide
     *         false si non vide
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
    

    /**
     * Cette methode supprime les caracteres d'espacement 
     * (espace, tabulation, etc...) en debut et en fin de chaine. 
     * Si le resultat est une chaine vide, ou si la chaine en entree 
     * vaut <code>null</code>, la valeur <code>null</code> est renvoyee.
     * 
     * @param str
     * 
     * @return une chaine epuree
     * 
     */
    public static String trimToNull(String str)
    {
        String result = null;
        if (null != str)
        {            
            final String newStr = str.trim();
            result = (0 == newStr.length()) ? null : newStr;
        }
        return result;
    }

    

    /**
     * Cette methode evalue les differentes formes litterales
     * de oui et non pour en faire un boolean.
     * 
     * @param string 
     * 
     * @return Boolean.TRUE si la chaine est VRAI || TRUE || 1 || OUI || YES || Y || O || ENABLED
     *         Boolean.FALSE si la chaine est FAUX || FALSE || 0 || NON || NO || N || DISABLED
     *         sinon null
     */
    public static Boolean convertToBoolean(String string) // $codepro.audit.disable cyclomaticComplexity
    {
        Boolean bVal = null;
        final var str = string.toUpperCase(Locale.FRANCE);
        if ("VRAI".equalsIgnoreCase(str) || "TRUE".equalsIgnoreCase(str) || 
                "1".equalsIgnoreCase(str) || "OUI".equalsIgnoreCase(str) || 
                "YES".equalsIgnoreCase(str) || "Y".equalsIgnoreCase(str) || 
                "O".equalsIgnoreCase(str) || "ENABLED".equalsIgnoreCase(str))
        {
            bVal = Boolean.TRUE;
        }
        else 
            if ("FAUX".equalsIgnoreCase(str) || "FALSE".equalsIgnoreCase(str) || 
                    "O".equalsIgnoreCase(str) || "NON".equalsIgnoreCase(str) || 
                    "NO".equalsIgnoreCase(str) || "N".equalsIgnoreCase(str) || 
                    "DISABLED".equalsIgnoreCase(str))
            {
                bVal = Boolean.FALSE;
            }
        return bVal;
    }
        
    

    public static String castToString(Object target) 
    {
        String stringValue = null;
        try {
            stringValue = (String) target;
        }
        catch (ClassCastException e) 
        {
            throw new ProcessusException("Impossible de caster " + target + " en String", e);
        }
        return (null == stringValue) ? null : stringValue.trim();
    }
    
    
    
    public static String removeArrayIndexes(String string)
    {
        return string.replace(ARRAY_INDEX_PATTERN, "");
    }
       
    
    
    public static String removePrefix(String s, String prefix)
    {
        return (null == s) ? null : s.substring(prefix.length()); 
    }
    
    
    
    public static String lastElementInKeypath(String path)
    {
        final int delimIndex = path.lastIndexOf('.');
        return (delimIndex == -1) ? path : path.substring(delimIndex + 1);
    }
        
    
    
    /**
     * Cette methode parse une chaine de caractere separe
     * par un code champs
     * 
     * @param parc
     * @param separateur
     */
    public static String[] parseStringToArray(String parc, String separateur)
    {
        final var token = new StringTokenizer(parc, separateur);
        final var array = new String[token.countTokens()];
        var i =0;
        while (token.hasMoreTokens())
        {
            array[i]=token.nextToken();
            i++;
        }
        return array;
    }
    
    
    
    /**
     * Cette methode retourne le nombre d'occurance du caractere present dans la chaine.
     * Le caractere peut etre de 1 ou une chaine.
     * 
     * @param chaine
     * @param caractere
     * 
     * @return compteur
     */
    public static int countCharacterOccurrence(String chaine, String caractere)
    {
        var compteur = 0;
    	
    	if ((null != caractere) && (null != chaine))
		{
    		if (1 == caractere.length())
    		{ 
    	        final int length = chaine.length();
    	        for (var i=0; i < length;i++)
	        	{
    	            var test = String.valueOf(chaine.charAt(i));
	        		if (test.equalsIgnoreCase(caractere))
	        		{
	        			compteur++;
	        		}
	        	}
    		}
	    	else if (caractere.length() > 1)
	    	{
	    		compteur = 0;
	    		var position = 0;
	    		final int fin = chaine.length();
	    		String temp = chaine;
	    		while (position != fin)
				{
					position = temp.indexOf(caractere);
					if (position < 0)
					{
						position = fin;
					}
					else
					{
						compteur++;
						position += caractere.length();
						temp = temp.substring(position, temp.length());
						if (position > fin)
						{
							position = fin;
						}
					}
				}
	    	}
		}    	
        return compteur;
    }  
    
    
    
    public static String join(Object[] target, String separator) 
    {

        final var sb = new StringBuilder();
        if (target.length > 0) {
            sb.append(target[0]);
            for (var i = 1; i < target.length; i++) {
                sb.append(separator);
                sb.append(target[i]);
            }
        }
        return sb.toString();
    }

    
    
    public static String join(Iterable<?> target, String separator) 
    {
        var sb = new StringBuilder();
        Iterator<?> it = target.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(separator);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }
    
    public static String bytesToHexString(byte[] encryptedPasswd)
    {
        var retString = new StringBuilder();
        for (var i = 0; i < encryptedPasswd.length; ++i)
        {
            retString.append(Integer.toHexString(0x0100 + (encryptedPasswd[i] & 0x00FF)).substring(1));
        }
        return retString.toString();
    }
    
    

    public static byte[] hexStringToBytes(String hexString)
    { 
        int len = hexString.length();
        var data = new byte[len / 2];
        for (var i = 0; i < len; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    
    
    /**
     * Cette methode recupere une chaine image d'un objet 
     * dont la longueur peut etre parametree.
     * 
     * @param objet
     * @param longueur max 0 si illimitee
     * 
     * @return une chaine image de l'objet
     */
    public static String stringValueOfObject(Object objet, int longueur)
    {
        String result = null;
        if (null != objet)
        {
            if (objet instanceof Calendar || objet instanceof Date)            
            {
                result = DateUtil.format(objet, "dd/MM/yyyy HH:mm:ss");
            }
            else
            {
                if ((String.valueOf(objet).length() > longueur) && (longueur > 0))
                {
                    result = String.valueOf(objet).substring(0, longueur) + "..."; // $codepro.audit.disable disallowStringConcatenation
                }
                else
                {
                    result = String.valueOf(objet);
                }
            }
        }
        return result;
    }
    
    
    
    public static boolean getMatch(String value, String pattern)
    {
        if (value != null && !value.isEmpty())
        {
            return Pattern.compile(pattern).matcher(value).matches();
        }
        else
        {
            return false;
        }
    }
}