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


import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe effectue des traitements 
 * sur les dates
 * 
 * @author ludovic.terral
 */
public class DateUtil extends DateUtils
{
    // ----------- Methode -----------
    /**
     * Cette methode parse une string representant un calendar
     * 
     * @param date - la date a formatter
     * @param pattern - the date format patterns to use, not null
     * 
     * @return un string
     *
     */
    public static String format(Object date, String pattern)
    {
        if (StringUtil.isEmpty(pattern))
        {
            throw new ProcessusException(LixboxResources.getString(
                    "ERROR.PARAM.INCORRECT.02", "pattern"));
        }
        if (null != date)
        {
            final FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
            if (date instanceof Date)
            {
                return dateFormat.format((Date) date);
            }
            if (date instanceof Calendar)
            {
                final Calendar cal = ((Calendar) date); 
                return dateFormat.format(cal.getTime());
            }
            throw new ProcessusException(
                    LixboxResources.getString("ERROR.PARAM.INCOMPATIBLE.01"));
        }
        return "";
    }

    

    /**
     * Cette methode parse une string representant une date
     * 
     * @param date - date to parse, not null
     * @param parsePatterns - the date format patterns to use, not null
     * 
     * @return une date
     */
    public static Date parseDate(String date, String[] parsePatterns)
    {
        Date result = null;
        try
        {
            if (!StringUtil.isEmpty(date))
            {
                result = DateUtils.parseDate(date, parsePatterns);
            }
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
        return result; 
    }
    


    /**
     * Cette methode parse une string representant un Calendar
     * 
     * @param cal - date to parse, not null
     * @param parsePatterns - the date format patterns to use, not null
     * 
     * @return un Calendar
     */
    public static Calendar parseCalendar(String cal, String[] parsePatterns)
    {
        Calendar result=null;
        try
        {       
            if (!StringUtil.isEmpty(cal))
            {
                final Date date = DateUtils.parseDate(cal, parsePatterns);
                result= Calendar.getInstance();
                result.setTime(date);
            }
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
        return result;
    }

    

    /**
     * Cette methode parse une string representant une date
     * 
     * @param cal - date to parse, not null
     * @param pattern - the date format patterns to use, not null
     * 
     * @return une date
     */
    public static Date parseDate(String cal, String pattern)
    {
        Date result = null; 
        try
        {
            if (!StringUtil.isEmpty(cal))
            {
                result = DateUtils.parseDate(cal, pattern);
            }
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
        return result; 
    }
    
    
    
    /**
     * Cette methode parse une string representant un calendar
     * 
     * @param cal - date to parse, not null
     * @param pattern - the date format patterns to use, not null
     * @param locale
     * @param timeZone
     * 
     * @return un Calendar
     */
    public static Calendar parseCalendar(String cal, String pattern, Locale locale, TimeZone timeZone)
    {
        Calendar result=null;
        try
        {    
            if (!StringUtil.isEmpty(cal))
            {
            	Locale.setDefault(locale);
            	TimeZone.setDefault(timeZone);
                final Date date = DateUtils.parseDate(cal, pattern);
                result = Calendar.getInstance();
                result.setTime(date);
            }            
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
        return result;
    }
    
    
    
    /**
     * Cette methode parse une string representant un calendar
     * 
     * @param cal - date to parse, not null
     * @param pattern - the date format patterns to use, not null
     * 
     * @return un Calendar
     */
    public static Calendar parseCalendar(String cal, String pattern)
    {
        return parseCalendar(cal, pattern, Locale.getDefault(), TimeZone.getDefault());
    }
    
    
    
    /**
     * Cette methode ajoute un delai en millisecondes a
     * un calendar
     * 
     * @param calOrig
     * @param delay
     * 
     * @return un calendar image de la somme du delai et du calendar
     */
    public static Calendar addDelayToCalendar(Calendar calOrig, long delay)
    {        
        final long d1 = calOrig.getTimeInMillis() + delay;
        calOrig.setTimeInMillis(d1);
        return calOrig;
    }
    
    
    
    /**
     * Cette methode ajoute un delai en millisecondes a
     * un date
     * 
     * @param dateOrig
     * @param delay
     * 
     * @return un calendar image de la somme du delai et du calendar
     */
    public static Date addDelayToDate(Date dateOrig, long delay)
    {
        final Calendar cal=Calendar.getInstance();
        final long d1 = dateOrig.getTime() + delay;
        cal.setTimeInMillis(d1);
        return cal.getTime();
    }
       
        
    
    /**
     * Fonction qui retourne le mois sous forme de chaine de caractère.
     * 
     * @param mois : mois duquel on veut le nom
     * 
     * @return nom du mois
     */
    public static String moisToString(int mois)
    {
        switch (mois)
        {
	        case 1:
	            return "Janvier";
	
	        case 2:
	            return "Février";
	
	        case 3:
	            return "Mars";
	
	        case 4:
	            return "Avril";
	
	        case 5:
	            return "Mai";
	
	        case 6:
	            return "Juin";
	
	        case 7:
	            return "Juillet";
	
	        case 8:
	            return "Août";
	
	        case 9:
	            return "Septembre";
	
	        case 10:
	            return "Octobre";
	
	        case 11:
	            return "Novembre";
	
	        case 12:
	            return "Décembre";

	        default:
	            return null;
        }
    }
}