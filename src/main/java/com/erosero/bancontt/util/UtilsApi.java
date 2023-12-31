package com.erosero.bancontt.util;

import org.hibernate.PropertyValueException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilsApi {

    private UtilsApi() {
    }

    public static List<String> validationExceptionMessage(Exception ex, int size) {
        List<String> listMessage = new ArrayList<>();
        try {
            String msg = "";
            String msgError = "";
            if (Objects.isNull(ex)) {
                return listMessage;
            } else {
                if(!Objects.isNull(ex.getCause())) {
                    if (!Objects.isNull(ex.getCause().getCause())) {
                        String text = ex.getCause().getCause().getMessage();
                        if (text.contains("Detail")) {
                            text = text.substring(text.indexOf("Detail"), text.length() - 2);
                        }
                        msg += text + "\n";
                    } else if (!Objects.isNull(ex.getCause())) {
                        PropertyValueException pp = (PropertyValueException) ex.getCause();
                        String text = "";
                        //controlar mensajes de not null
                        if (pp.getMessage().contains("not-null")) {
                            text = pp.getMessage().substring(0, pp.getMessage().indexOf("com.")) + " " + pp.getPropertyName();
                        }
                        msg += text + "\n";
                    }
                }else if (!isNullEmpty(ex.getMessage())) {
                    msg = ex.getMessage();
                } else if (isNotNullEmpty(ex.getLocalizedMessage())) {
                    msg += "\n" + ex.getLocalizedMessage();
                }
                try {
                    if (!Objects.isNull(ex.getCause())) {
                        msgError += getCauseException(ex);
                    } else {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        msgError += sStackTrace + "\n";
                    }
                } catch (Exception e) {
                    Logger.getLogger(UtilsApi.class.getName()).log(Level.SEVERE,
                            "Error al generar msg de getprocessExceptionMessage", e);
                }
            }
            if (isNullEmpty(msg)) {
                msg = "ERROR NO IDENTIFICADO";
            }
            if(msg.contains("messageTemplate=")){
                msg=msg.substring(msg.indexOf("messageTemplate="), msg.indexOf("}")-1);
                msg=msg.substring(msg.indexOf("=")+2);
            }
            listMessage.add(msg.substring(0, msg.length() < size ? msg.length() : size));
            listMessage.add(msgError.substring(0, msgError.length() < size ? msgError.length() : size));
        }catch(Exception e){
            listMessage.add("ERROR UNICO DE PARSEO");
            listMessage.add("Error no identificado");
        }

        return listMessage;
    }

    public static List<String> getProcessExceptionMessage(Exception ex, int size) {
        List<String> listMessage = new ArrayList<>();
        try {
            String msg = "";
            String msgError = "";
            if (Objects.isNull(ex)) {
                return listMessage;
            } else {
                if(!Objects.isNull(ex.getCause())) {
                    if (!Objects.isNull(ex.getCause().getCause())) {
                        String text = ex.getCause().getCause().getMessage();
                        if (text.contains("Detail")) {
                            text = text.substring(text.indexOf("Detail"), text.length() - 2);
                        }
                        msg += text + "\n";
                    } else if (!Objects.isNull(ex.getCause())) {
                        PropertyValueException pp = (PropertyValueException) ex.getCause();
                        String text = "";
                        //controlar mensajes de not null
                        if (pp.getMessage().contains("not-null")) {
                            text = pp.getMessage().substring(0, pp.getMessage().indexOf("com.")) + " " + pp.getPropertyName();
                        }
                        msg += text + "\n";
                    }
                }else if (!isNullEmpty(ex.getMessage())) {
                    msg = ex.getMessage();
                } else if (isNotNullEmpty(ex.getLocalizedMessage())) {
                    msg += "\n" + ex.getLocalizedMessage();
                }
                try {
                    if (!Objects.isNull(ex.getCause())) {
                        msgError += getCauseException(ex);
                    } else {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        msgError += sStackTrace + "\n";
                    }
                } catch (Exception e) {
                    Logger.getLogger(UtilsApi.class.getName()).log(Level.SEVERE,
                            "Error al generar msg de getprocessExceptionMessage", e);
                }
            }
            if (isNullEmpty(msg)) {
                msg = "ERROR NO IDENTIFICADO";
            }
            listMessage.add(msg.substring(0, msg.length() < size ? msg.length() : size));
            listMessage.add(msgError.substring(0, msgError.length() < size ? msgError.length() : size));
        }catch(Exception e){
            listMessage.add("ERROR UNICO DE PARSEO");
            listMessage.add("Error no identificado");
        }

        return listMessage;
    }

    public static boolean isNullEmpty(Object object) {
        if (Objects.isNull(object))
            return true;
        else if (object.toString().isEmpty())
            return true;
        else
            return false;

    }

    public static boolean isNotNullEmpty(Object object) {
        return !isNullEmpty(object);
    }

    public static String getCauseException(Throwable ex) {
        String msg = (ex.getCause() == null ? "" : ex.getCause().getMessage());
        if (ex.getCause().getCause() != null) {
            msg += (isNullEmpty(ex.getCause().getCause().getMessage()) ? "" : ex.getCause().getCause().getLocalizedMessage())+"\n";
        }
        return msg;
    }

}
