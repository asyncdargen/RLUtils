declare class Keyboard {
    static KEY_NONE: number;
    static KEY_ESCAPE: number;
    static KEY_1: number;
    static KEY_2: number;
    static KEY_3: number;
    static KEY_4: number;
    static KEY_5: number;
    static KEY_6: number;
    static KEY_7: number;
    static KEY_8: number;
    static KEY_9: number;
    static KEY_0: number;
    static KEY_MINUS: number; /* - on main keyboard */
    static KEY_EQUALS: number;
    static KEY_BACK: number; /* backspace */
    static KEY_TAB: number;
    static KEY_Q: number;
    static KEY_W: number;
    static KEY_E: number;
    static KEY_R: number;
    static KEY_T: number;
    static KEY_Y: number;
    static KEY_U: number;
    static KEY_I: number;
    static KEY_O: number;
    static KEY_P: number;
    static KEY_LBRACKET: number;
    static KEY_RBRACKET: number;
    static KEY_RETURN: number; /* Enter on main keyboard */
    static KEY_LCONTROL: number;
    static KEY_A: number;
    static KEY_S: number;
    static KEY_D: number;
    static KEY_F: number;
    static KEY_G: number;
    static KEY_H: number;
    static KEY_J: number;
    static KEY_K: number;
    static KEY_L: number;
    static KEY_SEMICOLON: number;
    static KEY_APOSTROPHE: number;
    static KEY_GRAVE: number; /* accent grave */
    static KEY_LSHIFT: number;
    static KEY_BACKSLASH: number;
    static KEY_Z: number;
    static KEY_X: number;
    static KEY_C: number;
    static KEY_V: number;
    static KEY_B: number;
    static KEY_N: number;
    static KEY_M: number;
    static KEY_COMMA: number;
    static KEY_PERIOD: number; /* . on main keyboard */
    static KEY_SLASH: number; /* / on main keyboard */
    static KEY_RSHIFT: number;
    static KEY_MULTIPLY: number; /* * on numeric keypad */
    static KEY_LMENU: number; /* left Alt */
    static KEY_SPACE: number;
    static KEY_CAPITAL: number;
    static KEY_F1: number;
    static KEY_F2: number;
    static KEY_F3: number;
    static KEY_F4: number;
    static KEY_F5: number;
    static KEY_F6: number;
    static KEY_F7: number;
    static KEY_F8: number;
    static KEY_F9: number;
    static KEY_F10: number;
    static KEY_NUMLOCK: number;
    static KEY_SCROLL: number; /* Scroll Lock */
    static KEY_NUMPAD7: number;
    static KEY_NUMPAD8: number;
    static KEY_NUMPAD9: number;
    static KEY_SUBTRACT: number; /* - on numeric keypad */
    static KEY_NUMPAD4: number;
    static KEY_NUMPAD5: number;
    static KEY_NUMPAD6: number;
    static KEY_ADD: number; /* + on numeric keypad */
    static KEY_NUMPAD1: number;
    static KEY_NUMPAD2: number;
    static KEY_NUMPAD3: number;
    static KEY_NUMPAD0: number;
    static KEY_DECIMAL: number; /* . on numeric keypad */
    static KEY_F11: number;
    static KEY_F12: number;
    static KEY_F13: number; /*                     (NEC PC98) */
    static KEY_F14: number; /*                     (NEC PC98) */
    static KEY_F15: number; /*                     (NEC PC98) */
    static KEY_F16: number; /* Extended Function keys - (Mac) */
    static KEY_F17: number;
    static KEY_F18: number;
    static KEY_KANA: number; /* (Japanese keyboard)            */
    static KEY_F19: number; /* Extended Function keys - (Mac) */
    static KEY_CONVERT: number; /* (Japanese keyboard)            */
    static KEY_NOCONVERT: number; /* (Japanese keyboard)            */
    static KEY_YEN: number; /* (Japanese keyboard)            */
    static KEY_NUMPADEQUALS: number; /*=on numeric keypad (NEC PC98) */
    static KEY_CIRCUMFLEX: number; /* (Japanese keyboard)            */
    static KEY_AT: number; /*                     (NEC PC98) */
    static KEY_COLON: number; /*                     (NEC PC98) */
    static KEY_UNDERLINE: number; /*                     (NEC PC98) */
    static KEY_KANJI: number; /* (Japanese keyboard)            */
    static KEY_STOP: number; /*                     (NEC PC98) */
    static KEY_AX: number; /*                     (Japan AX) */
    static KEY_UNLABELED: number; /*                        (J3100) */
    static KEY_NUMPADENTER: number; /* Enter on numeric keypad */
    static KEY_RCONTROL: number;
    static KEY_SECTION: number; /* Section symbol (Mac) */
    static KEY_NUMPADCOMMA: number; /* , on numeric keypad (NEC PC98) */
    static KEY_DIVIDE: number; /* / on numeric keypad */
    static KEY_SYSRQ: number;
    static KEY_RMENU: number; /* right Alt */
    static KEY_FUNCTION: number; /* Function (Mac) */
    static KEY_PAUSE: number; /* Pause */
    static KEY_HOME : number; /* Home on arrow keypad */
    static KEY_UP: number; /* UpArrow on arrow keypad */
    static KEY_PRIOR: number; /* PgUp on arrow keypad */
    static KEY_LEFT: number; /* LeftArrow on arrow keypad */
    static KEY_RIGHT: number; /* RightArrow on arrow keypad */
    static KEY_END: number; /* End on arrow keypad */
    static KEY_DOWN: number; /* DownArrow on arrow keypad */
    static KEY_NEXT: number; /* PgDn on arrow keypad */
    static KEY_INSERT: number; /* Insert on arrow keypad */
    static KEY_DELETE: number; /* Delete on arrow keypad */
    static KEY_CLEAR: number; /* Clear key (Mac) */
    static KEY_LMETA: number; /* Left Windows/Option key */
    static KEY_RMETA: number; /* Right Windows/Option key */
    static KEY_APPS: number; /* AppMenu key */
    static KEY_POWER: number;
    static KEY_SLEEP: number;

    static isKeyDown(key: number): boolean;

    static getEventKey(): number;

    static getEventCharacter(): number;

    static getEventKeyState(): boolean;

    static getKeyName(key: number): string;

    static getKeyIndex(keyName: string): number;
}