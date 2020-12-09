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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.guid.GuidGenerator;
import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est l'entite HtmlMail. Elle surcouche l'api JAVAMAIL
 * pour faciliter l'envoie des emails de type HTML.
 * 
 * @author ludovic.terral
 */
public class HtmlEmailImpl extends EmailImpl
{
    // ----------- Attribut -----------   
    public static final int CID_LENGTH = 10;
    private static final String HTML_MESSAGE_START = "<html><body><pre>";
    private static final String HTML_MESSAGE_END = "</pre></body></html>";
    protected String text;
    protected Map<String, InlineImage> inlineEmbeds;


    
    // ----------- Methodes -----------  
    public HtmlEmailImpl()
    {
        this.inlineEmbeds = new HashMap<>();
    }

    

    public HtmlEmailImpl setTextMsg(String aText)
    {
        if (StringUtil.isEmpty(aText))
        {
            throw new ProcessusException("Invalid message supplied");
        }
        this.text = aText;
        return this;
    }


    
    @Override
    public void setMsg(String msg)
    {
        if (StringUtil.isEmpty(msg))
        {
            throw new ProcessusException("Invalid message supplied");
        }
        setTextMsg(msg);
        StringBuilder htmlMsgBuf = new StringBuilder(msg.length() + HTML_MESSAGE_START.length() + HTML_MESSAGE_END.length());
        htmlMsgBuf.append(HTML_MESSAGE_START).append(msg).append(HTML_MESSAGE_END);
        setHtmlMsg(htmlMsgBuf.toString());
    }



    public String embed(String urlString, String name)
    {
        try
        {
            return embed(new URL(urlString), name);
        }
        catch (MalformedURLException e)
        {
            throw new ProcessusException("Invalid URL", e);
        }
    }
    public String embed(URL url, String name)
    {
        if (StringUtil.isEmpty(name))
        {
            throw new ProcessusException("name cannot be null or empty");
        }
        if (this.inlineEmbeds.containsKey(name))
        {
            InlineImage ii = this.inlineEmbeds.get(name);
            URLDataSource urlDataSource = (URLDataSource) ii.getDataSource();
            if (url.toString().equals(urlDataSource.toString()))
            {
                return ii.getCid();
            }
            throw new ProcessusException("embedded name '" + name + "' is already bound to URL " + urlDataSource.getURL()
                    + "; existing names cannot be rebound");
        }
        InputStream is = null;
        try
        {
            is = url.openStream();
        }
        catch (IOException e)
        {
            throw new ProcessusException("Invalid URL", e);
        }
        finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            }
            catch (IOException ioe)
            {
                //tant pis
            }
        }
        return embed(new URLDataSource(url), name);
    }
    public String embed(File file)
    {
        if (StringUtil.isEmpty(file.getName()))
        {
            throw new ProcessusException("file name cannot be null or empty");
        }
        String filePath = null;
        try
        {
            filePath = file.getCanonicalPath();
        }
        catch (IOException ioe)
        {
            throw new ProcessusException("couldn't get canonical path for " + file.getName(), ioe);
        }
        if (this.inlineEmbeds.containsKey(file.getName()))
        {
            InlineImage ii = this.inlineEmbeds.get(file.getName());
            FileDataSource fileDataSource = (FileDataSource) ii.getDataSource();
            String existingFilePath = null;
            try
            {
                existingFilePath = fileDataSource.getFile().getCanonicalPath();
            }
            catch (IOException ioe)
            {
                throw new ProcessusException("couldn't get canonical path for file " + fileDataSource.getFile().getName()
                        + "which has already been embedded", ioe);
            }
            if (filePath.equals(existingFilePath))
            {
                return ii.getCid();
            }
            throw new ProcessusException("embedded name '" + file.getName() + "' is already bound to file " + existingFilePath
                    + "; existing names cannot be rebound");
        }
        if (!(file.exists()))
        {
            throw new ProcessusException("file " + filePath + " doesn't exist");
        }
        if (!(file.isFile()))
        {
            throw new ProcessusException("file " + filePath + " isn't a normal file");
        }
        if (!(file.canRead()))
        {
            throw new ProcessusException("file " + filePath + " isn't readable");
        }
        return embed(new FileDataSource(file), file.getName());
    }
    public String embed(DataSource dataSource, String name)
    {
        if (this.inlineEmbeds.containsKey(name))
        {
            InlineImage ii = this.inlineEmbeds.get(name);
            if (dataSource.equals(ii.getDataSource()))
            {
                return ii.getCid();
            }
            throw new ProcessusException("embedded DataSource '" + name + "' is already bound to name "
                    + ii.getDataSource().toString() + "; existing names cannot be rebound");
        }
        String cid = GuidGenerator.getGUID(this).toLowerCase();
        return embed(dataSource, name, cid);
    }
    public String embed(DataSource dataSource, String name, String cid)
    {
        if (StringUtil.isEmpty(name))
        {
            throw new ProcessusException("name cannot be null or empty");
        }
        MimeBodyPart mbp = new MimeBodyPart();
        try
        {
            mbp.setDataHandler(new DataHandler(dataSource));
            mbp.setFileName(name);
            mbp.setDisposition("inline");
            mbp.setContentID("<" + cid + ">");
            InlineImage ii = new InlineImage(cid, dataSource);
            this.inlineEmbeds.put(name, ii);
            return cid;
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
    }



    @Override
    public void buildMimeMessage()
    {
        try
        {
            build();
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
        super.buildMimeMessage();
    }



    private void build() throws MessagingException
    {
        MimeMultipart container = getContainer();
        MimeMultipart subContainer = null;
        BodyPart msgText = null;
        container.setSubType("related");
        subContainer = new MimeMultipart("alternative");
        if (!StringUtil.isEmpty(this.text))
        {
            msgText = new MimeBodyPart();
            if (this.inlineEmbeds.size() > 0)
            {
                subContainer.addBodyPart(msgText);
            }
            else
            {
                container.addBodyPart(msgText);
            }
            if (!StringUtil.isEmpty(this.charset))
            {
                msgText.setContent(this.text, "text/plain; charset=" + this.charset);
            }
            else
            {
                msgText.setContent(this.text, "text/plain");
            }
        }        
        if (this.inlineEmbeds.size() <= 0)
        {
            return;
        }
        addPart(subContainer, 0);
    }

    
    
    
    /**
     * INNERCLASS
     */
    private static class InlineImage
    {
        private String cid;
        private DataSource dataSource;



        public InlineImage(String cid, DataSource dataSource)
        {
            this.cid = cid;
            this.dataSource = dataSource;
        }



        public String getCid()
        {
            return this.cid;
        }



        public DataSource getDataSource()
        {
            return this.dataSource;
        }



        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (!(obj instanceof InlineImage))
            {
                return false;
            }
            InlineImage that = (InlineImage) obj;
            return this.cid.equals(that.cid);
        }



        public int hashCode()
        {
            return this.cid.hashCode();
        }
    }
}
