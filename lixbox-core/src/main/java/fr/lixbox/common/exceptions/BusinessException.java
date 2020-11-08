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
package fr.lixbox.common.exceptions;

import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.model.Contexte;
import fr.lixbox.common.model.enumeration.NiveauEvenement;


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
