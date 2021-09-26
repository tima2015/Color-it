using System;
using Microsoft.Xna.Framework.Graphics;

namespace Color_it.game.coloring
{
    /// <summary>
    ///     Класс основной игры.
    /// </summary>
    public class Game : ModelViewController
    {
        public Game() : base(new GameModel(), new GameView(), new GameController())
        {
        }

        /// <summary>
        ///     Слушатель событий игры.
        /// </summary>
        public GameEventListener GameEventListener { get; } = new();

        /// <summary>
        ///     Объект текущей мини игры.
        /// </summary>
        public SubGame SubGame { get; set; }

        /// <summary>
        ///     Реализация модели основной игры.
        /// </summary>
        private class GameModel : IModel
        {
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
        }
    }
}