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
package fr.lixbox.common.converter;

import java.util.Calendar;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.util.DateUtil;
import fr.lixbox.common.util.StringUtil;

/**
 * Cette classe est le transtypeur de Calendar
 * 
 * @author ludovic.terral
 */
public class CalendarConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = -5486862206590576128L;
    public static final String DATE_HEURE_FORMAT = "dd/MM/yyyy HH:mm"; //$NON-NLS-1$
    public static final String DATE_HEURE_SECONDE_FORMAT = "dd/MM/yyyy HH:mm:ss"; //$NON-NLS-1$
    public static final String DATE_FORMAT = "dd/MM/yyyy";



    // ----------- Methode -----------
    @Override
    public Calendar convertFromPresentationFormat(String target)
    {
        Calendar result = null;
        if (!StringUtil.isEmpty(target))
        {
            try
            {
                result = DateUtil.parseCalendar(target, DATE_HEURE_SECONDE_FORMAT);
            }
            catch (ProcessusException pe)
            {
                LOG.trace(pe);
            }
            if (null == result)
            {
                try
                {
                    result = DateUtil.parseCalendar(target, DATE_HEURE_FORMAT);
                }
                catch (Exception e)
                {
                    LOG.trace(e);
                }
            }
            if (null == result)
            {
                try
                {
                    result = DateUtil.parseCalendar(target, DATE_FORMAT);
                }
                catch (Exception e)
                {
                    LOG.trace(e);
                }
            }
        }
        return result;
    }



    @Override
    public String formatForPresentation(Object target)
    {
        String result = null;
        if (null != target)
        {
            final String conversion = DateUtil.format(target, "HH:mm:ss");
            if ("00:00:00".equalsIgnoreCase(conversion))
            {
                result = DateUtil.format(target, DATE_FORMAT);
            }
            else
            {
                result = DateUtil.format(target, DATE_HEURE_FORMAT);
            }
        }
        return result;
    }
}
