package com.vltir;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class KiwiSettings {
    private static final String KIWI = "kiwi";

    @Rule(categories = {KIWI, CREATIVE, COMMAND})
    public static boolean tickFreezeToggle = false;

    @Rule(categories = {KIWI, CREATIVE, COMMAND})
    public static boolean tickSprintEasyInterrupt = false;

    @Rule(categories = {KIWI, CREATIVE, COMMAND})
    public static boolean tickWarpAlias = false;

}
