/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import mx.mc.init.Constantes;

/**
 *
 * @author apalacios
 */
public class GS1Parser {
    //Application Identifiers (AIs)
    public static final String AI_GTIN = "01";
    public static final String AI_GTIN_CONTAINED = "02";
    public static final String AI_LOT_NUMBER = "10";
    public static final String AI_EXPIRATION_DATE = "17";
    public static final String AI_QUANTITY = "37";
    
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final String FNC1 = "0x1D";
    
    public static class GS1Item {
        private String ai;
        private String value;

        public GS1Item() {
            ai = "";
            value = "";
        }

        public GS1Item(String ai, String value) {
            this.ai = ai;
            this.value = value;
        }

        public String getAi() {
            return ai;
        }

        public void setAi(String ai) {
            this.ai = ai;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }        
    }
    
    public static List<GS1Item> parse(String code) throws Exception {
        List<GS1Item> items = new ArrayList<>();
        if (code != null && !code.isEmpty()) {
            code = code.replace(FNC1, "");
            while (code.length() > 1) {
                int endPos = code.indexOf(")");
                if (!code.startsWith("(") || endPos <= 1 || endPos >= code.length() || code.substring(1, endPos).contains("(")) {
                    throw new Exception(RESOURCES.getString("gs1.err.codigoInvalido"));
                }
                String ai = code.substring(1, endPos);
                int i = endPos + 1;
                String value = "";
                while (i < code.length()) {
                    if (code.charAt(i) != '(')
                        value += code.charAt(i++);
                    else
                        break;
                }
                if (value.isEmpty())
                    throw new Exception(RESOURCES.getString("gs1.err.valorVacio") + " - (" + ai + ")");
                GS1Item item = new GS1Item(ai, value);
                items.add(item);
                code = code.substring(i);
            }
        }
        else
            throw new Exception(RESOURCES.getString("gs1.err.codigoVacio"));
        return items;
    }
}