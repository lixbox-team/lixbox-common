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
package fr.lixbox.common.mail.model;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.util.StringUtil;


/**
 * Cette entite sert au transfert de mail
 * 
 * @author ludovic.terral
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email
{
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(Email.class);
    
    protected String charset;
    protected String from;
    protected String toList;
    protected String ccList;
    protected String bccList;
    protected String subject;
    protected String msg;
    protected String msgId;
    

    
    // ----------- Methodes -----------
    public static Email valueOf(String json)
    {
        TypeReference<Email> typeRef = new TypeReference<Email>() {};
        Email result = null;
        if (StringUtil.isNotEmpty(json)) {
            var mapper = new ObjectMapper();
            try 
            {
                result = mapper.readValue(json, typeRef);
            } 
            catch (Exception e) {
                LOG.error(e, e);
            }
        }
        return result;
    }
    
    
    
    public String getCharset()
    {
        return charset;
    }
    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    public String getFrom()
    {
        return from;
    }
    public void setFrom(String from)
    {
        this.from = from;
    }
    
    

    public String getSubject()
    {
        return subject;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    
    
    public String getToList()
    {
        return toList;
    }
    public void setToList(String toList)
    {
        this.toList = toList;
    }
    
    

    public String getCcList()
    {
        return ccList;
    }
    public void setCcList(String ccList)
    {
        this.ccList = ccList;
    }
    
    

    public String getBccList()
    {
        return bccList;
    }
    public void setBccList(String bccList)
    {
        this.bccList = bccList;
    }
    
    

    public String getMsgId()
    {
        return msgId;
    }
    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }
    
    
    
    public String getMsg()
    {
        return msg;
    }
    public void setMsg(String msg)
    {
        this.msg = msg;
    }



    @Override
    public String toString()
    {
        var result = "";
        var mapper = new ObjectMapper();
        var writer = mapper.writer();
        try {
            result = writer.writeValueAsString(this);
        } 
        catch (JsonProcessingException e) 
        {
            LOG.error(e,e);
        }
        return result;
    }
}