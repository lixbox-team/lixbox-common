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

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe est le transtypeur du Integer
 * 
 * @author ludovic.terral
 */
public class IntegerConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = -9106768989983862420L;



	// ----------- Methode -----------
    @Override    
    public Integer convertFromPresentationFormat(String target)
    {
        Integer result = null;
        try
        {
            if (0 == target.length())
            {
                result = null;
            }
            else
            {
                result = Integer.valueOf(target);
            }
        }
        catch (NumberFormatException e)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.STRUTS.TRANSTYPEUR.INTEGER.NON.COMPATIBLE", target)); //$NON-NLS-1$
        }
        return result;
    }



    @Override
    public String formatForPresentation(Object target)
    {
        return (null == target) ? "0" : ((Integer) target).toString(); //$NON-NLS-1$
    }
}
