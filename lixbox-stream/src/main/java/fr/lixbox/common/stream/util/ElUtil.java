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
package fr.lixbox.common.stream.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

public class ElUtil
{
    private ElUtil()
    {
        // constructeur servant à transformer la classe en utilitaire
    }
    
    
    
    public static Object evalExpression(Map<String,Object> context , String expression)
    {
        JexlEngine jexl = new JexlBuilder().create();        
        JexlExpression e = jexl.createExpression(expression);
        JexlContext jc = new MapContext();
        
        for (Entry<String, Object> entry : context.entrySet())
        {
            jc.set(entry.getKey(), entry.getValue());            
        }
        return e.evaluate(jc);
    }
}
