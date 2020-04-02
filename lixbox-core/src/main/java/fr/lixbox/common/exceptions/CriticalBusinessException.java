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
package fr.lixbox.common.exceptions;

import javax.ejb.ApplicationException;

import fr.lixbox.common.model.ConteneurEvenement;


@ApplicationException(rollback=true)
public class CriticalBusinessException extends BusinessException
{
    // ----------- Attribut -----------      
    private static final long serialVersionUID = -1200906111644L;
    
    
    
    // ----------- Methode -----------      
    public CriticalBusinessException()
    {
        
    }

    

    public CriticalBusinessException(String arg0)
    {
        super(arg0);
    }
    


    public CriticalBusinessException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }
    


    public CriticalBusinessException(String arg0, ConteneurEvenement arg1)
    {
        super(arg0, arg1);  
    }
}
