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
