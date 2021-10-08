using System;
using Color_it.game.coloring;
using Microsoft.Xna.Framework.Graphics;

namespace Color_it.game.lines
{
    /// <summary>
    ///     Класс реализующий мини игру "Линии"
    /// </summary>
    public class LinesSubGame : SubGame
    {
        public LinesSubGame(GameEventListener listener) :
            base(new LinesModel(), new LinesView(), new LinesController(listener))
        {
        }

        /// <summary>
        ///     Реализация модели мини игры.
        /// </summary>
        private class LinesModel : IModel
        {
        }

        /// <summary>
        ///     Реализация отображения мини игры.
        /// </summary>
        private class LinesView : IView
        {
            public void Draw(SpriteBatch batch)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        ///     Реализация контроллера мини игры.
        /// </summary>
        private class LinesController : IController
        {
            //при выполнии условия закрашивания вызывайте listener.Notify(new PaintingEvent(count, color))
            //для подробностей смотрите GameEventListenr.cs
            private GameEventListener _listener;

            public LinesController(GameEventListener listener)
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