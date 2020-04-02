/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 *   Copyrigth - LIXTEC - Tous droits reserves.
 *   
 *   Le contenu de ce fichier est la propriete de la societe Lixtec.
 *   
 *   Toute utilisation de ce fichier et des informations, sous n'importe quelle
 *   forme necessite un accord ecrit explicite des auteurs
 *   
 *   @AUTHOR Ludovic TERRAL
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
    private int currentPosition;
    private int newPosition;
    private final int maxPosition;
    private final String str;
    private final String delimiters;
    private final int lastDelimiters;
    
    private List<String> tokens;
    
    
    
    // ----------- CONSTRUCTEURS -----------
    public StringTokenizer(String str, String delim)
    {
        newPosition = -1;

    	this.str = str;
    	this.maxPosition = str.length();
    	this.delimiters = delim;
        currentPosition = 0 - delimiters.length();
        this.lastDelimiters = (!StringUtil.isEmpty(str) && str.contains(delimiters))?str.lastIndexOf(delimiters):maxPosition;
		
        extraireTokens(true);
    }
    
    
    
    public StringTokenizer(String str, String delim, boolean controle)
    {
    	if (!StringUtil.isEmpty(str))
    	{
    		newPosition = -1;

        	this.str = str;
        	this.maxPosition = str.length();
        	this.delimiters = delim;
            currentPosition = 0 - delimiters.length();
            boolean valide = true;
        	if (controle)
            {
     			valide = str.contains(delimiters);
     		}
        	this.lastDelimiters = (!StringUtil.isEmpty(str) && valide)?str.lastIndexOf(delimiters):0;
            extraireTokens(controle);
		}
    	else
    	{
    		this.str = null;
        	this.maxPosition = -1;
        	this.lastDelimiters = -1;
        	this.delimiters = null;
        	this.tokens = new ArrayList<>();
    	}
    }

    

    // ----------- Methodes -----------
    private void extraireTokens(boolean controle)
    {        
        tokens = new ArrayList<>();
        int sizeDelimiters = delimiters.length();
        boolean valide = true;
        if (controle)
        {
			valide = str.contains(delimiters);
		}
        if (!StringUtil.isEmpty(str) && valide)
        {
            while ((currentPosition <= maxPosition) && (currentPosition <= lastDelimiters))
            {
            	newPosition = str.indexOf(delimiters, currentPosition + sizeDelimiters);
                if (newPosition == -1)
                {
                    newPosition = maxPosition;
                }
                String substring = StringUtils.mid(str, currentPosition + sizeDelimiters, newPosition - currentPosition - sizeDelimiters);                 // $codepro.audit.disable variableDeclaredInLoop
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