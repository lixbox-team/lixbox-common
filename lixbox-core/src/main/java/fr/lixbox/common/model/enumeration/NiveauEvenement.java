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
package fr.lixbox.common.model.enumeration;

import java.io.Serializable;


/**
 *  Cette classe d'enumeration regroupe tous les 
 *  niveaux d'erreurs pouvant etre rencontrees.
 *  
 *  @author ludovic.terral  
 */
public enum NiveauEvenement implements Serializable
{
    // ----------- Attribut -----------
    INFO("INFORMATION","INFO"), 
    WARN("WARNING","WARN"),
    ERROR("ERREUR","ERROR");
  
    private String libelle;
    private String libelleCourt;

    
    
    // ----------- Methode -----------
    private NiveauEvenement(String libelle, String libelleCourt)
    {
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
    }
    
    
    
    public String getLibelle()
    {
        return libelle;
    }
    


    public String getLibelleCourt()
    {
        return libelleCourt;
    }

    

    @Override
    public String toString()
    {
        return libelle;
    }
}
