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
