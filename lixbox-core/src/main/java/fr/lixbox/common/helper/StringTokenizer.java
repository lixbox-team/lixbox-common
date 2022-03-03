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
package fr.lixbox.common.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est un clone amelioree du StringTokenizer de SUN
 *
 * @author ludovic.terral
 */
public class StringTokenizer
{
    // ----------- Attributs -----------
    private String str;
    private String delimiters;
    
    private List<String> tokens;
    
    
    
    // ----------- CONSTRUCTEURS -----------
    public StringTokenizer(String str, String delim)
    {
        this.tokens=new ArrayList<>();
        if(StringUtil.isNotEmpty(str))
        {
            if (str.contains(delim))
            {    
            	this.str = str;
                this.delimiters = delim;    		
                extraireTokens();
            }
            else
            {
                this.tokens.add(str);
            }
        }
    }
    
    

    // ----------- Methodes -----------
    private void extraireTokens()
    {        
        int currentPosition = -1;
        int maxPosition;
        int lastDelimiters;
        int sizeDelimiters = delimiters.length();
        int newPosition;

        maxPosition = str.length();
        currentPosition = 0 - delimiters.length();
        lastDelimiters = (!StringUtil.isEmpty(str) && str.contains(delimiters))?str.lastIndexOf(delimiters):maxPosition;
        if (!StringUtil.isEmpty(str))
        {
            while ((currentPosition <= maxPosition) && (currentPosition <= lastDelimiters))
            {
            	newPosition = str.indexOf(delimiters, currentPosition + sizeDelimiters);
                if (newPosition == -1)
                {
                    newPosition = maxPosition;
                }
                var substring = StringUtils.mid(str, currentPosition + sizeDelimiters, newPosition - currentPosition - sizeDelimiters);                 // $codepro.audit.disable variableDeclaredInLoop
                if (!("\n".equalsIgnoreCase(delimiters) && StringUtil.isEmpty(substring)))
                {
                	tokens.add(substring);
				}
                currentPosition = newPosition;
            }
        }
    }



    public int size()
    {       
        return tokens.size();
    }
    
        
    
    public List<String> getTokens()
    {
        return tokens;   
    }
}