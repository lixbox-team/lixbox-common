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
package fr.lixbox.common.converter;

import java.io.Serializable;

import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est le transtypeur du Boolean
 * 
 * @author ludovic.terral
 */
public class BooleanConverter extends BaseConverter implements Serializable
{
    private static final long serialVersionUID = 201509041345L;
    
    
    
    // ----------- Methode ----------- 
    @Override
    public Boolean convertFromPresentationFormat(String target)
    {
        return StringUtil.convertToBoolean(target);     
    }
    
    
    
    @Override
    public String formatForPresentation(Object target)
    {
        String result = null;
        if (null != target)
        {        
            final boolean isTrue = ((Boolean) target).booleanValue();        
            result = isTrue ? "true" : "false";
        }
        return result;
    }
}
