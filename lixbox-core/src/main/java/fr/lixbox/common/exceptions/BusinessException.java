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
import fr.lixbox.common.model.Contexte;
import fr.lixbox.common.model.enumeration.NiveauEvenement;


@ApplicationException(rollback=false)
public class BusinessException extends Exception
{
    // ----------- Attribut -----------      
    private static final long serialVersionUID = -18830181616951524L;
    protected final ConteneurEvenement conteneur = new ConteneurEvenement();
    
    
    
    // ----------- Methode -----------      
    public BusinessException()
    {        
    }

    

    public BusinessException(String arg0)
    {
        super(arg0);
        conteneur.add(NiveauEvenement.ERROR, arg0, new Contexte());
    }


    
    public BusinessException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
        conteneur.add(NiveauEvenement.ERROR, arg0, new Contexte());
    }
    


    public BusinessException(String arg0, ConteneurEvenement arg1)
    {
        super(arg0);
        conteneur.addAll(arg1);        
    }
    
    
    
    public ConteneurEvenement getConteneur()
    {
        return conteneur;
    }
}
