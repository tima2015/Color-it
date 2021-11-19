using System;
using System.Windows.Controls;
using Color_it.game.lines;
using Color_it.game.match_three;
using Color_it.game.snake;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using TexturePackerLoader;

namespace Color_it.game.coloring
{
    /// <summary>
    ///     Класс основной игры.
    /// </summary>
    public class Game : ModelViewController
    {
        public Game(Type type, string levelJson) : base(new GameModel(levelJson), new GameView(), new GameController())
        {
            SubGame subGame;
            if (type == typeof(SnakeSubGame))
                subGame = new SnakeSubGame(GameEventListener);
            else if (type == typeof(MatchThreeSubGame))
                subGame = new MatchThreeSubGame(GameEventListener);
            else if (type == typeof(LinesSubGame))
                subGame = new LinesSubGame(GameEventListener);
            else
                throw new ArgumentException("type must be SnakeSubGame, MatchThreeSubGame or LinesSubGame!\n" +
                                            "Was taken " + type.Name);
            ((GameModel) Model).SubGame = subGame;
            ((GameController) Controller).Model = Model as GameModel;
        }

        /// <summary>
        ///     Слушатель событий игры.
        /// </summary>
        private GameEventListener GameEventListener { get; } = new();

        /// <summary>
        ///     Объект текущей мини игры.
        /// </summary>
        public SubGame SubGame { get; set; }

        /// <summary>
        ///     Реализация модели основной игры.
        /// </summary>
        private class GameModel : IModel
        {
            internal enum GameState
            {
                BEGIN, RUN, PAUSE, WIN, LOSE, END
            }

            public GameModel(String levelJson)
            {
                FillingMap = new FillingMap(levelJson);
            }

            public GameState State { get; set; } = GameState.BEGIN;

            public SubGame SubGame { get; set; }

            public FillingMap FillingMap { get; }
        }

        /// <summary>
        ///     Реализация отображения основной игры.
        /// </summary>
        private class GameView : IView
        {
            public void Draw(SpriteBatch batch, SpriteRender render)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        ///     Реализация контроллера основной игры.
        /// </summary>
        private class GameController : IController
        {
            internal GameModel Model { get; set; }


            public void Update(float delta)
            {
                Model.SubGame.Controller.Update(delta);
                foreach (var key in Keyboard.GetState().GetPressedKeys())
                {
                    var state = CheckKeyBoardInput(key);
                    if (state == Model.State) continue;
                    Model.State = state;
                    NotifyStateChanged(state);
                    break;
                }
            }

            private GameModel.GameState CheckKeyBoardInput(Keys key)
            {
                
                if (key == GameCore.Core.Settings.PauseAndBackKey)
                    switch (Model.State)
                    {
                        case GameModel.GameState.PAUSE:
                            return GameModel.GameState.RUN;
                        case GameModel.GameState.RUN:
                            return GameModel.GameState.PAUSE;
                        default:
                            return Model.State;
                    }

                if (key == GameCore.Core.Settings.EnterAndForwardKey)
                    if (Model.State is GameModel.GameState.LOSE or GameModel.GameState.WIN)
                        return GameModel.GameState.END;

                return Model.State;
            }

            private void NotifyStateChanged(GameModel.GameState state)
            {
                switch (state)
                {
                    case GameModel.GameState.BEGIN:
                        OnBegin();
                        break;
                    case GameModel.GameState.RUN:
                        OnResume();
                        break;
                    case GameModel.GameState.PAUSE:
                        OnPause();
                        break;
                    case GameModel.GameState.END:
                        OnEnd();
                        break;
                }
            }

            public void OnBegin()
            {
                Model.SubGame.Controller.OnBegin();
            }

            public void OnResume()
            {
                Model.SubGame.Controller.OnResume();
            }

            public void OnPause()
            {
                Model.SubGame.Controller.OnPause();
            }

            public void OnEnd()
            {
                Model.SubGame.Controller.OnEnd();
            }
        }
    }
}