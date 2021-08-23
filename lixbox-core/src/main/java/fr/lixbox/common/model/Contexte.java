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
package fr.lixbox.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Embeddable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est l'entite stocke un contexte
 * d'activite.
 * 
 * @author ludovic.terral
 */
@Embeddable
public class Contexte implements Serializable
{
    // ----------- Attribut -----------   
    private static final long serialVersionUID = -688305311616951524L;
    protected static final Log LOG = LogFactory.getLog(Contexte.class);  
    private Map<String, String> contenu;

    
    
    // ----------- Methode -----------
    public static Contexte valueOf(String json)
    {
        Contexte result = null;
        TypeReference<Contexte> typeRef = new TypeReference<Contexte>() {};
        if (StringUtil.isNotEmpty(json)) {
            var mapper = new ObjectMapper();

            try {
                result = mapper.readValue(json, typeRef);
            } catch (Exception var5) {
                LOG.error(var5, var5);
            }
        }

        return result;
    }
    
    
    
    public Contexte()
    {
        contenu = new HashMap<>();
    }
    
    
    
    public Contexte(Contexte contexte)
    {
        contenu = contexte.contenu;
    }   

    
    
    public Contexte dupliquer()
    {
        final var clone = new Contexte();
        for (Entry<String, String> entry: contenu.entrySet())
        {
            clone.contenu.put(entry.getKey(), entry.getValue());
        }
        return clone;
    }
           
        
 
    public Map<String, String> getContenu()
    {
        return contenu;
    }
    public void setContenu(Map<String, String> contenu)
    {
        this.contenu = contenu;
    }



    /**
     * Cette methode saisit un element contextuel
     * dans la pile de contexte.
     * 
     * @param elementName 
     * @param elementValue
     * 
     * @throws ProcessusException
     */
    public void put(final String elementName, final String elementValue)
    {        
        contenu.put(elementName, elementValue);
    }
    
    
    
    /**
     * Cette methode recupere un element contextuel
     * dans la pile de contexte.
     * 
     * @param element 
     * 
     * @return valeur de l'element contextuel
     */
    public String get(final String element)
    {
        return contenu.get(element);
    }

    

    @Override
    public String toString()
    {
        var result = "Content error";
        var mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } 
        catch (JsonProcessingException e) 
        {
            LOG.error(e,e);
        }
        return result;
    }
}