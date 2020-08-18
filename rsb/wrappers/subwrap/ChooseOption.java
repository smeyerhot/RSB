package net.runelite.client.rsb.wrappers.subwrap;

import net.runelite.api.MenuEntry;
import net.runelite.client.rsb.methods.Menu;
import net.runelite.client.rsb.methods.MethodContext;

import java.awt.*;

public class ChooseOption extends Menu {

    public ChooseOption(final MethodContext ctx) {
        super(ctx);
    }

    public RSMenuNode getLastMenuNode() {
        return getMenuNodes()[lastIndex];
    }

    public RSMenuNode[] getMenuNodes() {
        MenuEntry[] entries = methods.client.getMenuEntries();
        RSMenuNode[] reversed = new RSMenuNode[entries.length];
        for (int i = entries.length - 1, x = 0; i >= 0; i--, x++) {
            int yOff = TOP_OF_MENU_BAR + (MENU_ENTRY_LENGTH * x); //Offset from top of the menu to the entry itself
            reversed[i] = new RSMenuNode(i, new Rectangle(calculateX(), yOff, calculateWidth(), calculateHeight()), entries[x].getOption(), entries[x].getTarget(), entries[x].getType(),
                    entries[x].getIdentifier(), entries[x].getParam0(), entries[x].getParam1());
        }
        return reversed;
    }

    public Rectangle getArea() {
        return new Rectangle(calculateX(), calculateY(), calculateWidth(), calculateHeight());
    }


    public void close() {
        if (isOpen()) {
            doAction("Cancel");
        }
    }

    public boolean select(String... options) {
        for (String option : options) {
            if (doAction(option))
                return true;
        }
        return false;
    }

    public Point getPosition() {
        return new Point(calculateX(), calculateY());
    }

    public String[] getOptions() {
        return getActions();
    }

    public boolean isOptionValid(String... options) {
        MenuEntry[] entries = getEntries();
        for (String option : options) {
            for (int i = 0; i < entries.length; i++) {
                if (entries[i].getOption().toLowerCase().contains(option)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getOptionCount() {
        return getActions().length;
    }

    public String getHoverText() {
        MenuEntry[] entries = getEntries();
        String item = (entries[0].getOption() + " " + entries[0].getTarget().replaceAll("<.*?>", ""));
        return (entries.length > 2) ? item + " / " + (entries.length - 1) + " more options" : item;
    }

    public RSMenuNode getHoverMenuNode() {
        return getMenuNodes()[0];
    }

}
