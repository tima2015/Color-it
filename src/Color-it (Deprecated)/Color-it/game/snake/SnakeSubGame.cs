using System;
using Color_it.game.coloring;
using Microsoft.Xna.Framework.Graphics;
using TexturePackerLoader;

namespace Color_it.game.snake
{
    /// <summary>
    ///     Класс реализующий мини игру "Змейка"
    /// </summary>
    public class SnakeSubGame : SubGame
    {
        public SnakeSubGame(GameEventListener listener) :
            base(new SnakeModel(), new SnakeView(), new SnakeController(listener))
        {
        }

        /// <summary>
        ///     Реализация модели мини игры.
        /// </summary>
        private class SnakeModel : IModel
        {
        }

        /// <summary>
        ///     Реализация отображения мини игры.
        /// </summary>
        private class SnakeView : IView
        {
            public void Draw(SpriteBatch batch, SpriteRender render)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        ///     Реализация контроллера мини игры.
        /// </summary>
        private class SnakeController : IController
        {
            //при выполнии условия закрашивания вызывайте listener.Notify(new PaintingEvent(count, color))
            //для подробностей смотрите GameEventListenr.cs
            private GameEventListener _listener;

            public SnakeController(GameEventListener listener)
            {
                _listener = listener;
            }

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