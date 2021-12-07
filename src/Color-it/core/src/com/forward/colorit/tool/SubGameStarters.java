package com.forward.colorit.tool;

import com.forward.colorit.Core;
import com.forward.colorit.coloring.ColoringGameScreen;
import com.forward.colorit.coloring.ColoringLevelData;
import com.forward.colorit.lines.LinesSubGame;
import com.forward.colorit.snake.SnakeSubGame;
import com.forward.colorit.ui.action.StageReplaceAction;

public enum SubGameStarters {
    LINES{
        @Override
        public void run(ColoringLevelData data) {
            Core.core().getBackgroundStage().addAction(new StageReplaceAction(Core.core().getStageScreen(), new ColoringGameScreen(new LinesSubGame(), data), .75f));
        }
    }, MATCH_THREE {
        @Override
        public void run(ColoringLevelData data) {

        }
    }, SNAKE {
        @Override
        public void run(ColoringLevelData data) {
            Core.core().getBackgroundStage().addAction(new StageReplaceAction(Core.core().getStageScreen(), new ColoringGameScreen(new SnakeSubGame(), data), .75f));
        }
    };

    public abstract void run(ColoringLevelData data);
}
