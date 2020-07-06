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
import fr.lixbox.common.util.StringUtil;



/**
 * Cette classe est le transtypeur de Double
 * 
 * @author ludovic.terral
 */
public class DoubleConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = -1072049438172543403L;



	// ----------- Methode ----------- 
    @Override
    public Double convertFromPresentationFormat(String target) 
    {
    	Double result = null;
        if (StringUtil.isEmpty(target))
        {
            return Double.valueOf("0.0");
        }        
        target = target.replace(",",".");
        result=Double.valueOf(target);              
        if (null != result)
        {
            return result;            
        }
        throw new ProcessusException(LixboxResources.getString("ERROR.STRUTS.TRANSTYPEUR.DOUBLE.NON.COMPATIBLE", target)); //$NON-NLS-1$
    }
    
    
    
    @Override
    public String formatForPresentation(Object target)
    {
        return (null == target) ? "0.0" : ((Double) target).toString(); //$NON-NLS-1$
    }
}
