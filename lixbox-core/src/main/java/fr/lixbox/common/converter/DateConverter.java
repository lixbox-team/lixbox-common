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

import java.util.Date;

import fr.lixbox.common.util.DateUtil;


/**
 * Cette classe est le transtypeur de Date
 * 
 * @author ludovic.terral
 */
public class DateConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = 6875112184712048375L;
    public static final String DATE_FORMAT = "dd/MM/yyyy"; //$NON-NLS-1$
    
        
    
    // ----------- Methode -----------
    @Override
    public Date convertFromPresentationFormat(String target)
    {       
        return DateUtil.parseDate(target, DATE_FORMAT);
    }
    

    
    @Override
    public String formatForPresentation(Object target)
    {
        String result = null;
        if (null != target)
        {     
            result = DateUtil.format(target, DATE_FORMAT);
        }
        return result;
     }
}
