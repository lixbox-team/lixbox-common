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
package fr.lixbox.common.thread;

import java.io.Serializable;

import fr.lixbox.common.exceptions.BusinessException;

/**
 * Cette classe est un thread generique capable d'executer
 * n'importe quelle methode publique d'une instance d'objet
 * passe en parametre lors de l'instanciation.
 *  
 * @author ludovic.terral
 */
public class GenericThread<T> extends Thread implements Serializable, Runnable
{
    // ----------- Attribut -----------
    private static final long serialVersionUID = 120080923085200L; 
    private transient T result=null;
    private transient Object instance=null;
    private Class<?>[] typeParams=null;
    private transient Object[] params=null;
    private String nomMethode=null;
    private boolean isLaunched=false;
   
    
    
    // ----------- Methode -----------  
    public GenericThread(final Object instance, final String nomMethode, 
            final Class<?>[] typeParams, final Object[] params)
    {
        this.instance = instance;
        this.typeParams = typeParams.clone();
        this.params = params.clone();
        this.nomMethode = nomMethode;
    }
    

    
    @SuppressWarnings("unchecked")
    @Override
    public void run()
    {
        try
        {
        	isLaunched = true;
            final var method = instance.getClass().getMethod(nomMethode, typeParams);
            if (null != method)
            {
                result = (T) method.invoke(instance, params);
            }
        }
        catch(Exception e)
        {
            if (e.getCause() instanceof BusinessException)
            {
                final BusinessException be = (BusinessException) e.getCause();
                result = (T) be.getConteneur();
            }
        }
    }
    
    
    
    public T getResultatThread()
    {
        return result;
    } 
    
    
    
    public boolean isLaunched()
    {
    	return isLaunched;
    }
}
