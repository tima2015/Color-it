using System;
using Color_it.game.lines;
using Color_it.game.match_three;
using Color_it.game.snake;
using Microsoft.Xna.Framework.Graphics;

namespace Color_it.game.coloring
{
    /// <summary>
    ///     Класс основной игры.
    /// </summary>
    public class Game : ModelViewController
    {
        public Game(Type type) : base(new GameModel(), new GameView(), new GameController())
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
            ((GameModel) Model).SubG = subGame;
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
            
            private enum State
            {
                BEGIN, RUN, PAUSE, WIN, LOSE, END
            }

            private State _state = State.BEGIN;

            private SubGame _subGame;

            public SubGame SubG
            {
                get => _subGame;
                set => _subGame = value;
            }

            public SubGame SubGame1
            {
                get => _subGame;
                set => _subGame = value;
            }
        }

        /// <summary>
        ///     Реализация отображения основной игры.
        /// </summary>
        private class GameView : IView
        {
            public void Draw(SpriteBatch batch)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        ///     Реализация контроллера основной игры.
        /// </summary>
        private class GameController : IController
        {
            public void Update(float delta)
            {
                throw new NotImplementedException();
            }

            public void OnBegin()
            {
                throw new NotImplementedException();
            }

            public void OnResume()
            {
                throw new NotImplementedException();
            }

            public void OnPause()
            {
                throw new NotImplementedException();
            }

            public void OnEnd()
            {
                throw new NotImplementedException();
            }
        }
    }
}