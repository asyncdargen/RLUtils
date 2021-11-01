package ru.starfarm.core.hooks;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TextFormatting;
import ru.starfarm.core.hook.asm.Hook;
import ru.starfarm.core.hook.asm.ReturnCondition;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class ScoreHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void renderScoreboard(GuiIngame ig, ScoreObjective objective, ScaledResolution scaledRes) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>() {
            public boolean apply(@Nullable Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            collection = list;
        }

        int i = ig.getFontRenderer().getStringWidth(objective.getDisplayName());

        for (Score score : collection) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + TextFormatting.RED + score.getScorePoints();
            i = Math.max(i, ig.getFontRenderer().getStringWidth(s));
        }

        int i1 = collection.size() * ig.getFontRenderer().FONT_HEIGHT;
        int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3;
        int k1 = 3;
        int l1 = scaledRes.getScaledWidth() - i - 3;
        int j = 0;

        for (Score score1 : collection) {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2 = TextFormatting.RED + "";
            int k = j1 - j * ig.getFontRenderer().FONT_HEIGHT;
            int l = scaledRes.getScaledWidth() - 3 + 2;
            Gui.drawRect(l1 - 2, k, l, k + ig.getFontRenderer().FONT_HEIGHT, 1342177280);
            ig.getFontRenderer().drawString(s1, l1, k, 553648127);
            ig.getFontRenderer().drawString(s2, l - ig.getFontRenderer().getStringWidth(s2), k, 553648127);

            if (j == collection.size()) {
                String s3 = objective.getDisplayName();
                Gui.drawRect(l1 - 2, k - ig.getFontRenderer().FONT_HEIGHT - 1, l, k - 1, 1610612736);
                Gui.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                ig.getFontRenderer().drawString(s3, l1 + i / 2 - ig.getFontRenderer().getStringWidth(s3) / 2, k - ig.getFontRenderer().FONT_HEIGHT, 553648127);
            }
        }
    }
}
