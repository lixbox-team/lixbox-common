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
