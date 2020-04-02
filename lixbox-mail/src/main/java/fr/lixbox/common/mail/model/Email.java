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
package fr.lixbox.common.mail.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est une extension de la classe MultipartEmail
 * 
 * @author ludovic.terral
 */
public class Email
{
    private static final String ADDRESS_LIST_PROVIDED_WAS_INVALID = "Address List provided was invalid";

    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(Email.class);    
    
    public static final String SENDER_EMAIL = "sender.email";
    public static final String SENDER_NAME = "sender.name";
    public static final String RECEIVER_EMAIL = "receiver.email";
    public static final String RECEIVER_NAME = "receiver.name";
    public static final String EMAIL_SUBJECT = "email.subject";
    public static final String EMAIL_BODY = "email.body";
    public static final String CONTENT_TYPE = "content.type";
    public static final String MAIL_HOST = "mail.smtp.host";
    public static final String MAIL_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_FROM = "mail.smtp.from";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_USER = "mail.smtp.user";
    public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String MAIL_TRANSPORT_TLS = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String SMTP = "smtp";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String ATTACHMENTS = "attachments";
    public static final String FILE_SERVER = "file.server";
    public static final String MAIL_DEBUG = "mail.debug";
    public static final String KOI8_R = "koi8-r";
    public static final String ISO_8859_1 = "iso-8859-1";
    public static final String US_ASCII = "us-ascii";
    public static final String ATTACHMENT = "attachment";
    
    protected List<File> listePiecesJointes;
    protected MimeMessage message;
    protected String charset;
    protected InternetAddress fromAddress;
    protected String subject;
    protected MimeMultipart emailBody;
    protected Object content;
    protected String contentType;
    protected boolean debug;
    protected Date sentDate;
    protected Authenticator authenticator;
    protected String hostName;
    protected String smtpPort;
    protected String sslSmtpPort;
    protected List<InternetAddress> toList;
    protected List<InternetAddress> ccList;
    protected List<InternetAddress> bccList;
    protected List<InternetAddress> replyList;
    protected String bounceAddress;
    protected Map<String, String> headers;
    protected boolean popBeforeSmtp;
    protected String popHost;
    protected String popUsername;
    protected String popPassword;
    protected boolean tls;
    protected boolean ssl;
    
    private Session session;    
    private MimeMultipart container;
    private BodyPart primaryBodyPart;
    private String subType;
    private boolean initialized;
    private boolean boolHasAttachments;
    private String msgId;
    

    
    // ----------- Methodes -----------    
    public Email()
    {
        this.hostName = "serveurSMTP";
        this.smtpPort = "25";
        this.sslSmtpPort = "465";
        this.toList = new ArrayList<>();
        this.ccList = new ArrayList<>();
        this.bccList = new ArrayList<>();
        this.replyList = new ArrayList<>();
        this.headers = new HashMap<>();
    }

       

    public List<File> getListePiecesJointes()
    {
        if (listePiecesJointes == null)
        {
            listePiecesJointes = new ArrayList<>();
        }
        return listePiecesJointes;
    }


    
    public void setStrFrom(String from)
    {
        setFrom(new EmailAdresse(from));
    }
    
    
    
    public String getStrTo()
    {
        StringBuilder builder = new StringBuilder();
        for (InternetAddress adress:toList)
        {
            builder.append(new EmailAdresse(adress).getAdresseLitterale());
            builder.append(";");
        }        
        return builder.toString();
    }
    public void setStrTo(String to)
    {
        addTo(new EmailList(to));
    }
    
    
    
    public String getStrCc()
    {
        StringBuilder builder = new StringBuilder();
        for (InternetAddress adress:ccList)
        {
            builder.append(new EmailAdresse(adress).getAdresseLitterale());
            builder.append(";");
        }        
        return builder.toString();
    }
    public void setStrCc(String cc)
    {
        addCc(new EmailList(cc));
    }
    
    
    
    public String getStrBcc()
    {
        StringBuilder builder = new StringBuilder();
        for (InternetAddress adress:bccList)
        {
            builder.append(new EmailAdresse(adress).getAdresseLitterale());
            builder.append(";");
        }        
        return builder.toString();
    }
    public void setStrBcc(String bcc)
    {
        addBcc(new EmailList(bcc));
    }
    
    

    public void setDebug(boolean d)
    {
        this.debug = d;
    }


    
    public void setAuthentication(String userName, String password)
    {
        this.authenticator = new PasswordAuthenticator(userName, password);
    }



    public void setCharset(String newCharset)
    {
        Charset set = Charset.forName(newCharset);
        this.charset = set.name();
    }



    public void setContent(MimeMultipart aMimeMultipart)
    {
        this.emailBody = aMimeMultipart;
    }



    public Object getContent()
    {
        String result = "";
        try
        {
            if (this.primaryBodyPart!=null)        
            {            
                result = this.primaryBodyPart.getContent().toString();
            }
        }
        catch(Exception e)
        {
            LOG.debug(e);
        }
        return result;
    }
    public void setContent(Object aObject, String aContentType)
    {
        this.content = aObject;
        if (StringUtil.isEmpty(aContentType))
        {
            this.contentType = null;
        }
        else
        {
            this.contentType = aContentType;
            String strMarker = "; charset=";
            int charsetPos = aContentType.toLowerCase().indexOf(strMarker);
            if (charsetPos != -1)
            {
                charsetPos += strMarker.length();
                int intCharsetEnd = aContentType.toLowerCase().indexOf(' ', charsetPos);
                if (intCharsetEnd != -1)
                {
                    this.charset = aContentType.substring(charsetPos, intCharsetEnd);
                }
                else
                {
                    this.charset = aContentType.substring(charsetPos);
                }
            }
            else
            {
                if ((!(this.contentType.startsWith("text/"))) || StringUtil.isEmpty(this.charset))
                {
                    return;
                }
                StringBuilder contentTypeBuf = new StringBuilder(this.contentType);
                contentTypeBuf.append(strMarker);
                contentTypeBuf.append(this.charset);
                this.contentType = contentTypeBuf.toString();
            }
        }
    }



    public void setHostName(String aHostName)
    {
        this.hostName = aHostName;
    }



    public void setTLS(boolean withTLS)
    {
        this.tls = withTLS;
    }



    public void setSmtpPort(int aPortNumber)
    {
        if (aPortNumber < 1)
        {
            throw new IllegalArgumentException("Cannot connect to a port number that is less than 1 ( " + aPortNumber + " )");
        }
        this.smtpPort = Integer.toString(aPortNumber);
    }



    public void setMailSession(Session aSession)
    {
        Properties sessionProperties = aSession.getProperties();
        String auth = sessionProperties.getProperty(MAIL_SMTP_AUTH);
        if ("true".equalsIgnoreCase(auth))
        {
            String userName = sessionProperties.getProperty(MAIL_SMTP_USER);
            String password = sessionProperties.getProperty(MAIL_SMTP_PASSWORD);
            this.authenticator = new PasswordAuthenticator(userName, password);
            this.session = Session.getInstance(sessionProperties, this.authenticator);
        }
        else
        {
            this.session = aSession;
        }
    }



    public void setMailSessionFromJNDI(String jndiName) throws NamingException
    {
        if (StringUtil.isEmpty(jndiName))
        {
            throw new IllegalArgumentException("JNDI name missing");
        }
        Context ctx = null;
        if (jndiName.startsWith("java:"))
        {
            ctx = new InitialContext();
        }
        else
        {
            ctx = (Context) new InitialContext().lookup("java:comp/env");
        }
        setMailSession((Session) ctx.lookup(jndiName));
    }



    public Session getMailSession()
    {
        if (this.session == null)
        {
            Properties properties = new Properties(System.getProperties());
            properties.setProperty(MAIL_TRANSPORT_PROTOCOL, "smtp");
            if (StringUtil.isEmpty(this.hostName))
            {
                this.hostName = properties.getProperty(MAIL_HOST);
            }
            if (StringUtil.isEmpty(this.hostName))
            {
                throw new ProcessusException("Cannot find valid hostname for mail session");
            }
            properties.setProperty(MAIL_PORT, this.smtpPort);
            properties.setProperty(MAIL_HOST, this.hostName);
            properties.setProperty(MAIL_DEBUG, String.valueOf(this.debug));
            if (this.authenticator != null)
            {
                properties.setProperty(MAIL_TRANSPORT_TLS, (this.tls) ? "true" : "false");
                properties.setProperty(MAIL_SMTP_AUTH, "true");
            }
            if (this.ssl)
            {
                properties.setProperty(MAIL_PORT, this.sslSmtpPort);
                properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_PORT, this.sslSmtpPort);
                properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
                properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, "false");
            }
            if (this.bounceAddress != null)
            {
                properties.setProperty(MAIL_SMTP_FROM, this.bounceAddress);
            }
            this.session = Session.getInstance(properties, this.authenticator);
        }
        return this.session;
    }

    

    public String getFrom()
    {
        String result = "";
        if (this.fromAddress!=null)
        {
            result = this.fromAddress.toString();
        }
        return result;
    }
    public Email setFrom(String email)
    {
        return setFrom(email, null);
    }
    public Email setFrom(String email, String name)
    {
        return setFrom(email, name, null);
    }
    public Email setFrom(String email, String name, String charset)
    {
        this.fromAddress = createInternetAddress(email, name, charset);
        return this;
    }
    public Email setFrom(InternetAddress adresse)
    {        
        this.fromAddress = adresse;
        return this;
    }
    public Email setFrom(EmailAdresse adresse)
    {        
        return setFrom(adresse.getAdresseMail(), adresse.getNom());
    }
    
    
    
    public List<InternetAddress> getToList()
    {
        return toList;
    }    
    public Email addTo(EmailList adresses)
    {        
        for (EmailAdresse adresse : adresses.getListeEmailAdresses())
        {
            addTo(adresse.getAdresseMail(), adresse.getNom());
        }
        return this;
    }    
    public Email addTo(EmailAdresse adresse)
    {        
        return addTo(adresse.getAdresseMail(), adresse.getNom());
    }
    public Email addTo(String email)
    {
        return addTo(email, null);
    }
    public Email addTo(String email, String name)
    {
        return addTo(email, name, null);
    }
    public Email addTo(String email, String name, String charset)
    {
        this.toList.add(createInternetAddress(email, name, charset));
        return this;
    }
    public Email setTo(Collection<InternetAddress> aCollection)
    {
        if ((aCollection == null) || (aCollection.isEmpty()))
        {
            throw new ProcessusException(ADDRESS_LIST_PROVIDED_WAS_INVALID);
        }
        this.toList = new ArrayList<>(aCollection);
        return this;
    }
    public Email addTo(InternetAddress adresse)
    {        
        this.toList.add(adresse);
        return this;
    }
    public Email addTo(List<InternetAddress> adresses)
    {        
        this.toList.addAll(adresses);
        return this;
    }

    public Email addTo(InternetAddress[] adresses)
    {        
        if (adresses != null)
        {
            for (InternetAddress adress : adresses)
            {
                this.toList.add(adress);
            }
        }
        return this;
    }
    
        
    
    
    public List<InternetAddress> getCcList()
    {
        return ccList;
    }
    public Email addCc(EmailList adresses)
    {      
        if (adresses!=null && !CollectionUtil.isEmpty(adresses.getListeEmailAdresses()))
        {
            for (EmailAdresse adresse : adresses.getListeEmailAdresses())
            {
                addCc(adresse.getAdresseMail(), adresse.getNom());
            }
        }
        return this;
    }    
    public Email addCc(EmailAdresse adresse)
    {        
        return addCc(adresse.getAdresseMail(), adresse.getNom());
    }
    public Email addCc(String email)
    {
        return addCc(email, null);
    }
    public Email addCc(String email, String name)
    {
        return addCc(email, name, null);
    }
    public Email addCc(String email, String name, String charset)
    {
        this.ccList.add(createInternetAddress(email, name, charset));
        return this;
    }
    public Email setCc(Collection<InternetAddress> aCollection)
    {
        if ((aCollection == null) || (aCollection.isEmpty()))
        {
            throw new ProcessusException(ADDRESS_LIST_PROVIDED_WAS_INVALID);
        }
        this.ccList = new ArrayList<>(aCollection);
        return this;
    }   
    public Email addCc(InternetAddress adresse)
    {        
        this.ccList.add(adresse);
        return this;
    }
    public Email addCc(List<InternetAddress> adresses)
    {        
        this.ccList.addAll(adresses);
        return this;
    }

    public Email addCc(InternetAddress[] adresses)
    {      
        if (adresses != null)
        {
            for (InternetAddress adress : adresses)
            {
                this.ccList.add(adress);
            }
        }
        return this;
    }
        
    
    
    
    public List<InternetAddress> getBccList()
    {
        return bccList;
    }
    public Email addBcc(EmailList adresses)
    {        
        for (EmailAdresse adresse : adresses.getListeEmailAdresses())
        {
            addBcc(adresse.getAdresseMail(), adresse.getNom());
        }
        return this;
    }    
    public Email addBcc(EmailAdresse adresse)
    {        
        return addCc(adresse.getAdresseMail(), adresse.getNom());
    }        
    public Email addBcc(String email)
    {
        return addBcc(email, null);
    }
    public Email addBcc(String email, String name)
    {
        return addBcc(email, name, null);
    }
    public Email addBcc(String email, String name, String charset)
    {
        this.bccList.add(createInternetAddress(email, name, charset));
        return this;
    }
    public Email setBcc(Collection<InternetAddress> aCollection)
    {
        if ((aCollection == null) || (aCollection.isEmpty()))
        {
            throw new ProcessusException(ADDRESS_LIST_PROVIDED_WAS_INVALID);
        }
        this.bccList = new ArrayList<>(aCollection);
        return this;
    }    
    public Email addBcc(InternetAddress adresse)
    {        
        this.bccList.add(adresse);
        return this;
    }
    public Email addBcc(List<InternetAddress> adresses)
    {        
        this.bccList.addAll(adresses);
        return this;
    }

    public Email addBcc(InternetAddress[] adresses)
    {        
        if (adresses != null)
        {
            for (InternetAddress adress : adresses)
            {
                this.bccList.add(adress);
            }
        }
        return this;
    }

    

    public List<InternetAddress> getReplyTo()
    {
        return this.replyList;
    }
    public Email addReplyTo(EmailAdresse adresse)
    {        
        return addReplyTo(adresse.getAdresseMail(), adresse.getNom());
    } 
    public Email addReplyTo(String email)
    {
        return addReplyTo(email, null);
    }
    public Email addReplyTo(String email, String name)
    {
        return addReplyTo(email, name, null);
    }
    public Email addReplyTo(String email, String name, String charset)
    {
        this.replyList.add(createInternetAddress(email, name, charset));
        return this;
    }
    public Email setReplyTo(Collection<InternetAddress> aCollection)
    {
        if ((aCollection == null) || (aCollection.isEmpty()))
        {
            throw new ProcessusException(ADDRESS_LIST_PROVIDED_WAS_INVALID);
        }
        this.replyList = new ArrayList<>(aCollection);
        return this;
    }
    


    public void setHeaders(Map<String,String> map)
    {
        Iterator<Entry<String, String>> iterKeyBad = map.entrySet().iterator();
        while (iterKeyBad.hasNext())
        {
            Entry<String, String> entry = iterKeyBad.next();
            String strName = entry.getKey();
            String strValue = entry.getValue();
            if (StringUtil.isEmpty(strName))
            {
                throw new IllegalArgumentException("name can not be null");
            }
            if (StringUtil.isEmpty(strValue))
            {
                throw new IllegalArgumentException("value can not be null");
            }
        }
        this.headers = map;
    }
    public void addHeader(String name, String value)
    {
        if (StringUtil.isEmpty(name))
        {
            throw new IllegalArgumentException("name can not be null");
        }
        if (StringUtil.isEmpty(value))
        {
            throw new IllegalArgumentException("value can not be null");
        }
        this.headers.put(name, value);
    }



    public void setSubject(String aSubject)
    {
        this.subject = aSubject;
    }



    public Email setBounceAddress(String email)
    {
        this.bounceAddress = email;
        return this;
    }
    
    

    public String sendMimeMessage()
    {        
        try
        {
        	Transport transport = this.getMailSession().getTransport();
        	transport.connect();
        	Transport.send(this.message);
        	transport.close();
            return this.message.getMessageID();
        }
        catch (Exception e)
        {
            String msg = "Sending the email to the following server failed : " + getHostName() + ":" + getSmtpPort();
            throw new ProcessusException(msg, e);
        }
    }



    public MimeMessage getMimeMessage()
    {
        return this.message;
    }



    public String send()
    {
        buildMimeMessage();
        return sendMimeMessage();
    }



    public void setSentDate(Date date)
    {
        if (date!=null)
        {
            this.sentDate = (Date) date.clone();
        }
        else
        {
            this.sentDate = null;
        }
    }
    public Date getSentDate()
    {
        if (this.sentDate == null)
        {
            return new Date();
        }
        return (Date) this.sentDate.clone();
    }



    public String getSubject()
    {
        return this.subject;
    }



    public InternetAddress getFromAddress()
    {
        return this.fromAddress;
    }
    public EmailAdresse getFromAdress()
    {
        return new EmailAdresse(this.fromAddress);
    }



    public String getHostName()
    {
        if (!StringUtil.isEmpty(this.hostName))
        {
            return this.hostName;
        }
        return this.session.getProperty(MAIL_HOST);
    }



    public String getSmtpPort()
    {
        if (!StringUtil.isEmpty(this.smtpPort))
        {
            return this.smtpPort;
        }
        return this.session.getProperty(MAIL_PORT);
    }



    public boolean isTLS()
    {
        return this.tls;
    }



    public void setPopBeforeSmtp(boolean newPopBeforeSmtp, String newPopHost, String newPopUsername, String newPopPassword)
    {
        this.popBeforeSmtp = newPopBeforeSmtp;
        this.popHost = newPopHost;
        this.popUsername = newPopUsername;
        this.popPassword = newPopPassword;
    }



    public boolean isSSL()
    {
        return this.ssl;
    }
    public void setSSL(boolean ssl)
    {
        this.ssl = ssl;
    }



    public String getSslSmtpPort()
    {
        if (!StringUtil.isEmpty(this.sslSmtpPort))
        {
            return this.sslSmtpPort;
        }
        return this.session.getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT);
    }
    public void setSslSmtpPort(String sslSmtpPort)
    {
        this.sslSmtpPort = sslSmtpPort;
    }
    
    



    public void setSubType(String aSubType)
    {
        this.subType = aSubType;
    }
    public String getSubType()
    {
        return this.subType;
    }



    public Email addPart(String partContent, String partContentType)
    {
        BodyPart bodyPart = createBodyPart();
        try
        {
            bodyPart.setContent(partContent, partContentType);
            getContainer().addBodyPart(bodyPart);
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
        return this;
    }
    public Email addPart(MimeMultipart multipart)
    {
        try
        {
            return addPart(multipart, getContainer().getCount());
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
    }
    public Email addPart(MimeMultipart multipart, int index)
    {
        BodyPart bodyPart = createBodyPart();
        try
        {
            bodyPart.setContent(multipart);
            getContainer().addBodyPart(bodyPart, index);
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
        return this;
    }



    public String getMsg()
    {    
        String result="";
        try
        {
            result = getPrimaryBodyPart().getContent().toString();
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return result;
    }
    public void setMsg(String msg)
    {
        try
        {
            BodyPart primary = getPrimaryBodyPart();
            if ((primary instanceof MimePart) && (!StringUtil.isEmpty(this.charset)))
            {
                ((MimePart) primary).setText(msg, this.charset);
            }
            else
            {
                primary.setText(msg);
            }
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
    }
    
    public void setHtmlMsg(String msg)
    {
    	MimeMessage mimeMessage = new MimeMessage(this.getMailSession());
    	try
    	{
			mimeMessage.setContent(msg, Email.TEXT_HTML);
			mimeMessage.setFrom(this.getFrom());
			mimeMessage.setSubject(this.getSubject());
			for (InternetAddress adress : this.getToList())
			{
				mimeMessage.setRecipient(Message.RecipientType.TO, adress);
			}
			for (InternetAddress adress : this.getBccList())
			{
				mimeMessage.setRecipient(Message.RecipientType.BCC, adress);
			}
			for (InternetAddress adress : this.getCcList())
			{
				mimeMessage.setRecipient(Message.RecipientType.CC, adress);
			}
			
		}
    	catch (MessagingException e) {
    		throw new ProcessusException(e);
		}
    	this.message = mimeMessage;
    }

    

    public Email attach(EmailAttachement attachment)
    {
        Email result = null;
        if (attachment == null)
        {
            throw new ProcessusException("Invalid attachment supplied");
        }
        URL url = attachment.getURL();
        if (url == null)
        {
            String fileName = null;
            try
            {
                fileName = attachment.getPath();
                File file = new File(fileName);
                if (!(file.exists()))
                {
                    throw new IOException("\"" + fileName + "\" does not exist");
                }
                result = attach(new FileDataSource(file), attachment.getName(), attachment.getDescription(),
                        attachment.getDisposition());
            }
            catch (IOException e)
            {
                throw new ProcessusException("Cannot attach file \"" + fileName + "\"", e);
            }
        }
        else
        {
            result = attach(url, attachment.getName(), attachment.getDescription(), attachment.getDisposition());
        }
        return result;
    }
    public Email attach(URL url, String name, String description)
    {
        return attach(url, name, description, ATTACHMENT);
    }
    public Email attach(URL url, String name, String description, String disposition)
    {
        try
        {
            InputStream is = url.openStream();
            is.close();
        }
        catch (IOException e)
        {
            throw new ProcessusException("Invalid URL set");
        }
        return attach(new URLDataSource(url), name, description, disposition);
    }
    public Email attach(DataSource ds, String name, String description)
    {
        try
        {
            if ((ds == null) || (ds.getInputStream() == null))
            {
                throw new ProcessusException("Invalid Datasource");
            }
        }
        catch (IOException e)
        {
            throw new ProcessusException("Invalid Datasource");
        }
        return attach(ds, name, description, ATTACHMENT);
    }
    public Email attach(DataSource ds, String name, String description, String disposition)
    {
        if (StringUtil.isEmpty(name))
        {
            name = ds.getName();
        }
        BodyPart bodyPart = createBodyPart();
        try
        {
            getContainer().addBodyPart(bodyPart);
            bodyPart.setDisposition(disposition);
            bodyPart.setFileName(name);
            bodyPart.setDescription(description);
            bodyPart.setDataHandler(new DataHandler(ds));
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
        setBoolHasAttachments(true);
        return this;
    }



    protected BodyPart getPrimaryBodyPart() throws MessagingException
    {
        if (!(this.initialized))
        {
            init();
        }
        if (this.primaryBodyPart == null)
        {
            this.primaryBodyPart = createBodyPart();
            getContainer().addBodyPart(this.primaryBodyPart, 0);
        }
        return this.primaryBodyPart;
    }

    

    public boolean isBoolHasAttachments()
    {
        return this.boolHasAttachments;
    }
    public void setBoolHasAttachments(boolean b)
    {
        this.boolHasAttachments = b;
    }
    
    
    
    public String getMsgId()
    {
        return msgId;
    }
    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }



    protected boolean isInitialized()
    {
        return this.initialized;
    }
    protected void setInitialized(boolean b)
    {
        this.initialized = b;
    }
        
    

    protected MimeMultipart getContainer()
    {
        if (!(this.initialized))
        {
            init();
        }
        return this.container;
    }



    protected BodyPart createBodyPart()
    {
        return new MimeBodyPart();
    }



    protected MimeMultipart createMimeMultipart()
    {
        return new MimeMultipart();
    }

    protected void init()
    {
        if (this.initialized)
        {
            throw new IllegalStateException("Already initialized");
        }
        this.container = createMimeMultipart();
        setContent(this.container);
        this.initialized = true;
    }


    protected InternetAddress[] toInternetAddressArray(List<InternetAddress> list)
    {
        return list.toArray(new InternetAddress[list.size()]);
    }
    
    
    
    protected void buildMimeMessage()
    {
        try
        {
            if (this.primaryBodyPart != null)
            {
                BodyPart body = getPrimaryBodyPart();
                try
                {
                    body.getContent();
                }
                catch (IOException e)
                {
                    LOG.error(e);
                }
            }
            if (this.subType != null)
            {
                getContainer().setSubType(this.subType);
            }
            buildBasicMimeMessage();
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
    }    
    private void buildBasicMimeMessage()
    {
        try
        {
            getMailSession();
            this.message = new MimeMessage(this.session);
            if (!StringUtil.isEmpty(this.subject))
            {
                if (!StringUtil.isEmpty(this.charset))
                {
                    this.message.setSubject(this.subject, this.charset);
                }
                else
                {
                    this.message.setSubject(this.subject);
                }
            }
            if (this.content != null)
            {
                this.message.setContent(this.content, this.contentType);
            }
            else if (this.emailBody != null)
            {
                this.message.setContent(this.emailBody);
            }
            else
            {
                this.message.setContent("", TEXT_PLAIN);
            }
            if (this.fromAddress != null)
            {
                this.message.setFrom(this.fromAddress);
            }
            else if (this.session.getProperty(MAIL_SMTP_FROM) == null)
            {
                throw new ProcessusException("From address required");
            }
            if (this.toList.size() + this.ccList.size() + this.bccList.size() == 0)
            {
                throw new ProcessusException("At least one receiver address required");
            }
            if (!this.toList.isEmpty())
            {
                this.message.setRecipients(Message.RecipientType.TO, toInternetAddressArray(this.toList));
            }
            if (!this.ccList.isEmpty())
            {
                this.message.setRecipients(Message.RecipientType.CC, toInternetAddressArray(this.ccList));
            }
            if (!this.bccList.isEmpty())
            {
                this.message.setRecipients(Message.RecipientType.BCC, toInternetAddressArray(this.bccList));
            }
            if (!this.replyList.isEmpty())
            {
                this.message.setReplyTo(toInternetAddressArray(this.replyList));
            }
            if (!this.headers.isEmpty())
            {
                Set<Entry<String, String>> headers = this.headers.entrySet();
                for (Entry<String, String> header : headers)
                {                
                    String name = header.getKey();
                    String value = header.getKey();
                    this.message.addHeader(name, value);
                }
            }
            if (this.message.getSentDate() == null)
            {
                this.message.setSentDate(getSentDate());
            }
            if (this.popBeforeSmtp)
            {
                Store store = this.session.getStore("pop3");
                store.connect(this.popHost, this.popUsername, this.popPassword);
            }
        }
        catch (MessagingException me)
        {
            throw new ProcessusException(me);
        }
    }
    


    private InternetAddress createInternetAddress(String email, String name, String charsetName)
    {
        InternetAddress address = null;
        if (!StringUtil.isEmpty(email))
        {
            try
            {
                address = new InternetAddress(email);
                if (StringUtil.isEmpty(name))
                {
                    name = email;
                }
                if (StringUtil.isEmpty(charsetName))
                {
                    address.setPersonal(name);
                }
                else
                {
                    Charset set = Charset.forName(charsetName);
                    address.setPersonal(name, set.name());
                }
                address.validate();
            }
            catch (AddressException | UnsupportedEncodingException e)
            {
                throw new ProcessusException(e);
            }
        }
        return address;
    }



    @Override
    public String toString()
    {
        String result = "Content error";
        ObjectMapper mapper = new ObjectMapper();
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