package com.disquesea.disqueseaapi.domain.generator.document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;

import java.awt.*;
public abstract class TextFormatter{
    public final static Color PRIMARY_COLOR = new Color(56,56,56);

    public final static Color BACKGROUND_COLOR = new Color(238,237,237);

    public final static Color DETAIL_COLOR = new Color(0,86,235);

    public final static Font FONT_DETAIL_TITLE = setFont(FontFactory.HELVETICA,20, DETAIL_COLOR);

    public final static Font FONT_TITLE = setFont(FontFactory.HELVETICA, 20, PRIMARY_COLOR);

    public final static Font FONT_PARAGRAPH = setFont(FontFactory.HELVETICA, 12, PRIMARY_COLOR);

    public final static Font FONT_FOOTER = setFont(FontFactory.HELVETICA, 8, PRIMARY_COLOR);

    public static Font setFont(String name, Integer size, Color color){
        Font font = FontFactory.getFont(name);
        font.setSize(size);
        font.setColor(color);
        return font;
    }
}