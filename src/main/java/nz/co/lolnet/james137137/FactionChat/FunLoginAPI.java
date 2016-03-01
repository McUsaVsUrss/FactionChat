/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
class FunLoginAPI {

    static boolean enable;

    public FunLoginAPI(boolean aThis) {
        enable = aThis;
    }

    private static boolean isLoggedIn(Player player) {
        if (enable) {
            boolean result = false;
            try {
                result = com.mattecarra.funlogin.FunLoginPlugin.getInstance().isLogged(player);
            } catch (Exception e) {}
            return result;
        }
        return true;
    }

    public static boolean isAllowToChat(Player player) {
        if (!enable) {
            return true;
        }
        return isLoggedIn(player);
    }
}
