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
package fr.lixbox.common.mail.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.mail.model.Email;
import fr.lixbox.common.mail.model.EmailAdresse;
import fr.lixbox.common.mail.model.EmailAttachement;
import fr.lixbox.common.mail.model.EmailImpl;
import fr.lixbox.common.mail.model.EmailList;
import fr.lixbox.common.mail.model.HtmlEmailImpl;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.resource.LixboxResources;
import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.common.util.StringUtil;

/**
 * Cette methode est un utilitaire d'envoie de mail.
 * 
 * @author ludovic.terral
 */
public class MailUtil implements Serializable
{
    // ----------- Attribut -----------
    private static final long serialVersionUID = -200828021426304L;
    private static final Log LOG = LogFactory.getLog(MailUtil.class);
    
    private static final String PATH_SEPARATOR = "/";
    private static final String PORT_SEPARATOR = ":";
    private static final EmailAdresse MAIL_FROM = new EmailAdresse("Application <noreply@dev.lan>");
    private static final String MAIL_TMP_PATH = System.getProperty("java.io.tmpdir");
    
    private final ConteneurEvenement conteneurEvent = new ConteneurEvenement();
    private String hostMailName = "serveurSMTP";
    private int smtpPort = 25;
    private int popPort = 110;



    // ----------- Methode -----------
    /**
     * Cette methode sert a modifier l'adresse du serveur mail.
     * 
     * @param hostMailName
     */
    public void setHostMailName(String hostMailName)
    {
        if (!StringUtil.isEmpty(hostMailName) && hostMailName.contains(PORT_SEPARATOR))
        {
            this.popPort = Integer.parseInt(hostMailName.substring(hostMailName.indexOf(PATH_SEPARATOR) + 1));
            this.smtpPort = Integer.parseInt(hostMailName.substring(hostMailName.indexOf(PORT_SEPARATOR) + 1,
                    hostMailName.indexOf(PATH_SEPARATOR)));
            this.hostMailName = hostMailName.substring(0, hostMailName.indexOf(PORT_SEPARATOR));
        }
        else
        {
            this.hostMailName = hostMailName;
        }
    }



    /**
     * Cette methode envoie un simple mail.
     * 
     * @param from
     * @param destinataires
     * @param copies
     * @param copiesCachees
     * @param objet
     * @param message
     * @param adresseRetour
     * 
     * @return idmailEnvoye
     * 
     * @throws BusinessException
     */
    public String envoyerMail(EmailAdresse from, EmailList destinataires, EmailList copies,
            EmailList copiesCachees, String objet, String message, EmailAdresse adresseRetour)
                    throws BusinessException
    {
        // Preparation du mail
        final EmailImpl email = prepareMail(new EmailImpl(), from, destinataires, copies, copiesCachees,
                objet, message, adresseRetour);
        // Envoi du mail
        return envoyerMail(email);
    }



    /**
     * Envoie un simple mail d'adresses literrales
     * 
     * @param from
     * @param destinataires
     * @param copies
     * @param copiesCachees
     * @param objet
     * @param message
     * @param adresseRetour
     * @return
     * @throws BusinessException
     */
    public String envoyerMail(String strFrom, String strDestinataires, String strCopies,
            String strCopiesCachees, String objet, String message, String strAdresseRetour)
                    throws BusinessException
    {
        EmailAdresse from;
        EmailList destinataires;
        EmailList copies;
        EmailList copiesCachees;
        EmailAdresse adresseRetour;
        // Preparation des adresses mail
        from = new EmailAdresse(strFrom);
        destinataires = new EmailList(strDestinataires);
        copies = new EmailList(strCopies);
        copiesCachees = new EmailList(strCopiesCachees);
        adresseRetour = new EmailAdresse(strAdresseRetour);
        // Preparation du mail
        final EmailImpl email = prepareMail(new EmailImpl(), from, destinataires, copies, copiesCachees,
                objet, message, adresseRetour);
        // Envoi du mail
        return envoyerMail(email);
    }



    /**
     * Envoie un simple mail d'adresses literrales
     * 
     * @param from
     * @param destinataires
     * @param copies
     * @param copiesCachees
     * @param objet
     * @param message
     * @param adresseRetour
     * @return
     * @throws BusinessException
     */
    public String envoyerMailFormatHtml(String strFrom, String strDestinataires, String strCopies,
            String strCopiesCachees, String objet, String message, String strAdresseRetour)
                    throws BusinessException
    {
        // Preparation des adresses mail
        EmailAdresse from = null != strFrom ? new EmailAdresse(strFrom) : null;
        EmailList destinataires = null != strDestinataires ? new EmailList(strDestinataires) : null;
        EmailList copies = null != strCopies ? new EmailList(strCopies) : null;
        EmailList copiesCachees = null != strCopiesCachees ? new EmailList(strCopiesCachees) : null;
        EmailAdresse adresseRetour = null != strAdresseRetour ? new EmailAdresse(strAdresseRetour)
                : null;
        // Envoi du mail
        return envoyerMailFormatHtml(from, destinataires, copies, copiesCachees, objet, message,
                adresseRetour);
    }



    /**
     * Cette methode envoie un mail avec piece(s) jointe(s).
     * 
     * @param from
     * @param destinataires
     * @param copies
     * @param copiesCachees
     * @param objet
     * @param message
     * @param adresseRetour
     * @param piecesJointes
     * 
     * @throws BusinessException
     * 
     * @return idmailEnvoye
     */
    public String envoyerMailPiecesJointes(EmailAdresse from, EmailList destinataires,
            EmailList copies, EmailList copiesCachees, String objet, String message,
            EmailAdresse adresseRetour, List<Object> piecesJointes) throws BusinessException
    {
        // Preparation du mail
        EmailImpl email = prepareMail(new EmailImpl(), from, destinataires, copies, copiesCachees, objet,
                message, adresseRetour);
        // Ajout de la ou des pieces jointes
        email = attacherPiecesJointes(email, piecesJointes);
        // Envoi du mail
        return envoyerMail(email);
    }



    /**
     * Cette methode envoie un mail formate en HTML avec URLs jointe et piece(s)
     * jointe(s).
     * 
     * @param from
     * @param destinataires
     * @param copies
     * @param copiesCachees
     * @param objet
     * @param message
     * @param adresseRetour
     * 
     * @throws BusinessException
     *
     * @return idMailEnvoye
     */
    public String envoyerMailFormatHtml(EmailAdresse from, EmailList destinataires,
            EmailList copies, EmailList copiesCachees, String objet, String message,
            EmailAdresse adresseRetour) throws BusinessException
    {
        // Preparation du mail
        HtmlEmailImpl email = prepareMail(new HtmlEmailImpl(), from, destinataires, copies, copiesCachees,
                objet, message, adresseRetour);
        return email.sendMimeMessage();
    }



    /**
     * Cette methode a pour object de renvoyer une liste d'email liee a une
     * boite via l'utilisation du pop3
     * 
     * @param boite
     * @param motDePasse
     * 
     * @return liste Email
     */
    public List<EmailImpl> recevoirMailPop3(EmailAdresse boite, String motDePasse)
    {
        Message[] messages = null;
        List<EmailImpl> result = new ArrayList<>();
        try
        {
            Object[] params = connecterPop3(boite, motDePasse);
            Folder folder = (Folder) params[0];
            Store store = (Store) params[1];
            messages = folder.getMessages();
            // traiter les messages et les reconvertirs en objet Email
            if ((null != messages) && (messages.length > 0))
            {
                for (Message msg : messages)
                {
                    EmailImpl email = new EmailImpl();
                    email.setHostName(this.hostMailName);
                    email.setFrom((InternetAddress) msg.getFrom()[0]);
                    email.addTo((InternetAddress[]) msg.getRecipients(RecipientType.TO));
                    email.addCc((InternetAddress[]) msg.getRecipients(RecipientType.CC));
                    email.addBcc((InternetAddress[]) msg.getRecipients(RecipientType.BCC));
                    email.setSubject(msg.getSubject());
                    if (msg.getContent() instanceof MimeMultipart)
                    {
                        email.setContent((MimeMultipart) msg.getContent());
                    }
                    else
                    {
                        email.setContent(msg.getContent(), msg.getContentType());
                    }
                    email.setMsgId(Integer.toString(msg.getMessageNumber()));
                    email = recupererPiecesJointes(msg, email);
                    result.add(email);
                }
            }
            folder.close(true);
            store.close();
        }
        catch (Exception e)
        {
            LOG.fatal(e);
        }
        return result;
    }



    /**
     * Cette methode supprime tous les mails de la boite
     * 
     * @param boite
     * @param motDePasse
     */
    public void viderBoiteMailPop3(EmailAdresse boite, String motDePasse)
    {
        try
        {
            Object[] params = connecterPop3(boite, motDePasse);
            Folder folder = (Folder) params[0];
            Store store = (Store) params[1];
            Flags deleted = new Flags(Flags.Flag.DELETED);
            folder.setFlags(folder.getMessages(), deleted, true);
            folder.close(true);
            store.close();
        }
        catch (Exception e)
        {
            LOG.fatal(e);
        }
    }



    /**
     * Cette methode assure la suppression d'un message
     * 
     * @param boite
     * @param motDePasse
     * @param email
     */
    public void supprimerMailPop3(EmailAdresse boite, String motDePasse, EmailImpl email)
    {
        try
        {
            Object[] params = connecterPop3(boite, motDePasse);
            Folder folder = (Folder) params[0];
            Store store = (Store) params[1];
            Flags deleted = new Flags(Flags.Flag.DELETED);
            for (Message msg : folder.getMessages())
            {
                if (email.getSubject().equals(msg.getSubject())
                        && email.getFromAddress().equals(msg.getFrom()[0]))
                {
                    folder.setFlags(folder.getMessages(new int[] { msg.getMessageNumber() }),
                            deleted, true);
                }
            }
            folder.close(true);
            store.close();
        }
        catch (Exception e)
        {
            LOG.fatal(e);
        }
    }



    /**
     * Cette methode gere l envoi du mail.
     * 
     * @param email
     * 
     * @return idMailEnvoye
     * @throws BusinessException 
     */
    public String envoyerMail(Email email) throws BusinessException
    {
        // Preparation des adresses mail
        EmailAdresse from = new EmailAdresse(email.getFrom());
        EmailList destinataires = new EmailList(email.getToList());
        EmailList copies = new EmailList(email.getCcList());
        EmailList copiesCachees = new EmailList(email.getBccList());
        EmailAdresse adresseRetour = new EmailAdresse(email.getFrom());
        
        
        // Preparation du mail
        final EmailImpl sender = prepareMail(new EmailImpl(), from, destinataires, copies, copiesCachees,
                email.getSubject(), email.getMsg(), adresseRetour);
        // Envoi du mail
        return envoyerMail(sender);
    }
    


    /**
     * Cette methode gere l envoi du mail.
     * 
     * @param email
     * 
     * @return idMailEnvoye
     */
    public String envoyerMail(EmailImpl email)
    {
        try
        {
            email.setSmtpPort(this.smtpPort);
            return email.send();
        }
        catch (ProcessusException ee)
        {
            LOG.fatal(ee);
            throw new ProcessusException(LixboxResources.getString("error.list.MAILUTIL_10",
                    email.getSubject() + " le " + email.getSentDate() + " " + ee.getMessage()),
                    conteneurEvent);
        }
    }



    /**
     * Cette methode realise la connection a une boite au lettre au format POP3
     * 
     * @param boite
     * @param motDePasse
     * 
     * @throws MessagingException
     * 
     */
    private Object[] connecterPop3(EmailAdresse boite, String motDePasse) throws MessagingException
    {
        Object[] result = new Object[2];
        Session session = Session.getDefaultInstance(System.getProperties(), null);
        Store store = session.getStore("pop3");
        try
        {
            store.connect(hostMailName, popPort, boite.getAdresseMail(), motDePasse);
        }
        catch (Exception e)
        {
            LOG.trace(e);
            store.connect(hostMailName, popPort, boite.getNomUtilisateur(), motDePasse);
        }
        Folder folder = store.getDefaultFolder();
        folder = folder.getFolder("inbox");
        folder.open(Folder.READ_WRITE);
        result[0] = folder;
        result[1] = store;
        return result;
    }



    /**
     * Cette methode recupere les pieces jointes d'un mail
     * 
     * @param msg
     * 
     * @param email
     *            mis a jour
     */
    private static EmailImpl recupererPiecesJointes(Message msg, EmailImpl email)
    {
        try
        {
            if (msg.getContent() instanceof Multipart)
            {
                Multipart mp = (Multipart) msg.getContent();
                int n = mp.getCount();
                for (int j = 0; j < n; j++)
                {
                    Part part = mp.getBodyPart(j);
                    String disposition = part.getDisposition();
                    if (disposition != null && (disposition.equals(Part.ATTACHMENT)
                            || disposition.equals(Part.INLINE)))
                    {
                        LOG.trace(new File(MAIL_TMP_PATH).mkdirs());
                        File save = new File(MAIL_TMP_PATH + part.getFileName());
                        Files.delete(save.toPath());
                        try (
                            InputStream is = part.getInputStream();
                            FileOutputStream fos = new FileOutputStream(save);
                        )
                        {
                            byte[] tampon = new byte[8];
                            while ((is.read(tampon)) >= 0)
                            {
                                fos.write(tampon);
                            }
                        }
                        catch (IOException e)
                        {
                            LOG.fatal(e);
                        }
                        email.getListePiecesJointes().add(save);
                    }
                }
            }
        }
        catch (IOException | MessagingException e)
        {
            LOG.error(e);
            LOG.debug(e,e);
        }
        return email;
    }



    /**
     * Cette methode gere la preparation du mail.
     * 
     * @param email
     * @param from
     * @param destinataires
     * @param copies
     * @param copiesCachees
     * @param objet
     * @param message
     * @param adresseRetour
     * 
     * @throws BusinessException
     * 
     * @return email
     */
    private <T extends EmailImpl> T prepareMail(T email, EmailAdresse from, EmailList destinataires,
            EmailList copies, EmailList copiesCachees, String objet, String message,
            EmailAdresse returnAddress) throws BusinessException
    {
        // Controle des parametres
        if (!(email != null && !StringUtil.isEmpty(objet)))
        {
            throw new BusinessException(LixboxResources.getString("error.list.MAILUTIL_01"),
                    conteneurEvent);
        }
        // Preparation du mail
        EmailAdresse adresseRetour = returnAddress == null ? MAIL_FROM : returnAddress;
        try
        {
            email.setDebug(true); // Optionnel
            email.setHostName(hostMailName);
            email.setSmtpPort(smtpPort);
            email.setFrom(from.getAdresseMail(), from.getNom());
            // Ajout des destinataires
            if (null != destinataires
                    && !CollectionUtil.isEmpty(destinataires.getListeEmailAdresses()))
            {
                for (EmailAdresse adresse : destinataires.getListeEmailAdresses())
                {
                    email.addTo(adresse.getAdresseMail(), adresse.getNom());
                }
            }
            // Ajout des copies
            if (null != copies && !CollectionUtil.isEmpty(copies.getListeEmailAdresses()))
            {
                for (EmailAdresse adresse : copies.getListeEmailAdresses())
                {
                    email.addCc(adresse.getAdresseMail(), adresse.getNom());
                }
            }
            // Ajout des copies Cachees
            if (null != copiesCachees
                    && !CollectionUtil.isEmpty(copiesCachees.getListeEmailAdresses()))
            {
                for (EmailAdresse adresse : copiesCachees.getListeEmailAdresses())
                {
                    email.addBcc(adresse.getAdresseMail(), adresse.getNom());
                }
            }
            email.setBounceAddress(adresseRetour.getAdresseMail());
            email.setSubject(objet);
            if (!(email instanceof HtmlEmailImpl))
            {
                if (!StringUtil.isEmpty(message))
                {
                    email.setMsg(message);
                }
            }
            else
            {
                email.setHtmlMsg(message);
            }
            return email;
        }
        catch (ProcessusException ee)
        {
            LOG.trace(ee);
            throw new ProcessusException(
                    LixboxResources.getString("error.list.MAILUTIL_02", ee.getMessage()),
                    conteneurEvent);
        }
    }



    /**
     * Cette methode gere l attachement de pieces jointes au mail.
     * 
     * @param email
     * @param piecesJointes
     * 
     * @throws BusinessException
     * 
     * @return email
     */
    private <T extends EmailImpl> T attacherPiecesJointes(T email, List<Object> piecesJointes)
            throws BusinessException
    {
        // Controle des parametres
        if (!CollectionUtil.isEmpty(piecesJointes))
        {
            for (int i = 0; i < piecesJointes.size(); i++)
            {
                if (!(piecesJointes.get(i) instanceof String)
                        && !(piecesJointes.get(i) instanceof URL))
                {
                    throw new BusinessException(
                            LixboxResources.getString("error.list.MAILUTIL_03"), conteneurEvent);
                }
            }
        }
        // Attachement de pieces jointes au mail
        if (!CollectionUtil.isEmpty(piecesJointes))
        {
            for (int i = 0; i < piecesJointes.size(); i++)
            {
                try
                {
                    // Create the attachment
                    EmailAttachement attachment = new EmailAttachement();
                    if (piecesJointes.get(i) instanceof String)
                    {
                        attachment.setPath((String) piecesJointes.get(i));
                        attachment.setDescription(
                                "Document numero " + (i + 1) + " du mail " + email.getSubject());
                        int lastIndexOfSlash = ((String) piecesJointes.get(i)).lastIndexOf('\\')
                                + 1;
                        int lastIndexOfPoint = ((String) piecesJointes.get(i)).length();
                        attachment.setName(((String) piecesJointes.get(i))
                                .substring(lastIndexOfSlash, lastIndexOfPoint));
                        attachment.setPath((String) piecesJointes.get(i));
                    }
                    if (piecesJointes.get(i) instanceof URL)
                    {
                        attachment.setURL((URL) piecesJointes.get(i));
                        attachment.setDescription("Document URL du mail " + email.getSubject());
                        int lastIndexOfSlash = ((URL) piecesJointes.get(i)).getPath()
                                .lastIndexOf('/') + 1;
                        int lastIndexOfPoint = ((URL) piecesJointes.get(i)).getPath().length();
                        attachment.setName(((URL) piecesJointes.get(i)).getPath()
                                .substring(lastIndexOfSlash, lastIndexOfPoint));
                        attachment.setURL((URL) piecesJointes.get(i));
                    }
                    attachment.setDisposition(EmailAttachement.ATTACHMENT);
                    // add the attachment
                    email.attach(attachment);
                }
                catch (ProcessusException ee)
                {
                    LOG.trace(ee);
                    throw new ProcessusException(
                            LixboxResources.getString("error.list.MAILUTIL_04",
                                    piecesJointes.get(i) + " : " + ee.getMessage()),
                            conteneurEvent);
                }
            }
        }
        return email;
    }
}