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
