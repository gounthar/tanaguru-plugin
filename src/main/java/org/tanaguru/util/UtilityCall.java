/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tanaguru.util;

import java.util.Random;

/**
 *
 * @author mkebri
 */
public class UtilityCall {
/*
    *Generate random code  for tracking the audit
    */
   public static String getCode() {
        String chars = "_abcdefghijklmnopqrstuvwxyz|ABCDEFGHIJKLMNOPQRSTUVWXYZ&@1234567890*";
        int length = 5;

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
