package nz.co.lolnet.james137137.FactionChat;

import nz.co.lolnet.james137137.FactionChat.API.FactionChatAPI;
import nz.co.lolnet.james137137.FactionChat.API.EssentialsAPI;
import nz.co.lolnet.james137137.FactionChat.FactionsAPI.MyRel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatChannel {

    private FactionChat plugin;
    boolean IncludeTitle;

    ChatChannel(FactionChat aThis) {
        plugin = FactionChat.plugin;
        IncludeTitle = FactionChat.plugin.getConfig().getBoolean("FactionChatMessage.IncludeTitle");
    }

    /*
     * Sends a message to the player's Faction only.
     */
    protected void fChatF(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        String senderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name
        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {senderFaction, FactionChat.factionsAPI.getPlayerRank(player).toString(), FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String[] input2 = {ChatMode.FormatString(FactionChat.FactionChatMessage, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.FactionChatMessage, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
        senderFaction = FactionChat.factionsAPI.getFactionID(player);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && FactionChat.factionsAPI.getFactionID(myPlayer).equalsIgnoreCase(senderFaction) && myPlayer.hasPermission("FactionChat.FactionChat")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {

                myPlayer.sendMessage(spyMessage);
            }
        }

    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that is ally or truce with the player's Faction.
     */
    protected void fChatAT(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }
        String[] intput1 = {sSenderFaction, FactionChat.factionsAPI.getPlayerRank(player).toString(), FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.AllyTruceChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.AllyTruceChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.ALLY || relationship == MyRel.TRUCE
                    || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.AllyChat") && myPlayer.hasPermission("FactionChat.TruceChat") && !ChatMode.IsAllyMuted(myPlayer)) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }
    }

    public void fChatA(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }

        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player,message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }
        String[] intput1 = {sSenderFaction, FactionChat.factionsAPI.getPlayerRank(player).toString(), FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.AllyChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.AllyChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.ALLY
                    || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.AllyChat") && !ChatMode.IsAllyMuted(myPlayer)) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }
    }

    public void fChatTruce(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }

        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }
        String[] intput1 = {sSenderFaction, FactionChat.factionsAPI.getPlayerRank(player).toString(), FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.TruceChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.TruceChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.TRUCE
                    || sSenderFaction.equalsIgnoreCase(FactionChat.factionsAPI.getFactionName(myPlayer)) || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.TruceChat") && !ChatMode.IsAllyMuted(myPlayer)) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }
    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that enermies with the player's Faction.
     */
    protected void fChatE(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }

        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }
        String[] intput1 = {sSenderFaction, FactionChat.factionsAPI.getPlayerRank(player).toString(), FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.EnemyChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.EnemyChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.ENEMY
                    || sSenderFaction.equalsIgnoreCase(FactionChat.factionsAPI.getFactionName(player)) || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.EnemyChat") && !FactionChat.factionsAPI.isFactionless(myPlayer) && ChatMode.getChatMode(myPlayer).equals("ENEMY")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }

    }

    protected void fchato(CommandSender sender, String[] args) {

        if (!ChatMode.canChat(sender.getName())) {
            return;
        }

        Player player = (Player) sender;//get player

        if (args.length <= 1) { //checks if there is a message in command eg "hello world" in /fchat hello world
            player.sendMessage(FactionChat.messageFchatoMisstype);
        } else {

            String senderFaction = FactionChat.factionsAPI.getFactionName(player);

            if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
                player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
                //start of sending the message
                /*
                 * checks every player online to see if they belong in the senders faction if so it receives the message
                 * 
                 * added a admin chatspy with permision faction.spy
                 */
            } else {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }
                message = FactionChatAPI.filterChat(player, message);

                String playersFaction; //creates string outside loop
                String targetFaction = args[0] + senderFaction.charAt(senderFaction.length() - 2) + senderFaction.charAt(senderFaction.length() - 1);

                int count = 0;
                boolean allowCustomColour = player.hasPermission("essentials.chat.color");
                String playerTitle = "";
                if (FactionChat.IncludeTitle) {
                    playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
                }
                String playerName;
                if (FactionChat.useEssentialsNick) {
                    playerName = "~" + EssentialsAPI.getNickname(player);
                } else {
                    playerName = player.getName();
                }
                String[] intput1 = {senderFaction, FactionChat.factionsAPI.getPlayerRank(player).toString(), FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
                String[] input2 = {ChatMode.FormatString(FactionChat.OtherFactionChatSpy, intput1, playerTitle, allowCustomColour)};
                String toMessage = ChatMode.FormatString(FactionChat.OtherFactionChatTo, intput1, playerTitle, allowCustomColour);
                String FromMessage = ChatMode.FormatString(FactionChat.OtherFactionChatFrom, intput1, playerTitle, allowCustomColour);
                String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
                //start of loop
                for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

                    playersFaction = FactionChat.factionsAPI.getFactionName(myPlayer);

                    if (playersFaction.equalsIgnoreCase(senderFaction)) {
                        myPlayer.sendMessage(toMessage);
                    } else if (playersFaction.equalsIgnoreCase(targetFaction)) {
                        myPlayer.sendMessage(FromMessage);
                        count++;
                    } else if (ChatMode.isSpyOn(myPlayer)) {
                        myPlayer.sendMessage(spyMessage);
                    }

                }

                if (count == 0) {
                    player.sendMessage(ChatColor.RED + FactionChat.messageFchatoNoOneOnline);
                }
            }
        }

    }

    protected void fChatLeader(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }

        String senderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }

        String[] intput1 = {senderFaction, FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.LeaderChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.LeaderChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && FactionChatAPI.getPlayerRank(player).equals(FactionChat.LeaderRank) && ChatMode.getChatMode(myPlayer).equals("LEADER")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }

    }

    protected void fChatOfficer(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }

        String senderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (FactionChat.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
        } else {
            playerName = player.getName();
        }
        String[] intput1 = {senderFaction, FactionChatAPI.getPrefix(player) + playerName + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.OfficerChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.OfficerChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (FactionChatAPI.getPlayerRank(player).equals(FactionChat.LeaderRank)
                    || FactionChatAPI.getPlayerRank(player).equals(FactionChat.OfficerRank)) && ChatMode.getChatMode(myPlayer).equals("OFFICER")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }

    }

}
