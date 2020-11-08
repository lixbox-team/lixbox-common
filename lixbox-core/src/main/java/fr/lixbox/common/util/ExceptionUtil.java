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
package fr.lixbox.common.util;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.CriticalBusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe gere le flot d'exception et renvoie l'exception
 * adequate.
 * 
 * @author ludovic.terral
 */
public class ExceptionUtil extends ExceptionUtils
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(ExceptionUtil.class);
    
    
    
    // ----------- Methode -----------
    /**
     * Cette methode traite les exceptions que peuvent rencontrer
     * les methodes du code Metier et lance soit une business soit une critical
     * soit une processus Exception
     * 
     * @param e
     * @param racineClasse
     * @param throwException
     * 
     * @throws BusinessException
     */
    public static void traiterException(final Exception e, final String racineClasse, boolean throwException)
        throws BusinessException
    {
        Exception result = null;
        
        //Traitement des exceptions de l'application
        if (e instanceof javax.validation.ConstraintViolationException)
        {
            LOG.error(getRootCauseMessage(e));
            ConteneurEvenement conteneurEvenement = new ConteneurEvenement();
            conteneurEvenement.addAll(((javax.validation.ConstraintViolationException)e).getConstraintViolations());
            result = new BusinessException(LixboxResources.getString("MSG.ERROR.EXCEPUTI_03", racineClasse),conteneurEvenement);
        }
        if (e instanceof BusinessException)
        {
            if (!StringUtils.contains(getRootCauseMessage(e), "_01") &&
                    !StringUtils.contains(getRootCauseMessage(e), "_02"))
            {
                LOG.info(getRootCauseMessage(e));
            }
            result = e;
        }
        if (e instanceof CriticalBusinessException)
        {
            LOG.error(getRootCauseMessage(e));
            result = e;
        }
        if (e instanceof ProcessusException)
        {
            LOG.fatal(getRootCauseMessage(e));
            result = e;
        }
                
        
        //Traitement des exceptions aleatoires
        if (e instanceof EntityNotFoundException ||
            e instanceof NoResultException)            
        {
            result = new BusinessException(LixboxResources.getString("MSG.ERROR.EXCEPUTI_01", racineClasse));
        }
        
        
        //Toute exception inconnue est transformee en ProcessusException        
        if (null == result)
        {
            LOG.fatal(getRootCauseMessage(e));
            result = new ProcessusException(e);
        }         
        // throw une exception typee   
        if (throwException)
        {
            if (result instanceof BusinessException)
            {
                throw (BusinessException) result;
            }
            if (result instanceof CriticalBusinessException)
            {
                throw (CriticalBusinessException) result;
            }
            if (result instanceof ProcessusException)
            {
                throw (ProcessusException) result;
            }
        }
    }
}
