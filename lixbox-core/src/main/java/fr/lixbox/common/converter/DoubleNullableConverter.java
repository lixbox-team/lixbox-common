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

import fr.lixbox.common.util.StringUtil;



/**
 * Cette classe est le transtypeur de Double
 * 
 * @author ludovic.terral
 */
public class DoubleNullableConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = -3541922517195044295L;



	// ----------- Methode ----------- 
    @Override
    public Double convertFromPresentationFormat(String target) 
    {
        if (StringUtil.isEmpty(target))
        {
            return null;
        }       
        target = target.replace(",",".");
        return Double.valueOf(target);
    }
    
    
    
    @Override
    public String formatForPresentation(Object target)
    {
        return (null == target) ? "0.0" : ((Double) target).toString(); //$NON-NLS-1$
    }
}
